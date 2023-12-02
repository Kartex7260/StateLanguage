package kanti.sl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface StateLanguage {

	@NotNull
	Object parse(@NotNull Class<?> type, @NotNull String line);

	@NotNull
	String from(@NotNull Class<?> type, @NotNull Object obj);

	@NotNull
	static Builder builder() {
		return new StateLanguageImpl.Builder();
	}

	interface Builder {
		@NotNull
		Builder registerConverter(
			@NotNull Class<?> type,
			@NotNull StateObjectConverter objConverter
		);

		@NotNull
		Builder setObjectSerializer(@Nullable StateObjectSerializer.Builder objSerializer);

		@NotNull
		Builder setDefaultObjectConverter(@Nullable StateObjectConverter converter);

		@NotNull
		StateLanguage build();
	}

}
