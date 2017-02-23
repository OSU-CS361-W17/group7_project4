#CS361 Project 3 [![Build Status](https://travis-ci.org/OSU-CS361-W17/group7_project3.svg?branch=master)](https://travis-ci.org/OSU-CS361-W17/group7_project3)
For Project Stage 3, your group will import your group's code from Project Stage 2. The goal of this stage is to expand the game and refine the quality of code for the Battleship (Links to an external site.) game previously developed. The focus is on using Object-Oriented design (specifically inheritance).

Users are enjoying version 2 of your Battleship game, and it's popularity has increased dramatically, but some users are beginning to complain that the game play is boring after playing a few games. Some of these users have requested more variety to the types of ships that can be played. The marketing department has run several user focus groups and come up with the following suggestions:

Instead of having only military ships, add civilian ships as well.
* Remove Cruiser and Destroyer ship types from the military ships.
* Add Clipper (size: 3) and Dinghy (size: 1) ship types to the civilian ships.
* Since civilian ships do not possess armor, they sink after taking only 1 hit regardless of their size.
* Give the Battleship and Submarine ship types a stealth capability that allows them to evade scans.

#Tasks
To complete this assignment, each group needs to do the following:

* Repository Initialization. Initialize your group repository OSU-CS361-W17/group##_project3. You should use the previously developed code in your group's Project Stage 2 repository (OSU-CS361-W17/group##_project2) as the basis for this stage. Follow the Importing between GitHub repositories page for instructions on how to easily import your selected repository code into your group's Project Stage 3 repository. Only one member of the group needs to complete this step in order to begin this stage.

* Create a Sequence Diagram of Front-end/Back-end Communications. Based upon the version of Battleship that your team developed for Project Stage 2, create a sequence diagram that represents all of the communications between the back-end (Model) and front-end (View) components. You can refer to lecture slides, the Agile Modeling website (link (Links to an external site.)), and any other online references for instructions on drafting sequence diagrams. The sequence diagram can either be composed using UML modeling software, or drawn on paper (drawings should be labelled and legible). Scan and add this diagram to your group's Wiki pages.

* Create UML Class Diagrams for your solution. This step involves creating 3 different UML class diagrams. The first diagram should represent the class structure of your Battleship game from Project Stage 2. The second diagram should represent the expected class structure of your Battleship game with changes incorporated based upon the features listed above. The third diagram should represent the actual class structure of your final version after all of the requested features have been implemented. The second and third diagram could potentially look the same, but they are much more likely to differ based upon your group's actual implementation. The third diagram must match the version of your group's code that is submitted at the end of this assignment. These diagrams can either be composed using UML modeling software, or drawn on paper (drawings should be labelled and legible). Scan and add this diagram to your group's Wiki pages (names should be added to indicate the author of each diagram).

* Implement Requested Features in Object-Oriented Design. Groups should divide the work equally for implementing all necessary user stories and their associated features as requested above. The Java code in the Model portion of your Battleship game must adhere to Object-Oriented design (the Inheritance requirement in particular). User stories must be added to the group Wiki for each new feature (follow INVEST (Links to an external site.) for these). Tasks must be added into the GitHub Issue page on your group's Project Stage 3 repository. Each group member must have at least one issue assigned to them, and that task must involve implementation code that is added via Pull Request. Each group member is also responsible for constructively commenting on another group member's Pull Request.

# Tips and tricks:

To implement this code, you will be using [Java Spark](http://sparkjava.com).

You will make your life MUCH easier if you use [GSON](https://github.com/google/gson) to seralize/deserialize the JSON objects to/from java objects.

You might also find the jquery documentation useful.
