package net.darkhax.mobstages.compat.crt;

import com.blamejared.crafttweaker.api.actions.IAction;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import net.darkhax.mobstages.MobStageInfo;
import net.darkhax.mobstages.MobStages;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;

public class ActionOverlookSpawners implements IAction {

    private final EntityType<?> entityId;
    private final boolean ignoreSpawner;

    private final boolean isDimensional;
    private final String dimension;

    public ActionOverlookSpawners (EntityType<?> entity, boolean ignoreSpawner) {

        this(entity, ignoreSpawner, DimensionType.OVERWORLD_ID.toString(), false);
    }

    public ActionOverlookSpawners (EntityType<?> entity, boolean ignoreSpawner, String dimension) {

        this(entity, ignoreSpawner, dimension, true);
    }

    private ActionOverlookSpawners (EntityType<?> entity, boolean ignoreSpawner, String dimension, boolean isDimensional) {

        this.entityId = entity;
        this.ignoreSpawner = ignoreSpawner;
        this.isDimensional = isDimensional;
        this.dimension = dimension;
    }

    @Override
    public void apply () {

        if (this.isDimensional && MobStages.DIMENSIONAL_STAGE_INFO.get(this.entityId) == null) {

            throw new IllegalArgumentException("You must stage " + this.entityId + " before spawner can be toggled!");
        }

        final MobStageInfo info = this.isDimensional ? MobStages.DIMENSIONAL_STAGE_INFO.get(this.entityId).get(new ResourceLocation(this.dimension)) : MobStages.GLOBAL_STAGE_INFO.get(this.entityId);
        info.setAllowSpawners(this.ignoreSpawner);
    }

    @Override
    public String describe () {

        return "Toggling spawners for " + this.entityId + " to " + this.ignoreSpawner + (this.isDimensional ? " Dimension: " + this.dimension : "");
    }
}