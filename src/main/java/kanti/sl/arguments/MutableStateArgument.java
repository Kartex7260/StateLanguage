package kanti.sl.arguments;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MutableStateArgument extends StateArgument {

	void setKey(@NotNull String key);

	void setValue(@NotNull Object value);

	@NotNull
	static MutableStateArgument create(@NotNull String key, @Nullable Object value) {
		return new MutableStateArgumentImpl(key, value);
	}

	@NotNull
	static MutableStateArgument create(@NotNull String key) {
		return create(key, null);
	}

}
