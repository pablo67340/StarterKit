package name.richardson.james.bukkit.starterkit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import name.richardson.james.bukkit.starterkit.kit.ArmourKit;
import name.richardson.james.bukkit.starterkit.kit.InventoryKit;
import name.richardson.james.bukkit.starterkit.management.ListCommand;
import name.richardson.james.bukkit.starterkit.management.LoadCommand;
import name.richardson.james.bukkit.starterkit.management.SaveCommand;
import name.richardson.james.bukkit.utilities.command.Command;
import name.richardson.james.bukkit.utilities.command.HelpCommand;
import name.richardson.james.bukkit.utilities.command.invoker.CommandInvoker;
import name.richardson.james.bukkit.utilities.command.invoker.FallthroughCommandInvoker;
import name.richardson.james.bukkit.utilities.logging.PluginLoggerFactory;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;


public class StarterKit
  extends JavaPlugin
{
  private StarterKitConfiguration configuration;
  private final Logger logger = PluginLoggerFactory.getLogger(StarterKit.class);
  
  public StarterKit()
  {
    ConfigurationSerialization.registerClass(ArmourKit.class);
    ConfigurationSerialization.registerClass(InventoryKit.class);
  }
  
  public StarterKitConfiguration getStarterKitConfiguration()
  {
    return this.configuration;
  }
  
  public String getVersion()
  {
    return getDescription().getVersion();
  }
  
  public void onEnable()
  {
    try
    {
      loadConfiguration();
      registerCommands();
      registerListeners();
      updatePlugin();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  private void updatePlugin()
  {
    
  }
  
  private void loadConfiguration()
    throws IOException
  {
    File file = new File(getDataFolder().getAbsolutePath() + File.separatorChar + "config.yml");
    InputStream defaults = getResource("config.yml");
    this.configuration = new StarterKitConfiguration(file, defaults);
    this.logger.setLevel(this.configuration.getLogLevel());
  }
  
  protected void registerCommands()
  {
    Set<Command> commands = new HashSet<Command>();
    commands.add(new ListCommand(this.configuration));
    commands.add(new LoadCommand(this.configuration, getServer()));
    commands.add(new SaveCommand(this.configuration));
    
    HelpCommand command = new HelpCommand("sk", commands);
    CommandInvoker invoker = new FallthroughCommandInvoker(command);
    invoker.addCommands(commands);
    getCommand("sk").setExecutor(invoker);
  }
  
  protected void registerListeners()
  {
    new PlayerListener(this, getServer().getPluginManager(), this.configuration);
  }
}
