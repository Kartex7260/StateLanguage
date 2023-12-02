package kanti.sl.arguments

class MutableStateArgumentImpl @JvmOverloads constructor(
	override var name: String = "",
	value: Any? = null
) : MutableStateArgument {

	private var _value: Any? = value
	override var value: Any
		get() {
			val v = _value
			check(v != null) {
				"Value not initialized!"
			}
			return v
		}
		set(value) {
			val checkedType = TypeChecker.getType(value)
			_value = TypeChecker.convert(value, checkedType)
		}

}