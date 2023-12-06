package kanti.sl;

import kanti.sl.annotations.Argument;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class User {

	@NotNull
	private final String name;

	@NotNull
	private final int age;

	public User(
		@Argument(name = "name") @NotNull String name,
		@Argument(name = "age") int age
	) {
		this.name = name;
		this.age = age;
	}

	@NotNull
	public String getName() {
		return name;
	}

	@NotNull
	public int getAge() {
		return age;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return age == user.age && Objects.equals(name, user.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, age);
	}

}
