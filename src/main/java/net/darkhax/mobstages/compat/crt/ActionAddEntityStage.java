package net.darkhax.mobstages.compat.crt;

import crafttweaker.IAction;
import net.darkhax.mobstages.MobStages;

public class ActionAddEntityStage implements IAction {

    private final String stage;
    private final String entityId;

    private final boolean isDimensional;
    private final int dimension;

    public ActionAddEntityStage (String stage, String entity) {

        this(stage, entity, 0, false);
    }

    public ActionAddEntityStage (String stage, String entity, int dimension) {

        this(stage, entity, dimension, true);
    }

    private ActionAddEntityStage (String stage, String entity, int dimension, boolean isDimensional) {

        this.stage = stage;
        this.entityId = entity;
        this.isDimensional = isDimensional;
        this.dimension = dimension;
    }

    @Override
    public void apply () {

        if (this.isDimensional)
            MobStages.getOrCreateStageInfo(this.stage, this.entityId, this.dimension);
        else
            MobStages.getOrCreateStageInfo(this.stage, this.entityId);
    }

    @Override
    public String describe () {

        return "Adding " + this.entityId + " to stage." + this.stage + (this.isDimensional ? " Dimension: " + this.dimension : "");
    }
}