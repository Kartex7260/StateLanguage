package kanti.sl.objects

class StateObjectsImpl(
	objects: Iterable<StateObject>
) : StateObjects {

	private val mapObj: Map<String, StateObject>

	init {
		mapObj = buildMap {
			for (obj in objects) {
				put(obj.name, obj)
			}
		}
	}

	override val objects: List<StateObject>
		get() = mapObj.values.toList()

	override operator fun get(name: String): StateObject? {
		return mapObj[name]
	}
}