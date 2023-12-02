package kanti.sl.objects;

import kanti.sl.arguments.StateArgument;
import kanti.sl.arguments.StateArguments;
import org.jetbrains.annotations.NotNull;

public interface StateObject {

	@NotNull
	String getName();

	@NotNull
	StateArguments getArguments();

	@NotNull
	static StateObject create(@NotNull String name, @NotNull StateArgument... args) {
		return new StateObjectImpl(name, args);
	}

}
