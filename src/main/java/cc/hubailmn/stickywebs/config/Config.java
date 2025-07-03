package cc.hubailmn.stickywebs.config;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

@Data
public class Config {

  private final JavaPlugin plugin;

  private double hitboxInflateX;
  private double hitboxInflateY;
  private double hitboxInflateZ;

  private boolean disableFireworkUse;
  private boolean disableGlideInCobwebs;
  private boolean disableFlyThroughCobwebs;

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

    hitboxInflateX = config.getDouble("StickyWebs.hitbox-inflate.X-axis", -0.22);
    hitboxInflateY = config.getDouble("StickyWebs.hitbox-inflate.Y-axis", -0.2);
    hitboxInflateZ = config.getDouble("StickyWebs.hitbox-inflate.Z-axis", -0.22);

    disableFireworkUse = config.getBoolean("StickyWebs.cobwebs.disable-firework-use-in-webs", true);
    disableGlideInCobwebs = config.getBoolean("StickyWebs.cobwebs.disable-glide-In-Webs", true);
    disableFlyThroughCobwebs = config.getBoolean("StickyWebs.cobwebs.disable-fly-through-Webs", true);

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
