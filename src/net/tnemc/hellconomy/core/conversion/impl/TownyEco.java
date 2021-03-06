package net.tnemc.hellconomy.core.conversion.impl;

import net.tnemc.core.economy.currency.Currency;
import net.tnemc.hellconomy.core.HellConomy;
import net.tnemc.hellconomy.core.conversion.Converter;
import net.tnemc.hellconomy.core.conversion.InvalidDatabaseImport;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * HellConomy Minecraft Server Plugin
 * <p>
 * Created by Daniel on 5/28/2018.
 * <p>
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
public class TownyEco extends Converter {
  private File configFile = new File("plugins/TownyEco/config.yml");
  private FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

  private String prefix = config.getString("database.table_prefix");
  @Override
  public String name() {
    return "TownyEco";
  }

  @Override
  public void mysql() throws InvalidDatabaseImport {
    db.open(dataSource);
    String table = prefix + "balances";

    try(ResultSet results = db.getConnection().createStatement().executeQuery("SELECT uuid, world, currency, balance FROM " + table + ";")) {
      while(results.next()) {
        final String world = results.getString("world");
        Currency currency = HellConomy.currencyManager().get(world, results.getString("currency"));
        if(currency == null) {
          currency = HellConomy.currencyManager().get(HellConomy.instance().getDefaultWorld());
        }
        Converter.convertedAdd(results.getString("username"),
            world, currency.name(),
            new BigDecimal(results.getDouble("balance")));
      }
    } catch(SQLException ignore) {}
    db.close();
  }

  @Override
  public void sqlite() throws InvalidDatabaseImport {
    db.open(dataSource);
    String table = prefix + "balances";

    try(ResultSet results = db.getConnection().createStatement().executeQuery("SELECT uuid, world, currency, balance FROM " + table + ";")) {
      while(results.next()) {
        final String world = results.getString("world");
        Currency currency = HellConomy.currencyManager().get(world, results.getString("currency"));
        if(currency == null) {
          currency = HellConomy.currencyManager().get(HellConomy.instance().getDefaultWorld());
        }
        Converter.convertedAdd(results.getString("username"),
            world, currency.name(),
            new BigDecimal(results.getDouble("balance")));
      }
    } catch(SQLException ignore) {}
    db.close();
  }
}
