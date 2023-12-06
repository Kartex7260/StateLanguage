package kanti.sl.arguments.values;

import kanti.sl.SLContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SupportedValue {

	@NotNull
	Class<?> getType();

	@NotNull
	String getPrefix();

	boolean check(@NotNull Object value);

	boolean check(@NotNull Class<?> type);

	@NotNull
	Object normalize(@NotNull Object value);

	@NotNull
	String serialize(@NotNull Object value);

	@NotNull
	Object deserialize(@NotNull String line);

	@NotNull
	ValueNormalizer getNormalizer();

	@NotNull
	ValueCheckable getCheckable();

	@NotNull
	ValueSerializer getSerializer();

	interface Builder {

		@NotNull
		Builder setPrefix(@NotNull String prefix);

		@NotNull
		Builder setType(@NotNull Class<?> type);

		@NotNull
		Builder setCheckable(@NotNull ValueCheckable checkable);

		@NotNull
		Builder setNormalizer(@NotNull ValueNormalizer normalizer);

		@NotNull
		Builder setSerializer(@NotNull ValueSerializer serializer);

		@NotNull
		Builder setContext(@NotNull SLContext context);

		@NotNull
		SupportedValue build();

	}

	static Builder builder(@NotNull Class<?> type) {
		return new SupportedValueImpl.Builder()
			.setType(type);
	}

	@NotNull
	static Builder builder() {
		return new SupportedValueImpl.Builder();
	}

}

class SupportedValueImpl implements SupportedValue {

	private final String prefix;
	private final Class<?> type;
	private final ValueCheckable checkable;
	private final ValueNormalizer normalizer;
	private final ValueSerializer serializer;
	private final SLContext context;

	SupportedValueImpl(
		@NotNull String prefix,
		@NotNull Class<?> type,
		@NotNull ValueCheckable checkable,
		@NotNull ValueNormalizer converter,
		@NotNull ValueSerializer serializer,
		@NotNull SLContext context
	) {
		this.prefix = prefix;
		this.type = type;
		this.checkable = checkable;
		this.normalizer = converter;
		this.serializer = serializer;
		this.context = context;
	}

	@NotNull
	@Override
	public String getPrefix() {
		return prefix;
	}

	@NotNull
	@Override
	public Class<?> getType() {
		return type;
	}

	@Override
	public boolean check(@NotNull Object value) {
		return checkable.check(value);
	}

	@Override
	public boolean check(@NotNull Class<?> type) {
		return checkable.check(type);
	}

	@NotNull
	@Override
	public Object normalize(@NotNull Object value) {
		return normalizer.normalize(value);
	}

	@NotNull
	@Override
	public String serialize(@NotNull Object value) {
		SupportedValues supportedValues = context.getSupportedValues();
		return prefix + supportedValues.getPrefixValueSeparator() + serializer.serialize(value);
	}

	@NotNull
	@Override
	public Object deserialize(@NotNull String line) {
		SupportedValues supportedValues = context.getSupportedValues();
		String cleanLine = supportedValues.getCleanLine(line);
		return serializer.deserialize(cleanLine);
	}

	@NotNull
	@Override
	public ValueNormalizer getNormalizer() {
		return normalizer;
	}

	@NotNull
	@Override
	public ValueCheckable getCheckable() {
		return checkable;
	}

	@NotNull
	@Override
	public ValueSerializer getSerializer() {
		return serializer;
	}



	static class Builder implements SupportedValue.Builder {

		@Nullable
		private Class<?> type = null;
		@Nullable
		private ValueCheckable checkable = null;
		@NotNull
		private ValueNormalizer converter = ValueNormalizer.getInstance();
		@NotNull
		private ValueSerializer serializer = ValueSerializer.getInstance();
		@Nullable
		private SLContext context = null;
		@Nullable
		private String prefix = null;

		@NotNull
		@Override
		public SupportedValue.Builder setPrefix(@NotNull String prefix) {
			this.prefix = prefix;
			return this;
		}

		@NotNull
		@Override
		public SupportedValue.Builder setType(@NotNull Class<?> type) {
			this.type = type;
			return this;
		}

		@NotNull
		@Override
		public SupportedValue.Builder setCheckable(@NotNull ValueCheckable checkable) {
			this.checkable = checkable;
			return this;
		}

		@NotNull
		@Override
		public SupportedValue.Builder setNormalizer(@NotNull ValueNormalizer normalizer) {
			this.converter = normalizer;
			return this;
		}

		@NotNull
		@Override
		public SupportedValue.Builder setSerializer(@NotNull ValueSerializer serializer) {
			this.serializer = serializer;
			return this;
		}

		@NotNull
		@Override
		public SupportedValue.Builder setContext(@NotNull SLContext context) {
			this.context = context;
			return this;
		}

		@NotNull
		@Override
		public SupportedValue build() {
			if (type == null)
				throw new IllegalStateException("Type not initialized!");

			if (context == null)
				throw new IllegalStateException("Context not initialized");

			if (checkable == null) {
				checkable = ValueCheckable.create(type);
			}
			if (prefix == null)
				prefix = type.getName();
			return new SupportedValueImpl(
				prefix,
				type,
				checkable,
				converter,
				serializer,
				context
			);
		}

	}

}
