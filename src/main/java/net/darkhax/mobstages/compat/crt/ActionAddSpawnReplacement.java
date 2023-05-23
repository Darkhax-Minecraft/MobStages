package net.darkhax.mobstages.compat.crt;

import com.blamejared.crafttweaker.api.actions.IAction;
import net.darkhax.mobstages.MobStageInfo;
import net.darkhax.mobstages.MobStages;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;

public class ActionAddSpawnReplacement implements IAction {

    private final EntityType<?> entityId;
    private final EntityType<?> replacementId;

    private final boolean isDimensional;
    private final String dimension;

    public ActionAddSpawnReplacement (EntityType<?> entity, EntityType<?> replacement) {

        this(entity, replacement, DimensionType.OVERWORLD_ID.toString(), false);
    }

    public ActionAddSpawnReplacement (EntityType<?> entity, EntityType<?> replacement, String dimension) {

        this(entity, replacement, dimension, true);
    }

    private ActionAddSpawnReplacement (EntityType<?> entity, EntityType<?> replacement, String dimension, boolean isDimensional) {

        this.entityId = entity;
        this.replacementId = replacement;
        this.isDimensional = isDimensional;
        this.dimension = dimension;
    }

    @Override
    public void apply () {

        final MobStageInfo info = this.isDimensional ? MobStages.DIMENSIONAL_STAGE_INFO.get(this.entityId).get(new ResourceLocation(this.dimension)) : MobStages.GLOBAL_STAGE_INFO.get(this.entityId);
        info.setReplacement(this.replacementId);
    }

    @Override
    public String describe () {

        return "Adding a replacement for " + this.entityId + " to " + this.replacementId + (this.isDimensional ? " Dimension: " + this.dimension : "");
    }
}