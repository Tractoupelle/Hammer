package fr.tractopelle.hammer;

import fr.tractopelle.hammer.commands.HammerCommand;
import fr.tractopelle.hammer.config.Config;
import fr.tractopelle.hammer.listener.HammerListener;
import fr.tractopelle.hammer.manager.HammerManager;
import fr.tractopelle.hammer.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Collectors;

public class CorePlugin extends JavaPlugin {

    private Config configuration;
    private HammerManager hammerManager;
    private final Logger log = new Logger(this.getDescription().getFullName());

    @Override
    public void onEnable() {

        init();

    }

    private void init() {

        registerListeners();

        registerCommands();

        this.configuration = new Config(this, "config");

        log.info("=======================================", Logger.LogType.SUCCESS);
        log.info(" Plugin initialization in progress...", Logger.LogType.SUCCESS);
        log.info(" Author: Tractopelle#4020", Logger.LogType.SUCCESS);
        log.info("=======================================", Logger.LogType.SUCCESS);

        this.hammerManager = new HammerManager(
                Material.getMaterial(configuration.getString("HAMMER.MATERIAL")),
                configuration.getString("HAMMER.NAME"),
                configuration.getStringList("HAMMER.LORE"),
                configuration.getStringList("BLACKLIST-BLOCKS").stream().map(Material::getMaterial).collect(Collectors.toList()));

    }

    private void registerCommands() {
        new HammerCommand(this);
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new HammerListener(this), this);
    }

    public Config getConfiguration() { return configuration; }

    public HammerManager getHammerManager() { return hammerManager; }

}
