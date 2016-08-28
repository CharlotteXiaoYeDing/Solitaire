# Solitaire

A solitarie game written in Java.

![alt text](https://github.com/CharlotteXiaoYeDing/Solitaire/blob/master/Solitaire/Readme1.png "Logo Title Text 1")

The major components of the game is Deck, Discard, SuitStackManger, and WorkingStackManager is illustrated above.

####Card
 - An unique and immutable object identified by its rank and suit. Rank and Suit of the Card are represented by enum.
 - Used Flyweight Design Pattern to ensure the uniqueness of the Card

####Deck
 - An iterable stack

####SuitStackManager
- Suit stacks are stored in a map. For each suit stack, we only keep track of the Card on the top.
- We calculate the score of the game by adding up the number of cards in suit stacks.

####WorkingStack
- An iterable stack
- A field `aVisible` to keep track of the visibility of the cards. `aVisible` is the first card that is visible in the WorkingStack.

####WorkingStackManager
- An array of WorkingStack

####Location
- A tagging interface for identifying the location of the Card

####GameModel
- Used Singleton Pattern to ensure the uniqueness of the Game Model
- Used Observer Design Pattern to coordinate between GUI and Game Model 
- Manage moves (execute, undo, log) through Command Design Pattern

*The GUI is adapted from prmr/Solitaire. 

