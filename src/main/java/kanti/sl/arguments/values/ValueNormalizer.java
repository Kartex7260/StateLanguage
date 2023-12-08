package kanti.sl.arguments.values;

import kanti.sl.SLContext;
import kanti.sl.SLContextOwner;
import org.jetbrains.annotations.NotNull;

public interface ValueNormalizer extends SLContextOwner {

	void setContext(@NotNull SLContext context);

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

class ValueNormalizerImpl extends BaseValueNormalizer {

	@NotNull
	@Override
	public Object normalize(@NotNull Object value) {
		Class<?> type = value.getClass();
		throw new IllegalArgumentException("Unsupported value type=" + type.getName());
	}

	static ValueNormalizerImpl _instance = null;

}
