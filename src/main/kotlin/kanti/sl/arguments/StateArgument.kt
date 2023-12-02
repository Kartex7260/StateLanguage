package kanti.sl.arguments

interface StateArgument {

	val name: String
	val value: Any

	companion object {

		@JvmStatic
		fun create(name: String, value: Any): StateArgument {
			return StateArgumentImpl(
				name = name,
				value = value
			)
		}

	}

}