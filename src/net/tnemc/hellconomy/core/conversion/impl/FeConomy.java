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
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
public class FeConomy extends Converter {
  private File configFile = new File("plugins/Fe/config.yml");
  private FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

  private String table = config.getString("mysql.tables.accounts");
  private String nameColumn = config.getString("mysql.columns.accounts.username");
  private String balanceColumn = config.getString("mysql.columns.accounts.money");
  private Currency currency = HellConomy.currencyManager().get(HellConomy.instance().getDefaultWorld());
  private double rate = 1.0;

  @Override
  public String name() {
    return "FeConomy";
  }

  @Override
  public void mysql() throws InvalidDatabaseImport {
    db.open(dataSource);

    try(ResultSet results = db.getConnection().createStatement().executeQuery("SELECT * FROM " + table + ";")) {

      final Currency currency = HellConomy.currencyManager().get(HellConomy.instance().getDefaultWorld());
      while(results.next()) {
        Converter.convertedAdd(results.getString(nameColumn),
            HellConomy.instance().getDefaultWorld(), currency.name(),
            new BigDecimal(results.getDouble(balanceColumn)));
      }
    } catch(SQLException ignore) {}
    db.close();
  }

  @Override
  public void sqlite() throws InvalidDatabaseImport {
    db.open(dataSource);

    try(ResultSet results = db.getConnection().createStatement().executeQuery("SELECT * FROM " + table + ";")) {

      final Currency currency = HellConomy.currencyManager().get(HellConomy.instance().getDefaultWorld());
      while(results.next()) {
        Converter.convertedAdd(results.getString(nameColumn),
            HellConomy.instance().getDefaultWorld(), currency.name(),
            new BigDecimal(results.getDouble(balanceColumn)));
      }
    } catch(SQLException ignore) {}
    db.close();
  }
}