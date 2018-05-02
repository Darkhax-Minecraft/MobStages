package net.darkhax.mobstages;

import java.util.HashMap;
import java.util.Map;

import net.darkhax.bookshelf.lib.LoggingHelper;
import net.darkhax.bookshelf.util.EntityUtils;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod(modid = "mobstages", name = "Mob Stages", version = "@VERSION@", dependencies = "required-after:bookshelf;required-after:gamestages@[2.0.89,);required-after:crafttweaker", certificateFingerprint = "@FINGERPRINT@")
public class MobStages {
    
    public static final LoggingHelper LOG = new LoggingHelper("Mob Stages");
    
    /**
     * A map of global stage info. This is used as a fallback if dimensional specific info does
     * not exist. The key is the mob's id, and the value is the stage info for that mob.
     */
    public static final Map<String, MobStageInfo> GLOBAL_STAGE_INFO = new HashMap<>();
    
    /**
     * A map of dimension specific mob spawning stage data. The key is the entity id. The value
     * is a second map, where the key is the dimension id, and the value is the info for that.
     * This will override the global data.
     */
    public static final Map<String, Map<Integer, MobStageInfo>> DIMENSIONAL_STAGE_INFO = new HashMap<>();
    
    @Mod.EventHandler
    public void preInit (FMLPreInitializationEvent ev) {
        
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    public static MobStageInfo getOrCreateStageInfo (String stage, String entity) {
        
        final MobStageInfo info = GLOBAL_STAGE_INFO.getOrDefault(entity, new MobStageInfo(stage, entity));
        
        GLOBAL_STAGE_INFO.put(entity, info);
        return info;
    }
    
    public static MobStageInfo getOrCreateStageInfo (String stage, String entity, int dimension) {
        
        final Map<Integer, MobStageInfo> map = DIMENSIONAL_STAGE_INFO.getOrDefault(entity, new HashMap<>());
        final MobStageInfo info = map.getOrDefault(dimension, new MobStageInfo(stage, entity, dimension));
        
        map.put(dimension, info);
        DIMENSIONAL_STAGE_INFO.put(entity, map);
        return info;
    }
    
    public static boolean hasGlobalStage (String entityId) {
        
        return GLOBAL_STAGE_INFO.get(entityId) != null;
    }
    
    public static boolean hasDimensionStage (String entityId, int dimension) {
        
        return DIMENSIONAL_STAGE_INFO.containsKey(entityId) && DIMENSIONAL_STAGE_INFO.get(entityId).get(dimension) != null;
    }
    
    @SubscribeEvent
    public void checkSpawn (CheckSpawn event) {
        
        final ResourceLocation id = EntityList.getKey(event.getEntity());
        
        if (id != null) {
            
            final String name = id.toString();
            final int dimension = event.getEntity().dimension;
            
            MobStageInfo info = null;
            
            if (hasDimensionStage(name, dimension)) {
                
                info = DIMENSIONAL_STAGE_INFO.get(name).get(dimension);
                
                if (!allowSpawning(info, event)) {
                    return;
                }
            }
            
            if (hasGlobalStage(name)) {
                
                info = GLOBAL_STAGE_INFO.get(name);
                
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
            for (final EntityPlayer player : event.getWorld().playerEntities) {
                
                if (GameStageHelper.hasStage(player, info.getStage()) && EntityUtils.getDistanceFromEntity(player, event.getEntity()) < info.getRange()) {
                    return true;
                }
            }
            
            // If a replacement exists, spawn it.
            if (info.getReplacement() != null && !info.getReplacement().isEmpty()) {
                try {
                    
                    final Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation(info.getReplacement()), event.getWorld());
                    entity.setPosition(event.getX(), event.getY(), event.getZ());
                    event.getWorld().spawnEntity(entity);
                    event.getEntity().setDead();
                }
                
                catch (final Exception e) {
                    
                    MobStages.LOG.trace("Failed to spawn replacement mob!", e);
                }
            }
        }
        
        event.setResult(Result.DENY);
        return false;
    }
    
    public static void checkEntity (String entityId) {
        
        if (!ForgeRegistries.ENTITIES.containsKey(new ResourceLocation(entityId))) {
            
            throw new IllegalArgumentException("The entity for id " + entityId + " does not exist. This is an issue!");
        }
    }
}
