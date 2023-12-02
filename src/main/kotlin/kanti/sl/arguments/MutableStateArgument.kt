package kanti.sl.arguments

interface MutableStateArgument : StateArgument {

	override var name: String
	override var value: Any

	companion object {

		@JvmStatic
		@JvmOverloads
		fun create(
			name: String = "",
			value: Any? = null
		): MutableStateArgument {
			return MutableStateArgumentImpl(
				name = name,
				value = value
			)
		}

	}

}