package net.tnemc.hellconomy.core.common.account;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.DbName;
import org.javalite.activejdbc.annotations.Table;

import java.math.BigDecimal;
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

@DbName("HellConomy")
@Table("hellco_balances")
@CompositePK({"balance_owner", "balance_server", "balance_world", "balance_currency"})
public class Balance extends Model {

  //balance_owner
  //balance_server
  //balance_world
  //balance_currency
  //balance_amount

  public static boolean add(UUID owner, String server, String world, String currency, BigDecimal amount) {
    boolean exists = exists(owner, server, world, currency);
    Balance balance = (exists)? getBalance(owner, server, world, currency) : new Balance();

    balance.set("balance_owner", owner.toString());
    balance.set("balance_server", server);
    balance.set("balance_world", world);
    balance.set("balance_currency", currency);
    balance.set("balance_amount", amount);

    return balance.saveIt();
  }

  public static BigDecimal addBalanceValue(UUID owner, String server, String world, String currency, BigDecimal amount) {
    final BigDecimal result = (exists(owner, server, world, currency))? getBalanceValue(owner, server, world, currency).add(amount) : amount;

    add(owner, server, world, currency, result);

    return result;
  }

  public static BigDecimal subBalanceValue(UUID owner, String server, String world, String currency, BigDecimal amount) {
    final BigDecimal result = (exists(owner, server, world, currency))? getBalanceValue(owner, server, world, currency).subtract(amount) : BigDecimal.ZERO;

    add(owner, server, world, currency, result);

    return result;
  }

  public static BigDecimal getBalanceValue(UUID owner, String server, String world, String currency) {
    return getBalance(owner, server, world, currency).getBigDecimal("balance_amount");
  }

  public static Balance getBalance(UUID owner, String server, String world, String currency) {
    return Balance.findFirst("balance_owner = ? AND balance_server = ? AND balance_world = ? AND balance_currency = ?", owner.toString(), server, world, currency);
  }

  public static boolean exists(UUID owner, String server, String world, String currency) {
    return Balance.findFirst("balance_owner = ? AND balance_server = ? AND balance_world = ? AND balance_currency = ?", owner.toString(), server, world, currency) != null;
  }

  public static void delete(UUID owner, String server, String world, String currency) {
    Balance.delete("balance_owner = ? AND balance_server = ? AND balance_world = ? AND balance_currency = ?", owner.toString(), server, world, currency);
  }
}