# altoclef
Plays block game.

Powered by Baritone.

A client side bot that can accomplish any Minecraft task that is relatively simple and can be split into smaller tasks. "Relatively Simple" is a vague term, so check the list of current capabilities to see examples.

Video explaining how this bot can be used to beat Minecraft: https://www.youtube.com/watch?v=q5OmcinQ2ck

Butler system demo here: https://drive.google.com/file/d/1axVYYMJ5VjmVHaWlCifFHTwiXlFssOUc/view?usp=sharing

Will try to stream using this bot to beat the game with NO user input. Will be a world first hopefully.

### How it works

TODO: Fill this with some nerd stuff. Basically each task can run a smaller sub-task, which act recursively. By specifying the order you can have requirements and accomplish pretty large tasks by building them up from smaller bits.

TODO: Image or something to make it easier to explain.

Example pseudocode for getting a Wooden Pickaxe (not actual code):
```
task CollectWoodenPickaxe:
    if we don't have 3 planks:
        run CollectPlanks(3)
    if we don't have 2 sticks:
        run CollectSticks(2)
    if we don't have crafting table open:
        run OpenCraftingTable
    craft wooden pickaxe using 3 planks and 2 sticks in open table

task CollectSticks:
    if we don't have 2 planks:
        run CollectPlanks(2)
    craft sticks using 2 planks in inventory

task CollectPlanks:
    if we don't have 1 log:
        mine logs and pick up log drops (baritone)
    craft planks with 1 log in inventory

task OpenCraftingTable:
    if there is a crafting table nearby:
        go to crafting table and open it (baritone)
    if we don't have a crafting table:
        if we don't have 4 planks:
            run CollectPlanks(4)
        craft crafting table using 4 planks in inventory
    place crafting table (in inventory) nearby (baritone)

```

### Current capabilities, Examples:
- Obtain 400+ Items from a fresh survival world, like diamond armor, cake, and nether brick stairs
- Dodge mob projectiles and force field mobs away while accomplishing arbitrary tasks
- Collect + smelt food from animals, hay, & crops
- Receive commands from chat whispers via /msg. Whitelist + Blacklist configurable (hereby dubbed the Butler System)
- Simple config file that can be reloaded via command (check .minecraft directory)
- Beat the entire game on its own (no user input.) This includes:
    - Building a nether portal
    - Finding a nether fortress + collecting blaze rods from a blaze
    - Trade with piglins for ender pearls, collecting + crafting gold in the nether
    - Leave nether
    - Craft + throw eyes of ender and follow direction until stronghold portal is discovered.
    - Setting spawnpoint near stronghold portal
    - Entering end portal, destroying crystals and killing the dragon as it perches.
- Print the entire bee movie script with signs in a straight line, automatically collecting signs + bridging materials along the way.
- Become the terminator: Run away from players while unarmed, gather diamond gear in secret, then return and wreak havoc.

### TODO's/Future Features:
- Given any schematic, COLLECT ALL RESOURCES and BUILD the schematic, ideally from a fresh survival world if possible.
- (Currently delayed after 2b2t announced moving to 1.16) Port to Forge and develop a 1.12 version. I will need somebody's help for this, if you know gradle + want to help me get baritone imported into forge hit me up.
- "Travel" task for anarchy servers that travels in one direction or explores, automatically utilizing nether highways, setting bed respawn points and collecting food for survival.


### Installation

Currently no builds exist as many things are still in development. If you wish to try it out anyway, you may build the project yourself.

### Building the Project

1) Clone project + import I'd suggest using JetBrain's IntelliJ to import the project.

2) Read `libs/LIBS.txt`. (You will have to install [my fork of fabritone](https://gitlab.com/adrisj7/fabritone), build and then drag+drop the compiled dev jar into libs.)

3) run gradle task runClient (In IntelliJ open up the Gradle window and run altoclef/Tasks/fabric/runClient)

4) for building a jar to use as a fabric mod, run `altoclef/Tasks/build/jar` I think. Haven't tested this yet since I'm still developing the thing.

I'm aware these instructions are vague in some parts, so don't hesitate to contact me for questions/make a github issue, bug me all you want as long as it's not stuff you can't google in 5 seconds.
