package me.gorgeousone.cmdframework.command;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This command can have any kind of sub commands which can be added with {@link #addChild(BasicCommand)}
 */
public abstract class ParentCommand extends BasicCommand {
	
	private List<BasicCommand> children;
	private String childrenType;

	/**
	 * Creates a parent command with a name for the type children of this command. The name will be shown as parameter
	 * in the usage of this command.
	 * @param name the name of the command
	 * @param permission the permission required to run this command
	 * @param isPlayerRequired option to limit this command to player usage only
	 * @param childrenType the name for the type of children commands
	 */
	protected ParentCommand(String name, String permission, boolean isPlayerRequired, String childrenType) {
		this(name, permission, isPlayerRequired, childrenType, null);
	}

	/**
	 * Creates a parent command with a name for the type children of this command. The name will be shown as parameter
	 * in the usage of this command. The command will also have a parent.
	 * @param name the name of the command
	 * @param permission the permission required to run this command
	 * @param isPlayerRequired option to limit this command to player usage only
	 * @param childrenType the name for the type of children commands
	 * @param parent parent command of this command
	 */
	protected ParentCommand(String name, String permission, boolean isPlayerRequired, String childrenType, ParentCommand parent) {
		
		super(name, permission, isPlayerRequired, parent);

		this.childrenType = "<" + childrenType + ">";
		this.children = new ArrayList<>();
	}

	/**
	 * Returns a list of children of this command.
	 */
	public List<BasicCommand> getChildren() {
		return children;
	}

	/**
	 * Adds a child command to this command.
	 * @param child a child command
	 */
	public void addChild(BasicCommand child) {
		children.add(child);
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] arguments) {
		
		if(arguments.length == 0) {
			sendUsage(sender);
			return false;
		}
		
		for(BasicCommand child : getChildren()) {
			
			if(child.matches(arguments[0]))
				return child.onCommand(sender, Arrays.copyOfRange(arguments, 1, arguments.length));
		}
		
		sendUsage(sender);
		return false;
	}
	
	@Override
	public String getUsage() {
		return super.getUsage() + " " + childrenType;
	}

	/**
	 * Returns a trimmed usage of this command so children commands can add their own usage to it.
	 */
	public String getParentUsage() {
		return super.getUsage();
	}

	@Override
	public List<String> getTabList(String[] arguments) {

		if(arguments.length == 1) {
			
			List<String> tabList = new LinkedList<>();
			
			for(BasicCommand child : getChildren())
				tabList.add(child.getName());
			
			return tabList;
		}
		
		for(BasicCommand child : getChildren()) {
			
			if(child.matches(arguments[0]))
				return child.getTabList(Arrays.copyOfRange(arguments, 1, arguments.length));
		}
		
		return new LinkedList<>();
	}
}