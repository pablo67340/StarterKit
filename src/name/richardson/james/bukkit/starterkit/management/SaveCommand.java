package name.richardson.james.bukkit.starterkit.management;

import java.io.IOException;
import name.richardson.james.bukkit.starterkit.StarterKitConfiguration;
import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.formatters.DefaultColourFormatter;
import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundleByClassLocalisation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permissible;

public class SaveCommand
  extends AbstractCommand
{
  public static final String PERMISSION_ALL = "starterkit.save";
  private final ColourFormatter colourFormatter = new DefaultColourFormatter();
  private final StarterKitConfiguration configuration;
  private final Localisation localisation = new ResourceBundleByClassLocalisation(SaveCommand.class);
  private PlayerInventory inventory;
  
  public SaveCommand(StarterKitConfiguration configuration)
  {
    this.configuration = configuration;
  }
  
  public void execute(CommandContext commandContext)
  {
    if (isAuthorised(commandContext.getCommandSender()))
    {
      if (!(commandContext.getCommandSender() instanceof Player)) {
        commandContext.getCommandSender().sendMessage(this.colourFormatter.format(this.localisation.getMessage("player-command-sender-required"), ColourFormatter.FormatStyle.ERROR));
      } else {
        try
        {
          Player player = (Player)commandContext.getCommandSender();
          this.inventory = player.getInventory();
          this.configuration.setInventory(this.inventory);
          player.sendMessage(this.colourFormatter.format(this.localisation.getMessage("kit-saved"), ColourFormatter.FormatStyle.INFO));
        }
        catch (IOException e)
        {
          commandContext.getCommandSender().sendMessage(this.colourFormatter.format(this.localisation.getMessage("unable-to-save-kit"), ColourFormatter.FormatStyle.ERROR));
        }
      }
    }
    else {
      commandContext.getCommandSender().sendMessage(this.colourFormatter.format(this.localisation.getMessage("no-permission"), ColourFormatter.FormatStyle.ERROR));
    }
  }
  
  public boolean isAuthorised(Permissible permissible)
  {
    return permissible.hasPermission("starterkit.save");
  }
}
