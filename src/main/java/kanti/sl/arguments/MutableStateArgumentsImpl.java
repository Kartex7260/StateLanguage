package kanti.sl.arguments;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MutableStateArgumentsImpl implements MutableStateArguments {

	private final Map<String, MutableStateArgument> mapArgs;

	public MutableStateArgumentsImpl(@NotNull MutableStateArgument... args) {
		mapArgs = new HashMap<>();
		for (MutableStateArgument arg : args) {
			mapArgs.put(arg.getKey(), arg);
		}
	}

	public MutableStateArgumentsImpl() {
		mapArgs = new HashMap<>();
	}

	@NotNull
	@Override
	public List<MutableStateArgument> getArguments() {
		return new ArrayList<>(mapArgs.values());
	}

	@Nullable
	@Override
	public MutableStateArgument get(@NotNull String key) {
		return mapArgs.get(key);
	}

	@NotNull
	@Override
	public MutableStateArguments put(@NotNull MutableStateArgument arg) {
		mapArgs.put(arg.getKey(), arg);
		return this;
	}

}
