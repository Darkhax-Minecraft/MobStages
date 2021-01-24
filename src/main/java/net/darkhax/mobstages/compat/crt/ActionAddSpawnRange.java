package net.darkhax.mobstages.compat.crt;

import com.blamejared.crafttweaker.api.actions.IAction;
import net.darkhax.mobstages.MobStageInfo;
import net.darkhax.mobstages.MobStages;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;

public class ActionAddSpawnRange implements IAction {

    private final String entityId;
    private final int range;

    private final boolean isDimensional;
    private final String dimension;

    public ActionAddSpawnRange (String entity, int range) {

        this(entity, range, DimensionType.OVERWORLD_ID.toString(), false);
    }

    public ActionAddSpawnRange (String entity, int range, String dimension) {

        this(entity, range, dimension, true);
    }

    private ActionAddSpawnRange (String entity, int range, String dimension, boolean isDimensional) {

        this.entityId = entity;
        this.range = range;
        this.isDimensional = isDimensional;
        this.dimension = dimension;
    }

    @Override
    public void apply () {

        final MobStageInfo info = this.isDimensional ? MobStages.DIMENSIONAL_STAGE_INFO.get(this.entityId).get(new ResourceLocation(this.dimension)) : MobStages.GLOBAL_STAGE_INFO.get(this.entityId);
        info.setRange(this.range);
    }

    @Override
    public String describe () {

        return "Overriding spawn range of " + this.entityId + " to " + this.range + (this.isDimensional ? " Dimension: " + this.dimension : "");
    }
}