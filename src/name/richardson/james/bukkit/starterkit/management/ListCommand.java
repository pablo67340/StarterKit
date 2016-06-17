package name.richardson.james.bukkit.starterkit.management;

import name.richardson.james.bukkit.starterkit.StarterKitConfiguration;
import name.richardson.james.bukkit.starterkit.utilities.formatters.ItemCountChoiceFormatter;
import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.formatters.ChoiceFormatter;
import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.formatters.DefaultColourFormatter;
import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundleByClassLocalisation;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permissible;

public class ListCommand
  extends AbstractCommand
{
  public static final String PERMISSION_ALL = "starterkit.list";

  private final StarterKitConfiguration configuration;
  private final ChoiceFormatter choiceFormatter = new ItemCountChoiceFormatter();
  private final Localisation localisation = new ResourceBundleByClassLocalisation(ListCommand.class);
  private final ColourFormatter colourFormatter = new DefaultColourFormatter();
  
  public ListCommand(StarterKitConfiguration configuration)
  {
    this.configuration = configuration;
    this.choiceFormatter.setMessage(this.colourFormatter.format(this.localisation.getMessage("header"), ColourFormatter.FormatStyle.HEADER));
  }
  
  public void execute(CommandContext commandContext)
  {
    if (isAuthorised(commandContext.getCommandSender()))
    {
      this.choiceFormatter.setArguments(new Object[] { Integer.valueOf(this.configuration.getItemCount()) });
      commandContext.getCommandSender().sendMessage(this.choiceFormatter.getMessage());
      if (this.configuration.getArmourKit().getItemCount() != 0) {
        commandContext.getCommandSender().sendMessage(ChatColor.YELLOW + this.localisation.getMessage("armour-list", new Object[] { buildKitList(this.configuration.getArmourKit().getContents()) }));
      }
      if (this.configuration.getInventoryKit().getItemCount() != 0) {
        commandContext.getCommandSender().sendMessage(ChatColor.YELLOW + this.localisation.getMessage("backpack-list", new Object[] { buildKitList(this.configuration.getInventoryKit().getContents()) }));
      }
    }
    else
    {
      commandContext.getCommandSender().sendMessage(this.colourFormatter.format(this.localisation.getMessage("no-permission"), ColourFormatter.FormatStyle.ERROR));
    }
  }
  
  public boolean isAuthorised(Permissible permissible)
  {
    if (permissible.hasPermission("starterkit.list")) {
      return true;
    }
    return false;
  }
  
  private String buildKitList(ItemStack[] items)
  {
    StringBuilder message = new StringBuilder();
    for (ItemStack item : items) {
      if (item != null)
      {
        if (item.getAmount() == 0) {
          message.append(1);
        } else {
          message.append(item.getAmount());
        }
        message.append(" ");
        message.append(item.getType().name());
        message.append(", ");
      }
    }
    message.delete(message.length() - 2, message.length());
    message.append(".");
    return message.toString();
  }
}
