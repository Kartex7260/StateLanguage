package kanti.sl

import kanti.sl.objects.MutableStateObject
import kanti.sl.objects.StateObject

interface StateObjectConverter<State> {

	fun convert(stateObject: MutableStateObject, state: State)

	fun convert(stateObject: StateObject): State

}