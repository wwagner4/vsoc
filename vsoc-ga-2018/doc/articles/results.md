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

## Iteration 5
### Principles

* Same as Iteration 4
* Improve Fitness-Function of Iteration 4

### Fitness Function 'data02A04'

```
 + math.min(500, data.kicksMax)
 + math.min(50000, data.kicksMin * 100)
 - data.kickOutMean
 + math.min(5000, data.otherGoalsMax * 500)
 + data.otherGoalsMin * 1000
 - data.ownGoalsMean * 500
```

#### Expected Values

To get an overview of the expected results the max values of
the elements of the fitness function are calculated.

| Element        | +/- | reinforcement | max Actions  | max Value  |
| -------------- | --- | ------------: | -----------: | ---------: |
| kicksMax       |  +  | 1             | 500          | 500        |
| kicksMin       |  +  | 100           | 500          | 50.000     |
| kickOutMean    |  -  | 1             | -            | -          |
| otherGoalsMax  |  +  | 500           | 10           | 5.000      |
| otherGoalsMin  |  +  | 1000          | -            | -          |
| ownGoalsMin    |  -  | 500           | -            | -          |

table 051

#### Testruns
There where 10 independend testruns each with configured with the exact same
configuration. 

Names of the testruns: 'work001', 'work002', 'work003', 'work004', 'work005', 'work006', 
'bob001', 'bob002', 'bob003', 'bob004'. 

Looking at the timlines of the fitness value and some other parameters show that the 
testruns can be assigned to two categories.

* Kickers: All players kick the ball. 
* Goalgetters: One player of the team scores goals. The other players do not contribue to the fitness value.

| Category    | Testrun                                                       |
| ----------- | ------------------------------------------------------------- |
| Kickers     | 'work001', 'work002', 'work004', 'bob001'                     |
| Goalgetters | 'work003', 'work005', 'work006', 'bob002', 'bob003', 'bob004' |

B04AlltrainGaB04kicksAndGoals
![categories](https://raw.githubusercontent.com/wwagner4/vsoc/master1/vsoc-ga-2018/doc/articles/resources/results/iter5/B04AlltrainGaB04kicksAndGoals.png)

#### Kickers
Testruns in the kicker category improve the capabillity of all players to kick the ball.
They do not improve any of the other capabillities as e.g. scoring goals.
That means they are optimizing the number of kicks of the worst player, and by that the
number of kicks for all players.

The following diagram shows that the score (fitness value) is only created from 'kicksMin'.

![kickers kicks min](https://raw.githubusercontent.com/wwagner4/vsoc/master1/vsoc-ga-2018/doc/articles/resources/results/iter5/B04KickertrainGaB04kicksMinScore.png)

The max score value for kicksMin is 50.000 (see table 051), which is not yet reached for any of the testruns.

Video: [work001 &#9654;](https://youtu.be/_M_wEQ-NmK8)

#### Goalgetters
Testruns in that categoryimprove the capabillity of one player to score goals. The other players
of a team do not contribue anything to the fitness.

The following diagram shows that the score is only created from goalsMax.

![kickers kicks min](https://raw.githubusercontent.com/wwagner4/vsoc/master1/vsoc-ga-2018/doc/articles/resources/results/iter5/B04GoalGettertrainGaB04goalsToScore.png)

The max score value for goalsMax is 5000 (see table 051), which is almost reached by some testruns ('bob004', 'work005', ...).
Though the max value was reached none of the simulations started to optimize another parameter.
They seem to be stuck in a local maximum.

Video: [bob004 &#9654;](https://youtu.be/lHIdfN83Hto)

## Iteration 4
### Principles

* Players are trained independendly.
* Improved stepwise optimizing fitness function. 

### Fitntness Function 'data02A03'

```
 + math.min(100, data.kicksMax) 
 + math.min(2000, data.kicksMean) * 10 
 + math.min(5000, data.kicksMin * 100) 
 - math.min(1000, data.kickOutMean) 
 + math.min(10000, data.otherGoalsMax * 500) 
 + math.min(80000, data.otherGoalsMin * 1000) 
 - math.min(10000, data.ownGoalsMean * 500)
```
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

The values for scoring are capped (why did I do that ?).

Penalties are given for scoring own goals.

#### Expected Values

To get an overview of the expected results the max values of
the elements of the fitness function are calculated.

| Element        | +/- | max Actions  | max Value  |
| -------------- | --- | -----------: | ---------: |
| kicksMax       |  +  | 100          | 100        |
| kicksMean      |  +  | 200          | 2.000      |
| kicksMin       |  +  | 50           | 5.000      |
| kickOutMean    |  -  | 1.000        | 1.000      |
| otherGoalsMax  |  +  | 20           | 10.000     |
| otherGoalsMin  |  +  | 80           | 80.000     |
| ownGoalsMin    |  -  | 20           | 10.000     |

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

```
 bob001, bob002, bob003, bob004, 
 work001, work002, work003, wok004, work005, work006
```

bob and work are the names of the used workstations.

### Results
In the following we will analyse if, and in what degree, the postulated
training goals where attained.

#### Categories
The following diagram shows that the populations can be fit into three categories

![categories](https://raw.githubusercontent.com/wwagner4/vsoc/master1/vsoc-ga-2018/doc/articles/resources/results/iter4/cattrainGaB03.png)

* One Kicker [OK]
* All Kickers [AL]
* One Goalgetter [OG]

The **OK** teams include one player hitting the ball as often as he can.
The hitting player moves slowly towards the ball and hits it very soft
so that he can hit it again and again. The other players. NOT WHAT WE WANTED.
![categories](https://raw.githubusercontent.com/wwagner4/vsoc/master1/vsoc-ga-2018/doc/articles/resources/results/iter4/OKtrainGaB03.png)

[work001 &#9654;](https://www.youtube.com/embed/wRZgbNliXYo)

In **AL** Teams all players are are kicking the ball. They are not
very focused on that aim but at least all players are kicking.
SOMHOW WHAT WE WANTED TO GET.
![categories](https://raw.githubusercontent.com/wwagner4/vsoc/master1/vsoc-ga-2018/doc/articles/resources/results/iter4/ALtrainGaB03.png)

[bob001 &#9654;](https://www.youtube.com/embed/pXlvV-XJO8s)

The **OG** category represents teams where one player is scoring goals, but
the other two are not evolving at all. NOT WHAT WE WANTED.
![categories](https://raw.githubusercontent.com/wwagner4/vsoc/master1/vsoc-ga-2018/doc/articles/resources/results/iter4/OGtrainGaB03.png)

[bob002 &#9654;](https://www.youtube.com/embed/IdiImG9voAA)

### Conclusion and Proposals for the next iteration
One of the main aims of the simulation is to breed teams where all players are scoring goals. In this
iteration most population are stuck in local minima where players do not fulfill the
main aim.

The only populations (Category AL) where actually all players are trained are unsatisfying as
scoring goals is not really what the players have learned.

As a consequence of that the fitness function of further iterations should reward goals of the
worst player without limitation.

Another consequence must be, that the max value of the worst player must be higher. The max action count for
that element of the fitness function must be much higher than 20 (For details see the table above).

Rewarding of mean values seems to have no positive effect and should not be a separate
element of the fitness function any longer.

## Iteration 3

### Aims and Principles
Goal of that iteration was to find a better fitness functions 
to avoid the pitfalls from the previous iteration. 
Mainly to avoid the breeding of teams that have only one 
evolving player. 
To achieve that goal the max value for the best kicking 
palyer was reduced.

### Score for all Populations
![Score for all Populations](https://raw.githubusercontent.com/wwagner4/vsoc/master1/vsoc-ga-2018/doc/articles/resources/results/iter3/trainGaB02.png)

This diagram shows us that some populations stopped increasing
fitness between 1000 and 1500 generations. two of those at a relative
high level of about 7000 (bob002, work003). five at a lower level of
3000 to 5000 (bob001, w001, w002 w005, w006). bob003 and wok004 show
still increasing fitness at a high level. bob004 also shows continous
increasing fitness althogh it did not increase very fast at the
beginning.

### Relation between Max, Mean and Min Fitness
![Relation between Max, Mean and Min Fitness](https://raw.githubusercontent.com/wwagner4/vsoc/master1/vsoc-ga-2018/doc/articles/resources/results/iter3/kickstrainGaB02.png)

This diagram shows us two classes of population. populations
where all players are kicking [ALL] the ball and others where only one
player kicks the ball.

| Class | Populations                                       |
| ----- | ------------------------------------------------- |
| ALL   | bob002, bob003, work003, work004                  |
| ONE   | bob001, bob004, work001, work002, work005, work00 |

The populations with the continous incrasing fitness can be
found in both groups. bob004 gets incrasing fitness by improving
exactly one player. bob003 and work004 have all players kicking the
ball, which was the goal of this iteration.

### Goals vs Own Goals
![Goals vs Own Goals](https://raw.githubusercontent.com/wwagner4/vsoc/master1/vsoc-ga-2018/doc/articles/resources/results/iter3/goalstrainGaB02.png)
on that diagram you can watch how the kicking of goals evolves.
of course goalkicking increases as kicking increases because the
players hit the goals by chance. therefor we also observe the number
of owngoals. if this number is lower than the number of goals the
players tend to shoot more goals than own goals (good) at the end of
the training there is only one populaton that fulfills that task
(work006). other population where shooting goals for a certain time
but then did not improve that (very important) behaviour any longer
(bob001 generation 200 to 1500, bob004 generation 700 to 1400)

### Videos (Screenshots of simulations)
[bob001 &#9654;](https://www.youtube.com/embed/JnysjnrOf40)
[bob002 &#9654;](https://www.youtube.com/embed/58RfdxZ6MEY)
[bob003 &#9654;](https://www.youtube.com/embed/mdAbUIv9Sek)
[bob004 &#9654;](https://www.youtube.com/embed/-0raLNPos4c)
[work001 &#9654;](https://www.youtube.com/embed/-0raLNPos4c)
[work002 &#9654;](https://www.youtube.com/embed/SnDZplg0Ejg)
[work003 &#9654;](https://www.youtube.com/embed/Q9RRPBsHCLI)
[work004 &#9654;](https://www.youtube.com/embed/UPI7UdZmMrs)
[work005 &#9654;](https://www.youtube.com/embed/KalS5Bx0TJY)
[work006 &#9654;](https://www.youtube.com/embed/r6_EfeEtTEM)


## Iteration 2
### Diagrams
![iter2 01](https://raw.githubusercontent.com/wwagner4/vsoc/master1/vsoc-ga-2018/doc/articles/resources/results/iter3/trainGaB01.png)
![iter2 02](https://raw.githubusercontent.com/wwagner4/vsoc/master1/vsoc-ga-2018/doc/articles/resources/results/iter3/scorecomptrainGaB01.png)
![iter2 03](https://raw.githubusercontent.com/wwagner4/vsoc/master1/vsoc-ga-2018/doc/articles/resources/results/iter3/kickstrainGaB01.png)

### Videos

[bob001 &#9654;](https://www.youtube.com/embed/PZAosVPFOXw)
One player per team is kicking. Other
players have little to no interest at the ball.

[bob002 &#9654;](https://www.youtube.com/embed/uYDOse_okbk)
One player per team is kicking and scoring goals.
Own goals are avoided. Other players have no interest at the ball.

[bob003 &#9654;](https://www.youtube.com/embed/0Rpm12aq38I)
One player per team is kicking and scoring goals.
Own goals are avoided.<br> Other players have no interest at the ball.

[bob004 &#9654;](https://www.youtube.com/embed/g1mHbM2xOj0)
One player per team is kicking.<br> No goals.
Other players have no interest at the ball.

[work001 &#9654;](https://www.youtube.com/embed/817eIpDZeGM)
One player per team is kicking. No goals.
Other players have no interest at the ball.

[work002 &#9654;](https://www.youtube.com/embed/KHeifGK_bZo)
One player per team is kicking and scoring goals.
Own goals are avoided.<br> Other players have no interest at the ball

[work003 &#9654;](https://www.youtube.com/embed/DXwhpZ_Kn54)
One player per team is kicking and scoring some goals.
Other players have no interest at the ball.

[work004 &#9654;](https://www.youtube.com/embed/CU2l0annFM4)
One player per team is kicking and scoring goals.
Own goals are avoided<br> Other players have no interest at the ball

### Conclusions
Only one player per team learns kicking and (sometimes) scoring goals.
NOT EXACTLY WHAT WE WANTED.

## Iteration 1
### Diagrams and related Video(s)
![iter1](https://raw.githubusercontent.com/wwagner4/vsoc/master1/vsoc-ga-2018/doc/articles/resources/results/iter1/trainGa04M0om02.png)

[04M0om02 &#9654;](https://www.youtube.com/embed/EtJ4-NlgMf0)


![iter1](https://raw.githubusercontent.com/wwagner4/vsoc/master1/vsoc-ga-2018/doc/articles/resources/results/iter1/trainGa04M0om02varL.png)

[04M0om02varL &#9654;](https://www.youtube.com/embed/i8lH4ojFnpE)


![iter1](https://raw.githubusercontent.com/wwagner4/vsoc/master1/vsoc-ga-2018/doc/articles/resources/results/iter1/trainGa05fitFac03b.png)

[fitFac03b &#9654;](https://www.youtube.com/embed/Vg93pYbuNIw)

Two more videos with unknown parametersets

[trainGa03 a &#9654;](https://www.youtube.com/embed/YVx2tspZJwE)
[trainGa03 b &#9654;](https://www.youtube.com/embed/s2fviN9zUj0)







