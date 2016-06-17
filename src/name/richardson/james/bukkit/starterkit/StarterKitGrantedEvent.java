package name.richardson.james.bukkit.starterkit;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class StarterKitGrantedEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private final String playerName;
  private final PlayerInventory inventory;
  
  public StarterKitGrantedEvent(String playerName, PlayerInventory inventory)
  {
    this.playerName = playerName;
    this.inventory = inventory;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public String getPlayerName()
  {
    return this.playerName;
  }
  
  public PlayerInventory getInventory()
  {
    return this.inventory;
  }
  
  public int getInventoryItemCount()
  {
    int i = 0;
    for (ItemStack stack : this.inventory.getContents()) {
      if (stack != null) {
        i = stack.getAmount();
      }
    }
    return i;
  }
}
