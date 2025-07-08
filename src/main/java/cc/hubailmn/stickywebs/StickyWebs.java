package cc.hubailmn.stickywebs;

import cc.hubailmn.stickywebs.config.Config;
import cc.hubailmn.stickywebs.lisener.ExplosionListener;
import cc.hubailmn.stickywebs.lisener.TntMinecartBoostListener;
import cc.hubailmn.stickywebs.lisener.cobweb.FireworkListener;
import cc.hubailmn.stickywebs.lisener.cobweb.GlideListener;
import cc.hubailmn.stickywebs.lisener.cobweb.MovementListener;
import cc.hubailmn.utility.interaction.CSend;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class StickyWebs extends JavaPlugin implements Listener {

  @Getter
  private static StickyWebs instance;

  @Getter
  private Config stickyWebsConfig;

  @Override
  public void onEnable() {
    instance = this;

    getConfig().options().copyDefaults(true);
    saveDefaultConfig();

    CSend.init(getDataFolder());
    stickyWebsConfig = new Config(this);

    Bukkit.getPluginManager().registerEvents(new ExplosionListener(), this);
    Bukkit.getPluginManager().registerEvents(new FireworkListener(), this);
    Bukkit.getPluginManager().registerEvents(new GlideListener(), this);
    Bukkit.getPluginManager().registerEvents(new MovementListener(), this);
    Bukkit.getPluginManager().registerEvents(new TntMinecartBoostListener(), this);

    var reloadCommand = getCommand("stickyWebReload");

    if (reloadCommand != null) {
      reloadCommand.setExecutor((sender, command, label, args) -> {
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

    getLogger().info(
      """
        §7Plugin §aStickyWebs §7enabled successfully!
        §7Need help or want to join the community?
        §7Join the Discord: §9https://discord.hubailmn.cc
        """
    );
  }

  @Override
  public void onDisable() {

  }

}
