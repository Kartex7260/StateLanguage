package kanti.sl.std;

import kanti.sl.arguments.values.ValueNormalizer;
import org.jetbrains.annotations.NotNull;

public class ByteNormalizer implements ValueNormalizer {

	@NotNull
	@Override
	public Object normalize(@NotNull Object value) {
		Class<?> type = value.getClass();
		if (!type.equals(Byte.class))
			throw new IllegalArgumentException("Expected byte, actual = " + type.getName());
		return (int) value;
	}

}
