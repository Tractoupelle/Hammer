package fr.tractopelle.hammer;

import fr.tractopelle.hammer.commands.command.HammerCommand;
import fr.tractopelle.hammer.config.Config;
import fr.tractopelle.hammer.listener.PlayerListener;
import fr.tractopelle.hammer.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CorePlugin extends JavaPlugin {

    private Config configuration;
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

    }

    private void registerCommands() {
        new HammerCommand(this);
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerListener(this), this);
    }

    public Config getConfiguration() { return configuration; }

}
