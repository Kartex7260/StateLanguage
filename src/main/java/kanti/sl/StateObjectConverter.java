package kanti.sl;

import kanti.sl.arguments.MutableStateArguments;
import kanti.sl.arguments.StateArguments;
import org.jetbrains.annotations.NotNull;

public interface StateObjectConverter extends SLContextOwner {

	void setContext(@NotNull SLContext context);

	void convert(@NotNull MutableStateArguments args, @NotNull Object state);

	@NotNull
	Object convert(@NotNull StateArguments args);

}
