package kanti.sl

interface ValueConverter {

	fun convert(value: Any): String

	fun convert(line: String): Any

	interface Builder {

		fun supportPrimitive(support: Boolean): Builder

		fun build(): ValueConverter

	}

}