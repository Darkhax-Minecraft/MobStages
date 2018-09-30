# Mob Stages [![](http://cf.way2muchnoise.eu/278359.svg)](https://minecraft.curseforge.com/projects/mob-stages) [![](http://cf.way2muchnoise.eu/versions/278359.svg)](https://minecraft.curseforge.com/projects/mob-stages)

This mod hooks in to the GameStage API, and allows mob spawning to be gated behind custom progression. 

[![Nodecraft](https://nodecraft.com/assets/images/logo-dark.png)](https://nodecraft.com/r/darkhax)    
This project is sponsored by Nodecraft. Use code [Darkhax](https://nodecraft.com/r/darkhax) for 30% off your first month of service!

## Description
This mod is an addon for the GameStage API. This allows for mob spawning to be put into a custom progression system. You should check out the GameStage API mod's description for more info. To give a brief run down, stages are parts of the progression system set up by the modpack or server. Stages are given to players through a command, which is typically ran by a questing mod, advancement, or even a Command Block. 

## Setup
This mod uses [CraftTweaker](https://minecraft.curseforge.com/projects/crafttweaker) for configuration.

### Global Setup
- `mod.MobStages.addStage(String stage, String entityId);` - Adds a mob to a stage. When it attempts to spawn in the world, it will check for nearby players that have this stage unlocked. If no valid players are found, the mob will not be able to spawn.
- `mob.MobStages.addReplacement(String entityId, String replacementId);` - Adds a replacement for the mob. If it fails to spawn, the replacement will be spawned instead. This requires that you add the mob to a stage first. This is optional.
- `mob.MobStages.addRange(String entityId, int range);` - Sets the range to search for players. The fault range is 256, and is automatically set for you. This is optional.
- `mob.MobStages.toggleSpawners(String entityId, boolean allow);` - Allows for mob spawners to ignore the stage rules and spawn the mob anyway. 

### Dimensional Setup
This mod adds dimension specific versions of all the methods in the previous section. The dimension versions accept the numeric dimension id as an additional last parameter. For example, `mod.MobStages.addStage(String stage, String entityId);` becomes `mod.MobStages.addStage(String stage, String entityId, int dimension);`. If a mob has a dimensional entry, it will override the global entry. 

### Example Script

```apache
// Creepers require stage one to spawn
mods.MobStages.addStage("one", "minecraft:creeper");

// Skeletons require stage two, or any spawner.
mods.MobStages.addStage("two", "minecraft:skeleton");
mods.MobStages.toggleSpawner("minecraft:skeleton", true);

// Spiders require stage three in the nether.
mods.MobStages.addStage("three", "minecraft:spider", -1);

// Zombies can spawn from spawners in the nether
mods.MobStages.addStage("four", "minecraft:zombie", -1);
mods.MobStages.toggleSpawner("minecraft:zombie", true, -1);

// Zombies are replaced by bats in other dimensions.
mods.MobStages.addStage("four", "minecraft:zombie");
mods.MobStages.addReplacement("minecraft:zombie", "minecraft:bat");
```
