package cc.hubailmn.stickywebs;

import cc.hubailmn.stickywebs.command.StickyWebsCommand;
import cc.hubailmn.stickywebs.config.Config;
import cc.hubailmn.stickywebs.lisener.blockreset.BlockPlacedListener;
import cc.hubailmn.stickywebs.lisener.cobweb.FireworkListener;
import cc.hubailmn.stickywebs.lisener.cobweb.GlideListener;
import cc.hubailmn.stickywebs.lisener.cobweb.MovementListener;
import cc.hubailmn.stickywebs.lisener.minecart.ExplosionListener;
import cc.hubailmn.stickywebs.lisener.minecart.TntMinecartBoostListener;
import cc.hubailmn.stickywebs.util.BlockResetManager;
import cc.hubailmn.utility.interaction.CSend;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class StickyWebs extends JavaPlugin implements Listener {

    @Getter
    private static StickyWebs instance;

    private Config stickyWebsConfig;
    private BlockResetManager blockResetManager;

    @Override
    public void onEnable() {
        instance = this;

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        CSend.init(getDataFolder());
        this.stickyWebsConfig = new Config(this);
        this.blockResetManager = new BlockResetManager();

        Bukkit.getPluginManager().registerEvents(new ExplosionListener(), this);
        Bukkit.getPluginManager().registerEvents(new FireworkListener(), this);
        Bukkit.getPluginManager().registerEvents(new GlideListener(), this);
        Bukkit.getPluginManager().registerEvents(new MovementListener(), this);
        Bukkit.getPluginManager().registerEvents(new TntMinecartBoostListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlacedListener(), this);

        var reloadCommand = getCommand("stickyWeb");

        if (reloadCommand != null) {
            reloadCommand.setExecutor(new StickyWebsCommand());
        }

        CSend.prefixed("§ePlugin §aStickyWebs§e has been §aenabled§e successfully!");
        CSend.prefixed("§eNeed help or want to join the community?");
        CSend.prefixed("§eJoin the Discord: §dhttps://discord.hubailmn.cc");
    }

    @Override
    public void onDisable() {
        CSend.prefixed("§ePlugin §aStickyWebs§e has been §cdisabled§e successfully!");
    }

}
