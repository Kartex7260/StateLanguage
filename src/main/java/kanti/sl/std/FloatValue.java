package kanti.sl.std;

import kanti.sl.arguments.values.SupportedValue;
import kanti.sl.arguments.values.ValueCheckable;
import kanti.sl.arguments.values.ValueSerializer;
import org.jetbrains.annotations.NotNull;

public class FloatValue {

	@NotNull
	public static SupportedValue.Builder getSupportedValue() {
		return SupportedValue.builder(Float.class)
			.setPrefix("FLOAT")
			.setSerializer(new FloatSerializer())
			.setCheckable(new FloatCheckable());
	}

}

class FloatSerializer implements ValueSerializer {

	@NotNull
	@Override
	public String serialize(@NotNull Object value) {
		return value.toString();
	}

	@NotNull
	@Override
	public Object deserialize(@NotNull String line) {
		return Float.parseFloat(line);
	}

}

class FloatCheckable implements ValueCheckable {

	@Override
	public boolean check(@NotNull Object value) {
		return check(value.getClass());
	}

	@Override
	public boolean check(@NotNull Class<?> type) {
		if (type.isPrimitive()) {
			return type.equals(float.class);
		} else {
			return type.equals(Float.class);
		}
	}

}
