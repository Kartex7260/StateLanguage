package kanti.sl;

import kanti.sl.arguments.MutableStateArguments;
import kanti.sl.arguments.StateArguments;

public interface StateObjectConverter {

	void convert(MutableStateArguments args, Object state);

	Object convert(StateArguments args);

}
