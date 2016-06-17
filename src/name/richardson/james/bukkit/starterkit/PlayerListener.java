package name.richardson.james.bukkit.starterkit;

import java.util.logging.Level;
import java.util.logging.Logger;
import name.richardson.james.bukkit.starterkit.kit.ArmourKit;
import name.richardson.james.bukkit.starterkit.kit.InventoryKit;
import name.richardson.james.bukkit.utilities.listener.AbstractListener;
import name.richardson.james.bukkit.utilities.logging.PluginLoggerFactory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;

public class PlayerListener
  extends AbstractListener
{
  private final Logger logger = PluginLoggerFactory.getLogger(PlayerListener.class);
  private final InventoryKit inventory;
  private final ArmourKit armour;
  private final boolean kitOnDeath;
  
  public PlayerListener(StarterKit plugin, PluginManager pluginManager, StarterKitConfiguration configuration)
  {
    super(plugin, pluginManager);
    this.inventory = configuration.getInventoryKit();
    this.armour = configuration.getArmourKit();
    this.kitOnDeath = configuration.isProvidingKitOnDeath();
  }
  
  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void onPlayerJoin(PlayerJoinEvent event)
  {
    Player player = event.getPlayer();
    if (!player.hasPlayedBefore()) {
      giveKit(player);
    }
  }
  
  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
  public void onPlayerRespawn(PlayerRespawnEvent event)
  {
    if (this.kitOnDeath) {
      giveKit(event.getPlayer());
    }
  }
  
  private void giveKit(Player player)
  {
    this.logger.log(Level.FINE, "Granting kit: {0}", player.getName());
    PlayerInventory inventory = player.getInventory();
    inventory.clear();
    inventory.setArmorContents(this.armour.getContents());
    inventory.setContents(this.inventory.getContents());
    StarterKitGrantedEvent event = new StarterKitGrantedEvent(player.getName(), inventory);
    Bukkit.getServer().getPluginManager().callEvent(event);
  }
}
