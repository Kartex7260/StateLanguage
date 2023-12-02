package kanti.sl;

import kanti.sl.arguments.StateArgument;
import kanti.sl.arguments.StateArguments;
import kanti.sl.objects.StateObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StateObjectSerializerImpl implements StateObjectSerializer {

	private final String nameArgsSeparator;
	private final String argsSeparator;
	private final String keyValueSeparator;
	private final ValueConverter valueConverter;

	public StateObjectSerializerImpl(
		String nameArgsSeparator,
		String argsSeparator,
		String keyValueSeparator,
		ValueConverter valueConverter
	) {
		this.nameArgsSeparator = nameArgsSeparator;
		this.argsSeparator = argsSeparator;
		this.keyValueSeparator = keyValueSeparator;
		this.valueConverter = valueConverter;
	}

	@NotNull
	@Override
	public String serialize(@NotNull StateObject obj) {
		final String name = obj.getName();
		final List<? extends StateArgument> args = obj.getArguments().getArguments();
		final StringBuilder sb = new StringBuilder();

		sb.append(name);
		if (!args.isEmpty()) {
			sb.append(nameArgsSeparator);

			for (int index = 0; index < args.size(); index++) {
				StateArgument arg = args.get(index);
				String valueString = valueConverter.convert(arg.getValue());
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
		return StateObject.create(nameArgs[0], args);
	}

	@NotNull
	private StateArgument[] getArgs(@NotNull String line) {
		List<StateArgument> args = new ArrayList<>();
		String[] argsString = line.split(argsSeparator);
		int argsCount = 0;
		for (String argString : argsString) {
			String[] keyValue = argString.split(keyValueSeparator);
			if (keyValue.length < 2)
				throw new IllegalStateException("Invalid input arg string (key:value): " + argString);

			Object value = valueConverter.convert(keyValue[1]);
			StateArgument arg = StateArgument.create(keyValue[0], value);
			args.add(arg);
			argsCount++;
		}

		StateArgument[] argsResult = new StateArgument[argsCount];
		for (int index = 0; index < argsCount; index++) {
			argsResult[index] = args.get(index);
		}
		return argsResult;
	}

	public static class Builder implements StateObjectSerializer.Builder {

		private static final String defaultNameArgsSeparator = ":";
		private static final String defaultArgsSeparator = ",";
		private static final String defaultKeyValueSeparator = "=";

		private String nameArgsSeparator = null;
		private String argsSeparator = null;
		private String keyValueSeparator = null;
		private ValueConverter.Builder valueConverterBuilder = null;

		@NotNull
		@Override
		public StateObjectSerializer.Builder setNameArgsSeparator(@Nullable String separator) {
			nameArgsSeparator = separator;
			return this;
		}

		@NotNull
		@Override
		public StateObjectSerializer.Builder setArgsSeparator(@Nullable String separator) {
			argsSeparator = separator;
			return this;
		}

		@NotNull
		@Override
		public StateObjectSerializer.Builder setKeyValueSeparator(@Nullable String separator) {
			keyValueSeparator = separator;
			return this;
		}

		@NotNull
		@Override
		public StateObjectSerializer.Builder setValueConverter(@Nullable ValueConverter.Builder builder) {
			valueConverterBuilder = builder;
			return this;
		}

		@NotNull
		@Override
		public StateObjectSerializer build() {
			String nameArgsSeparator;
			String argsSeparator;
			String keyValueSeparator;
			ValueConverter valueConverter;

			if (this.nameArgsSeparator == null) {
				nameArgsSeparator = defaultNameArgsSeparator;
			} else {
				nameArgsSeparator = this.nameArgsSeparator;
			}

			if (this.argsSeparator == null) {
				argsSeparator = defaultArgsSeparator;
			} else {
				argsSeparator = this.argsSeparator;
			}

			if (this.keyValueSeparator == null) {
				keyValueSeparator = defaultKeyValueSeparator;
			} else {
				keyValueSeparator = this.keyValueSeparator;
			}

			if (valueConverterBuilder == null) {
				valueConverter = ValueConverter.builder().build();
			} else {
				valueConverter = valueConverterBuilder.build();
			}

			return new StateObjectSerializerImpl(
					nameArgsSeparator,
					argsSeparator,
					keyValueSeparator,
					valueConverter
			);
		}

	}

}
