package kanti.sl.objects

import kanti.sl.arguments.MutableStateArgument
import kanti.sl.arguments.MutableStateArguments

interface MutableStateObject : StateObject {

	override var name: String
	override val arguments: MutableStateArguments

	companion object {

		@JvmStatic
		@JvmOverloads
		fun create(
			name: String = "",
			vararg arguments: MutableStateArgument
		): MutableStateObject {
			return MutableStateObjectImpl(
				name = name,
				args = arguments.asIterable()
			)
		}

	}

}