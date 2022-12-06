package de.schulung.samples.blog;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A test slice for testing the whole context. Those tests share the same bean context,
 * that is configured to include
 * <ul>
 *     <li>all Spring components</li>
 *     <li>a test database</li>
 *     <li><tt>test</tt> and <tt>test-app</tt> profiles</li>
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@SpringBootTest
@AutoConfigureTestDatabase
@WithMockUser(roles = "READER")
@ActiveProfiles({ "test", "test-app" })
public @interface BlogAppTest {
}
