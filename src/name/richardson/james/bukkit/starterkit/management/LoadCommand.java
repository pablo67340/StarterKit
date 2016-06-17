package name.richardson.james.bukkit.starterkit.management;

import name.richardson.james.bukkit.starterkit.StarterKitConfiguration;
import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.formatters.DefaultColourFormatter;
import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundleByClassLocalisation;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permissible;

public class LoadCommand
  extends AbstractCommand
{
  public static final String PERMISSION_ALL = "starterkit.load";
  public static final String PERMISSION_SELF = "starterkit.load.self";
  public static final String PERMISSION_OTHERS = "starterkit.load.others";
  private final StarterKitConfiguration configuration;
  private final Server server;
  private final Localisation localisation = new ResourceBundleByClassLocalisation(LoadCommand.class);
  private final ColourFormatter colourFormatter = new DefaultColourFormatter();
  private Player player;
  
  public LoadCommand(StarterKitConfiguration configuration, Server server)
  {
    this.configuration = configuration;
    this.server = server;
  }
  
  public void execute(CommandContext context)
  {
    if (!setPlayer(context)) {
      return;
    }
    if (!hasPermission(context.getCommandSender())) {
      return;
    }
    ItemStack[] inventory = this.configuration.getInventoryKit().getContents();
    ItemStack[] armour = this.configuration.getArmourKit().getContents();
    this.player.getInventory().setContents(inventory);
    this.player.getInventory().setArmorContents(armour);
    this.player.sendMessage(this.colourFormatter.format(this.localisation.getMessage("kit-loaded"), ColourFormatter.FormatStyle.INFO));
  }
  
  private boolean hasPermission(CommandSender sender)
  {
    boolean isSenderTargetingSelf = this.player.getName().equalsIgnoreCase(sender.getName());
    if ((sender.hasPermission("starterkit.load.self")) && (isSenderTargetingSelf)) {
      return true;
    }
    if ((sender.hasPermission("starterkit.load.others")) && (!isSenderTargetingSelf)) {
      return true;
    }
    sender.sendMessage(this.colourFormatter.format(this.localisation.getMessage("no-permission"), ColourFormatter.FormatStyle.ERROR));
    return false;
  }
  
  private boolean setPlayer(CommandContext context)
  {
    this.player = null;
    if (!context.has(0))
    {
      if ((context.getCommandSender() instanceof Player)) {
        this.player = ((Player)context.getCommandSender());
      }
    }
    else {
      this.player = this.server.getPlayer(context.getString(0));
    }
    if (this.player == null)
    {
      context.getCommandSender().sendMessage(this.colourFormatter.format(this.localisation.getMessage("must-specify-player"), ColourFormatter.FormatStyle.ERROR));
      return false;
    }
    return true;
  }
  
  public boolean isAuthorised(Permissible permissible)
  {
    if (permissible.hasPermission("starterkit.load")) {
      return true;
    }
    if (permissible.hasPermission("starterkit.load.self")) {
      return true;
    }
    if (permissible.hasPermission("starterkit.load.others")) {
      return true;
    }
    return false;
  }
}
