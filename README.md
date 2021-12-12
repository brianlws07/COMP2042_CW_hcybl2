# Software Maintenance (COMP2042 UNMC) Coursework  
## COMP2042_CW_hcybl2

- Name: Brian Lim Wei Sheng
- StudentID: 20318893
- IDE: Intellij

## Purpose of this coursework
This program is a Brick Destroyer game that is maintained by me, Brian Lim Wei Sheng. I have made some refactoring to improve readability of the source code.

## Refactoring:
### Ball (abstract class inhertied by RubberBall)
* Firstly I have encapsulated the fields up, down, left, right so that they are private and also generate getters and setters for them
* In ball constructor, instead of setlocation() for ball's coordinate. I instantiated them with specific coordinates
* move() and moveTo() methods have similar implementation, so identical lines between these two methods are extracted into a new method moveFrame(). moveFrame() moves the temporary Rectangle frame and reset ball's coordinate when ball is moved
 

### Player 
Player represent the green bar.
* In player class constructor, there is a line that set value of moveAmount to 0. But I replaced it with stop method that has the same implementation so that player bar is still when program first starts


### Brick (abstract class inherited by ClayBrick, CementBrick, SteelBrick, YellowBrick)
Bricks are displayed on wall that player are supposed to destroy all by using ball.
* removed Constant variable MIN_CRACK that is never used
* removed name variable in all Brick classes, because it is assigned but never used
* encapsulate brickFace and generate getter for it
* pull up overriden methods makeBrickFace to superclass Brick because it has the same implementation for all subclasses
* ##### Major Refactoring: separated the inner class Crack from Brick into its own class, Crack 


### Crack
Crack are drawn onto CementBrick or YellowBrick if they are impacted by ball
* Removed InMiddle and Jump methods because probability to invoke these 2 methods are very small. Plus, cracks are still able to generate onto Bricks without these 2 methods


### Wall
Wall displays wall of bricks
* Encapsulate bricks, ball, player and generate getter for them
* ##### Major Refactoring: methods that are involved in creating levels are extracted out and made into a new class called Level


### Level
Level is involved in creating multiple levels
* extracted the identical last few lines in makeSingleTypeLevel(), makeChessboardLevel(), makefinalLevel() into a new method called _2ndlinelastbrick(). _2ndlinelastbrick is responsible for creating a brick at the last brick in 2nd line of wall.
 

### DebugPanel
DebugConsole that pops up by pressing keys (alt+shift+f1)
* removed wall because it is assigned but never used
* removed white background color since DebugPanel is covered by JButtons and JSliders


## Extensions:
* Changed the color of buttons and sliders in DebugConsole
* Added a new background image and changed the color of border in HomeMenu
* Added a new infoButton in HomeMenu, and also created a new InfoFrame that contains infoguides and background image
* Added a background image in GameFrame
* Created an additional final level with a new YellowBrick
* Created a highscore pop up when ball is lost or finished game
* Separated classes into their respective packages with specific names

## Additions:
- Maven build file is generated at target folder
- Javadocs are generated in Javadocs folder
- Junit test are generated in test folder






