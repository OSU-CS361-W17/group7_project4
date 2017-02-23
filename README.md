# cs361 Project 3
For Project Stage 3, your group will import your group's code from Project Stage 2. The goal of this stage is to expand the game and refine the quality of code for the Battleship (Links to an external site.) game previously developed. The focus is on using Object-Oriented design (specifically inheritance).

Users are enjoying version 2 of your Battleship game, and it's popularity has increased dramatically, but some users are beginning to complain that the game play is boring after playing a few games. Some of these users have requested more variety to the types of ships that can be played. The marketing department has run several user focus groups and come up with the following suggestions:

Instead of having only military ships, add civilian ships as well.
* Remove Cruiser and Destroyer ship types from the military ships.
* Add Clipper (size: 3) and Dinghy (size: 1) ship types to the civilian ships.
* Since civilian ships do not possess armor, they sink after taking only 1 hit regardless of their size.
* Give the Battleship and Submarine ship types a stealth capability that allows them to evade scans.

# Tips and tricks:

To implement this code, you will be using [Java Spark](http://sparkjava.com).

You will make your life MUCH easier if you use [GSON](https://github.com/google/gson) to seralize/deserialize the JSON objects to/from java objects.

You might also find the jquery documentation useful.
