package name.richardson.james.bukkit.starterkit.kit;

import org.bukkit.inventory.ItemStack;

public abstract interface Kit
{
  public abstract ItemStack[] getContents();
  
  public abstract int getItemCount();
}
