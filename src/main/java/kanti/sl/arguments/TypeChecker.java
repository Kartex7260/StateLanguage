package kanti.sl.arguments;

import org.jetbrains.annotations.NotNull;

public final class TypeChecker {

	@NotNull
	public static Type getType(@NotNull Object value) {
		Class<?> type = value.getClass();
		if (type.equals(Byte.class)) {
			return Type.ConvertToInt;
		} else if (type.equals(Short.class)) {
			return Type.ConvertToInt;
		} else if (type.equals(Integer.class)) {
			return Type.Support;
		} else if (type.equals(Long.class)) {
			return Type.ConvertToInt;
		} else if (type.equals(Float.class)) {
			return Type.ConvertToDouble;
		} else if (type.equals(Double.class)) {
			return Type.Support;
		} else if (type.equals(Boolean.class)) {
			return Type.Support;
		} else if (type.equals(String.class)) {
			return Type.Support;
		} else {
			return Type.Unsupported;
		}
	}

	@NotNull
	public static Object convert(@NotNull Object value, @NotNull Type type) {
		String valueString = value.toString();
		switch (type) {
			case Support:
				return value;
			case ConvertToInt:
				return Integer.parseInt(valueString);
			case ConvertToDouble:
				return Double.parseDouble(valueString);
			case Unsupported:
				Class<?> klazz = value.getClass();
				throw new IllegalStateException("Unsupported type=" + klazz.getSimpleName());
		}
		Class<?> klazz = value.getClass();
		throw new IllegalStateException("Unsupported type=" + klazz.getSimpleName());
	}

	public static boolean check(@NotNull Object value) {
		Class<?> type = value.getClass();
		if (type.equals(Integer.class)) {
			return true;
		} else if (type.equals(Double.class)) {
			return true;
		} else if (type.equals(Boolean.class)) {
			return true;
		} else return type.equals(String.class);
	}

	public enum Type {
		Support,
		ConvertToInt,
		ConvertToDouble,
		Unsupported
	}

}
