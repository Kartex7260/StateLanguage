package kanti.sl.std;

import kanti.sl.arguments.values.*;
import org.jetbrains.annotations.NotNull;

public final class BooleanValue {

	private static SupportedValue _instance = null;

	@NotNull
	public static SupportedValue getSupportedValue() {
		if (_instance == null) {
			_instance = SupportedValue.builder(Boolean.class)
				.setLineDeterminant(new BooleanLineDeterminant())
				.setSerializer(new BooleanSerializer())
				.setCheckable(new BooleanCheckable())
				.build();
		}
		return _instance;
	}

}

class BooleanLineDeterminant implements LineDeterminant {

	@Override
	public boolean isThis(@NotNull String line) {
		return line.equals("true") || line.equals("false");
	}

}

class BooleanSerializer implements ValueSerializer {

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

class BooleanCheckable implements ValueCheckable {

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
