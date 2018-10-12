# Results of the vsoc-2018 project

## General Description
The aim of that project is to create controllers for autonomous 
soccer clients using unsuperwised learning. The following technologies
where applied.

* Neural Networks
* Genetic Algorithm

The training environment is a simulator based on the 
[sserver](https://www.robocup.org/leagues/24) of the 
robocup 2d soccer simulation league. In our case it uses only 3 players
per team and a reduced set of flags. 

The learning process (not only of players but also of the author) is
devided into iterations where the results of each iteration are 
analysed and documented. The results of that analyse should lead to 
a better configuration or even new principles in the next iteration. 

External Links

* Simulator: [vsoc-2007](https://github.com/wwagner4/vsoc/tree/master1/vsoc-2007/vsoc-core)
* Sourcecode: [vsoc-ga-2018](https://github.com/wwagner4/vsoc/tree/master1/vsoc-ga-2018) 
* Neural Nets: [deeplearning4J](https://deeplearning4j.org/)
* Genetic Algorithm: Is part of vsoc-ga-2018

## Glosar
Some Expressions I use in the following text that might not be 
immediately understandable.

* **Stepwise fitness function**: A fitness function not only 
focusing on the final goal of game. 
E.g. Train the players to kick the ball before they are 
trained to score goals (which is the finall goal by the way)

## Iteration 4
### Principles
* Players are trained independendly.
* Improved stepwise optimizing fitness function. 

### Fitntness Function 'data02A03'

 + math.min(100, data.kicksMax) 
 + math.min(2000, data.kicksMean) * 10 
 + math.min(5000, data.kicksMin * 100) 
 - math.min(1000, data.kickOutMean) 
 + math.min(10000, data.otherGoalsMax * 500) 
 + math.min(80000, data.otherGoalsMin * 1000) 
 - math.min(10000, data.ownGoalsMean * 500)
 
Reformated for better understanding
 
#### General Description
Has rewards for kicking and goal scoring. Gives penalty for 
kick out and own goals. 

All rewards and penalties are given for a team of three players
during the test of one generation. During this test a team
played several matches. All values are the
average scored during theses matches. E.g. if team a played 3 matches
and the best kicker of that team kicked the ball 5 times in the
first match, 6 times in the second match and 7 times in
the third match, the kicksMax value for that team would be 6.
 
#### Kicking
The reward for kicking is split into tree elements. The number
of kicks of the best kicking player (kicksMax), the average
number of kicks for of all players (kicksMean) and the number of
kicks of the worst player (kicksMin). 
 
In order to avoid the scenario of having one player that optimizes
his kicking skills while the other two player are not trained 
at all these values are weighted. A higher reward is given to kicksMean (10 times higher) and kicksMin (100 times higher).
To avoid gettig teams that optimize their kicking skills but
do not focus on scoring goals, all three values are capped. Again the
maximum value for kicksMin is the highest to force all three
players to develop their kicking skills.
 
Penalties are given for kicking out the ball. This should train the players to keep the ball inside the feeld.
 
#### Goal Scoring
To get a reward only the goals of the best scoring player 
(otherGoalsMax) and those of the worst scoring player 
(oterGoalsMin) are taken in account. Goals are
in general weighted much heigher than kicks to favour
the main purpose of the game. To get teams where all players
score goals, goals of the best player are weighted less than 
those of the worst player. 

The values for scoring are capped (why did I do that ???).

Penalties are given for scoring own goals.

#### Expected Values
To get an overview of the expected results the max values of
the elements of the fitness function are calculated 

|Element       |+/-|max Actions |max Value |
|--------------|---|-----------:|---------:|
|kicksMax      | + |100         |100       |
|kicksMean     | + |200         |2.000     |
|kicksMin      | + |50          |5.000     |
|kickOutMean   | - |1.000       |1.000     |
|otherGoalsMax | + |20          |10.000    |
|otherGoalsMin | + |80          |80.000    |
|ownGoalsMin   | - |20          |10.000    |

(why did I not make these calculations before the testrun ???)

### Summary of Training Goals

* All three players learn how to kick
* All three players keep the ball inside the field
* All three players score goals.
* All three players do not score own goals
  
### Description of the Testrun
Seven independend Populations where tested. They had all the
exact same parameters. Only the start values of the neural nets where
randomly choosen.

The names of these populations are:

 bob001, bob002, bob003, bob004, 
 work001, work002, work003, wok004, work005, work006
 
bob and work are the names of the used workstations.

### Results
In the following we will analyse if, and in what degree, the postulated
training goals where attained.

#### Categories
The results of the 7 populations can be fit into three categories.

* One Kicker [OK]
* All Kickers [AL]
* One Goalgetter [OG]

**OG** teams include one player hitting the ball as often as he can. 
The hitting player moves slowly towards the ball and hits it very soft
so that he can hit it again and again. The other players usually 
keep their initial behaviour. NOT WHAT WE WANTED.

 work001, work006
TODO Diagram that shows that behaviour


In **AL** Teams all players are are kicking the ball. They are not 
very focused on that aim. TODO Continue 
TODO Diagram that shows that behaviour
 
TODO Description of OG + Diagram that sows that behaviour 
 
 
 
# Iteration 3
## Aims and Principles
Goal of that iteration was to find a better fitness functions 
to avoid the pitfalls from the previous iteration. 
Mainly to avoid the breeding of teams that have only one 
evolving player. 
To achieve that goal the max value for the best kicking 
palyer was reduced.

## Score for all Populations
![alt text](res/it3/trainGaB02.png "Logo Title Text 1")



# Iteration 2

# Iteration 1 



