package kanti.sl.arguments

interface MutableStateArguments : StateArguments {

	override val arguments: List<MutableStateArgument>

	fun put(argument: MutableStateArgument)

	override operator fun get(key: String): MutableStateArgument?

	companion object {

		@JvmStatic
		fun create(vararg argument: MutableStateArgument): MutableStateArguments {
			return MutableStateArgumentsImpl(
				arguments = argument.asIterable()
			)
		}

	}

}