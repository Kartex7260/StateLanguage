package kanti.sl.objects

interface MutableStateObjects : StateObjects {

	override val objects: List<MutableStateObject>

	fun put(obj: MutableStateObject)

	override operator fun get(name: String): MutableStateObject?

	companion object {

		@JvmStatic
		fun create(vararg objects: MutableStateObject): MutableStateObjects {
			return MutableStateObjectsImpl(
				objects = objects.asIterable()
			)
		}

	}

}