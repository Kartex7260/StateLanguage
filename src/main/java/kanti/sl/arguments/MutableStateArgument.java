package kanti.sl.arguments;

import kanti.sl.SLContext;
import kanti.sl.SLContextOwner;
import kanti.sl.arguments.values.SupportedValues;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MutableStateArgument extends StateArgument {

	void setKey(@NotNull String key);

	void setValue(@NotNull Object value);

	@NotNull
	static MutableStateArgument create(
		@NotNull SLContext context,
		@NotNull String key,
		@Nullable Object value
	) {
		SupportedValues supportedValues = context.getSupportedValues();
		if (value != null)
			value = supportedValues.normalize(value);
		return new MutableStateArgumentImpl(context, key, value);
	}

	@NotNull
	static MutableStateArgument create(
		@NotNull SLContext context,
		@NotNull String key
	) {
		return create(context, key, null);
	}

}

class MutableStateArgumentImpl implements MutableStateArgument {

	@NotNull
	private String key;
	@Nullable
	private Object value;

	private final SLContext context;
	private final SupportedValues supportedValues;

	public MutableStateArgumentImpl(
		@NotNull SLContext context,
		@NotNull String key,
		@Nullable Object value
	) {
		this.key = key;
		this.value = value;

		this.context = context;
		supportedValues = context.getSupportedValues();
	}

	@Override
	public void setKey(@NotNull String key) {
		this.key = key;
	}

	@Override
	public void setValue(@NotNull Object value) {
		this.value = supportedValues.normalize(value);
	}

	@NotNull
	@Override
	public String getKey() {
		return key;
	}

	@NotNull
	@Override
	public Object getValue() {
		if (value == null)
			throw new IllegalStateException("State not initialized");
		return value;
	}

	@NotNull
	@Override
	public SLContext getContext() {
		return context;
	}

}
