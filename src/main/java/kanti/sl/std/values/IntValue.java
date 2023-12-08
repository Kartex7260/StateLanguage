package kanti.sl.std.values;

import kanti.sl.arguments.values.*;
import org.jetbrains.annotations.NotNull;

public final class IntValue {

	@NotNull
	public static SupportedValue.Builder getSupportedValue() {
		return SupportedValue.builder(Integer.class)
			.setPrefix("INTEGER")
			.setSerializer(new IntSerializer())
			.setCheckable(new IntCheckable());
	}

}

class IntSerializer extends BaseValueSerializer {

	@NotNull
	@Override
	public String serialize(@NotNull Object value) {
		return value.toString();
	}

	@NotNull
	@Override
	public Object deserialize(@NotNull String line) {
		return Integer.valueOf(line);
	}

}

class IntCheckable extends BaseValueCheckable {

	@Override
	public boolean check(@NotNull Object value) {
		return check(value.getClass());
	}

	@Override
	public boolean check(@NotNull Class<?> type) {
		if (type.isPrimitive()) {
			return type.equals(int.class);
		} else {
			return type.equals(Integer.class);
		}
	}

}
