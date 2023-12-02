package kanti.sl.arguments;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MutableStateArgumentImpl implements MutableStateArgument {

	@NotNull
	private String key;
	@Nullable
	private Object value;

	public MutableStateArgumentImpl() {
		key = "";
	}

	public MutableStateArgumentImpl(@NotNull String key) {
		this(key, null);
	}

	public MutableStateArgumentImpl(@NotNull String key, @Nullable Object value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public void setKey(@NotNull String key) {
		this.key = key;
	}

	@Override
	public void setValue(@NotNull Object value) {
		TypeChecker.Type type = TypeChecker.getType(value);
		this.value = TypeChecker.convert(value, type);
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

}
