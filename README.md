# Table of Contents
1.  [What is Fantasy Premier League?](https://github.com/kevinhoxha/FPL-Machine-Learning#what-is-fantasy-premier-league)  
2.  [Project's Objective](https://github.com/kevinhoxha/FPL-Machine-Learning#projects-objective)  
3.  [Training Data](https://github.com/kevinhoxha/FPL-Machine-Learning#training-data)  
4.  [The Model](https://github.com/kevinhoxha/FPL-Machine-Learning#the-model)  
  -[Feature Normalization](https://github.com/kevinhoxha/FPL-Machine-Learning#feature-normalization)  
  -[Players' Positions](https://github.com/kevinhoxha/FPL-Machine-Learning#players-positions)  
  -[The Neural Network](https://github.com/kevinhoxha/FPL-Machine-Learning#the-neural-network)  
  -[K-Fold Validation](https://github.com/kevinhoxha/FPL-Machine-Learning#k-fold-validation)  
  -[Further Model Tuning](https://github.com/kevinhoxha/FPL-Machine-Learning#further-model-tuning)  
5.  [Conclusions](https://github.com/kevinhoxha/FPL-Machine-Learning#conclusions)  
# What is Fantasy Premier League?
Fantasy Premier League (FPL) is an online game in which participants assemble an imaginary team of real-life Premier League players and score points based on their statistical performance in matches. In FPL, each participant has a budget of £100m to build a team of 15 players. Players expected to score more points generally cost more than players expected to score less points. Each week, 11 players are started and 4 must be benched.
# Project's Objective
The goal of this project is to predict the performance of Premier League players based on their past performances. The model calculates the expected fantasy points scored for a player based on average goals, assists, and other metrics in the last 4 games.
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

- Some other features, such as kickoff time and FPL transfers were also included in the repository. However, I removed these from my analysis since they do not have any correlation with match performance.
- I combined multiple seasons into a single data set so that every player's data is a long, continuous time series. That's reasonable because the  players's performance from one season to another is usually a smooth transition. 
- I calculated 2, 3, and 4-game moving averages for all these statistics for each player, as well as the number of points they scored and minutes they played in their next match. Using moving averages to get a mathematical representation of a player's form avoids week-to-week spikes and drops in performance.
- I removed the records with ```avg_minutes=0``` because they indicate benched players, and therefore have no significance.

# The Model
### Feature Normalization  
The values of the 18 different features used in this model span very different ranges. For example, ```avg_minutes``` ranges from 0 to 90, ```avg_total_points``` and several other features take positive integer values typically less than 20, while ```avg_creativity``` and ```avg_infulence``` range from 0 to 150. Thus, to make the learning easier, I decided to normalize most features by using the feature-wise Z-scores of individual values instead of the values themselves. That's done in ```normalize_feature()``` method. For the ```avg_minutes``` played, however, I'm just normalizing them as a percentage of a full game of 90 minutes.

### Players' Positions  
Midfielders and attacking players earn most of their points for scoring goals, whereas goalies and defenders earn the points by preventing goals. Thus, I decided to separate all training data by player positions and effectively build two distinct models.

### The Neural Network  
Given that each training data set consists of about 16,000 records, I'm using a network with only 2 hidden layers to avoid overfitting. This model is designed to predict the total points a player will get in the next game. As such, the network ends with a single Dense layer and no activation, i.e. a linear layer, because I want the prediction values to be continuous in any range. If I had chosen a *sigmoid* activation then the predictions would be in the interval [0,1]. I'm using the *mse* loss function — *mean squared error*, the squared difference between the predicted value and target — as it's common with regression models like this. Additionally, I'm also monitoring the MAE (*mean absolute error*) metric which represents the mean of absolute differences between prediction and target values during training.

### K-Fold Validation  
The number of epochs is a parameter of the model and, in order to find the optimal value for it, I ran the training for all epoch values in [1, 500] interval and monitored the resulting MAE values. To improve this further, I used the K-fold validation method with k=4. Basically, for any given epoch number, the training data gets split into 4 subsets. Each subset is used as a validation set while the other 3 are used for training. The final validation score for that given epoch is then the average scores of all validation runs. Finally, I plotted the validation MAEs for all epochs. I used a smoothing function to remove some of the volatility in MAE.  
![alt text](https://i.imgur.com/Je7tTkz.png)
![alt text](https://i.imgur.com/v5gomTE.png)
### Further Model Tuning  
Following the same method above, at some pint in the future, I will also tune some other model parameters like the number of hidden layers, batch size, and more granular models for each player position in the field.

# Conclusions
