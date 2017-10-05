package net.darkhax.mobstages.compat.crt;

import crafttweaker.IAction;
import net.darkhax.mobstages.MobStageInfo;
import net.darkhax.mobstages.MobStages;

public class ActionOverlookSpawners implements IAction {

    private final String entityId;
    private final boolean ignoreSpawner;

    private final boolean isDimensional;
    private final int dimension;

    public ActionOverlookSpawners (String entity, boolean ignoreSpawner) {

        this(entity, ignoreSpawner, 0, false);
    }

    public ActionOverlookSpawners (String entity, boolean ignoreSpawner, int dimension) {

        this(entity, ignoreSpawner, dimension, true);
    }

    private ActionOverlookSpawners (String entity, boolean ignoreSpawner, int dimension, boolean isDimensional) {

        this.entityId = entity;
        this.ignoreSpawner = ignoreSpawner;
        this.isDimensional = isDimensional;
        this.dimension = dimension;
    }

    @Override
    public void apply () {

        final MobStageInfo info = this.isDimensional ? MobStages.DIMENSIONAL_STAGE_INFO.get(this.entityId).get(this.dimension) : MobStages.GLOBAL_STAGE_INFO.get(this.entityId);
        info.setAllowSpawners(this.ignoreSpawner);
    }

    @Override
    public String describe () {

        return "Toggling spawners for " + this.entityId + " to " + this.ignoreSpawner + (this.isDimensional ? " Dimension: " + this.dimension : "");
    }
}