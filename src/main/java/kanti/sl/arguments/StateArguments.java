package kanti.sl.arguments;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public interface StateArguments {

	@NotNull
	List<? extends StateArgument> getArguments();

	@Nullable
	StateArgument get(@NotNull String key);

	@NotNull
	static StateArguments create(@NotNull StateArgument... args) {
		return new StateArgumentsImpl(args);
	}

}
