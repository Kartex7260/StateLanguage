package kanti.sl.arguments

class MutableStateArgumentsImpl @JvmOverloads constructor(
	arguments: Iterable<MutableStateArgument> = listOf()
) : MutableStateArguments {

	private val argMap: MutableMap<String, MutableStateArgument> = HashMap()

	override val arguments: List<MutableStateArgument>
		get() = argMap.values.toList()

	init {
		for (arg in arguments) {
			argMap[arg.name] = arg
		}
	}

	override fun put(argument: MutableStateArgument) {
		argMap[argument.name] = argument
	}

	override operator fun get(key: String): MutableStateArgument? {
		return argMap[key]
	}
}