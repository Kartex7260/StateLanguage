package kanti.sl;

import kanti.sl.objects.MutableStateObject;
import kanti.sl.objects.StateObject;
import kanti.sl.std.BaseContext;
import kanti.sl.std.ReflectionObjectConverter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public interface StateLanguage extends SLContextOwner {

	@NotNull
	Object deserialize(@NotNull Class<?> type, @NotNull String line);

	@NotNull
	String serialize(@NotNull Class<?> type, @NotNull Object obj);

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
		Builder setDefaultObjectConverter(@NotNull StateObjectConverter converter);

		@NotNull
		Builder setContext(@NotNull SLContext.Builder contextBuilder);

		@NotNull
		StateLanguage build();
	}

}

class StateLanguageImpl implements StateLanguage {

	private final Map<Class<?>, StateObjectConverter> converterMap;
	private final StateObjectConverter defaultObjectConverter;
	private final SLContext context;

	StateLanguageImpl(
		@NotNull Map<Class<?>, StateObjectConverter> converterMap,
		@NotNull StateObjectConverter defaultObjectConverter,
		@NotNull SLContext context
	) {
		this.converterMap = converterMap;
		this.defaultObjectConverter = defaultObjectConverter;
		this.context = context;
	}

	@NotNull
	@Override
	public Object deserialize(@NotNull Class<?> type, @NotNull String line) {
		StateObjectSerializer serializer = context.getStateObjectSerializer();
		StateObjectConverter converter = converterMap.get(type);
		if (converter == null)
			converter = defaultObjectConverter;

		StateObject deserializedObject = serializer.deserialize(line);
		return converter.convert(deserializedObject.getArguments());
	}

	@NotNull
	@Override
	public String serialize(@NotNull Class<?> type, @NotNull Object obj) {
		StateObjectSerializer serializer = context.getStateObjectSerializer();
		StateObjectConverter converter = converterMap.get(type);
		if (converter == null)
			converter = defaultObjectConverter;

		MutableStateObject mutableObject = MutableStateObject.create(context, type.getSimpleName());
		converter.convert(mutableObject.getArguments(), obj);
		return serializer.serialize(mutableObject);
	}

	@NotNull
	@Override
	public SLContext getContext() {
		return context;
	}

	public static class Builder implements StateLanguage.Builder {

		@NotNull
		private final Map<Class<?>, StateObjectConverter> converterMap = new HashMap<>();
		@NotNull
		private StateObjectConverter defObjectConverter = new ReflectionObjectConverter();
		@NotNull
		private SLContext.Builder contextBuilder = BaseContext.getBuilder();

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
		public StateLanguage build() {
			SLContext context = contextBuilder.build();
			defObjectConverter.setContext(context);
			for (StateObjectConverter converter : converterMap.values()) {
				converter.setContext(context);
			}
			return new StateLanguageImpl(
				converterMap,
				defObjectConverter,
				context
			);
		}

	}

}
