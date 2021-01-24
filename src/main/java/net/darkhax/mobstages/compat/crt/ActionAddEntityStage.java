package net.darkhax.mobstages.compat.crt;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IAction;
import net.darkhax.mobstages.MobStages;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.registries.ForgeRegistries;

public class ActionAddEntityStage implements IAction {

    private final String stage;
    private final String entityId;

    private final boolean isDimensional;
    private final String dimension;

    public ActionAddEntityStage (String stage, String entity) {

        this(stage, entity, DimensionType.OVERWORLD_ID.toString(), false);
    }

    public ActionAddEntityStage (String stage, String entity, String dimension) {

        this(stage, entity, dimension, true);
    }

    private ActionAddEntityStage (String stage, String entity, String dimension, boolean isDimensional) {

        this.stage = stage;
        this.entityId = entity;
        this.isDimensional = isDimensional;
        this.dimension = dimension;
    }

    @Override
    public void apply () {
        if (!ForgeRegistries.ENTITIES.containsKey(new ResourceLocation(this.entityId))) {
            CraftTweakerAPI.logError("No entity found for " + this.entityId);
        }
        if (this.isDimensional) {
            MobStages.getOrCreateStageInfo(this.stage, this.entityId, new ResourceLocation(this.dimension));
        }
        else {
            MobStages.getOrCreateStageInfo(this.stage, this.entityId);
        }
    }

    @Override
    public String describe () {

        return "Adding " + this.entityId + " to stage." + this.stage + (this.isDimensional ? " Dimension: " + this.dimension : "");
    }
}