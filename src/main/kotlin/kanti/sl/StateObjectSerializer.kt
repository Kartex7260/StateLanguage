package kanti.sl

import kanti.sl.objects.MutableStateObject
import kanti.sl.objects.StateObject

interface StateObjectSerializer {

	fun serialize(state: StateObject): String

	fun deserialize(line: String): StateObject

	interface Builder {

		fun setNameArgsSeparator(separator: Char?): Builder

		fun setArgsSeparator(separator: Char?): Builder

		fun setKeyValueSeparator(separator: Char?): Builder

		fun setValueConverter(builder: ValueConverter.Builder?): Builder

		fun builder(): StateObjectSerializer

	}

}