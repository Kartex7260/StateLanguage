package kanti.sl.objects;

import kanti.sl.SLContext;
import kanti.sl.arguments.MutableStateArgument;
import kanti.sl.arguments.MutableStateArguments;
import org.jetbrains.annotations.NotNull;

public interface MutableStateObject extends StateObject {

	void setName(@NotNull String name);

	@NotNull
	@Override
	MutableStateArguments getArguments();

	@NotNull
	static MutableStateObject create(
		@NotNull SLContext context,
		@NotNull String name,
		@NotNull MutableStateArgument... args
	) {
		return new MutableStateObjectImpl(context, name, args);
	}

}

class MutableStateObjectImpl implements MutableStateObject {

	private final SLContext context;
	private String name;
	private final MutableStateArguments args;

	MutableStateObjectImpl(
		@NotNull SLContext context,
		@NotNull String name,
		@NotNull MutableStateArgument... args
	) {
		this.context = context;
		this.name = name;
		this.args = MutableStateArguments.create(context, args);
	}

	@Override
	public void setName(@NotNull String name) {
		this.name = name;
	}

	@NotNull
	@Override
	public MutableStateArguments getArguments() {
		return args;
	}

	@NotNull
	@Override
	public String getName() {
		return name;
	}

	@NotNull
	@Override
	public SLContext getContext() {
		return context;
	}

}
