package net.darkhax.mobstages.compat.crt;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.MobStages")
public class MobStagesCrT {

    @ZenCodeType.Method
    public static void addStage (String stage, String entityId) {

        CraftTweakerAPI.apply(new ActionAddEntityStage(stage, entityId));
    }

    @ZenCodeType.Method
    public static void addStage (String stage, String entityId, String dimension) {

        CraftTweakerAPI.apply(new ActionAddEntityStage(stage, entityId, dimension));
    }

    @ZenCodeType.Method
    public static void addReplacement (String entityId, String replacementId) {

        CraftTweakerAPI.apply(new ActionAddSpawnReplacement(entityId, replacementId));
    }

    @ZenCodeType.Method
    public static void addReplacement (String entityId, String replacementId, String dimension) {

        CraftTweakerAPI.apply(new ActionAddSpawnReplacement(entityId, replacementId, dimension));
    }

    @ZenCodeType.Method
    public static void addRange (String entityId, int range) {

        CraftTweakerAPI.apply(new ActionAddSpawnRange(entityId, range));
    }

    @ZenCodeType.Method
    public static void addReplacement (String entityId, int range, String dimension) {

        CraftTweakerAPI.apply(new ActionAddSpawnRange(entityId, range, dimension));
    }

    @ZenCodeType.Method
    public static void toggleSpawner (String entityId, boolean allow) {

        CraftTweakerAPI.apply(new ActionOverlookSpawners(entityId, allow));
    }

    @ZenCodeType.Method
    public static void toggleSpawner (String entityId, boolean allow, String dimension) {

        CraftTweakerAPI.apply(new ActionOverlookSpawners(entityId, allow, dimension));
    }
}
