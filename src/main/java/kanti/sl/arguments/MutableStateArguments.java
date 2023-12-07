package kanti.sl.arguments;

import kanti.sl.SLContext;
import kanti.sl.arguments.values.SupportedValues;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public interface MutableStateArguments extends StateArguments {

	@NotNull
	@Override
	List<MutableStateArgument> getArguments();

	@Nullable
	@Override
	MutableStateArgument get(@NotNull String key);

	@NotNull MutableStateArgument put(@NotNull String key, @Nullable Object value);

	@Nullable
	MutableStateArgument remove(@NotNull String key);

	void clear();

	@NotNull
	static MutableStateArguments create(
		@NotNull SLContext context,
		@NotNull MutableStateArgument... args
	) {
		return new MutableStateArgumentsImpl(context, args);
	}

}

class MutableStateArgumentsImpl implements MutableStateArguments {

	private final SLContext context;
	private final Map<String, MutableStateArgument> mapArgs;

	MutableStateArgumentsImpl(
		@NotNull SLContext context,
		@NotNull MutableStateArgument... args
	) {
		this.context = context;
		mapArgs = new HashMap<>();
		for (MutableStateArgument arg : args) {
			mapArgs.put(arg.getKey(), arg);
		}
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
	public MutableStateArgument put(@NotNull String key, @Nullable Object value) {
		MutableStateArgument argument = MutableStateArgument.create(context, key, value);
		mapArgs.put(argument.getKey(), argument);
		return argument;
	}

	@Nullable
	@Override
	public MutableStateArgument remove(@NotNull String key) {
		return mapArgs.remove(key);
	}

	@Override
	public void clear() {
		mapArgs.clear();
	}

	@NotNull
	@Override
	public SLContext getContext() {
		return context;
	}

}
