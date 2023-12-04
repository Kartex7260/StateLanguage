package kanti.sl.arguments;

import kanti.sl.SLContext;
import kanti.sl.SLContextOwner;
import kanti.sl.arguments.values.SupportedValues;
import org.jetbrains.annotations.NotNull;

public interface StateArgument extends SLContextOwner {

	@NotNull
	String getKey();

	@NotNull
	Object getValue();

	@NotNull
	static StateArgument create(
		@NotNull SLContext context,
		@NotNull String name,
		@NotNull Object value
	) {
		SupportedValues supportedValues = context.getSupportedValues();
		Object convertedValue = supportedValues.normalize(value);
		return new StateArgumentImpl(context, name, convertedValue);
	}

}

class StateArgumentImpl implements StateArgument {

	private final String _key;
	private final Object _value;
	private final SLContext context;

	StateArgumentImpl(@NotNull SLContext context, @NotNull String key, @NotNull Object value) {
		this.context = context;
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

	@NotNull
	@Override
	public SLContext getContext() {
		return context;
	}

}
