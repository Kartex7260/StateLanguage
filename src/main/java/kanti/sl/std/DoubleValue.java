package kanti.sl.std;

import kanti.sl.arguments.values.*;
import org.jetbrains.annotations.NotNull;

public final class DoubleValue {

	private static SupportedValue _instance = null;

	@NotNull
	public static SupportedValue getSupportedValue() {
		if (_instance == null) {
			_instance = SupportedValue.builder(Double.class)
				.setLineDeterminant(new DoubleDeterminant())
				.setSerializer(new DoubleSerializer())
				.setNormalizer(new DoubleNormalizer())
				.setCheckable(new DoubleCheckable())
				.build();
		}
		return _instance;
	}

}

class DoubleSerializer implements ValueSerializer {

	@NotNull
	@Override
	public String serialize(@NotNull Object value) {
		return value.toString();
	}

	@NotNull
	@Override
	public Object deserialize(@NotNull String line) {
		return Double.parseDouble(line);
	}

}

class DoubleDeterminant implements LineDeterminant {

	@Override
	public boolean isThis(@NotNull String line) {
		try {
			Double.parseDouble(line);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

}

class DoubleNormalizer implements ValueNormalizer {

	@NotNull
	@Override
	public Object normalize(@NotNull Object value) {
		Class<?> type = value.getClass();
		if (type.equals(Float.class)) {
			return (Double) value;
		} else if (type.equals(Double.class)) {
			return value;
		}
		throw new IllegalArgumentException("Unsupported value type=" + type.getName());
	}

}

class DoubleCheckable implements ValueCheckable {

	@Override
	public boolean check(@NotNull Object value) {
		return check(value.getClass());
	}

	@Override
	public boolean check(@NotNull Class<?> type) {
		if (type.isPrimitive()) {
			return type.equals(double.class);
		} else {
			return type.equals(Double.class);
		}
	}

}
