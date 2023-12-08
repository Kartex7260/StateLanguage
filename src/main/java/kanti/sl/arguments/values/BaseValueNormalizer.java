package kanti.sl.arguments.values;

import kanti.sl.SLContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseValueNormalizer implements ValueNormalizer {

	@Nullable
	private SLContext context = null;

	@NotNull
	@Override
	public SLContext getContext() {
		if (context == null)
			throw new IllegalStateException("Context not initialized!");
		return context;
	}

	@Override
	public void setContext(@NotNull SLContext context) {
		this.context = context;
	}

}
