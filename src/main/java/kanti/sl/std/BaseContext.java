package kanti.sl.std;

import kanti.sl.SLContext;
import kanti.sl.arguments.values.SupportedValues;
import org.jetbrains.annotations.NotNull;

public final class BaseContext {

	@NotNull
	public static SLContext.Builder getBuilder() {
		return SLContext.builder()
			.setSupportedValues(baseSupportedValues());
	}

	@NotNull
	public static SupportedValues.Builder baseSupportedValues() {
		return SupportedValues.builder()
			.registerSupportedValue(BooleanValue.getSupportedValue())
			.registerSupportedValue(IntValue.getSupportedValue())
			.registerSupportedValue(DoubleValue.getSupportedValue())
			.registerSupportedValue(StringValue.getSupportedValue())
			.registerValueNormalizer(Byte.class, new ByteNormalizer())
			.registerValueNormalizer(Short.class, new ShortNormalizer())
			.registerValueNormalizer(Long.class, new LongNormalizer())
			.registerValueNormalizer(Float.class, new FloatNormalizer());
	}

}
