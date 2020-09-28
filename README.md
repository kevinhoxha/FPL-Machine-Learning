# Table of Contents
[What is Fantasy Premier League](https://github.com/kevinhoxha/FPL-Machine-Learning#what-is-fantasy-premier-league)  
[Project's Objective](https://github.com/kevinhoxha/FPL-Machine-Learning#projects-objective)  
[Training Data](https://github.com/kevinhoxha/FPL-Machine-Learning#training-data)  
[The Model](https://github.com/kevinhoxha/FPL-Machine-Learning#the-model)  
[Conclusions](https://github.com/kevinhoxha/FPL-Machine-Learning#conclusions)  
# What is Fantasy Premier League?
Fantasy Premier League (FPL) is an online game in which participants assemble an imaginary team of real-life Premier League players and score points based on their statistical performance in matches. In FPL, each participant has a budget of £100m to build a team of 15 players. Players expected to score more points generally cost more than players expected to score less points. Each week, 11 players are started and 4 must be benched.
# Project's Objective
The goal of this project is to predict the performance of Premier League players based on their past performances. The model calculates the expected fantasy points scored for a player based on average goals, assists, and other metrics in the last 6 games.
# Training Data
The data used to train our model comes from [vaastav](https://github.com/vaastav)'s [Fantasy-Premier-League](https://github.com/vaastav/Fantasy-Premier-League) repository. This repository includes data for every player and every gameweek. The statistics included in this repository includes:

```assists``` - The number of times a player makes the final pass leading to a goal. Earns a player 3 points in FPL.  
```bonus``` - Number of bonus points given to a player. Allocated after a match based on BPS.  
```bps``` - Bonus Points System. Utilizes a range of statistics to capture actions on the pitch and create a performance score for each player, which is used to calculate bonus points.  
```clean_sheets``` - Awarded if the player's team does not concede a goal. Defenders and goalkeepers earn 4 points for a clean sheet, while midfielders earn 1 point.  
```creativity``` - Assesses player performance in terms of producing goalscoring opportunities for others. It can be used as a guide to identify the players most likely to supply assists.  
```goals _conceded``` - Number of goals conceded. Defenders and goalkeepers lose points as goals conceded increases.  
```goals_scored``` - Number of goals scored. Attackers earn 4 points per goal, midfielders earn 5 points per goal, and defenders earn 6 points per goal.  
```ict_index``` - Statistical index which uses match event data to generate a single score for three key areas – Influence, Creativity and Threat.  
```influence``` - Evaluates the degree to which that player has made an impact on a single match or throughout the season. It takes into account events and actions that could directly or indirectly effect the outcome of the fixture.  
```minutes``` - The number of minutes a player plays. A player earns 1 point for playing 1-60 minutes and 2 points for playing 60+ minutes.  
```opponent_team``` - A number which represents the team that a player is playing against.  
```own_goals``` - The number of own goals a player scored in a match. A player loses 2 points for each own goal.  
```penalties_missed``` - The number of penalties a player misses in a match. A player loses 2 points for each missed penalty.  
```penalties_saved``` - The number of penalties saved by a goalkeeper. A goalkeeper earns 5 points for each saved penalty.   
```red_cards``` - The number of red cards a player receives. A player loses 3 points if they are shown a red card.  
```saves``` - The number of saves a goalkeeper makes. Goalkeepers earn points as saves increases.  
```threat``` - A value that examines a player's threat on goal. It gauges individuals most likely to score goals.  
```total_points``` - The total number of points scored by a player.  
```value``` - The price of a player in FPL.  
```was_home``` - Whether a player's match was home or away.  
```yellow_cards``` - The number of yellow cards received by a player. Players lose 1 point for receiving a yellow card.  
```position``` - The position of a player on the field (goalkeeper, defender, midfielder, attacker), which determines how many points a player earns for specific actions.

- Some other factors, such as kickoff time and FPL transfers were also included in the repository. However, I removed these from my analysis since they do not have any correlation with match performance.
- I formatted all of the player data by combining every player's matches throughout all seasons they have been in the Premier League into one file. 
- I calculated 2, 3, 4, 5, and 6 game moving averages for all these statistics for each player, as well as the number of points they scored and minutes they played in their next match.
- Used moving averages to get a mathematical representation of a player's form that avoids week-to-week spikes and drops in performance.
- If the moving average for minutes was 0 minutes, I removed this data point from the data, as the player did not play, and therefore a good prediction could not be made.

# The Model
- layers, optimizer, loss function
- K-fold validation
# Conclusions