package net.tnemc.hellconomy.core.command.money;

import net.tnemc.hellconomy.core.HellConomy;
import net.tnemc.hellconomy.core.command.TNECommand;
import org.bukkit.command.CommandSender;

/**
 * Created by creatorfromhell.
 *
 * HellConomy Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0
 * International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/
 * or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */
public class MoneyCommand extends TNECommand {
  public MoneyCommand(HellConomy plugin) {
    super(plugin);

    subCommands.add(new MoneyBalanceCommand(plugin));
    subCommands.add(new MoneyGiveCommand(plugin));
    subCommands.add(new MoneyOtherCommand(plugin));
    subCommands.add(new MoneyPayCommand(plugin));
    subCommands.add(new MoneySetCommand(plugin));
    subCommands.add(new MoneyTakeCommand(plugin));
    subCommands.add(new MoneyTopCommand(plugin));
  }

  @Override
  public String getName() {
    return "money";
  }

  @Override
  public String[] getAliases() {
    return new String[] {
        "bal", "balance", "pay", "baltop",
        "givemoney", "givebal", "setbal",
        "setmoney", "takemoney", "takebal",
        "balo", "balother"
    };
  }

  @Override
  public String getNode() {
    return "";
  }

  @Override
  public boolean console() {
    return true;
  }

  @Override
  public boolean execute(CommandSender sender, String command, String[] arguments) {
    if(command.equalsIgnoreCase("givemoney") ||
        command.equalsIgnoreCase("givebal")) {
      TNECommand sub = findSub("give");
      if(sub.canExecute(sender)) {
        return sub.execute(sender, command, arguments);
      }
    }

    if(command.equalsIgnoreCase("setmoney") ||
        command.equalsIgnoreCase("setbal")) {
      TNECommand sub = findSub("set");
      if(sub.canExecute(sender)) {
        return sub.execute(sender, command, arguments);
      }
    }

    if(command.equalsIgnoreCase("takemoney") ||
        command.equalsIgnoreCase("takebal")) {
      TNECommand sub = findSub("take");
      if(sub.canExecute(sender)) {
        return sub.execute(sender, command, arguments);
      }
    }

    if(command.equalsIgnoreCase("baltop")) {
      TNECommand sub = findSub("top");
      if(sub.canExecute(sender)) {
        return sub.execute(sender, command, arguments);
      }
    }

    if(command.equalsIgnoreCase("pay")) {
      TNECommand sub = findSub("pay");
      if(sub.canExecute(sender)) {
        return sub.execute(sender, command, arguments);
      }
    }

    if(command.equalsIgnoreCase("balance") ||
        command.equalsIgnoreCase("bal") ||
        arguments.length == 0) {
      TNECommand sub = findSub("balance");
      if(sub.canExecute(sender)) {
        return sub.execute(sender, command, arguments);
      }
    }

    if(command.equalsIgnoreCase("balother") ||
        command.equalsIgnoreCase("balo") ||
        arguments.length == 0) {
      TNECommand sub = findSub("other");
      if(sub.canExecute(sender)) {
        return sub.execute(sender, command, arguments);
      }
    }
    return super.execute(sender, command, arguments);
  }
}