package kanti.sl.std;

import kanti.sl.SLContext;
import kanti.sl.arguments.values.SupportedValues;
import kanti.sl.std.values.*;
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
			.registerSupportedValue(ByteValue.getSupportedValue())
			.registerSupportedValue(BooleanValue.getSupportedValue())
			.registerSupportedValue(ShortValue.getSupportedValue())
			.registerSupportedValue(IntValue.getSupportedValue())
			.registerSupportedValue(LongValue.getSupportedValue())
			.registerSupportedValue(FloatValue.getSupportedValue())
			.registerSupportedValue(DoubleValue.getSupportedValue())
			.registerSupportedValue(StringValue.getSupportedValue());
	}

}
