package kanti.sl.arguments

interface MutableStateArgument : StateArgument {

	override var name: String
	override var value: Any

}