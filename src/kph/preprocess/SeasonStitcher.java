package kph.preprocess;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class SeasonStitcher
{
	private static String FIELDS = "assists,bonus,clean_sheets,goals_conceded,goals_scored,minutes,opponent_team,own_goals,penalties_missed,penalties_saved,red_cards,round,saves,total_points,was_home,yellow_cards";
	
	public static void main(String[] args)
	{
		try
		{
			Scanner s = new Scanner(new File("C:\\MyGithub\\Fantasy-Premier-League\\data\\2019-20\\player_idlist.csv"));
			while (s.hasNextLine())
			{
				String[] name = s.nextLine().split(",");
				if (name[0].equals("first_name"))
				{
					continue;
				}
				FileWriter playerFile = new FileWriter("C:\\MyGithub\\FPL-Machine-Learning\\data\\raw\\" + name[0] + "_" + name[1] + ".csv");
				playerFile.write(FIELDS + "\n");
				for (int i = 0; i < 4; i++)
				{
					try
					{
						Scanner f;
						if (i == 0 || i == 1)
						{
							f = new Scanner(new File("C:\\MyGithub\\Fantasy-Premier-League\\data\\20" + (16 + i) + "-" + (17 + i) + "\\players\\" + name[0] + "_" + name[1] + "\\gw.csv"));
						}
						else if (i == 2)
						{
							Scanner s2 = new Scanner(new File("C:\\MyGithub\\Fantasy-Premier-League\\data\\2018-19\\player_idlist.csv"));
							int num = 0;
							while (s2.hasNextLine())
							{
								String[] line = s2.nextLine().split(",");
								if (line[0].equals(name[0]))
								{
									num = Integer.parseInt(line[2]);
								}
							}
							s2.close();
							f = new Scanner(new File("C:\\MyGithub\\Fantasy-Premier-League\\data\\20" + (16 + i) + "-" + (17 + i) + "\\players\\" + name[0] + "_" + name[1] + "_" + num + "\\gw.csv"));
						}
						else
						{
							f = new Scanner(new File("C:\\MyGithub\\Fantasy-Premier-League\\data\\20" + (16 + i) + "-" + (17 + i) + "\\players\\" + name[0] + "_" + name[1] + "_" + name[2] + "\\gw.csv"));
						}
						while (f.hasNextLine())
						{
							String str1 = f.nextLine();
							if (str1.equals(""))
							{
								continue;
							}
							String[] str = str1.split(",");
							if (str[0].equals("assists"))
							{
								continue;
							}
							if (i != 3)
							{
								playerFile.write(str[0] + "," + str[4] + "," + str[6] + "," + str[17] + "," + str[18] + "," + str[27] + "," + str[30] + "," + str[31] + "," + str[33] + "," + str[34] + "," + str[36] + "," + str[37] + "," + str[38] + "," + str[46] + "," + str[51] + "," + str[53]);
							}
							else
							{
								playerFile.write(str[0] + "," + str[1] + "," + str[3] + "," + str[7] + "," + str[8] + "," + str[12] + "," + str[13] + "," + str[14] + "," + str[15] + "," + str[16] + "," + str[17] + "," + str[18] + "," + str[19] + "," + str[24] + "," + str[29] + "," + str[30]);	
							}
							if (f.hasNextLine())
							{
								playerFile.write("\n");
							}
						}
						f.close();
					}
					catch (Exception e)
					{
						continue;
					}
				}
				playerFile.close();
			}
			s.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}

/*
assists, bonus, clean_sheets, goals_conceded, goals_scored, minutes, opponent_team, own_goals, penalties_missed, penalties_saved, red_cards, round, saves, total_points, was_home, yellow_cards
2019-20: 0, 1, 3, 7, 8, 12, 13, 14, 15, 16, 17, 18, 19, 24, 29, 30
2018-19: 0, 4, 6, 17, 18, 27, 30, 31, 33, 34, 36, 37, 38, 46, 51, 53
2017-18: 0, 4, 6, 17, 18, 27, 30, 31, 33, 34, 36, 37, 38, 46, 51, 53
2016-17: 0, 4, 6, 17, 18, 27, 30, 31, 33, 34, 36, 37, 38, 46, 51, 53
*/