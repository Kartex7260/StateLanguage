package kanti.sl.objects

import kanti.sl.arguments.MutableStateArgument
import kanti.sl.arguments.MutableStateArguments
import kanti.sl.arguments.MutableStateArgumentsImpl

class MutableStateObjectImpl @JvmOverloads constructor(
	override var name: String = "",
	args: Iterable<MutableStateArgument> = listOf()
) : MutableStateObject {

	override val arguments: MutableStateArguments = MutableStateArgumentsImpl(args)

}