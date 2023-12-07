package kanti.sl.arguments.values;

import kanti.sl.SLContext;
import kanti.sl.SLContextOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public interface SupportedValues extends SLContextOwner {

	@NotNull
	String getPrefixValueSeparator();

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

	@NotNull
	String getCleanPrefix(@NotNull String line);

	@NotNull
	String getCleanLine(@NotNull String line);

	interface Builder {

		@NotNull
		Builder setPrefixValueSeparator(@NotNull String separator);

		@NotNull
		Builder setDefaultSupportedValue(
			@NotNull SupportedValue.Builder supportedValue
		);

		@NotNull
		Builder registerSupportedValue(
			@NotNull SupportedValue.Builder supportedValue
		);

		@NotNull
		Builder registerValueNormalizer(
			@NotNull Class<?> from,
			@NotNull ValueNormalizer converter
		);

		@NotNull
		Builder setContext(@NotNull SLContext context);

		@NotNull
		SupportedValues build();

	}

	@NotNull
	static Builder builder() {
		return new SupportedValuesImpl.Builder();
	}

}

class SupportedValuesImpl implements SupportedValues {

	private final String prefixValueSeparator;

	private final Map<Class<?>, SupportedValue> valueMap;
	private final Map<Class<?>, ValueNormalizer> normalizerMap;

	private final SupportedValue defaultSupportedValue;
	private final SLContext context;

	SupportedValuesImpl(
		@NotNull String prefixValueSeparator,
		@NotNull Map<Class<?>, SupportedValue> valueMap,
		@NotNull Map<Class<?>, ValueNormalizer> normalizerMap,
		@NotNull SupportedValue defaultSupportedValue,
		@NotNull SLContext context
	) {
		this.prefixValueSeparator = prefixValueSeparator;

		this.valueMap = valueMap;
		this.normalizerMap = normalizerMap;

		this.defaultSupportedValue = defaultSupportedValue;
		this.context = context;
	}

	@NotNull
	@Override
	public String getPrefixValueSeparator() {
		return prefixValueSeparator;
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
		String cleanPrefix = getCleanPrefix(line);
		for (SupportedValue supportedValue : valueMap.values()) {
			if (supportedValue.getPrefix().equals(cleanPrefix))
				return supportedValue;
		}
		throw new IllegalStateException("Unsupported value type");
	}

	@NotNull
	public String getCleanPrefix(@NotNull String line) {
		String[] separated = line.split(prefixValueSeparator);
		if (separated.length < 2)
			throw new IllegalArgumentException("Invalid line = " + line);
		return separated[0];
	}

	@NotNull
	public String getCleanLine(@NotNull String line) {
		String[] separated = line.split(prefixValueSeparator);
		if (separated.length < 2)
			throw new IllegalArgumentException("Invalid line = " + line);
		return separated[1];
	}

	@NotNull
	@Override
	public SLContext getContext() {
		return context;
	}

	static class Builder implements SupportedValues.Builder {

		@NotNull
		private final List<SupportedValue.Builder> valueBuilderMap = new ArrayList<>();
		@NotNull
		private final Map<Class<?>, SupportedValue> valueMap = new LinkedHashMap<>();
		@NotNull
		private final Map<Class<?>, ValueNormalizer> normalizerMap = new LinkedHashMap<>();

		@NotNull
		private SupportedValue.Builder defaultSupportedValue = SupportedValue.builder()
			.setType(Class.class)
			.setPrefix("CLASS");
		@Nullable
		private SLContext context = null;

		@NotNull
		private String prefixValueSeparator = "-";

		@NotNull
		@Override
		public SupportedValues.Builder setPrefixValueSeparator(@NotNull String separator) {
			prefixValueSeparator = separator;
			return this;
		}

		@NotNull
		@Override
		public SupportedValues.Builder registerSupportedValue(
			@NotNull SupportedValue.Builder supportedValue
		) {
			valueBuilderMap.add(supportedValue);
			return this;
		}

		@NotNull
		@Override
		public SupportedValues.Builder setDefaultSupportedValue(
			@NotNull SupportedValue.Builder supportedValue
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
		public SupportedValues.Builder setContext(@NotNull SLContext context) {
			this.context = context;
			return this;
		}

		@NotNull
		@Override
		public SupportedValues build() {
			if (context == null)
				throw new IllegalStateException("Context not initialized");

			for (SupportedValue.Builder builder : valueBuilderMap) {
				SupportedValue supportedValue = builder
					.setContext(context)
					.build();
				valueMap.put(supportedValue.getType(), supportedValue);
			}
			return new SupportedValuesImpl(
				prefixValueSeparator,
				valueMap, normalizerMap,
				defaultSupportedValue
					.setContext(context)
					.build(),
				context
			);
		}

	}

}
