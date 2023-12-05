package kanti.sl;

import kanti.sl.arguments.MutableStateArguments;
import kanti.sl.arguments.StateArguments;
import org.jetbrains.annotations.NotNull;

public interface StateObjectConverter {

	void convert(@NotNull MutableStateArguments args, @NotNull Object state);

	@NotNull
	Object convert(@NotNull StateArguments args);

}
