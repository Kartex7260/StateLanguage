package kanti.sl.objects

interface StateObjects {

	val objects: List<StateObject>

	operator fun get(name: String): StateObject?

	companion object {

		@JvmStatic
		fun create(vararg obj: StateObject): StateObjects {
			return StateObjectsImpl(
				objects = obj.asIterable()
			)
		}

	}

}