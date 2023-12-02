package kanti.sl.arguments;

import org.jetbrains.annotations.NotNull;

public interface StateArgument {

	@NotNull
	String getKey();

	@NotNull
	Object getValue();

	@NotNull
	static StateArgument create(@NotNull String name, @NotNull Object value) {
		return new StateArgumentImpl(name, value);
	}

}
