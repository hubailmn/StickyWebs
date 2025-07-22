package cc.hubailmn.stickywebs.command;

import cc.hubailmn.stickywebs.StickyWebs;
import cc.hubailmn.utility.command.TabComplete;
import cc.hubailmn.utility.interaction.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StickyWebsCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§eUsage: /stickywebs <reload|bypass>");
            return true;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "reload" -> {
                if (!sender.hasPermission("stickywebs.command.reload")) {
                    sender.sendMessage("§cYou do not have permission to reload the configuration.");
                    return true;
                }

                StickyWebs.getInstance().reloadConfig();
                StickyWebs.getInstance().getStickyWebsConfig().reload();
                sender.sendMessage("§eStickyWebs configuration has been reloaded successfully.");

                if (sender instanceof Player player) {
                    SoundUtil.play(player, SoundUtil.SoundType.CONFIRM);
                }

            }

            case "bypass" -> {
                if (!(sender instanceof Player player)) return true;

                if (!sender.hasPermission("stickywebs.command.bypass")) {
                    sender.sendMessage("§cYou do not have permission to toggle bypass.");
                    return true;
                }

                boolean isBypassing = StickyWebs.getInstance().getBlockResetManager().toggleBypass(player.getUniqueId());

                if (isBypassing) {
                    player.sendMessage("§aYou are now bypassing block resets.");
                } else {
                    player.sendMessage("§cYou are no longer bypassing block resets.");
                }

                SoundUtil.play(player, SoundUtil.SoundType.CONFIRM);
            }

            default -> sender.sendMessage("§cUnknown subcommand. Use §e/stickywebs <reload|bypass>");
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        TabComplete tab = new TabComplete(sender, command, label, args);

        if (args.length == 1) {
            tab.add(1, new String[]{"reload", "bypass"});
        }

        return tab.build();
    }
}
