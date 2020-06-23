package kph.preprocess;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TrainingDataCreator
{
	private static String FIELDS = "avg_assists,avg_bonus,avg_clean_sheets,avg_goals_conceded,avg_goals_scored,avg_minutes,avg_opponent_team,avg_own_goals,avg_penalties_missed,avg_penalties_saved,avg_red_cards,avg_round,avg_saves,avg_total_points,avg_yellow_cards,actual_points";
	
	public static void main(String[] args)
	{
		try
		{
			File dir = new File("C:\\MyGithub\\FPL-Machine-Learning\\data\\raw");
			FileWriter playerFile3 = new FileWriter("C:\\MyGithub\\FPL-Machine-Learning\\data\\training\\training3.csv");
			FileWriter playerFile4 = new FileWriter("C:\\MyGithub\\FPL-Machine-Learning\\data\\training\\training4.csv");
			FileWriter playerFile5 = new FileWriter("C:\\MyGithub\\FPL-Machine-Learning\\data\\training\\training5.csv");
			File[] directoryListing = dir.listFiles();
			playerFile3.write(FIELDS + "\n");
			playerFile4.write(FIELDS + "\n");
			playerFile5.write(FIELDS + "\n");
			for (File child : directoryListing)
			{
				Scanner s = new Scanner(child);
				ArrayList<String[]> matches = new ArrayList<>();
				while (s.hasNextLine())
				{
					String[] line = s.nextLine().split(",");
					matches.add(line);	
				}
				for (int i = 4; i < matches.size(); i++)
				{
					for (int j = 0; j < matches.get(0).length; j++)
					{
						if (j == 14)
						{
							continue;
						}
						double d = (Double.parseDouble(matches.get(i-1)[j]) + Double.parseDouble(matches.get(i-2)[j]) + Double.parseDouble(matches.get(i-3)[j]))/3.0;
						playerFile3.write(d + ",");
					}
					playerFile3.write(Integer.parseInt(matches.get(i)[13]) + "\n");
				}
				for (int i = 5; i < matches.size(); i++)
				{
					for (int j = 0; j < matches.get(0).length; j++)
					{
						if (j == 14)
						{
							continue;
						}
						double d = (Double.parseDouble(matches.get(i-1)[j]) + Double.parseDouble(matches.get(i-2)[j]) + Double.parseDouble(matches.get(i-3)[j]) + Double.parseDouble(matches.get(i-4)[j]))/4.0;
						playerFile4.write(d + ",");
					}
					playerFile4.write(Integer.parseInt(matches.get(i)[13]) + "\n");
				}
				for (int i = 6; i < matches.size(); i++)
				{
					for (int j = 0; j < matches.get(0).length; j++)
					{
						if (j == 14)
						{
							continue;
						}
						double d = (Double.parseDouble(matches.get(i-1)[j]) + Double.parseDouble(matches.get(i-2)[j]) + Double.parseDouble(matches.get(i-3)[j]) + Double.parseDouble(matches.get(i-4)[j]) + Double.parseDouble(matches.get(i-5)[j]))/5.0;
						playerFile5.write(d + ",");
					}
					playerFile5.write(Integer.parseInt(matches.get(i)[13]) + "\n");
				}
				s.close();
			}
			playerFile3.close();
			playerFile4.close();
			playerFile5.close();
		} catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
