package kanti.sl.std.values;

import kanti.sl.arguments.values.BaseValueSerializer;
import kanti.sl.arguments.values.SupportedValue;
import org.jetbrains.annotations.NotNull;

public final class StringValue {

	@NotNull
	public static SupportedValue.Builder getSupportedValue() {
		return SupportedValue.builder(String.class)
			.setPrefix("STRING")
			.setSerializer(new StringSerializer());
	}

}

class StringSerializer extends BaseValueSerializer {

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
