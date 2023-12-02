package kanti.sl

interface StateLanguage {

	fun <State> parse(klass: Class<State>, line: String): State

	fun <State> from(klass: Class<State>, obj: State): String

	interface Builder {

		fun <State> registerConverter(
			klass: Class<State>,
			serializer: StateObjectConverter<State>
		): Builder

		fun setStateObjectConverter(converter: StateObjectSerializer.Builder?): Builder

		fun build(): StateLanguage

	}

	companion object {

		@JvmStatic
		fun builder(): Builder {
			return StateLanguageImpl.Builder()
		}

	}

}