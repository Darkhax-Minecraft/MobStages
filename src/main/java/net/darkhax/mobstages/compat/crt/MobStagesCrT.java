package net.darkhax.mobstages.compat.crt;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.MobStages")
public class MobStagesCrT {
    
    @ZenMethod
    public static void addStage (String stage, String entityId) {
        
        CraftTweakerAPI.apply(new ActionAddEntityStage(stage, entityId));
    }
    
    @ZenMethod
    public static void addStage (String stage, String entityId, int dimension) {
        
        CraftTweakerAPI.apply(new ActionAddEntityStage(stage, entityId, dimension));
    }
    
    @ZenMethod
    public static void addReplacement (String entityId, String replacementId) {
        
        CraftTweakerAPI.apply(new ActionAddSpawnReplacement(entityId, replacementId));
    }
    
    @ZenMethod
    public static void addReplacement (String entityId, String replacementId, int dimension) {
        
        CraftTweakerAPI.apply(new ActionAddSpawnReplacement(entityId, replacementId, dimension));
    }
    
    @ZenMethod
    public static void addRange (String entityId, int range) {
        
        CraftTweakerAPI.apply(new ActionAddSpawnRange(entityId, range));
    }
    
    @ZenMethod
    public static void addReplacement (String entityId, int range, int dimension) {
        
        CraftTweakerAPI.apply(new ActionAddSpawnRange(entityId, range, dimension));
    }
    
    @ZenMethod
    public static void toggleSpawner (String entityId, boolean allow) {
        
        CraftTweakerAPI.apply(new ActionOverlookSpawners(entityId, allow));
    }
    
    @ZenMethod
    public static void toggleSpawner (String entityId, boolean allow, int dimension) {
        
        CraftTweakerAPI.apply(new ActionOverlookSpawners(entityId, allow, dimension));
    }
}
