package kanti.sl.std;

import kanti.sl.arguments.values.ValueNormalizer;
import org.jetbrains.annotations.NotNull;

public class ShortNormalizer implements ValueNormalizer {

	@NotNull
	@Override
	public Object normalize(@NotNull Object value) {
		Class<?> type = value.getClass();
		if (!type.equals(Short.class))
			throw new IllegalArgumentException("Expected short, actual = " + type.getName());
		return (int) value;
	}

}
