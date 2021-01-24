package net.darkhax.mobstages.compat.crt;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.MobStages")
public class MobStagesCrT {

    @ZenCodeType.Method
    public static void addStage (String stage, MCEntityType entityId) {

        CraftTweakerAPI.apply(new ActionAddEntityStage(stage, entityId.getInternal()));
    }

    @ZenCodeType.Method
    public static void addStage (String stage, MCEntityType entityId, String dimension) {

        CraftTweakerAPI.apply(new ActionAddEntityStage(stage, entityId.getInternal(), dimension));
    }

    @ZenCodeType.Method
    public static void addReplacement (MCEntityType entityId, MCEntityType replacementId) {

        CraftTweakerAPI.apply(new ActionAddSpawnReplacement(entityId.getInternal(), replacementId.getInternal()));
    }

    @ZenCodeType.Method
    public static void addReplacement (MCEntityType entityId, MCEntityType replacementId, String dimension) {

        CraftTweakerAPI.apply(new ActionAddSpawnReplacement(entityId.getInternal(), replacementId.getInternal(), dimension));
    }

    @ZenCodeType.Method
    public static void addRange (MCEntityType entityId, int range) {

        CraftTweakerAPI.apply(new ActionAddSpawnRange(entityId.getInternal(), range));
    }

    @ZenCodeType.Method
    public static void addReplacement (MCEntityType entityId, int range, String dimension) {

        CraftTweakerAPI.apply(new ActionAddSpawnRange(entityId.getInternal(), range, dimension));
    }

    @ZenCodeType.Method
    public static void toggleSpawner (MCEntityType entityId, boolean allow) {

        CraftTweakerAPI.apply(new ActionOverlookSpawners(entityId.getInternal(), allow));
    }

    @ZenCodeType.Method
    public static void toggleSpawner (MCEntityType entityId, boolean allow, String dimension) {

        CraftTweakerAPI.apply(new ActionOverlookSpawners(entityId.getInternal(), allow, dimension));
    }
}
