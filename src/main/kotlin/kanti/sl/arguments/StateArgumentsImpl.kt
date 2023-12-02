package kanti.sl.arguments

class StateArgumentsImpl(
	arguments: Iterable<StateArgument>
) : StateArguments {

	private val statesMap: Map<String, StateArgument>

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

	override fun iterator(): Iterator<StateArgument> {
		return statesMap.values.iterator()
	}

}