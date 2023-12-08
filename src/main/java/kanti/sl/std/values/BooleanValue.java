package kanti.sl.std.values;

import kanti.sl.arguments.values.*;
import org.jetbrains.annotations.NotNull;

public final class BooleanValue {

	@NotNull
	public static SupportedValue.Builder getSupportedValue() {
		return SupportedValue.builder(Boolean.class)
			.setPrefix("BOOLEAN")
			.setSerializer(new BooleanSerializer())
			.setCheckable(new BooleanCheckable());
	}

}

class BooleanSerializer extends BaseValueSerializer {

	@NotNull
	@Override
	public String serialize(@NotNull Object value) {
		return value.toString();
	}

	@NotNull
	@Override
	public Object deserialize(@NotNull String line) {
		return Boolean.parseBoolean(line);
	}

}

class BooleanCheckable extends BaseValueCheckable {

	@Override
	public boolean check(@NotNull Object value) {
		return check(value.getClass());
	}

	@Override
	public boolean check(@NotNull Class<?> type) {
		if (type.isPrimitive()) {
			return type.equals(boolean.class);
		} else {
			return type.equals(Boolean.class);
		}
	}

}
