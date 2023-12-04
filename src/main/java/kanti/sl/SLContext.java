package kanti.sl;

import kanti.sl.arguments.values.SupportedValues;
import org.jetbrains.annotations.NotNull;

public interface SLContext {

	SupportedValues getSupportedValues();

	interface Builder {

		@NotNull
		Builder setSupportedValues(@NotNull SupportedValues.Builder builder);

		@NotNull
		SLContext build();

	}

	@NotNull
	static Builder builder() {
		return new SLContextImpl.Builder();
	}

}

class SLContextImpl implements SLContext {

	private final SupportedValues supportedValue;

	SLContextImpl(
		@NotNull SupportedValues supportedValues
	) {
		this.supportedValue = supportedValues;
	}

	@Override
	public SupportedValues getSupportedValues() {
		return supportedValue;
	}

	static class Builder implements SLContext.Builder {

		private SupportedValues.Builder supportedValuesBuilder = SupportedValues.builder();

		@NotNull
		@Override
		public SLContext.Builder setSupportedValues(@NotNull SupportedValues.Builder builder) {
			this.supportedValuesBuilder = builder;
			return this;
		}

		@NotNull
		@Override
		public SLContext build() {
			return new SLContextImpl(
				supportedValuesBuilder.build()
			);
		}

	}

}
