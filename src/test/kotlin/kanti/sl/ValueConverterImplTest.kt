package kanti.sl

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
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
		assertEquals("20", stg)
	}

	@Test
	fun convertDouble() {
		val stg = ValueConverterImpl(true).convert(2.0)
		assertEquals("2.0", stg)
	}

	@Test
	fun convertBoolean() {
		val stg = ValueConverterImpl(true).convert(true)
		assertEquals("true", stg)
	}

	@Test
	fun convertString() {
		val stg = ValueConverterImpl(true).convert("Hello" as Any)
		assertEquals("Hello", stg)
	}

	@Test
	fun convertToPrimitiveInt() {
		val i = ValueConverterImpl(true).convert("1234")
		assertTrue(i is Int)
		assertEquals(1234, i)
	}

	@Test
	fun convertToPrimitiveDouble() {
		val i = ValueConverterImpl(true).convert("12.34")
		assertTrue(i is Double)
		assertEquals(12.34, i)
	}

	@Test
	fun convertToPrimitiveBoolean() {
		val i = ValueConverterImpl(true).convert("true")
		assertTrue(i is Boolean)
		assertEquals(true, i)
	}

	@Test
	fun convertToString1() {
		val i = ValueConverterImpl(true).convert("tree")
		assertTrue(i is String)
		assertEquals("tree", i)
	}

	@Test
	fun convertToString2() {
		val i = ValueConverterImpl(true).convert("320d")
		assertTrue(i is String)
		assertEquals("320d", i)
	}

}