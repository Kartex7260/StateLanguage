package kanti.sl.arguments.values;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public interface SupportedValues {

	@Nullable
	SupportedValue getType(@NotNull Class<?> type);

	@NotNull
	Object normalize(@NotNull Class<?> to, @NotNull Object value);

	@NotNull
	Object normalize(@NotNull Object value);

	boolean check(@NotNull Object value);

	boolean isSupported(@NotNull Class<?> type);

	@NotNull
	String serialize(@NotNull Object value);

	@NotNull
	Object deserialize(@NotNull String line);

	@NotNull
	SupportedValue determineValueType(@NotNull String line);

	interface Builder {

		@NotNull
		Builder setDefaultSupportedValue(
			@NotNull SupportedValue supportedValue
		);

		@NotNull
		Builder registerSupportedValue(
			@NotNull SupportedValue supportedValue
		);

		@NotNull
		Builder registerValueNormalizer(
			@NotNull Class<?> from,
			@NotNull ValueNormalizer converter
		);

		@NotNull
		Builder unregisterSupportedValue(
			@NotNull Class<?> type
		);

		@NotNull
		Builder unregisterValueNormalizer(
			@NotNull Class<?> type
		);

		@NotNull
		SupportedValues build();

	}

	@NotNull
	static Builder builder() {
		return new SupportedValuesImpl.Builder();
	}

}

class SupportedValuesImpl implements SupportedValues {

	private final Map<Class<?>, SupportedValue> valueMap;
	private final Map<Class<?>, ValueNormalizer> normalizerMap;

	private final SupportedValue defaultSupportedValue;

	SupportedValuesImpl(
		@NotNull Map<Class<?>, SupportedValue> valueMap,
		@NotNull Map<Class<?>, ValueNormalizer> normalizerMap,
		@NotNull SupportedValue defaultSupportedValue
	) {
		this.valueMap = valueMap;
		this.normalizerMap = normalizerMap;

		this.defaultSupportedValue = defaultSupportedValue;
	}

	@Nullable
	@Override
	public SupportedValue getType(@NotNull Class<?> type) {
		return valueMap.get(type);
	}

	@NotNull
	@Override
	public Object normalize(@NotNull Class<?> to, @NotNull Object value) {
		SupportedValue supportedValue = valueMap.get(value.getClass());
		if (supportedValue != null) {
			return value;
		}
		supportedValue = valueMap.get(to);
		if (supportedValue == null) {
			supportedValue = defaultSupportedValue;
		}
		return supportedValue.normalize(value);
	}

	@NotNull
	@Override
	public Object normalize(@NotNull Object value) {
		SupportedValue supportedValue = valueMap.get(value.getClass());
		if (supportedValue != null) {
			return value;
		}
		ValueNormalizer normalizer = normalizerMap.get(value.getClass());
		if (normalizer == null) {
			normalizer = defaultSupportedValue.getNormalizer();
		}
		return normalizer.normalize(value);
	}

	@Override
	public boolean check(@NotNull Object value) {
		for (SupportedValue supportedValue : valueMap.values()) {
			if (supportedValue.check(value))
				return true;
		}
		return false;
	}

	@Override
	public boolean isSupported(@NotNull Class<?> type) {
		for (SupportedValue supportedValue : valueMap.values()) {
			if (supportedValue.check(type))
				return true;
		}
		return false;
	}

	@NotNull
	@Override
	public String serialize(@NotNull Object value) {
		SupportedValue supportedValue = valueMap.get(value.getClass());
		if (supportedValue == null) {
			ValueNormalizer normalizer = normalizerMap.get(value.getClass());
			if (normalizer == null) {
				normalizer = defaultSupportedValue.getNormalizer();
			}
			value = normalizer.normalize(value);
			supportedValue = valueMap.get(value.getClass());
			if (supportedValue == null)
				supportedValue = defaultSupportedValue;
		}
		return supportedValue.serialize(value);
	}

	@NotNull
	@Override
	public Object deserialize(@NotNull String line) {
		SupportedValue supportedValue = determineValueType(line);
		return supportedValue.deserialize(line);
	}

	@NotNull
	@Override
	public SupportedValue determineValueType(@NotNull String line) {
		for (SupportedValue supportedValue : valueMap.values()) {
			if (supportedValue.isThis(line))
				return supportedValue;
		}
		throw new IllegalStateException("Unsupported value type");
	}

	static class Builder implements SupportedValues.Builder {

		private final Map<Class<?>, SupportedValue> valueMap = new LinkedHashMap<>();
		private final Map<Class<?>, ValueNormalizer> normalizerMap = new LinkedHashMap<>();

		private SupportedValue defaultSupportedValue = SupportedValue.builder().build();

		@NotNull
		@Override
		public SupportedValues.Builder registerSupportedValue(@NotNull SupportedValue supportedValue) {
			valueMap.put(supportedValue.getType(), supportedValue);
			return this;
		}

		@NotNull
		@Override
		public SupportedValues.Builder setDefaultSupportedValue(
			@NotNull SupportedValue supportedValue
		) {
			defaultSupportedValue = supportedValue;
			return this;
		}

		@NotNull
		@Override
		public SupportedValues.Builder registerValueNormalizer(
			@NotNull Class<?> from,
			@NotNull ValueNormalizer normalizer
		) {
			normalizerMap.put(from, normalizer);
			return this;
		}

		@NotNull
		@Override
		public SupportedValues.Builder unregisterSupportedValue(@NotNull Class<?> type) {
			valueMap.remove(type);
			return this;
		}

		@NotNull
		@Override
		public SupportedValues.Builder unregisterValueNormalizer(@NotNull Class<?> type) {
			normalizerMap.remove(type);
			return this;
		}

		@NotNull
		@Override
		public SupportedValues build() {
			return new SupportedValuesImpl(
				valueMap, normalizerMap,
				defaultSupportedValue
			);
		}

	}

}
