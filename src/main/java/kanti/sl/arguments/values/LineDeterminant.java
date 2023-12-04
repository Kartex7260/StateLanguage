package kanti.sl.arguments.values;

import org.jetbrains.annotations.NotNull;

public interface LineDeterminant {

	boolean isThis(@NotNull String line);

	static LineDeterminant getInstance() {
		if (LineDeterminantImpl._instance == null) {
			LineDeterminantImpl._instance = new LineDeterminantImpl();
		}
		return LineDeterminantImpl._instance;
	}

}

class LineDeterminantImpl implements LineDeterminant {

	@Override
	public boolean isThis(@NotNull String line) {
		throw new IllegalArgumentException("Unsupported value type!");
	}

	static LineDeterminantImpl _instance = null;

}
