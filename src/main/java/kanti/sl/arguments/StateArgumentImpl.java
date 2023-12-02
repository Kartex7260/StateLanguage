package kanti.sl.arguments;

import org.jetbrains.annotations.NotNull;

public class StateArgumentImpl implements StateArgument {

	private final String _key;
	private final Object _value;

	public StateArgumentImpl(@NotNull String key, @NotNull Object value) {
		_key = key;
		_value = value;
	}

	@NotNull
	@Override
	public String getKey() {
		return _key;
	}

	@NotNull
	@Override
	public Object getValue() {
		return _value;
	}

}
