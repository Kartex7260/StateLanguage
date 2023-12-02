package kanti.sl

import kanti.sl.arguments.StateArgument
import kanti.sl.objects.StateObject
import kotlin.text.StringBuilder

class StateObjectSerializerImpl(
	private val nameArgsSeparator: Char,
	private val argsSeparator: Char,
	private val keyValueSeparator: Char,
	private val valueConverter: ValueConverter
) : StateObjectSerializer {

	override fun serialize(state: StateObject): String {
		val name = state.name
		val args = state.arguments.arguments
		val sb = StringBuilder()
		sb.append(name)
		if (args.isNotEmpty()) {
			sb.append(nameArgsSeparator)
			for (index in args.indices) {
				val arg = args[index]
				sb.append(arg.name)
					.append(keyValueSeparator)
					.append(valueConverter.convert(arg.value))
				if (index != args.size - 1)
					sb.append(argsSeparator)
			}
		}
		return sb.toString()
	}

	override fun deserialize(line: String): StateObject {
		val nameArgs = line.split(nameArgsSeparator)
		check(nameArgs.size >=  2) {
			"Invalid input string (name:args): $line"
		}

		val args = getArgs(nameArgs[1])
		return StateObject.create(nameArgs[0], *args)
	}

	private fun getArgs(line: String): Array<StateArgument> {
		val args = mutableListOf<StateArgument>()
		val argsStrings = line.split(argsSeparator)
		for (argString in argsStrings) {
			val keyValue = argString.split(keyValueSeparator)
			check(keyValue.size >= 2) {
				"Invalid input string (key:value): $argString"
			}
			args.add(
				StateArgument.create(
					keyValue[0],
					valueConverter.convert(keyValue[1])
				)
			)
		}
		return args.toTypedArray()
	}

	class Builder : StateObjectSerializer.Builder {

		private val defaultNameArgsSeparator = ':'
		private val defaultArgsSeparator = ','
		private val defaultKeyValueSeparator = '='

		private var nameArgsSeparator: Char? = null
		private var argsSeparator: Char? = null
		private var keyValueSeparator: Char? = null
		private var valueConverterBuilder: ValueConverter.Builder? = null

		override fun setNameArgsSeparator(separator: Char?): StateObjectSerializer.Builder {
			nameArgsSeparator = separator
			return this
		}

		override fun setArgsSeparator(separator: Char?): StateObjectSerializer.Builder {
			argsSeparator = separator
			return this
		}

		override fun setKeyValueSeparator(separator: Char?): StateObjectSerializer.Builder {
			keyValueSeparator = separator
			return this
		}

		override fun setValueConverter(
			builder: ValueConverter.Builder?
		): StateObjectSerializer.Builder {
			valueConverterBuilder = builder
			return this
		}

		override fun builder(): StateObjectSerializer {
			return StateObjectSerializerImpl(
				nameArgsSeparator = nameArgsSeparator ?: defaultNameArgsSeparator,
				argsSeparator = argsSeparator ?: defaultArgsSeparator,
				keyValueSeparator = keyValueSeparator ?: defaultKeyValueSeparator,
				valueConverter = (valueConverterBuilder ?: ValueConverter.builder()).build()
			)
		}

	}

}