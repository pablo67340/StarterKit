/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractPluginUpdater.java is part of BukkitUtilities.

 BukkitUtilities is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.utilities.updater;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;



import name.richardson.james.bukkit.utilities.logging.PluginLoggerFactory;

/**
 * This abstract class provides a default implementation for common methods of {@link PluginUpdater}. In addition it
 * defines a list of branches and states that may be supported by Updaters.
 */
public abstract class AbstractPluginUpdater implements PluginUpdater {

	private final Branch branch;
	private final String name;
	private final PluginUpdater.State state;
	private final String version;
	private final Logger logger;

	public AbstractPluginUpdater(PluginDescriptionFile pluginDescriptionFile, PluginUpdater.Branch branch, PluginUpdater.State state) {
		this.name = pluginDescriptionFile.getName();
		this.version = pluginDescriptionFile.getVersion();
		this.branch = branch;
		this.state = state;
		this.logger = PluginLoggerFactory.getLogger(this.getClass());
	}

	public final Branch getBranch() {
		return branch;
	}

	public final String getName() {
		return name;
	}

	public final PluginUpdater.State getState() {
		return this.state;
	}

	public final String getLocalVersion() {
		return version;
	}

	public final Logger getLogger() {
		return logger;
	}

	public boolean isNewVersionAvailable() {

			return false;
		
	}

}