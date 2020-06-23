package kph.preprocess;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TrainingDataCreator
{
	private static String FIELDS = "avg_assists,avg_bonus,avg_clean_sheets,avg_goals_conceded,avg_goals_scored,avg_minutes,avg_opponent_team,avg_own_goals,avg_penalties_missed,avg_penalties_saved,avg_red_cards,avg_round,avg_saves,avg_total_points,avg_home,avg_yellow_cards,actual_points";

	public static void main(String[] args)
	{
		createFile(3);
		createFile(4);
		createFile(5);
	}

	public static void createFile(int numGames)
	{
		try
		{
			File dir = new File("C:\\MyGithub\\FPL-Machine-Learning\\data\\raw");
			FileWriter playerFile = new FileWriter(
					"C:\\MyGithub\\FPL-Machine-Learning\\data\\training\\training" + numGames + ".csv");
			FileWriter testFile = new FileWriter(
					"C:\\MyGithub\\FPL-Machine-Learning\\data\\test\\test" + numGames + ".csv");
			File[] directoryListing = dir.listFiles();
			playerFile.write(FIELDS + "\n");
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
							if (j == 14)
							{
								for (int k = 1; k <= numGames; k++)
								{
									if (Boolean.parseBoolean(matches.get(i - k)[j]))
									{
										d += 1;
									}
								}
								d /= numGames;
							} else
							{
								for (int k = 1; k <= numGames; k++)
								{
									d += Double.parseDouble(matches.get(i - k)[j]);
								}
								d /= numGames;
							}
							playerFile.write(d + ",");
						}
						playerFile.write(Integer.parseInt(matches.get(i)[13]) * 1.0 + "\n");
					} else
					{
						for (int j = 0; j < matches.get(0).length; j++)
						{
							double d = 0;
							if (j == 14)
							{
								for (int k = 1; k <= numGames; k++)
								{
									if (Boolean.parseBoolean(matches.get(i - k)[j]))
									{
										d += 1;
									}
								}
								d /= numGames;
							} else
							{
								for (int k = 1; k <= numGames; k++)
								{
									d += Double.parseDouble(matches.get(i - k)[j]);
								}
								d /= numGames;
							}
							testFile.write(d + ",");
						}
						testFile.write(Integer.parseInt(matches.get(i)[13]) * 1.0 + "\n");
					}
				}
				s.close();
			}
			playerFile.close();
			testFile.close();
		} catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
