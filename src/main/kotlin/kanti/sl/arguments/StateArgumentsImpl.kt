package kanti.sl.arguments

class StateArgumentsImpl(
	arguments: Iterable<StateArgument>
) : StateArguments {

	private val statesMap: Map<String, StateArgument>

	override val arguments: List<StateArgument>
		get() = statesMap.values.toList()

	init {
		statesMap = buildMap {
			for (arg in arguments) {
				put(arg.name, arg)
			}
		}
	}

	override operator fun get(key: String): StateArgument? {
		return statesMap[key]
	}

}