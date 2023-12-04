package kanti.sl.arguments.values;

import org.jetbrains.annotations.NotNull;

public interface ValueSerializer {

	@NotNull
	String serialize(@NotNull Object value);

	@NotNull
	Object deserialize(@NotNull String line);

	static ValueSerializer getInstance() {
		if (ValueSerializerImpl._instance == null) {
			ValueSerializerImpl._instance = new ValueSerializerImpl();
		}
		return ValueSerializerImpl._instance;
	}

}

class ValueSerializerImpl implements ValueSerializer {

	@NotNull
	@Override
	public String serialize(@NotNull Object value) {
		Class<?> type = value.getClass();
		throw new IllegalArgumentException("Unsupported type=" + type.getName());
	}

	@NotNull
	@Override
	public Object deserialize(@NotNull String line) {
		throw new IllegalArgumentException("Unsupported type");
	}

	static ValueSerializerImpl _instance = null;

}
