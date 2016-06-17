package name.richardson.james.bukkit.starterkit.kit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@SerializableAs("ArmourKit")
public class ArmourKit
  implements ConfigurationSerializable, Kit
{
  private final ItemStack[] items;
  
  public static ArmourKit deserialize(Map<String, Object> map)
  {
    List<ItemStack> items = new ArrayList<ItemStack>();
    
    int i = 4;
    do
    {
      items.add(null);i--;
    } while (i >= 0);
    for (String key : map.keySet()) {
      if (!key.startsWith("==")) {
        items.set(Integer.parseInt(key), (ItemStack)map.get(key));
      }
    }
    return new ArmourKit(items);
  }
  
  public ArmourKit()
  {
    this.items = new ItemStack[0];
  }
  
  public ArmourKit(List<ItemStack> items)
  {
    this.items = ((ItemStack[])items.toArray(new ItemStack[items.size()]));
  }
  
  public ArmourKit(PlayerInventory inventory)
  {
    this.items = inventory.getArmorContents();
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
      if ((item != null) && (item.getType() != Material.AIR)) {
        map.put(Integer.toString(slot), item);
      }
    }
    return map;
  }
}
