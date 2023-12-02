package kanti.sl

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

class ValueConverterImplTest {

	@Test
	fun convertFail1() {
		try {
			ValueConverterImpl(true).convert(Class::class)
			assert(false)
		} catch (_: IllegalArgumentException) {
		}
	}

	@Test
	fun convertFail2() {
		try {
			ValueConverterImpl(true).convert(0.1f)
			assert(false)
		} catch (_: IllegalArgumentException) {
		}
	}

	@Test
	fun convertFail3() {
		try {
			ValueConverterImpl(true).convert(UByte.MAX_VALUE)
			assert(false)
		} catch (_: IllegalArgumentException) {
		}
	}

	@Test
	fun convertInt() {
		val stg = ValueConverterImpl(true).convert(20)
		Assertions.assertEquals("20", stg)
	}

	@Test
	fun convertDouble() {
		val stg = ValueConverterImpl(true).convert(2.0)
		Assertions.assertEquals("2.0", stg)
	}

	@Test
	fun convertBoolean() {
		val stg = ValueConverterImpl(true).convert(true)
		Assertions.assertEquals("true", stg)
	}

	@Test
	fun convertString() {
		val stg = ValueConverterImpl(true).convert("Hello" as Any)
		Assertions.assertEquals("Hello", stg)
	}

	@Test
	fun convertToPrimitiveInt() {
		val i = ValueConverterImpl(true).convert("1234")
		Assertions.assertTrue(i is Int)
		Assertions.assertEquals(1234, i)
	}

	@Test
	fun convertToPrimitiveDouble() {
		val i = ValueConverterImpl(true).convert("12.34")
		Assertions.assertTrue(i is Double)
		Assertions.assertEquals(12.34, i)
	}

	@Test
	fun convertToPrimitiveBoolean() {
		val i = ValueConverterImpl(true).convert("true")
		Assertions.assertTrue(i is Boolean)
		Assertions.assertEquals(true, i)
	}

	@Test
	fun convertToString1() {
		val i = ValueConverterImpl(true).convert("tree")
		Assertions.assertTrue(i is String)
		Assertions.assertEquals("tree", i)
	}

	@Test
	fun convertToString2() {
		val i = ValueConverterImpl(true).convert("320d")
		Assertions.assertTrue(i is String)
		Assertions.assertEquals("320d", i)
	}

}