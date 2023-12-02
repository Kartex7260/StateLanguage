package kanti.sl

import kanti.sl.arguments.StateArgument
import kanti.sl.objects.StateObject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class StateObjectSerializerImplTest {

	private val serializer: StateObjectSerializer = StateObjectSerializer.builder().build()

	@Test
	fun serialize() {
		val stg = serializer.serialize(
			StateObject.create(
				name = "Test",
				args = arrayOf(
					StateArgument.create("isSuccess", true),
					StateArgument.create("amount", 100.3)
				)
			)
		)
		assertEquals(
			"Test:isSuccess=true,amount=100.3",
			stg
		)
	}

	@Test
	fun deserialize() {
		val line = "Test:isSuccess=true,amount=100.3"
		val obj = serializer.deserialize(line)
		assertEquals("Test", obj.name)

		val arg1 = obj.arguments["isSuccess"]
		assertEquals(arg1?.name, "isSuccess")
		assertEquals(arg1?.value, true)

		val arg2 = obj.arguments["amount"]
		assertEquals(arg2?.name, "amount")
		assertEquals(arg2?.value, 100.3)
	}

}