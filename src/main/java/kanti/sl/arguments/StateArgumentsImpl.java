package kanti.sl.arguments;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class StateArgumentsImpl implements StateArguments {

	private final Map<String, StateArgument> argsMap;

	public StateArgumentsImpl(@NotNull StateArgument... args) {
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

}
