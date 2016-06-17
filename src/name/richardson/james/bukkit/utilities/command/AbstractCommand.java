package name.richardson.james.bukkit.utilities.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.command.matcher.Matcher;

/**
 * This abstract implementation provides final methods for most of the methods provided the Command interface. It should be used for convenience when
 * implementing your own Commands.
 */
public abstract class AbstractCommand implements Command {

	private final CommandMetadata commandMetadata = new DefaultCommandMetadata(this.getClass());
	private final List<Matcher> matchers = new ArrayList<Matcher>();

	public final void addMatcher(Matcher matcher) {
		this.matchers.add(matcher);
	}

	public final Set<String> getArgumentMatches(CommandContext commandContext) {
		int lastArgumentIndex = commandContext.size() - 1;
		if (commandContext.size() < lastArgumentIndex || commandContext.size() == 0) return Collections.EMPTY_SET;
		if (commandContext.size() < lastArgumentIndex || getMatchers().isEmpty()) return Collections.EMPTY_SET;
		return getMatchers().get(lastArgumentIndex).matches(commandContext.getString(lastArgumentIndex));
	}

	public final String getDescription() {
		return getCommandMetadata().getDescription();
	}

	public final String getName() {
		return getCommandMetadata().getName();
	}

	public final String getUsage() {
		return getCommandMetadata().getUsage();
	}

	/**
	 * Return the CommandMetadata attached to this command.
	 *
	 * @return the command metadata.
	 */
	protected final CommandMetadata getCommandMetadata() {
		return commandMetadata;
	}

	/**
	 * Returns an ordered list of all the matchers attached to this command.
	 *
	 * @return the matchers currently attached.
	 */
	protected final List<Matcher> getMatchers() {
		return Collections.unmodifiableList(matchers);
	}

}
