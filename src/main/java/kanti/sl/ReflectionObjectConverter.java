package kanti.sl;

import kanti.sl.annotations.Argument;
import kanti.sl.annotations.Ignore;
import kanti.sl.annotations.Select;
import kanti.sl.arguments.MutableStateArguments;
import kanti.sl.arguments.StateArgument;
import kanti.sl.arguments.StateArguments;
import kanti.sl.arguments.values.SupportedValues;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Consumer;

public class ReflectionObjectConverter extends BaseStateObjectConverter {

	private final String fullClassNameKey = "fullClassName";
	private final String methodGetPrefix = "get";

	@Override
	public void convert(@NotNull MutableStateArguments args, @NotNull Object state) {
		Class<?> type = state.getClass();
		args.put(fullClassNameKey, type.getName());

		List<Method> methods = getGetMethods(type);

		for (Method method : methods) {
			String key = getArgumentKey(method);
			try {
				Object value = method.invoke(state);
				args.put(key, value);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@NotNull
	@Override
	public Object convert(@NotNull StateArguments args) {
		String className = getClassName(args);
		try {
			Class<?> type = Class.forName(className);
			Constructor<?> constructor = getConstructor(type);
			List<Object> values = new ArrayList<>();
			for (Parameter parameter : constructor.getParameters()) {
				String argumentKey = getArgumentKey(parameter);
				StateArgument argument = args.get(argumentKey);
				if (argument == null) {
					throw new IllegalArgumentException("Not found argument = " + argumentKey);
				}
				values.add(argument.getValue());
			}
			return constructor.newInstance(values.toArray());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
			InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	@NotNull
	private List<Method> getGetMethods(@NotNull Class<?> type) {
		SupportedValues supportedValues = getContext().getSupportedValues();
		List<Method> methods = new ArrayList<>();
		for (Method method : type.getMethods()) {
			if (!Modifier.isPublic(method.getModifiers()))
				continue;
			if (!method.getName().startsWith(methodGetPrefix))
				continue;
			if (method.getParameterCount() > 0)
				continue;
			if (!supportedValues.isSupported(method.getReturnType()))
				continue;
			Ignore ignore = method.getAnnotation(Ignore.class);
			if (ignore != null)
				continue;
			methods.add(method);
		}
		return methods;
	}

	@NotNull
	private String getArgumentKey(@NotNull Method method) {
		 Argument argument = method.getAnnotation(Argument.class);
		 if (argument != null) {
			 return argument.name();
		 }
		 String fullName = method.getName();
		 String withoutGetName = fullName.substring(3);
		 String firstSymbol = withoutGetName.substring(0, 1);
		 String firstSymbolLow = firstSymbol.toLowerCase();
		 return withoutGetName.replace(firstSymbol, firstSymbolLow);
	}

	@NotNull
	private String getArgumentKey(@NotNull Parameter parameter) {
		Argument argument = parameter.getAnnotation(Argument.class);
		if (argument != null)
			return argument.name();
		throw new IllegalStateException("Parameter not have " + Argument.class.getName() + " annotation");
	}

	@NotNull
	private String getClassName(@NotNull StateArguments args) {
		StateArgument classNameArgument = args.get(fullClassNameKey);
		if (classNameArgument == null)
			throw new IllegalArgumentException("Not found full class name");
		return (String) classNameArgument.getValue();
	}

	@NotNull
	private Constructor<?> getConstructor(@NotNull Class<?> type) {
		Constructor<?>[] constructors = type.getConstructors();
		for (Constructor<?> constructor : constructors) {
			Select select = constructor.getAnnotation(Select.class);
			if (select != null)
				return constructor;
		}

		Optional<Constructor<?>> optional = Arrays.stream(constructors)
			.max(Comparator.comparingInt(Constructor::getParameterCount));

		if (optional.isPresent())
			return optional.get();
		throw new IllegalStateException("A suitable constructor was not found");
	}

}
