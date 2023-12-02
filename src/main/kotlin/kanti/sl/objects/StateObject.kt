package kanti.sl.objects

import kanti.sl.arguments.StateArgument
import kanti.sl.arguments.StateArguments

interface StateObject {

	val name: String
	val arguments: StateArguments

	companion object {

		@JvmStatic
		fun create(
			name: String,
			vararg args: StateArgument
		): StateObject {
			return StateObjectImpl(
				name = name,
				args = args.asIterable()
			)
		}

	}

}