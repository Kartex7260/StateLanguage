package kanti.sl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StateLanguageImplTest {

	private final StateLanguage sl = StateLanguage.builder().build();

	@Test
	public void parse() {
		User user = new User("Jojo", 1000);
		String line = sl.from(User.class, user);
		Assertions.assertEquals(
			"User:fullClassName=kanti.sl.User,name=Jojo,age=1000",
			line
		);
	}

	@Test
	public void from() {
		String line = "User:fullClassName=kanti.sl.User,name=Jojo,age=1000";
		User user = (User) sl.parse(User.class, line);
		User expected = new User("Jojo", 1000);
		Assertions.assertEquals(
			expected,
			user
		);
	}

}


