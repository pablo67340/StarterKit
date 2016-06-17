package name.richardson.james.bukkit.starterkit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import name.richardson.james.bukkit.starterkit.kit.ArmourKit;
import name.richardson.james.bukkit.starterkit.kit.InventoryKit;
import name.richardson.james.bukkit.utilities.persistence.configuration.SimplePluginConfiguration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.PlayerInventory;

public class StarterKitConfiguration
  extends SimplePluginConfiguration
{
  private InventoryKit inventory;
  private ArmourKit armour;
  
  public StarterKitConfiguration(File file, InputStream defaults)
    throws IOException
  {
    super(file, defaults);
    setDefaultKit();
    ConfigurationSection section = getConfiguration().getConfigurationSection("kit");
    this.armour = ((ArmourKit)section.get("armour"));
    this.inventory = ((InventoryKit)section.get("backpack"));
  }
  
  public ArmourKit getArmourKit()
  {
    return this.armour;
  }
  
  public InventoryKit getInventoryKit()
  {
    return this.inventory;
  }
  
  public int getItemCount()
  {
    return this.armour.getItemCount() + this.inventory.getItemCount();
  }
  
  public boolean isProvidingKitOnDeath()
  {
    return getConfiguration().getBoolean("provide-kit-on-death");
  }
  
  public void setInventory(PlayerInventory inventory)
    throws IOException
  {
    ConfigurationSection section = getConfiguration().getConfigurationSection("kit");
    this.inventory = new InventoryKit(inventory);
    section.set("backpack", this.inventory);
    this.armour = new ArmourKit(inventory);
    section.set("armour", this.armour);
    save();
  }
  
  private void setDefaultKit()
    throws IOException
  {
    if (!getConfiguration().isConfigurationSection("kit"))
    {
      ConfigurationSection section = getConfiguration().createSection("kit");
      section.set("backpack", new InventoryKit());
      section.set("armour", new ArmourKit());
    }
    save();
  }
}
