package kanti.sl.arguments

interface StateArguments : Iterable<StateArgument> {

	operator fun get(key: String): StateArgument?

	companion object {

		@JvmStatic
		fun create(vararg stateArgument: StateArgument): StateArguments {
			return StateArgumentsImpl(
				arguments = stateArgument.asIterable()
			)
		}

	}

}