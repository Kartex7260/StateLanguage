package kanti.sl

import kanti.sl.arguments.MutableStateArguments
import kanti.sl.arguments.StateArguments
import kanti.sl.objects.MutableStateObject
import kanti.sl.objects.StateObject

interface StateObjectConverter<State> {

	fun convert(arguments: MutableStateArguments, state: State)

	fun convert(arguments: StateArguments): State

}