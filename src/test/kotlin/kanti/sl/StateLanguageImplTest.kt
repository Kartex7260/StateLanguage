package kanti.sl

import kanti.sl.arguments.MutableStateArgument
import kanti.sl.arguments.MutableStateArguments
import kanti.sl.arguments.StateArguments
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StateLanguageImplTest {

	private val sl: StateLanguage = StateLanguage.builder()
		.registerConverter(User::class.java, UserObjectConverter)
		.build()

	@Test
	fun parse() {
		val user = User("Jojo", 1000)
		val stg = sl.from(user.javaClass, user)
		Assertions.assertEquals(
			"User:name=Jojo,age=1000",
			stg
		)
	}

	@Test
	fun from() {
		val line = "User:name=Jojo,age=1000"
		val parsed = sl.parse(User::class.java, line)
		Assertions.assertEquals(
			User("Jojo", 1000),
			parsed
		)
	}

}

private object UserObjectConverter : StateObjectConverter {

	override fun convert(arguments: MutableStateArguments, state: Any) {
		if (state !is User)
			return
		arguments.put( "name", state.name)
		arguments.put("age", state.age)
	}

	override fun convert(arguments: StateArguments): User {
		val name = arguments["name"]?.value as String? ?: throw IllegalStateException("No name")
		val age = arguments["age"]?.value as Int? ?: throw IllegalStateException("No age")
		return User(
			name = name,
			age = age
		)
	}

}

private data class User(
	val name: String,
	val age: Int
)