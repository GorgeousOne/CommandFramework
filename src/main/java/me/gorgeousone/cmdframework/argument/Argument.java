package me.gorgeousone.cmdframework.argument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Objects of this class can be added to an {@link me.gorgeousone.cmdframework.command.ArgCommand} to define required
 * arguments for a command call.
 */
public class Argument {
	
	private String name;
	private ArgType type;
	private List<String> tabList;
	private ArgValue defValue;

	/**
	 * Creates an argument with a name for the command's usage and a type
	 * @param name name of the argument
	 * @param type type of the argument
	 */
	public Argument(String name, ArgType type) {
		this(name, type, null, new String[] {});
	}

	/**
	 * Creates an argument with a name for the command's usage, a type and a tab list
	 * @param name name of the argument
	 * @param type type of the argument
	 * @param tabList list of suggested inputs for the argument
	 */
	public Argument(String name, ArgType type, String... tabList) {
		this(name, type, null, tabList);
	}

	/**
	 * Creates an argument with a name for the command's usage, a type and a default value if there was no input passed
	 * for this argument.
	 * @param name name of the argument
	 * @param type type of the argument
	 * @param defValue predefined input for the argument
	 */
	public Argument(String name, ArgType type, ArgValue defValue) {
		this(name, type, defValue, new String[] {});
	}

	/**
	 * Creates an argument with a name for the command's usage, a type, a tab list and a default value if there was no input passed
	 * for this argument.
	 * @param name name of the argument
	 * @param type type of the argument
	 * @param tabList list of suggested inputs for the argument
	 * @param defValue default input for the argument
	 */
	public Argument(String name, ArgType type, ArgValue defValue, String... tabList) {
		
		this.name = name;
		this.type = type;
		this.defValue = defValue;

		this.tabList = new ArrayList<>();
		this.tabList.addAll(Arrays.asList(tabList));
	}

	/**
	 * Returns if the argument has a pred default value if there was no input passed for this argument.
	 */
	public boolean hasDefault() {
		return getDefault() != null;
	}

	/**
	 * Returns the default value for this argument.
	 */
	public ArgValue getDefault() {
		return defValue;
	}

	/**
	 * Returns the name shown in the usage if the argument's command.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the the type inputs for this argument should have.
	 */
	public ArgType getType() {
		return type;
	}

	/**
	 * Returns the list of suggested inputs for the argument.
	 */
	public List<String> getTabList() {
		return tabList;
	}
}