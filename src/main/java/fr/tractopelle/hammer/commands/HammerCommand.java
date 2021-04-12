package fr.tractopelle.hammer.commands;

import fr.tractopelle.hammer.CorePlugin;
import fr.tractopelle.hammer.utils.ItemBuilder;
import fr.tractopelle.hammer.utils.Logger;
import fr.tractopelle.hammer.utils.command.HCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HammerCommand extends HCommand {

    private CorePlugin corePlugin;

    public HammerCommand(CorePlugin corePlugin) {
        super(corePlugin, "hammer", "hammer.admin",true);
        this.corePlugin = corePlugin;
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {

        String prefix = corePlugin.getConfiguration().getString("PREFIX");

        if(args.length != 2){

            commandSender.sendMessage(prefix + corePlugin.getConfiguration().getString("USAGE"));
            return false;

        } else {

            Player target = Bukkit.getPlayer(args[0]);

            if(target == null){

                commandSender.sendMessage(prefix + corePlugin.getConfiguration().getString("UNKNOW-PLAYER"));
                return false;

            }

            int number;

            try {

                number = Integer.parseInt(args[1]);

            } catch (NumberFormatException exception){
                commandSender.sendMessage(prefix + corePlugin.getConfiguration().getString("NOT-A-NUMBER"));
                return false;
            }

            ItemBuilder hammer = new ItemBuilder(Material.getMaterial(corePlugin.getConfiguration().getString("HAMMER.MATERIAL")), number)
                    .setName(corePlugin.getConfiguration().getString("HAMMER.NAME"))
                    .setLore(corePlugin.getConfiguration().getStringList("HAMMER.LORE"));

            commandSender.sendMessage(prefix + corePlugin.getConfiguration().getString("GIVE")
                    .replace("%number%", String.valueOf(number))
                    .replace("%player%", target.getName()));

            if (target.getInventory().firstEmpty() == -1) {
                target.getWorld().dropItem(target.getLocation(), hammer.toItemStack());
            } else {
                target.getInventory().addItem(hammer.toItemStack());
            }

        }

        return false;
    }
}
