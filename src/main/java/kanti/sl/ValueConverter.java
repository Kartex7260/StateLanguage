package kanti.sl;

import org.jetbrains.annotations.NotNull;

public interface ValueConverter {

	@NotNull
	String convert(@NotNull Object value);

	@NotNull
	Object convert(@NotNull String line);

	interface Builder {

		@NotNull
		Builder supportPrimitive(boolean support);

		@NotNull
		ValueConverter build();

	}

	@NotNull
	static Builder builder() {
		return new ValueConverterImpl.Builder();
	}

}
