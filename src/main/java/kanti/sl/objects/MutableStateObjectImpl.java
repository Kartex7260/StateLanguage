package kanti.sl.objects;

import kanti.sl.arguments.MutableStateArgument;
import kanti.sl.arguments.MutableStateArguments;
import org.jetbrains.annotations.NotNull;

public class MutableStateObjectImpl implements MutableStateObject {

	private String name;
	private final MutableStateArguments args;

	public MutableStateObjectImpl(@NotNull String name, MutableStateArgument... args) {
		this.name = name;
		this.args = MutableStateArguments.create(args);
	}

	public MutableStateObjectImpl(@NotNull String name) {
		this.name = name;
		this.args = MutableStateArguments.create();
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

}
