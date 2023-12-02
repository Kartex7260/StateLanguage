package kanti.sl

import kanti.sl.arguments.MutableStateArgument
import kanti.sl.arguments.MutableStateArguments
import kanti.sl.arguments.StateArguments
import kanti.sl.objects.MutableStateObject
import kanti.sl.objects.StateObject
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class StateLanguageImplTest {

	private val sl: StateLanguage = StateLanguage.builder()
		.registerConverter(User::class.java, UserObjectConverter)
		.build()

	@Test
	fun parse() {
		val user = User("Jojo", 1000)
		val stg = sl.from(user.javaClass, user)
		assertEquals(
			"User:name=Jojo,age=1000",
			stg
		)
	}

	@Test
	fun from() {
		val line = "User:name=Jojo,age=1000"
		val parsed = sl.parse(User::class.java, line)
		assertEquals(
			User("Jojo", 1000),
			parsed
		)
	}

}

private object UserObjectConverter : StateObjectConverter<User> {

	override fun convert(arguments: MutableStateArguments, state: User) {
		arguments.put(
			MutableStateArgument.create(
				name = "name",
				value = state.name
			)
		)
		arguments.put(
			MutableStateArgument.create(
				name = "age",
				value = state.age
			)
		)
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