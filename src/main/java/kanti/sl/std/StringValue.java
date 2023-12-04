package kanti.sl.std;

import kanti.sl.arguments.values.LineDeterminant;
import kanti.sl.arguments.values.SupportedValue;
import kanti.sl.arguments.values.ValueNormalizer;
import kanti.sl.arguments.values.ValueSerializer;
import org.jetbrains.annotations.NotNull;

public final class StringValue {

	private static SupportedValue _instance = null;

	@NotNull
	public static SupportedValue getSupportedValue() {
		if (_instance == null) {
			_instance = SupportedValue.builder(String.class)
				.setLineDeterminant(new StringDeterminant())
				.setSerializer(new StringSerializer())
				.setNormalizer(new StringNormalizer())
				.build();
		}
		return _instance;
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

class StringDeterminant implements LineDeterminant {

	@Override
	public boolean isThis(@NotNull String line) {
		return true;
	}

}

class StringNormalizer implements ValueNormalizer {

	@NotNull
	@Override
	public Object normalize(@NotNull Object value) {
		Class<?> type = value.getClass();
		if (type.equals(String.class)) {
			return value;
		}
		throw new IllegalArgumentException("Unsupported value type=" + type.getName());
	}

}
