package net.darkhax.mobstages;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import net.darkhax.bookshelf.util.EntityUtils;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Mod("mobstages")
@Mod.EventBusSubscriber(modid="mobstages", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobStages {

    public static final Logger LOGGER = LogManager.getLogger();

    /**
     * A map of global stage info. This is used as a fallback if dimensional specific info does
     * not exist. The key is the mob's id, and the value is the stage info for that mob.
     */
    public static final Map<EntityType<?>, MobStageInfo> GLOBAL_STAGE_INFO = new HashMap<>();

    /**
     * A map of dimension specific mob spawning stage data. The key is the entity id. The value
     * is a second map, where the key is the dimension id, and the value is the info for that.
     * This will override the global data.
     */
    public static final Map<EntityType<?>, Map<ResourceLocation, MobStageInfo>> DIMENSIONAL_STAGE_INFO = new HashMap<>();

    public static MobStageInfo getOrCreateStageInfo (String stage, EntityType<?> entity) {

        final MobStageInfo info = GLOBAL_STAGE_INFO.getOrDefault(entity, new MobStageInfo(stage, entity));

        GLOBAL_STAGE_INFO.put(entity, info);
        return info;
    }

    public static MobStageInfo getOrCreateStageInfo (String stage, EntityType<?> entity, ResourceLocation dimension) {

        final Map<ResourceLocation, MobStageInfo> map = DIMENSIONAL_STAGE_INFO.getOrDefault(entity, new HashMap<>());
        final MobStageInfo info = map.getOrDefault(dimension, new MobStageInfo(stage, entity, dimension));

        map.put(dimension, info);
        DIMENSIONAL_STAGE_INFO.put(entity, map);
        return info;
    }

    public static boolean hasGlobalStage (EntityType<?> entityId) {

        return GLOBAL_STAGE_INFO.get(entityId) != null;
    }

    public static boolean hasDimensionStage (EntityType<?> entityId, ResourceLocation dimension) {

        return DIMENSIONAL_STAGE_INFO.containsKey(entityId) && DIMENSIONAL_STAGE_INFO.get(entityId).get(dimension) != null;
    }

    @SubscribeEvent
    public static void checkSpawn (CheckSpawn event) {

        if (event.getEntity() != null) {

            final EntityType<?> entityType = event.getEntity().getType();
            final ResourceLocation dimension = event.getEntity().getEntityWorld().getDimensionKey().getRegistryName();

            MobStageInfo info;

            if (hasDimensionStage(entityType, dimension)) {

                info = DIMENSIONAL_STAGE_INFO.get(entityType).get(dimension);

                if (!allowSpawning(info, event)) {
                   return;
                }
            }

            if (hasGlobalStage(entityType)) {

                info = GLOBAL_STAGE_INFO.get(entityType);

                if (!allowSpawning(info, event)) {
                    return;
                }
            }
        }
    }

    private static boolean allowSpawning (MobStageInfo info, CheckSpawn event) {

        if (info != null) {

            // Check if spawners are ignored
            if (info.allowSpawners() && event.isSpawner()) {
                return true;
            }

            // Checks if players have the needed stage
            for (final PlayerEntity player : event.getWorld().getPlayers()) {

                if (GameStageHelper.hasStage(player, info.getStage()) && EntityUtils.getDistanceFromEntity(player, event.getEntity()) < info.getRange()) {
                    return true;
                }
            }

            // If a replacement exists, spawn it.
            if (info.getReplacement() != null && event.getWorld() instanceof World) {
                try {

                    final Entity entity = info.getReplacement().create((World) event.getWorld());
                    if(entity != null) {
                        entity.setPosition(event.getX(), event.getY(), event.getZ());
                        event.getWorld().addEntity(entity);
                    }
                    event.getEntity().remove();
                }

                catch (final Exception e) {

                    LOGGER.trace("Failed to spawn replacement mob!", e);
                }
            }
        }

        event.setResult(Event.Result.DENY);
        return false;
    }
}