package me.gorgeousone.cmdframework.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * This class is the parent of all other types of commands classes. It can be used to create a command without further arguments or
 * any sub commands. Any first level command still has to be listed in the plugin.yml.
 */
public abstract class BasicCommand {
	
	private String name;
	private String permission;
	private boolean isPlayerRequired;

	private Set<String> aliases;
	private ParentCommand parent;

	/**
	 * Creates a basic command without a parent command.
	 * @param name the name of the command
	 * @param permission the permission required to run this command
	 * @param isPlayerRequired option to limit this command to player usage only
	 */
	protected BasicCommand(String name, String permission, boolean isPlayerRequired) {
		this(name, permission, isPlayerRequired,null);
	}

	/**
	 * Creates a basic command with a parent command defined.
	 * @param name the name of the command
	 * @param permission the permission required to run this command
	 * @param isPlayerRequired option to limit this command to player usage only
	 * @param parent the parent command of this command, cannot be changed afterwards
	 */
	protected BasicCommand(String name, String permission, boolean isPlayerRequired, ParentCommand parent) {
		
		this.name = name;
		this.permission = permission;
		this.isPlayerRequired = isPlayerRequired;
		this.parent = parent;

		aliases = new HashSet<>();
		aliases.add(name);
	}

	public String getName() {
		return name;
	}
	
	public String getPermission() {
		return permission;
	}

	/**
	 * Methods that checks if a given string matches with the name or any alias of the command.
	 * @param alias possible alias of the command
	 */
	public boolean matches(String alias) {
		return aliases.contains(alias.toLowerCase());
	}

	/**
	 * Adds an alias to the command.
	 * @param newAlias new alias for the command
	 */
	protected void addAlias(String newAlias) {
		aliases.add(newAlias.toLowerCase());
	}

	/**
	 * Returns if the command was created with a parent command given.
	 */
	public boolean isChild() {
		return parent != null;
	}

	/**
	 * Returns the parent command of this command
	 * @return null if no parent command defined
	 */
	public ParentCommand getParent() {
		return parent;
	}

	/**
	 * This methods is run if all requirements of the command are met, e.g.
	 * sufficient permissions of the sender and if really a player is running the command.
	 * @param sender the sender of the argument
	 * @param arguments the passed arguments
	 * @return true if the command was executed correctly
	 */
	protected abstract boolean onCommand(CommandSender sender, String[] arguments);

	/**
	 * Calls {@link #onCommand(CommandSender, String[])} if all requirements defined before are met.
	 * @param sender command sender (a player or console input etc.)
	 * @param arguments passed arguments
	 */
	public boolean execute(CommandSender sender, String[] arguments) {
		
		if(isPlayerRequired && !(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
			return false;
		}
		
		if(permission != null && !sender.hasPermission(getPermission())) {
			sender.sendMessage(ChatColor.RED + "You do not have the permission for this command.");
			return false;
		}

		return onCommand(sender, arguments);
	}

	/**
	 * Returns a tab list based on the already entered arguments.
	 * @param arguments arguments entered in command line
	 */
	public List<String> getTabList(String[] arguments) {
		return new LinkedList<>();
	}

	/**
	 * Returns the automatically generated usage of the command.
	 */
	public String getUsage() {

		if(isChild())
			return getParent().getParentUsage() + " " + getName();
		else
			return "/" + getName();
	}

	/**
	 * Sends the usage of the command to a command sender.
	 * @param sender sender to send the usage to
	 */
	public void sendUsage(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Usage: " + getUsage());
	}
}