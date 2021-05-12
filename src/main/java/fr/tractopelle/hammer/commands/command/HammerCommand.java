package fr.tractopelle.hammer.commands.command;

import fr.tractopelle.hammer.CorePlugin;
import fr.tractopelle.hammer.commands.HCommand;
import fr.tractopelle.hammer.item.ItemBuilder;
import fr.tractopelle.hammer.item.RItemUnsafe;
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
                    .setListLore(corePlugin.getConfiguration().getStringList("HAMMER.LORE"));

            RItemUnsafe rItemUnsafe = new RItemUnsafe(hammer);
            rItemUnsafe.setString("identifier", "hammer");

            commandSender.sendMessage(prefix + corePlugin.getConfiguration().getString("GIVE")
                    .replace("%number%", String.valueOf(number))
                    .replace("%player%", target.getName()));

            if (target.getInventory().firstEmpty() == -1) {
                target.getWorld().dropItem(target.getLocation(), rItemUnsafe.toItemBuilder().toItemStack());
            } else {
                target.getInventory().addItem(rItemUnsafe.toItemBuilder().toItemStack());
            }

        }

        return false;
    }
}
