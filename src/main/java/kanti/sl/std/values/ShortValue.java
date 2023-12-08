package kanti.sl.std.values;

import kanti.sl.arguments.values.*;
import org.jetbrains.annotations.NotNull;

public final class ShortValue {

	@NotNull
	public static SupportedValue.Builder getSupportedValue() {
		return SupportedValue.builder(Short.class)
			.setPrefix("SHORT")
			.setSerializer(new ShortSerializer())
			.setCheckable(new ShortCheckable());
	}

}

class ShortSerializer extends BaseValueSerializer {

	@NotNull
	@Override
	public String serialize(@NotNull Object value) {
		return value.toString();
	}

	@NotNull
	@Override
	public Object deserialize(@NotNull String line) {
		return Short.parseShort(line);
	}

}

class ShortCheckable extends BaseValueCheckable {

	@Override
	public boolean check(@NotNull Object value) {
		return check(value.getClass());
	}

	@Override
	public boolean check(@NotNull Class<?> type) {
		if (type.isPrimitive()) {
			return type.equals(short.class);
		} else {
			return type.equals(Short.class);
		}
	}

}
