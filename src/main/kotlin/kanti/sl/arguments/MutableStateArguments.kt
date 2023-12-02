package kanti.sl.arguments

import kanti.sl.arguments.StateArgument
import kanti.sl.arguments.StateArguments

interface MutableStateArguments : StateArguments {

	fun put(argument: StateArgument)

}