package kanti.sl

import kanti.sl.arguments.TypeChecker

class ValueConverterImpl(
	private val supportPrimitive: Boolean
) : ValueConverter {

	override fun convert(value: Any): String {
		require(TypeChecker.check(value)) {
			"Unsupported type: ${value.javaClass.simpleName}"
		}
		return value.toString()
	}

	override fun convert(line: String): Any {
		return if (supportPrimitive) {
			convertToValueWithPrimitive(line)
		} else {
			return line
		}
	}

	private fun convertToValueWithPrimitive(line: String): Any {
		if (line == "true" || line == "false")
			return line.toBoolean()
		if (line.contains('.'))
			return line.toDouble()
		return line.toIntOrNull() ?: line
	}

	class Builder : ValueConverter.Builder {

		private var supportPrimitive: Boolean = true

		override fun supportPrimitive(support: Boolean): ValueConverter.Builder {
			supportPrimitive = support
			return this
		}

		override fun build(): ValueConverter {
			return ValueConverterImpl(
				supportPrimitive = supportPrimitive
			)
		}
	}

}