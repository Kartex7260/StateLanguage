package kanti.sl.objects;

import kanti.sl.arguments.MutableStateArgument;
import kanti.sl.arguments.MutableStateArguments;
import org.jetbrains.annotations.NotNull;

public interface MutableStateObject extends StateObject {

	void setName(@NotNull String name);

	@NotNull
	@Override
	MutableStateArguments getArguments();

	@NotNull
	static MutableStateObject create(@NotNull String name, MutableStateArgument... args) {
		return new MutableStateObjectImpl(name, args);
	}

}
