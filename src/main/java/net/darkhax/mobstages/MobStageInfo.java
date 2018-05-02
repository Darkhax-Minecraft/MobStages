package net.darkhax.mobstages;

public class MobStageInfo {
    
    private String stage;
    private String entityId;
    private int range;
    private int dimension;
    private boolean allowSpawners;
    private String replacement;
    
    public MobStageInfo(String stage, String entityId) {
        
        this(stage, entityId, 0);
    }
    
    public MobStageInfo(String stage, String entityId, int dimension) {
        
        this.stage = stage;
        this.entityId = entityId;
        
        this.range = 256;
        this.dimension = dimension;
        this.allowSpawners = false;
        this.replacement = "";
    }
    
    public String getStage () {
        
        return this.stage;
    }
    
    public String getEntityId () {
        
        return this.entityId;
    }
    
    public int getRange () {
        
        return this.range;
    }
    
    public int getDimension () {
        
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
    
    public void setEntityId (String entityId) {
        
        this.entityId = entityId;
    }
    
    public void setRange (int range) {
        
        this.range = range;
    }
    
    public void setDimension (int dimension) {
        
        this.dimension = dimension;
    }
    
    public String getReplacement () {
        
        return this.replacement;
    }
    
    public void setReplacement (String replacement) {
        
        this.replacement = replacement;
    }
}
