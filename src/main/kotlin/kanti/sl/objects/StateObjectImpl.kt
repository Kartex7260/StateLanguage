package kanti.sl.objects

import kanti.sl.arguments.StateArgument
import kanti.sl.arguments.StateArguments
import kanti.sl.arguments.StateArgumentsImpl

class StateObjectImpl(
	override val name: String,
	args: Iterable<StateArgument>
) : StateObject {

	override val arguments: StateArguments = StateArgumentsImpl(args)

}