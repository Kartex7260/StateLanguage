package kanti.sl.arguments.values;

import kanti.sl.SLContext;
import kanti.sl.SLContextOwner;
import org.jetbrains.annotations.NotNull;

public interface ValueCheckable extends SLContextOwner {

	void setContext(@NotNull SLContext context);

	boolean check(@NotNull Object value);

	boolean check(@NotNull Class<?> type);

	@NotNull
	static ValueCheckable create(@NotNull Class<?> type) {
		return new ValueCheckableImpl(type);
	}

}

class ValueCheckableImpl extends BaseValueCheckable {

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
