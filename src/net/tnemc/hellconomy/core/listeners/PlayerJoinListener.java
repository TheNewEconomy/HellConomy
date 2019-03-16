package net.tnemc.hellconomy.core.listeners;

import net.tnemc.hellconomy.core.HellConomy;
import net.tnemc.hellconomy.core.common.account.HellAccount;
import net.tnemc.hellconomy.core.currency.HellCurrency;
import net.tnemc.hellconomy.core.currency.ItemCalculations;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by creatorfromhell.
 *
 * HellConomy Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0
 * International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/
 * or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */
public class PlayerJoinListener implements Listener {

  private HellConomy plugin;

  public PlayerJoinListener(HellConomy plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onJoin(PlayerJoinEvent event) {
    final Player player = event.getPlayer();
    final UUID id = player.getUniqueId();
    final String world = HellConomy.instance().normalizeWorld(player.getWorld().getName());
    final boolean first = !HellAccount.exists(id);

    if(first) {
      HellAccount.add(id, player.getName(), new Date().getTime(), true);
      HellAccount.initializeHoldings(id, world);
    }

    HellConomy.instance().getWorldManager(world).getItemCurrencies().forEach(value -> {
      final HellCurrency currency = HellConomy.currencyManager().get(world, value);
      ItemCalculations.setItems(HellConomy.currencyManager().get(world, value),
                                HellAccount.getHoldings(id, world, currency, true), player.getInventory(), false);
    });
  }
}