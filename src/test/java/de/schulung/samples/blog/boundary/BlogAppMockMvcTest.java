package de.schulung.samples.blog.boundary;

import de.schulung.samples.blog.domain.BlogPostService;
import de.schulung.samples.blog.domain.HashTagService;
import de.schulung.samples.blog.shared.config.BlogAppConfigurationProperties;
import de.schulung.samples.blog.shared.config.SecurityConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A test slice for MockMVC tests. Those tests share the same bean context,
 * that is configured to include
 * <ul>
 *     <li>Spring MVC components (controllers, controller advices...)</li>
 *     <li>components in the <tt>boundary</tt> package</li>
 *     <li>service mocks (we can get them using @{@link org.springframework.beans.factory.annotation.Autowired})</li>
 *     <li>shared components like application properties</li>
 *     <li>security configuration with a mock user with <tt>READER</tt> role</li>
 *     <li><tt>test</tt> and <tt>test-mockmvc</tt> profiles</li>
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@WebMvcTest
@ComponentScan(basePackageClasses = {
  // boundary
  BlogAppMockMvcTest.class,
  // shared.config
  BlogAppConfigurationProperties.class,
  SecurityConfiguration.class,
})
@Import(BlogAppMockMvcTest.BoundaryMocksConfiguration.class)
@WithMockUser(roles = "READER")
@ActiveProfiles({ "test", "test-mockmvc" })
public @interface BlogAppMockMvcTest {

    @TestConfiguration
    class BoundaryMocksConfiguration {

        @MockBean
        BlogPostService blogPostServiceMock;

        @MockBean
        HashTagService hashTagServiceMock;

    }

}
