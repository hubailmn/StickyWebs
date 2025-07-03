package cc.hubailmn.stickywebs;

import cc.hubailmn.stickywebs.config.Config;
import cc.hubailmn.stickywebs.lisener.ExplosionListener;
import cc.hubailmn.stickywebs.lisener.FireworkListener;
import cc.hubailmn.stickywebs.lisener.GlideListener;
import cc.hubailmn.stickywebs.lisener.MovementListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class StickyWebs extends JavaPlugin implements Listener {

  @Getter
  private Config stickyWebsConfig;

  @Getter
  private static StickyWebs instance;

  @Override
  public void onEnable() {
    instance = this;

    getConfig().options().copyDefaults(true);
    saveDefaultConfig();

    stickyWebsConfig = new Config(this);

    Bukkit.getPluginManager().registerEvents(new FireworkListener(), this);
    Bukkit.getPluginManager().registerEvents(new GlideListener(), this);
    Bukkit.getPluginManager().registerEvents(new ExplosionListener(), this);
    Bukkit.getPluginManager().registerEvents(new MovementListener(), this);

    getCommand("stickyWebReload").setExecutor((sender, command, label, args) -> {
      if (!sender.hasPermission("StickyWeb.reload")) {
        sender.sendMessage("§cYou do not have permission to use this command.");
        return true;
      }

      reloadConfig();
      stickyWebsConfig.reload();
      sender.sendMessage("§eConfiguration reloaded successfully.");
      return true;
    });

  }

  @Override
  public void onDisable() {

  }

}
