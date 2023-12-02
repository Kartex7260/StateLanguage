package kanti.sl.objects;

import kanti.sl.arguments.StateArgument;
import kanti.sl.arguments.StateArguments;
import org.jetbrains.annotations.NotNull;

public class StateObjectImpl implements StateObject {

	private final String name;
	private final StateArguments args;

	public StateObjectImpl(@NotNull String name, @NotNull StateArgument... args) {
		this.name = name;
		this.args = StateArguments.create(args);
	}

	@NotNull
	@Override
	public String getName() {
		return name;
	}

	@NotNull
	@Override
	public StateArguments getArguments() {
		return args;
	}

}
