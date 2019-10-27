package me.gorgeousone.cmdframework.command;

import me.gorgeousone.cmdframework.argument.ArgType;
import me.gorgeousone.cmdframework.argument.ArgValue;
import me.gorgeousone.cmdframework.argument.Argument;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This command can have multiple {@link Argument}s. Before {@link #onCommand(CommandSender, String[])} is called
 * it will be checked if there are enough and correct arguments for the command
 */
public abstract class ArgCommand extends BasicCommand {

	private List<Argument> arguments;

	protected ArgCommand(String name, String permission, boolean isPlayerRequired) {
		this(name, permission, isPlayerRequired, null);
	}

	protected ArgCommand(String name, String permission, boolean isPlayerRequired, ParentCommand parent) {

		super(name, permission, isPlayerRequired, parent);
		this.arguments = new ArrayList<>();
	}

	/**
	 * Returns a list of arguments that have been added to the command
	 */
	public List<Argument> getArgs() {
		return arguments;
	}

	/**
	 * Adds an argument that will be required when executing the command
	 * @param newArg a new argument
	 */
	protected void addArg(Argument newArg) {
		arguments.add(newArg);
	}

	@Override
	public String getUsage() {

		StringBuilder usage = new StringBuilder(super.getUsage());

		for (Argument arg : getArgs()) {
			usage.append(" <");
			usage.append(arg.getName());
			usage.append(">");
		}

		return usage.toString();
	}

	@Override
	public List<String> getTabList(String[] arguments) {

		if (this.arguments.size() < arguments.length)
			return new LinkedList<>();

		return this.arguments.get(arguments.length - 1).getTabList();
	}

	/**
	 * This methods is similar to {@link #onCommand(CommandSender, String[])} only that the arguments are wrapped as
	 * {@link ArgValue}s. It will only be called of there are sufficient arguments passed and if they match
	 * the required {@link ArgType}
	 * @param sender the sender of the command
	 * @param arguments the entered arguments of the command
	 * @return true if the command was executed correctly
	 */
	protected abstract boolean onCommand(CommandSender sender, ArgValue[] arguments);

	@Override
	protected boolean onCommand(CommandSender sender, String[] stringArgs) {

		int argsSize = getArgs().size();
		int stringArgsLength = stringArgs.length;

		ArgValue[] values = new ArgValue[Math.max(argsSize, stringArgsLength)];

		try {
			if (stringArgsLength >= argsSize)
				createMoreValuesThanOwnArgs(values, stringArgs);
			else
				createMoreValuesThanSenderInput(values, stringArgs);

		}catch (ArrayIndexOutOfBoundsException ex) {
			sendUsage(sender);
			return false;

		} catch (IllegalArgumentException ex) {
			sender.sendMessage(ex.getMessage());
			return false;
		}

		onCommand(sender, values);
		return true;
	}

	protected void createMoreValuesThanOwnArgs(ArgValue[] values, String[] stringArgs) {

		for (int i = 0; i < values.length; i++) {

			values[i] = i < getArgs().size() ?
					new ArgValue(getArgs().get(i), stringArgs[i]) :
					new ArgValue(ArgType.STRING, stringArgs[i]);
		}
	}

	protected void createMoreValuesThanSenderInput(ArgValue[] values, String[] stringArgs) {

		for (int i = 0; i < values.length; i++) {
			Argument arg = getArgs().get(i);

			if (i < stringArgs.length) {
				values[i] = new ArgValue(getArgs().get(i), stringArgs[i]);
				continue;
			}

			if (arg.hasDefault())
				values[i] = arg.getDefault();
			else
				throw new ArrayIndexOutOfBoundsException();
		}
	}
}