package kanti.sl.std;

import kanti.sl.arguments.values.ValueNormalizer;
import org.jetbrains.annotations.NotNull;

public class LongNormalizer implements ValueNormalizer {

	@NotNull
	@Override
	public Object normalize(@NotNull Object value) {
		Class<?> type = value.getClass();
		if (!type.equals(Long.class))
			throw new IllegalArgumentException("Expected long, actual = " + type.getName());
		return (int) value;
	}

}
