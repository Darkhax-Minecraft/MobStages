package net.darkhax.orestages.compat.crt;

import crafttweaker.IAction;
import net.darkhax.orestages.MobStageInfo;
import net.darkhax.orestages.MobStages;

public class ActionAddSpawnRange implements IAction {

    private final String entityId;
    private final int range;

    private final boolean isDimensional;
    private final int dimension;

    public ActionAddSpawnRange (String entity, int range) {

        this(entity, range, 0, false);
    }

    public ActionAddSpawnRange (String entity, int range, int dimension) {

        this(entity, range, dimension, true);
    }

    private ActionAddSpawnRange (String entity, int range, int dimension, boolean isDimensional) {

        this.entityId = entity;
        this.range = range;
        this.isDimensional = isDimensional;
        this.dimension = dimension;
    }

    @Override
    public void apply () {

        final MobStageInfo info = this.isDimensional ? MobStages.DIMENSIONAL_STAGE_INFO.get(this.entityId).get(this.dimension) : MobStages.GLOBAL_STAGE_INFO.get(this.entityId);
        info.setRange(this.range);
    }

    @Override
    public String describe () {

        return "Overriding spawn range of " + this.entityId + " to " + this.range + (this.isDimensional ? " Dimension: " + this.dimension : "");
    }
}