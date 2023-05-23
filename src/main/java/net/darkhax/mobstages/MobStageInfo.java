package net.darkhax.mobstages;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;

public class MobStageInfo {

    private String stage;
    private EntityType<?> entityId;
    private int range;
    private ResourceLocation dimension;
    private boolean allowSpawners;
    private EntityType<?> replacement;

    public MobStageInfo (String stage, EntityType<?> entityId) {

        this(stage, entityId, DimensionType.OVERWORLD_ID);
    }

    public MobStageInfo (String stage, EntityType<?> entityId, ResourceLocation dimension) {

        this.stage = stage;
        this.entityId = entityId;

        this.range = 256;
        this.dimension = dimension;
        this.allowSpawners = false;
    }

    public String getStage () {

        return this.stage;
    }

    public EntityType<?> getEntityId () {

        return this.entityId;
    }

    public int getRange () {

        return this.range;
    }

    public ResourceLocation getDimension () {

        return this.dimension;
    }

    public boolean allowSpawners () {

        return this.allowSpawners;
    }

    public boolean isAllowSpawners () {

        return this.allowSpawners;
    }

    public void setAllowSpawners (boolean allowSpawners) {

        this.allowSpawners = allowSpawners;
    }

    public void setStage (String stage) {

        this.stage = stage;
    }

    public void setEntityId (EntityType<?> entityId) {

        this.entityId = entityId;
    }

    public void setRange (int range) {

        this.range = range;
    }

    public void setDimension (ResourceLocation dimension) {

        this.dimension = dimension;
    }

    public EntityType<?> getReplacement () {

        return this.replacement;
    }

    public void setReplacement (EntityType<?> replacement) {

        this.replacement = replacement;
    }
}
