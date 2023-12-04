package kanti.sl.std;

import kanti.sl.arguments.values.ValueNormalizer;
import org.jetbrains.annotations.NotNull;

public class FloatNormalizer implements ValueNormalizer {

	@NotNull
	@Override
	public Object normalize(@NotNull Object value) {
		Class<?> type = value.getClass();
		if (!type.equals(Float.class))
			throw new IllegalArgumentException("Expected float, actual = " + type.getName());
		return (double) value;
	}

}
