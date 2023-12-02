package kanti.sl.arguments;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public interface MutableStateArguments extends StateArguments {

	@NotNull
	@Override
	List<MutableStateArgument> getArguments();

	@Nullable
	@Override
	MutableStateArgument get(@NotNull String key);

	@NotNull MutableStateArguments put(@NotNull MutableStateArgument arg);

	@NotNull
	static MutableStateArguments create(@NotNull MutableStateArgument... args) {
		return new MutableStateArgumentsImpl(args);
	}

}
