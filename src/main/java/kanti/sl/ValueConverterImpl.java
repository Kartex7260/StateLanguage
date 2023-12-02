package kanti.sl;

import kanti.sl.arguments.TypeChecker;
import org.jetbrains.annotations.NotNull;

public class ValueConverterImpl implements ValueConverter {

	private final boolean supportPrimitive;

	public ValueConverterImpl(boolean supportPrimitive) {
		this.supportPrimitive = supportPrimitive;
	}

	@NotNull
	@Override
	public String convert(@NotNull Object value) {
		if (!TypeChecker.check(value)) {
			Class<?> klazz = value.getClass();
			throw new IllegalArgumentException("Unsupported type=" + klazz.getSimpleName());
		}
		return value.toString();
	}

	@NotNull
	@Override
	public Object convert(@NotNull String line) {
		if (supportPrimitive) {
			return convertToValueWithPrimitive(line);
		} else {
			return line;
		}
	}

	@NotNull
	private Object convertToValueWithPrimitive(@NotNull String line) {
		if (line.equals("true") || line.equals("false")) {
			return Boolean.parseBoolean(line);
		} else if (line.contains(".")) {
			return Double.parseDouble(line);
		} else {
			try {
				return Integer.parseInt(line);
			} catch (NumberFormatException ex) {
				return line;
			}
		}
	}

	static class Builder implements ValueConverter.Builder {

		private boolean supportPrimitive = true;

		@NotNull
		@Override
		public ValueConverter.Builder supportPrimitive(boolean support) {
			supportPrimitive = support;
			return this;
		}

		@NotNull
		@Override
		public ValueConverter build() {
			return new ValueConverterImpl(supportPrimitive);
		}

	}

}
