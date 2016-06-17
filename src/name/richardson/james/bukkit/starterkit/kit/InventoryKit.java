package name.richardson.james.bukkit.starterkit.kit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@SerializableAs("InventoryKit")
public class InventoryKit
  implements ConfigurationSerializable, Kit
{
  private final ItemStack[] items;
  
  public static InventoryKit deserialize(Map<String, Object> map)
  {
    List<ItemStack> items = new ArrayList<ItemStack>(36);
    
    int i = 36;
    do
    {
      items.add(null);i--;
    } while (i >= 0);
    for (String key : map.keySet()) {
      try
      {
        if (!key.startsWith("==")) {
          items.set(Integer.parseInt(key), (ItemStack)map.get(key));
        }
      }
      catch (ClassCastException e)
      {
        e.printStackTrace();
      }
    }
    return new InventoryKit(items);
  }
  
  public InventoryKit()
  {
    this.items = new ItemStack[0];
  }
  
  public InventoryKit(List<ItemStack> items)
  {
    this.items = ((ItemStack[])items.toArray(new ItemStack[items.size()]));
  }
  
  public InventoryKit(PlayerInventory inventory)
  {
    this.items = inventory.getContents();
  }
  
  public ItemStack[] getContents()
  {
    return (ItemStack[])this.items.clone();
  }
  
  public int getItemCount()
  {
    int n = 0;
    for (ItemStack item : this.items) {
      if (item != null) {
        n++;
      }
    }
    return n;
  }
  
  public Map<String, Object> serialize()
  {
    Map<String, Object> map = new HashMap<String, Object>();
    int slot = -1;
    for (ItemStack item : this.items)
    {
      slot++;
      if (item != null) {
        map.put(Integer.toString(slot), item);
      }
    }
    return map;
  }
}
