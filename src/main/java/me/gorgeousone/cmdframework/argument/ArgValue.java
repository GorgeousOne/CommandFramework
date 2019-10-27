package me.gorgeousone.cmdframework.argument;

import org.bukkit.ChatColor;

/**
 * When an {@link me.gorgeousone.cmdframework.command.ArgCommand} is called the parameters of the command will be stored
 * in an object of this class, which makes the parameter available as different (primitive) data types depending on the given {@link ArgType}.
 */
public class ArgValue {

	private static final String argumentTypeException = ChatColor.RED + "'%value%' is not a %type%.";

	private int intVal;
	private double decimalVal;
	private String stringVal;
	private boolean booleanVal;

	/**
	 * Creates an ArgValue using an argument passed to determine an argument type
	 * @param argument the argument needed to determine the argument type
	 * @param value the value to store
	 */
	public ArgValue(Argument argument, String value) {
		this(argument.getType(), value);
	}

	/**
	 * Creates an ArgValue using an argument type directly
	 * @param type the type the argument value should have
	 * @param value the value to store
	 */
	public ArgValue(ArgType type, String value) {
		setValue(type, value);
	}

	/**
	 * Returns the stored argument value as a string.
	 */
	public String getString() {
		return stringVal;
	}

	/**
	 * Returns the stored argument value as a int if possible.
	 */
	public int getInt() {
		return intVal;
	}

	/**
	 * Returns the stored argument value as a double if possible.
	 */
	public double getDouble() {
		return decimalVal;
	}

	/**
	 * Returns the stored argument value as a boolean if possible.
	 */
	public boolean getBoolean() {
		return booleanVal;
	}

	protected void setValue(ArgType type, String value) {

		stringVal = value;

		try {
			switch (type) {
			
			case INTEGER:
				intVal = Integer.parseInt(value);
			
			case DECIMAL:
				decimalVal = Double.parseDouble(value);
				break;

			case BOOLEAN:
				booleanVal = Boolean.parseBoolean(value);
				break;

			default:
				break;
			}
			
		} catch (Exception ex) {
			throw new IllegalArgumentException(argumentTypeException.replace("%value%", value).replace("%type%", type.simpleName()));
		}
	}
}
