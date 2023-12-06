package kanti.sl.std;

import kanti.sl.arguments.values.SupportedValue;
import kanti.sl.arguments.values.ValueCheckable;
import kanti.sl.arguments.values.ValueSerializer;
import org.jetbrains.annotations.NotNull;

public final class StringValue {

	@NotNull
	public static SupportedValue.Builder getSupportedValue() {
		return SupportedValue.builder(String.class)
			.setPrefix("STRING")
			.setSerializer(new StringSerializer())
			.setCheckable(new StringCheckable());
	}

}

class StringSerializer implements ValueSerializer {

	@NotNull
	@Override
	public String serialize(@NotNull Object value) {
		return value.toString();
	}

	@NotNull
	@Override
	public Object deserialize(@NotNull String line) {
		return line;
	}

}

class StringCheckable implements ValueCheckable {

	@Override
	public boolean check(@NotNull Object value) {
		return check(value.getClass());
	}

	@Override
	public boolean check(@NotNull Class<?> type) {
		return type.equals(String.class);
	}

}
