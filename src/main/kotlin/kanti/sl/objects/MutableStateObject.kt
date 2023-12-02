package kanti.sl.objects

import kanti.sl.arguments.MutableStateArguments

interface MutableStateObject : StateObject {

	override var name: String
	override val arguments: MutableStateArguments

}