package net.darkhax.mobstages.compat.crt;

import crafttweaker.IAction;
import net.darkhax.mobstages.MobStageInfo;
import net.darkhax.mobstages.MobStages;

public class ActionAddSpawnReplacement implements IAction {

    private final String entityId;
    private final String replacementId;

    private final boolean isDimensional;
    private final int dimension;

    public ActionAddSpawnReplacement (String entity, String replacement) {

        this(entity, replacement, 0, false);
    }

    public ActionAddSpawnReplacement (String entity, String replacement, int dimension) {

        this(entity, replacement, dimension, true);
    }

    private ActionAddSpawnReplacement (String entity, String replacement, int dimension, boolean isDimensional) {

        this.entityId = entity;
        this.replacementId = replacement;
        this.isDimensional = isDimensional;
        this.dimension = dimension;
    }

    @Override
    public void apply () {

        final MobStageInfo info = this.isDimensional ? MobStages.DIMENSIONAL_STAGE_INFO.get(this.entityId).get(this.dimension) : MobStages.GLOBAL_STAGE_INFO.get(this.entityId);
        info.setReplacement(this.replacementId);
    }

    @Override
    public String describe () {

        return "Adding a replacement for " + this.entityId + " to " + this.replacementId + (this.isDimensional ? " Dimension: " + this.dimension : "");
    }
}