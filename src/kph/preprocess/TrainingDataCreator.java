package kph.preprocess;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TrainingDataCreator
{
	private static String FIELDS = "avg_assists,avg_bonus,avg_bps,avg_clean_sheets,avg_creativity,avg_goals_conceded,avg_goals_scored,avg_ict_index,avg_influence,avg_minutes,avg_opponent_team,avg_own_goals,avg_penalties_missed,avg_penalties_saved,avg_red_cards,avg_saves,avg_threat,avg_total_points,avg_value,was_home,avg_yellow_cards,position,actual_points";

	public static void main(String[] args)
	{
		createFile(2);
		createFile(3);
		createFile(4);
		createFile(5);
		createFile(6);
	}

	public static void createFile(int numGames)
	{
		try
		{
			File dir = new File("C:\\MyGithub\\FPL-Machine-Learning\\data\\raw");
			FileWriter allFile = new FileWriter("C:\\MyGithub\\FPL-Machine-Learning\\data\\training\\ma" + numGames + "_all.csv");
			FileWriter trainingFile = new FileWriter(
					"C:\\MyGithub\\FPL-Machine-Learning\\data\\training\\ma" + numGames + "_train.csv");
			FileWriter testFile = new FileWriter(
					"C:\\MyGithub\\FPL-Machine-Learning\\data\\training\\ma" + numGames + "_test.csv");
			File[] directoryListing = dir.listFiles();
			allFile.write(FIELDS + "\n");
			trainingFile.write(FIELDS + "\n");
			testFile.write(FIELDS + "\n");
			for (File child : directoryListing)
			{
				Scanner s = new Scanner(child);
				ArrayList<String[]> matches = new ArrayList<>();
				while (s.hasNextLine())
				{
					String[] line = s.nextLine().split(",");
					matches.add(line);
				}
				for (int i = numGames + 1; i < matches.size(); i++)
				{
					if (Math.random() < .7) 
					{
						for (int j = 0; j < matches.get(0).length; j++)
						{
							double d = 0;
							if (j == 19)
							{
								if (Boolean.parseBoolean(matches.get(i)[j]))
								{
									d = 1;
								}
								else
								{
									d = 0;
								}
							}
							else
							{
								for (int k = 1; k <= numGames; k++)
								{
									d += Double.parseDouble(matches.get(i - k)[j]);
								}
								d /= numGames;
							}
							allFile.write(d + ",");
							trainingFile.write(d + ",");
						}
						allFile.write(Integer.parseInt(matches.get(i)[17]) * 1.0 + "\n");
						trainingFile.write(Integer.parseInt(matches.get(i)[17]) * 1.0 + "\n");
					}
					else
					{
						for (int j = 0; j < matches.get(0).length; j++)
						{
							double d = 0;
							if (j == 19)
							{
								if (Boolean.parseBoolean(matches.get(i)[j]))
								{
									d = 1;
								}
								else
								{
									d = 0;
								}
							}
							else
							{
								for (int k = 1; k <= numGames; k++)
								{
									d += Double.parseDouble(matches.get(i - k)[j]);
								}
								d /= numGames;
							}
							allFile.write(d + ",");
							testFile.write(d + ",");
						}		
						allFile.write(Integer.parseInt(matches.get(i)[17]) * 1.0 + "\n");
						testFile.write(Integer.parseInt(matches.get(i)[17]) * 1.0 + "\n");
					}
				}
				s.close();
			}
			allFile.close();
			trainingFile.close();
			testFile.close();
		} catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
