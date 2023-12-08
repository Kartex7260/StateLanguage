package kanti.sl

import kanti.sl.arguments.StateArgument
import kanti.sl.objects.StateObject
import kanti.sl.std.BaseContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StateObjectSerializerImplTest {

	private val serializer: StateObjectSerializer = StateObjectSerializer.builder()
		.setContext(BaseContext.getBuilder().build())
		.build()

	@Test
	fun serialize() {
		val stg = serializer.serialize(
			StateObject.create(serializer.context, "Test", *arrayOf(
					StateArgument.create(serializer.context, "isSuccess", true),
					StateArgument.create(serializer.context, "amount", 100.3)
				)
			)
		)
		Assertions.assertEquals(
			"Test:isSuccess=BOOLEAN-true,amount=DOUBLE-100.3",
			stg
		)
	}

	@Test
	fun deserialize() {
		val line = "Test:isSuccess=BOOLEAN-true,amount=DOUBLE-100.3"
		val obj = serializer.deserialize(line)
		Assertions.assertEquals("Test", obj.name)

		val arg1 = obj.arguments["isSuccess"]
		Assertions.assertEquals(arg1?.key, "isSuccess")
		Assertions.assertEquals(arg1?.value, true)

		val arg2 = obj.arguments["amount"]
		Assertions.assertEquals(arg2?.key, "amount")
		Assertions.assertEquals(arg2?.value, 100.3)
	}

}