# CS361 Project 4 [![Build Status](https://travis-ci.org/OSU-CS361-W17/group7_project4.svg?branch=master)](https://travis-ci.org/OSU-CS361-W17/group7_project4)
The version 3 of your Battleship game was a runaway success, with several other companies jumping into the market to compete with you. Management is now requesting that your development team add new features to the gameplay and also "add smarts to the AI so that it isn't as easy to beat the computer" (quote from the VP of Marketing, who professes to not understand how computers work). Because your development team had to rush to release versions 1-3 of the game, some of the maintainability and code quality has suffered. So along with the new features, your project manager has asked that you clean-up the code so that it can be passed along to a support team that will be maintaining it after we release version 4.

For Project Stage 4, your group will import your group's code from Project Stage 3. The goal of this stage is to add some additional features, polish your design, and finalize your group's Battleship (Links to an external site.) game. The focus is on combining all of the concepts and skills learned throughout the term.

The features outlined for this release are:

* The computer should have an Easy and Hard difficulty setting. These settings should adhere to the following requirements:
  * **Easy:** The computer places ships in specific hard-coded locations on the board. When firing, the computer should follow a pattern that could result in winning the game (i.e. no patterns that prevent the computer from ever winning).
  * **Hard:** The computer places ships in random locations on the board; no overlapping allowed. When firing, the computer will randomly shoot until it hits one of the player's ships. When it hits a ship, the computer will begin firing at nearby locations in order to find and sink that particular ship. The computer returns to firing at random locations once it has sunk that particular ship. (Further details regarding this feature provided below.)

* **OPTIONAL:** Some users are asking for a modification to the gameplay so that when the player hits the computer's ship, they get another turn to fire. This "additional turn" feature should continue until either the game ends or the player misses. [This feature is optional, but can be used to add an additional feature to your project if you cannot divide the other tasks evenly among your group members.]

# Learning Objectives

This assignment will help you learn the following:

* Work with a team to design and implement changes to your model and view
* Conduct code reviews within a team in order to verify functionality, prevent conflicts, and increase quality
* Use UML to describe your solution and prevent architecture anti-patterns
* Divide tasks among team members
* Use GitHub Issue Tracking, and Pull Requests correctly

# Tasks
To complete this assignment, each group needs to do the following:

* **Repository Initialization.** Initialize your group repository OSU-CS361-W17/group##_project4. You should use the previously developed code in your group's Project Stage 3 repository (OSU-CS361-W17/group##_project3) as the basis for this stage. Follow the Importing between GitHub repositories page for instructions on how to easily import your selected repository code into your group's Project Stage 4 repository. Only one member of the group needs to complete this step in order to begin this stage.

* **Create a UML Class Diagram for your solution.** This step should result in a UML Class Diagram that represents the class structure of all Java components in your Battleship game, including any additions or modifications required for the features in this stage. The diagram should show that your code structure adheres to the Single Responsibility Principle and that there is no code duplication. The diagram must match the version of your group's code that is submitted at the end of this assignment. This diagram can either be composed using UML modeling software, or drawn on paper (drawings should be labelled and legible). Scan and add this diagram to your group's Wiki pages.

* **Implement Requested Features in Object-Oriented Design.** Groups should divide the work equally for implementing all necessary features as requested above. The Java code in the Model portion of your Battleship game must adhere to Object-Oriented design. Your View (UI) should change to allow users to select the Computer difficulty setting. User stories must be added to the group Wiki for each new feature (follow INVEST (Links to an external site.) for these). Tasks must be added into the GitHub Issue page on your group's Project Stage 4 repository. Each group member must have at least one issue assigned to them. Each task/issue must involve implementation code that is added via Pull Request.

* **Conduct Code Review on Pull Requests.** Each team member should conduct a code review for another team member's Pull Request. The requirement is that each team member do a code review for at least one other team member's Pull Request. This should result in code comments within the Files Changed portion of Pull Requests on GitHub. Comments on the Pull Request itself can also help your team members understand the results of your code review. Look at code quality, feature logic, check for bugs, check for code duplication, and verify that code adheres to Object-Oriented design and the Single Responsibility Principle.

* **Implement Firing Logic for Computer on Hard Setting.** The computer on Hard setting will have to do the following: 
  * The computer begins by randomly choosing locations to fire at on the player's board.
  * If the computer hits a ship with its fire, then it will try to sink the ship.
  * The computer must adhere to the player rules of Battleship. The computer cannot "cheat" by seeing the location of the player's board or ships.
