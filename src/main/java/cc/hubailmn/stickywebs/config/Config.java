package cc.hubailmn.stickywebs.config;

import cc.hubailmn.stickywebs.StickyWebs;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

@Data
public class Config {

    private final JavaPlugin plugin;

    private boolean debug;

    private boolean blockResetEnabled;
    private long blockResetDelay;
    private Set<Material> blockResetAllowedBlocks;
    private int maxBatch;
    private long taskIntervalTicks;

    private double hitboxInflateX;
    private double hitboxInflateY;
    private double hitboxInflateZ;

    private boolean disableFireworkUse;
    private boolean disableGlideInCobwebs;
    private boolean disableFlyThroughCobwebs;

    private boolean boostEnabled;
    private double boostMultiplier;
    private boolean requirePowered;
    private Set<Material> validBoostRails;

    private boolean tntEnabled;
    private float tntPower;

    private boolean tntMinecartEnabled;
    private float tntMinecartPower;

    public Config(JavaPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();

        debug = config.getBoolean("StickyWebs.debug", false);

        blockResetEnabled = config.getBoolean("StickyWebs.block-reset.enabled", true);
        blockResetDelay = config.getLong("StickyWebs.block-reset.delay-seconds", 180L) * 20;
        blockResetAllowedBlocks = new HashSet<>();

        for (String name : config.getStringList("StickyWebs.block-reset.blocks")) {
            try {
                blockResetAllowedBlocks.add(Material.valueOf(name.toUpperCase()));
            } catch (IllegalArgumentException e) {
                StickyWebs.getInstance().getLogger().warning("[BlockReset] Invalid block type in config: " + name);
            }
        }

        maxBatch = config.getInt("StickyWebs.block-reset.max-batch-size", 100);
        taskIntervalTicks = config.getLong("StickyWebs.block-reset.task-interval-ticks", 5L);

        hitboxInflateX = config.getDouble("StickyWebs.hitbox-inflate.X-axis", -0.22);
        hitboxInflateY = config.getDouble("StickyWebs.hitbox-inflate.Y-axis", -0.2);
        hitboxInflateZ = config.getDouble("StickyWebs.hitbox-inflate.Z-axis", -0.22);

        disableFireworkUse = config.getBoolean("StickyWebs.cobwebs.disable-firework-use-in-webs", true);
        disableGlideInCobwebs = config.getBoolean("StickyWebs.cobwebs.disable-glide-In-Webs", true);
        disableFlyThroughCobwebs = config.getBoolean("StickyWebs.cobwebs.disable-fly-through-Webs", true);

        boostEnabled = config.getBoolean("StickyWebs.explosives.tnt-minecart-boost.enabled");
        boostMultiplier = config.getDouble("StickyWebs.explosives.tnt-minecart-boost.boost-multiplier", 10);
        requirePowered = config.getBoolean("StickyWebs.explosives.tnt-minecart-boost.must-be-powered", true);

        validBoostRails = new HashSet<>();
        for (String name : config.getStringList("StickyWebs.explosives.tnt-minecart-boost.valid-rail-blocks")) {
            try {
                validBoostRails.add(Material.valueOf(name.toUpperCase()));
            } catch (IllegalArgumentException e) {
                StickyWebs.getInstance().getLogger().warning("[TntBoost] Invalid rail type in config: " + name);
            }
        }

        tntEnabled = config.getBoolean("StickyWebs.explosives.tnt.enabled", true);
        tntPower = (float) config.getDouble("StickyWebs.explosives.tnt.explosion-power", 3.0);

        tntMinecartEnabled = config.getBoolean("StickyWebs.explosives.tnt-minecart.enabled", true);
        tntMinecartPower = (float) config.getDouble("StickyWebs.explosives.tnt-minecart.explosion-power", 5.0);
    }

    public void reload() {
        plugin.reloadConfig();
        load();
    }
}
