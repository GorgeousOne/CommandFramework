package me.gorgeousone.cmdframework.handler;

import me.gorgeousone.cmdframework.command.BasicCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.LinkedList;
import java.util.List;

/**
 * A class that handles the tab completion of commands.
 */
public class CommandCompleter implements TabCompleter {

	private CommandHandler cmdHandler;

	/**
	 * Creates a CommandCompleter for the commands registered in the passed command handler
	 * @param cmdHandler command handler of commands to tab complete
	 */
	public CommandCompleter(CommandHandler cmdHandler) {
		this.cmdHandler = cmdHandler;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		
		for(BasicCommand command : cmdHandler.getCommands()) {
			
			if(command.matches(cmd.getName())) {
				List<String> tabList = new LinkedList<>();
				
				for(String tab : command.getTabList(args)) {
					if(tab.startsWith(args[args.length-1]))
						tabList.add(tab);
				}
				
				return tabList;
			}
		}
		return null;
	}
}