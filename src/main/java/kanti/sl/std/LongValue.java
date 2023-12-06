package kanti.sl.std;

import kanti.sl.arguments.values.SupportedValue;
import kanti.sl.arguments.values.ValueCheckable;
import kanti.sl.arguments.values.ValueSerializer;
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

class LongSerializer implements ValueSerializer {

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

class LongCheckable implements ValueCheckable {

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
