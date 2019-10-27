package me.gorgeousone.cmdframework.argument;

/**
 * The different types an {@link Argument} can have. The type of an argument will be checked when an
 * {@link me.gorgeousone.cmdframework.command.ArgCommand} is called and the passed arguments are being wrapped in {@link ArgValue}s.
 */
public enum ArgType {

	INTEGER("integer"),
	DECIMAL("number"),
	STRING("string"),
	BOOLEAN("boolean");

	private String simpleName;
	
	ArgType(String simpleName) {
		this.simpleName = simpleName;
	}
	
	public String simpleName() {
		return simpleName;
	}
}