package me.gorgeousone.cmdframework.handler;

import me.gorgeousone.cmdframework.command.BasicCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashSet;
import java.util.Set;

/**
 * A class that handles the execution of registered commands of this framework.
 * First level commands still need to be listed in the plugin.yml
 */
public class CommandHandler implements CommandExecutor {

	private JavaPlugin plugin;
	private Set<BasicCommand> commands;
	private CommandCompleter cmdCompleter;

	public CommandHandler(JavaPlugin plugin) {

		this.plugin = plugin;
		this.commands = new HashSet<>();
		this.cmdCompleter = new CommandCompleter(plugin, this);
	}

	/**
	 * Registers a command of this framework to in order to handle it's execution.
	 * @param command command to register
	 */
	public void registerCommand(BasicCommand command) {
		commands.add(command);
		plugin.getCommand(command.getName()).setExecutor(this);
		plugin.getCommand(command.getName()).setTabCompleter(cmdCompleter);
	}

	/**
	 * Returns a list of all registered commands
	 */
	public Set<BasicCommand> getCommands() {
		return commands;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String cmdName = cmd.getName();
		
		for(BasicCommand command : commands) {
			
			if(command.matches(cmdName)) {
				command.execute(sender, args);
				return true;
			}
		}
		return false;
	}
}