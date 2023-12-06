package kanti.sl.std;

import kanti.sl.arguments.values.*;
import org.jetbrains.annotations.NotNull;

public final class IntValue {

	private static SupportedValue _instance = null;

	@NotNull
	public static SupportedValue getSupportedValue() {
		if (_instance == null) {
			_instance = SupportedValue.builder(Integer.class)
				.setLineDeterminant(new IntDeterminant())
				.setSerializer(new IntSerializer())
				.setNormalizer(new IntNormalizer())
				.setCheckable(new IntCheckable())
				.build();
		}
		return _instance;
	}

}

class IntDeterminant implements LineDeterminant {

	@Override
	public boolean isThis(@NotNull String line) {
		try {
			Integer.valueOf(line);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

}
class IntSerializer implements ValueSerializer {

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

class IntNormalizer implements ValueNormalizer {

	@NotNull
	@Override
	public Object normalize(@NotNull Object value) {
		Class<?> type = value.getClass();
		if (type.equals(Byte.class)) {
			return (int) value;
		} else if (type.equals(Short.class)) {
			return (int) value;
		} else if (type.equals(Integer.class)) {
			return value;
		} else if (type.equals(Long.class)) {
			return (int) value;
		}
		throw new IllegalArgumentException("Unsupported value type=" + type.getName());
	}

}

class IntCheckable implements ValueCheckable {

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
