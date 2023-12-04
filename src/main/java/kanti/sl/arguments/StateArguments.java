package kanti.sl.arguments;

import kanti.sl.SLContext;
import kanti.sl.SLContextOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public interface StateArguments extends SLContextOwner {

	@NotNull
	List<? extends StateArgument> getArguments();

	@Nullable
	StateArgument get(@NotNull String key);

	@NotNull
	static StateArguments create(
		@NotNull SLContext context,
		@NotNull StateArgument... args
	) {
		return new StateArgumentsImpl(context, args);
	}

}

class StateArgumentsImpl implements StateArguments {

	private final SLContext context;
	private final Map<String, StateArgument> argsMap;

	StateArgumentsImpl(
		@NotNull SLContext context,
		@NotNull StateArgument... args
	) {
		this.context = context;
		argsMap = new LinkedHashMap<>();
		for (StateArgument arg : args) {
			argsMap.put(arg.getKey(), arg);
		}
	}

	@Override
	@NotNull
	public List<StateArgument> getArguments() {
		return new ArrayList<>(argsMap.values());
	}

	@Nullable
	@Override
	public StateArgument get(@NotNull String key) {
		return argsMap.get(key);
	}

	@NotNull
	@Override
	public SLContext getContext() {
		return context;
	}

}
