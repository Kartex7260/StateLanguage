package kanti.sl.std.values;

import kanti.sl.arguments.values.*;
import org.jetbrains.annotations.NotNull;

public final class DoubleValue {

	@NotNull
	public static SupportedValue.Builder getSupportedValue() {
		return SupportedValue.builder(Double.class)
			.setPrefix("DOUBLE")
			.setSerializer(new DoubleSerializer())
			.setCheckable(new DoubleCheckable());
	}

}

class DoubleSerializer extends BaseValueSerializer {

	@NotNull
	@Override
	public String serialize(@NotNull Object value) {
		return value.toString();
	}

	@NotNull
	@Override
	public Object deserialize(@NotNull String line) {
		return Double.parseDouble(line);
	}

}

class DoubleCheckable extends BaseValueCheckable {

	@Override
	public boolean check(@NotNull Object value) {
		return check(value.getClass());
	}

	@Override
	public boolean check(@NotNull Class<?> type) {
		if (type.isPrimitive()) {
			return type.equals(double.class);
		} else {
			return type.equals(Double.class);
		}
	}

}
