package kanti.sl.objects

class MutableStateObjectsImpl @JvmOverloads constructor(
	objects: Iterable<MutableStateObject> = listOf()
) : MutableStateObjects {

	private val objMap: MutableMap<String, MutableStateObject> = HashMap()

	override val objects: List<MutableStateObject>
		get() = objMap.values.toList()

	override fun put(obj: MutableStateObject) {
		objMap[obj.name] = obj
	}

	override fun get(name: String): MutableStateObject? {
		return objMap[name]
	}
}