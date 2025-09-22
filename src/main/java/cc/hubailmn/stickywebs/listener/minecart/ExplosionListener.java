package cc.hubailmn.stickywebs.listener.minecart;

import cc.hubailmn.stickywebs.StickyWebs;
import cc.hubailmn.stickywebs.config.Config;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ExplosionListener implements Listener {

    @EventHandler
    public void onExplosionDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Config config = StickyWebs.getInstance().getStickyWebsConfig();

        switch (damager.getType()) {
            case PRIMED_TNT -> {
                if (config.isTntEnabled()) {
                    double multiplier = config.getTntPower() / 4.0;
                    event.setDamage(event.getDamage() * multiplier);
                }
            }

            case MINECART_TNT -> {
                if (config.isTntMinecartEnabled()) {
                    double multiplier = config.getTntMinecartPower() / 4.0;
                    event.setDamage(event.getDamage() * multiplier);
                }
            }
        }
    }
}
