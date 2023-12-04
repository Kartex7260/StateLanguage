package kanti.sl.objects;

import kanti.sl.SLContext;
import kanti.sl.SLContextOwner;
import kanti.sl.arguments.StateArgument;
import kanti.sl.arguments.StateArguments;
import org.jetbrains.annotations.NotNull;

public interface StateObject extends SLContextOwner {

	@NotNull
	String getName();

	@NotNull
	StateArguments getArguments();

	@NotNull
	static StateObject create(
		@NotNull SLContext context,
		@NotNull String name,
		@NotNull StateArgument... args
	) {
		return new StateObjectImpl(context, name, args);
	}

}

class StateObjectImpl implements StateObject {

	private final SLContext context;
	private final String name;
	private final StateArguments args;

	StateObjectImpl(
		@NotNull SLContext context,
		@NotNull String name,
		@NotNull StateArgument... args
	) {
		this.context = context;
		this.name = name;
		this.args = StateArguments.create(context, args);
	}

	@NotNull
	@Override
	public String getName() {
		return name;
	}

	@NotNull
	@Override
	public StateArguments getArguments() {
		return args;
	}

	@NotNull
	@Override
	public SLContext getContext() {
		return context;
	}

}
