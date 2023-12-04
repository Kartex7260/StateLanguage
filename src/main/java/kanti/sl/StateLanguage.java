package kanti.sl;

import kanti.sl.arguments.values.SupportedValue;
import kanti.sl.arguments.values.SupportedValues;
import kanti.sl.arguments.values.ValueNormalizer;
import kanti.sl.objects.MutableStateObject;
import kanti.sl.objects.StateObject;
import kanti.sl.std.BaseContext;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public interface StateLanguage extends SLContextOwner {

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
		Builder setObjectSerializer(@NotNull StateObjectSerializer.Builder objSerializer);

		@NotNull
		Builder setDefaultObjectConverter(@NotNull StateObjectConverter converter);

		@NotNull
		Builder setContext(@NotNull SLContext.Builder contextBuilder);

		@NotNull
		Builder setDefaultSupportedValue(
			@NotNull SupportedValue supportedValue
		);

		@NotNull
		Builder registerSupportedValue(@NotNull SupportedValue supportedValue);

		@NotNull
		Builder registerValueNormalizer(
			@NotNull Class<?> from,
			@NotNull ValueNormalizer converter
		);

		@NotNull
		Builder unregisterSupportedValue(@NotNull Class<?> type);

		@NotNull
		Builder unregisterValueNormalizer(@NotNull Class<?> type);

		@NotNull
		StateLanguage build();
	}

}

class StateLanguageImpl implements StateLanguage {

	private final Map<Class<?>, StateObjectConverter> converterMap;
	private final StateObjectSerializer stateSerializer;
	private final StateObjectConverter defaultObjectConverter;
	private final SLContext context;

	StateLanguageImpl(
		@NotNull Map<Class<?>, StateObjectConverter> converterMap,
		@NotNull StateObjectSerializer stateSerializer,
		@NotNull StateObjectConverter defaultObjectConverter,
		@NotNull SLContext context
	) {
		this.converterMap = converterMap;
		this.stateSerializer = stateSerializer;
		this.defaultObjectConverter = defaultObjectConverter;
		this.context = context;
	}

	@NotNull
	@Override
	public Object parse(@NotNull Class<?> type, @NotNull String line) {
		StateObjectConverter converter = converterMap.get(type);
		if (converter == null)
			converter = defaultObjectConverter;

		StateObject deserializedObject = stateSerializer.deserialize(line);
		return converter.convert(deserializedObject.getArguments());
	}

	@NotNull
	@Override
	public String from(@NotNull Class<?> type, @NotNull Object obj) {
		StateObjectConverter converter = converterMap.get(type);
		if (converter == null)
			converter = defaultObjectConverter;

		MutableStateObject mutableObject = MutableStateObject.create(context, type.getSimpleName());
		converter.convert(mutableObject.getArguments(), obj);
		return stateSerializer.serialize(mutableObject);
	}

	@NotNull
	@Override
	public SLContext getContext() {
		return context;
	}

	public static class Builder implements StateLanguage.Builder {

		private final Map<Class<?>, StateObjectConverter> converterMap = new HashMap<>();
		private StateObjectSerializer.Builder stateSerializerBuilder = StateObjectSerializer.builder();
		private StateObjectConverter defObjectConverter = new ReflectionObjectConverter();

		private SLContext.Builder contextBuilder = null;

		private final SupportedValues.Builder supportedValuesBuilder = BaseContext.baseSupportedValues();

		@NotNull
		@Override
		public StateLanguage.Builder registerConverter(
			@NotNull Class<?> type,
			@NotNull StateObjectConverter objConverter
		) {
			converterMap.put(type, objConverter);
			return this;
		}

		@NotNull
		@Override
		public StateLanguage.Builder setObjectSerializer(
			@NotNull StateObjectSerializer.Builder objSerializer
		) {
			stateSerializerBuilder = objSerializer;
			return this;
		}

		@NotNull
		@Override
		public StateLanguage.Builder setDefaultObjectConverter(
			@NotNull StateObjectConverter converter
		) {
			defObjectConverter = converter;
			return this;
		}

		@NotNull
		@Override
		public StateLanguage.Builder setContext(@NotNull SLContext.Builder contextBuilder) {
			this.contextBuilder = contextBuilder;
			return this;
		}

		@NotNull
		@Override
		public StateLanguage.Builder setDefaultSupportedValue(@NotNull SupportedValue supportedValue) {
			supportedValuesBuilder.setDefaultSupportedValue(supportedValue);
			return this;
		}

		@NotNull
		@Override
		public StateLanguage.Builder registerSupportedValue(@NotNull SupportedValue supportedValue) {
			supportedValuesBuilder.registerSupportedValue(supportedValue);
			return this;
		}

		@NotNull
		@Override
		public StateLanguage.Builder registerValueNormalizer(@NotNull Class<?> from, @NotNull ValueNormalizer converter) {
			supportedValuesBuilder.registerValueNormalizer(from, converter);
			return this;
		}

		@NotNull
		@Override
		public StateLanguage.Builder unregisterSupportedValue(@NotNull Class<?> type) {
			supportedValuesBuilder.unregisterSupportedValue(type);
			return this;
		}

		@NotNull
		@Override
		public StateLanguage.Builder unregisterValueNormalizer(@NotNull Class<?> type) {
			supportedValuesBuilder.unregisterValueNormalizer(type);
			return this;
		}

		@NotNull
		@Override
		public StateLanguage build() {
			if (contextBuilder == null) {
				contextBuilder = SLContext.builder()
					.setSupportedValues(supportedValuesBuilder);
			}
			SLContext context = contextBuilder.build();
			StateObjectSerializer stateObjectSerializer = stateSerializerBuilder
				.setContext(context)
				.build();

			return new StateLanguageImpl(
				converterMap,
				stateObjectSerializer,
				defObjectConverter,
				context
			);
		}

	}

}
