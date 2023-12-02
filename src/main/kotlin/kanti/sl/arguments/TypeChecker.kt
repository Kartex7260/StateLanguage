package kanti.sl.arguments

object TypeChecker {

	@JvmStatic
	fun getType(any: Any): Type {
		return when (any) {
			is Byte -> Type.ConvertToInt
			is Short -> Type.ConvertToInt
			is Int -> Type.Support
			is Long -> Type.ConvertToInt
			is Float -> Type.ConvertToDouble
			is Double -> Type.Support
			is Boolean -> Type.Support
			is String -> Type.Support
			else -> Type.Unsupported
		}
	}

	@JvmStatic
	fun check(any: Any): Boolean {
		return when (any) {
			is Int -> true
			is Double -> true
			is Boolean -> true
			is String -> true
			else -> false
		}
	}

	@JvmStatic
	fun convert(value: Any, type: Type): Any {
		return when (type) {
			Type.Support -> {
				value
			}
			Type.ConvertToInt -> {
				value.toString().toInt()
			}
			Type.ConvertToDouble -> {
				value.toString().toDouble()
			}
			Type.Unsupported -> {
				throw IllegalArgumentException("Unsupported type: ${value.javaClass.simpleName}")
			}
		}
	}

	enum class Type {
		Support,
		ConvertToInt,
		ConvertToDouble,
		Unsupported
	}

}