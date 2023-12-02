package kanti.sl;

import kanti.sl.objects.MutableStateObject;
import kanti.sl.objects.StateObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class StateLanguageImpl implements StateLanguage {

	private final Map<Class<?>, StateObjectConverter> converterMap;
	private final StateObjectSerializer stateSerializer;
	private final StateObjectConverter defaultObjectConverter;

	public StateLanguageImpl(
			@NotNull Map<Class<?>, StateObjectConverter> converterMap,
			@NotNull StateObjectSerializer stateSerializer,
			@NotNull StateObjectConverter defaultObjectConverter
	) {
		this.converterMap = converterMap;
		this.stateSerializer = stateSerializer;
		this.defaultObjectConverter = defaultObjectConverter;
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

		MutableStateObject mutableObject = MutableStateObject.create(type.getSimpleName());
		converter.convert(mutableObject.getArguments(), obj);
		return stateSerializer.serialize(mutableObject);
	}

	public static class Builder implements StateLanguage.Builder {

		private final Map<Class<?>, StateObjectConverter> converterMap = new HashMap<>();
		private StateObjectSerializer.Builder stateSerializer = null;
		private StateObjectConverter defObjectConverter = null;

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
				@Nullable StateObjectSerializer.Builder objSerializer
		) {
			stateSerializer = objSerializer;
			return this;
		}

		@NotNull
		@Override
		public StateLanguage.Builder setDefaultObjectConverter(
				@Nullable StateObjectConverter converter
		) {
			defObjectConverter = converter;
			return this;
		}

		@NotNull
		@Override
		public StateLanguage build() {
			StateObjectSerializer serializer;
			StateObjectConverter defObjectConverter;

			if (stateSerializer == null) {
				serializer = StateObjectSerializer.builder().build();
			} else {
				serializer = stateSerializer.build();
			}

			if (this.defObjectConverter == null) {
				defObjectConverter = new ReflectionObjectConverter();
			} else {
				defObjectConverter = this.defObjectConverter;
			}

			return new StateLanguageImpl(
					converterMap,
					serializer,
					defObjectConverter
			);
		}

	}

}
