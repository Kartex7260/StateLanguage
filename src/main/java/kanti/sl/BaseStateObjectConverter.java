package kanti.sl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseStateObjectConverter implements StateObjectConverter {

	@Nullable
	private SLContext context;

	public void setContext(@NotNull SLContext context) {
		this.context = context;
	}

	@NotNull
	@Override
	public SLContext getContext() {
		if (context == null) {
			throw new IllegalStateException("Context not initialized!");
		}
		return context;
	}

}
