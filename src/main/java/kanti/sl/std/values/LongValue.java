package kanti.sl.std.values;

import kanti.sl.arguments.values.*;
import org.jetbrains.annotations.NotNull;

public final class LongValue {

	@NotNull
	public static SupportedValue.Builder getSupportedValue() {
		return SupportedValue.builder(Long.class)
			.setPrefix("LONG")
			.setSerializer(new LongSerializer())
			.setCheckable(new LongCheckable());
	}

}

class LongSerializer extends BaseValueSerializer {

	@NotNull
	@Override
	public String serialize(@NotNull Object value) {
		return value.toString();
	}

	@NotNull
	@Override
	public Object deserialize(@NotNull String line) {
		return Long.parseLong(line);
	}

}

class LongCheckable extends BaseValueCheckable {

	@Override
	public boolean check(@NotNull Object value) {
		return check(value.getClass());
	}

	@Override
	public boolean check(@NotNull Class<?> type) {
		if (type.isPrimitive()) {
			return type.equals(long.class);
		} else {
			return type.equals(Long.class);
		}
	}

}
