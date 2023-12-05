package kanti.sl;

import kanti.sl.arguments.values.SupportedValues;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SLContext {

	@NotNull
	SupportedValues getSupportedValues();

	@NotNull
	StateObjectSerializer getStateObjectSerializer();

	interface Builder {

		@NotNull
		Builder setSupportedValues(@NotNull SupportedValues.Builder builder);

		@NotNull
		Builder setObjectSerializer(@NotNull StateObjectSerializer.Builder serializer);

		@NotNull
		SLContext build();

	}

	@NotNull
	static Builder builder() {
		return new SLContextImpl.Builder();
	}

}

class SLContextImpl implements SLContext {

	@NotNull
	private final SupportedValues.Builder supportedValuesBuilder;
	@NotNull
	private final StateObjectSerializer.Builder objectSerializerBuilder;

	@Nullable
	private SupportedValues supportedValues = null;
	@Nullable
	private StateObjectSerializer objectSerializer = null;

	SLContextImpl(
		@NotNull SupportedValues.Builder supportedValuesBuilder,
		@NotNull StateObjectSerializer.Builder objectSerializerBuilder
	) {
		this.supportedValuesBuilder = supportedValuesBuilder;
		this.objectSerializerBuilder = objectSerializerBuilder;
	}

	@NotNull
	@Override
	public SupportedValues getSupportedValues() {
		if (supportedValues == null) {
			supportedValues = supportedValuesBuilder.build();
		}
		return supportedValues;
	}

	@NotNull
	@Override
	public StateObjectSerializer getStateObjectSerializer() {
		if (objectSerializer == null) {
			objectSerializer = objectSerializerBuilder
				.setContext(this).build();
		}
		return objectSerializer;
	}

	static class Builder implements SLContext.Builder {

		@NotNull
		private SupportedValues.Builder supportedValuesBuilder = SupportedValues.builder();
		@NotNull
		private StateObjectSerializer.Builder objectSerializerBuilder = StateObjectSerializer.builder();

		@NotNull
		@Override
		public SLContext.Builder setSupportedValues(@NotNull SupportedValues.Builder builder) {
			this.supportedValuesBuilder = builder;
			return this;
		}

		@NotNull
		@Override
		public SLContext.Builder setObjectSerializer(
			@NotNull StateObjectSerializer.Builder serializer
		) {
			this.objectSerializerBuilder = serializer;
			return this;
		}

		@NotNull
		@Override
		public SLContext build() {
			return new SLContextImpl(
				supportedValuesBuilder,
				objectSerializerBuilder
			);
		}

	}

}
