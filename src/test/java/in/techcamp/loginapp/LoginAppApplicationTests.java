package in.techcamp.loginapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class LoginAppApplicationTests {

		@Autowired
		private PasswordEncoder encoder;

		@Test
		void encode() {
			System.out.println(encoder.encode("test"));
			System.out.println(encoder.encode("user"));
		}
}
