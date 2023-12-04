package kanti.sl;

import kanti.sl.arguments.StateArgument;
import kanti.sl.arguments.values.SupportedValues;
import kanti.sl.objects.StateObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public interface StateObjectSerializer extends SLContextOwner {

	@NotNull
	String serialize(@NotNull StateObject obj);

	@NotNull
	StateObject deserialize(@NotNull String line);

	interface Builder {

		@NotNull
		Builder setNameArgsSeparator(@NotNull String separator);

		@NotNull
		Builder setArgsSeparator(@NotNull String separator);

		@NotNull
		Builder setKeyValueSeparator(@NotNull String separator);

		@NotNull
		Builder setContext(@NotNull SLContext context);

		@NotNull
		StateObjectSerializer build();

	}

	@NotNull
	static Builder builder() {
		return new StateObjectSerializerImpl.Builder();
	}

}

class StateObjectSerializerImpl implements StateObjectSerializer {

	private final String nameArgsSeparator;
	private final String argsSeparator;
	private final String keyValueSeparator;
	private final SLContext context;

	public StateObjectSerializerImpl(
		@NotNull String nameArgsSeparator,
		@NotNull String argsSeparator,
		@NotNull String keyValueSeparator,
		@NotNull SLContext context
	) {
		this.nameArgsSeparator = nameArgsSeparator;
		this.argsSeparator = argsSeparator;
		this.keyValueSeparator = keyValueSeparator;
		this.context = context;
	}

	@NotNull
	@Override
	public String serialize(@NotNull StateObject obj) {
		SupportedValues supportedValues = context.getSupportedValues();
		final String name = obj.getName();
		final List<? extends StateArgument> args = obj.getArguments().getArguments();
		final StringBuilder sb = new StringBuilder();

		sb.append(name);
		if (!args.isEmpty()) {
			sb.append(nameArgsSeparator);

			for (int index = 0; index < args.size(); index++) {
				StateArgument arg = args.get(index);
				String valueString = supportedValues.serialize(arg.getValue());
				sb.append(arg.getKey())
					.append(keyValueSeparator)
					.append(valueString);
				if (index != args.size() - 1)
					sb.append(argsSeparator);
			}
		}

		return sb.toString();
	}

	@NotNull
	@Override
	public StateObject deserialize(@NotNull String line) {
		String[] nameArgs = line.split(nameArgsSeparator);
		if (nameArgs.length < 2)
			throw new IllegalStateException("Invalid input state string (name:args): " + line);

		StateArgument[] args = getArgs(nameArgs[1]);
		return StateObject.create(context, nameArgs[0], args);
	}

	@NotNull
	private StateArgument[] getArgs(@NotNull String line) {
		SupportedValues supportedValues = context.getSupportedValues();
		List<StateArgument> args = new ArrayList<>();
		String[] argsString = line.split(argsSeparator);
		int argsCount = 0;
		for (String argString : argsString) {
			String[] keyValue = argString.split(keyValueSeparator);
			if (keyValue.length < 2)
				throw new IllegalStateException("Invalid input arg string (key:value): " + argString);

			Object value = supportedValues.deserialize(keyValue[1]);
			StateArgument arg = StateArgument.create(context, keyValue[0], value);
			args.add(arg);
			argsCount++;
		}

		StateArgument[] argsResult = new StateArgument[argsCount];
		for (int index = 0; index < argsCount; index++) {
			argsResult[index] = args.get(index);
		}
		return argsResult;
	}

	@NotNull
	@Override
	public SLContext getContext() {
		return context;
	}

	static class Builder implements StateObjectSerializer.Builder {

		private String nameArgsSeparator = ":";
		private String argsSeparator = ",";
		private String keyValueSeparator = "=";
		private SLContext context = null;

		@NotNull
		@Override
		public StateObjectSerializer.Builder setNameArgsSeparator(@NotNull String separator) {
			nameArgsSeparator = separator;
			return this;
		}

		@NotNull
		@Override
		public StateObjectSerializer.Builder setArgsSeparator(@NotNull String separator) {
			argsSeparator = separator;
			return this;
		}

		@NotNull
		@Override
		public StateObjectSerializer.Builder setKeyValueSeparator(@NotNull String separator) {
			keyValueSeparator = separator;
			return this;
		}

		@NotNull
		@Override
		public StateObjectSerializer.Builder setContext(@NotNull SLContext context) {
			this.context = context;
			return this;
		}

		@NotNull
		@Override
		public StateObjectSerializer build() {
			if (context == null) {
				context = SLContext.builder().build();
			}
			return new StateObjectSerializerImpl(
				nameArgsSeparator,
				argsSeparator,
				keyValueSeparator,
				context
			);
		}

	}

}
