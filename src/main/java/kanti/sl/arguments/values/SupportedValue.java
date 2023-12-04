package kanti.sl.arguments.values;

import org.jetbrains.annotations.NotNull;

public interface SupportedValue {

	@NotNull
	Class<?> getType();

	boolean check(@NotNull Object value);

	@NotNull
	Object normalize(@NotNull Object value);

	@NotNull
	String serialize(@NotNull Object value);

	@NotNull
	Object deserialize(@NotNull String line);

	boolean isThis(@NotNull String line);

	@NotNull
	ValueNormalizer getNormalizer();

	@NotNull
	ValueCheckable getCheckable();

	@NotNull
	ValueSerializer getSerializer();

	@NotNull
	LineDeterminant getLineDeterminant();

	interface Builder {

		Builder setType(@NotNull Class<?> type);

		Builder setCheckable(@NotNull ValueCheckable checkable);

		Builder setNormalizer(@NotNull ValueNormalizer normalizer);

		Builder setSerializer(@NotNull ValueSerializer serializer);

		Builder setLineDeterminant(@NotNull LineDeterminant determinant);

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

	private final Class<?> type;
	private final ValueCheckable checkable;
	private final ValueNormalizer normalizer;
	private final ValueSerializer serializer;
	private final LineDeterminant determinant;

	SupportedValueImpl(
		@NotNull Class<?> type,
		@NotNull ValueCheckable checkable,
		@NotNull ValueNormalizer converter,
		@NotNull ValueSerializer serializer,
		@NotNull LineDeterminant determinant
	) {
		this.type = type;
		this.checkable = checkable;
		this.normalizer = converter;
		this.serializer = serializer;
		this.determinant = determinant;
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

	@NotNull
	@Override
	public Object normalize(@NotNull Object value) {
		return normalizer.normalize(value);
	}

	@NotNull
	@Override
	public String serialize(@NotNull Object value) {
		return serializer.serialize(value);
	}

	@NotNull
	@Override
	public Object deserialize(@NotNull String line) {
		return serializer.deserialize(line);
	}

	@Override
	public boolean isThis(@NotNull String line) {
		return determinant.isThis(line);
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

	@NotNull
	@Override
	public LineDeterminant getLineDeterminant() {
		return determinant;
	}

	static class Builder implements SupportedValue.Builder {

		private Class<?> type = null;
		private ValueCheckable checkable = null;
		private ValueNormalizer converter = ValueNormalizer.getInstance();
		private ValueSerializer serializer = ValueSerializer.getInstance();
		private LineDeterminant determinant = LineDeterminant.getInstance();

		@Override
		public SupportedValue.Builder setType(@NotNull Class<?> type) {
			this.type = type;
			return this;
		}

		@Override
		public SupportedValue.Builder setCheckable(@NotNull ValueCheckable checkable) {
			this.checkable = checkable;
			return this;
		}

		@Override
		public SupportedValue.Builder setNormalizer(@NotNull ValueNormalizer normalizer) {
			this.converter = normalizer;
			return this;
		}

		@Override
		public SupportedValue.Builder setSerializer(@NotNull ValueSerializer serializer) {
			this.serializer = serializer;
			return this;
		}

		@Override
		public SupportedValue.Builder setLineDeterminant(@NotNull LineDeterminant determinant) {
			this.determinant = determinant;
			return this;
		}

		@NotNull
		@Override
		public SupportedValue build() {
			if (checkable == null) {
				checkable = ValueCheckable.create(type);
			}
			return new SupportedValueImpl(
				type,
				checkable,
				converter,
				serializer,
				determinant
			);
		}

	}

}
