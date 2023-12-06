package kanti.sl.arguments.values;

import org.jetbrains.annotations.NotNull;

public interface ValueCheckable {

	boolean check(@NotNull Object value);

	boolean check(@NotNull Class<?> type);

	@NotNull
	static ValueCheckable create(@NotNull Class<?> type) {
		return new ValueCheckableImpl(type);
	}

}

class ValueCheckableImpl implements ValueCheckable {

	private final Class<?> type;

	ValueCheckableImpl(@NotNull Class<?> type) {
		this.type = type;
	}

	@Override
	public boolean check(@NotNull Object value) {
		return type.equals(value.getClass());
	}

	@Override
	public boolean check(@NotNull Class<?> type) {
		return this.type.equals(type);
	}

}
