package name.richardson.james.bukkit.starterkit.utilities.formatters;

import name.richardson.james.bukkit.utilities.formatters.AbstractChoiceFormatter;

public class ItemCountChoiceFormatter
  extends AbstractChoiceFormatter
{
  public ItemCountChoiceFormatter()
  {
    setFormats(new String[] { "none", "one", "many" });
    setLimits(new double[] { 0.0D, 1.0D, 2.0D });
  }
}
