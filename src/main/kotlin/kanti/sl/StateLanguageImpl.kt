package kanti.sl

import kanti.sl.objects.MutableStateObject

class StateLanguageImpl private constructor(
	private val converters: Map<Class<*>, StateObjectConverter<*>>,
	private val serializer: StateObjectSerializer
): StateLanguage {

	override fun <State> parse(klass: Class<State>, line: String): State {
		val converter = converters[klass] as StateObjectConverter<State>?
			?: throw IllegalStateException("Serialize for ${klass.simpleName} not registered!")
		val deserialized = serializer.deserialize(line)
		return converter.convert(deserialized.arguments)
	}

	override fun <State> from(klass: Class<State>, obj: State): String {
		val converter = converters[klass] as StateObjectConverter<State>?
			?: throw IllegalStateException("Serialize for ${klass.simpleName} not registered!")
		val mutableObject = MutableStateObject.create(klass.simpleName)
		converter.convert(mutableObject.arguments, obj)
		return serializer.serialize(mutableObject)
	}

	class Builder : StateLanguage.Builder {

		private val converterMap: MutableMap<Class<*>, StateObjectConverter<*>> = HashMap()
		private var stateSerializer: StateObjectSerializer.Builder? = null

		override fun <State> registerConverter(
			klass: Class<State>,
			serializer: StateObjectConverter<State>
		): StateLanguage.Builder {
			converterMap[klass] = serializer
			return this
		}

		override fun setStateObjectConverter(
			converter: StateObjectSerializer.Builder?
		): StateLanguage.Builder {
			stateSerializer = converter
			return this
		}

		override fun build(): StateLanguage {
			return StateLanguageImpl(
				converters = converterMap,
				serializer = stateSerializer?.builder()
					?: StateObjectSerializer.builder().builder()
			)
		}
	}

}