package kanti.sl.arguments

import kanti.sl.arguments.StateArgument
import kanti.sl.arguments.StateArguments

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