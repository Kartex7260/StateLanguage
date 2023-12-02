package kanti.sl.arguments

class MutableStateArgumentsImpl(
	arguments: Iterable<StateArgument> = listOf()
) : MutableStateArguments {

	private val argMap: MutableMap<String, StateArgument> = HashMap()

	init {
		for (arg in arguments) {
			argMap.put(arg.name, arg)
		}
	}

	override fun iterator(): Iterator<StateArgument> {
		return argMap.values.iterator()
	}

	override fun put(argument: StateArgument) {
		argMap[argument.name] = argument
	}

	override operator fun get(key: String): StateArgument? {
		return argMap[key]
	}
}