package kanti.sl;

import kanti.sl.objects.StateObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface StateObjectSerializer {

	@NotNull
	String serialize(@NotNull StateObject obj);

	@NotNull
	StateObject deserialize(@NotNull String line);

	interface Builder {

		@NotNull
		Builder setNameArgsSeparator(@Nullable String separator);

		@NotNull
		Builder setArgsSeparator(@Nullable String separator);

		@NotNull
		Builder setKeyValueSeparator(@Nullable String separator);

		@NotNull
		Builder setValueConverter(@Nullable ValueConverter.Builder builder);

		@NotNull
		StateObjectSerializer build();

	}

	@NotNull
	static Builder builder() {
		return new StateObjectSerializerImpl.Builder();
	}

}
