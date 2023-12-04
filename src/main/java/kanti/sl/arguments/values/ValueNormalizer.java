package kanti.sl.arguments.values;

import org.jetbrains.annotations.NotNull;

public interface ValueNormalizer {

	@NotNull
	Object normalize(@NotNull Object value);

	@NotNull
	static ValueNormalizer getInstance() {
		if (ValueNormalizerImpl._instance == null) {
			ValueNormalizerImpl._instance = new ValueNormalizerImpl();
		}
		return ValueNormalizerImpl._instance;
	}

}

class ValueNormalizerImpl implements ValueNormalizer {

	@NotNull
	@Override
	public Object normalize(@NotNull Object value) {
		Class<?> type = value.getClass();
		throw new IllegalArgumentException("Unsupported value type=" + type.getName());
	}

	static ValueNormalizerImpl _instance = null;

}
