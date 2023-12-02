package kanti.sl.arguments

import kanti.sl.arguments.StateArgument
import kanti.sl.arguments.StateArguments

interface MutableStateArguments : StateArguments {

	fun put(argument: StateArgument)

	companion object {

		@JvmStatic
		fun create(vararg argument: StateArgument): MutableStateArguments {
			return MutableStateArgumentsImpl(
				arguments = argument.asIterable()
			)
		}

	}

}