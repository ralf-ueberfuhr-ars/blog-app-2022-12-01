package de.schulung.samples.blog.boundary.rest;

import de.schulung.samples.blog.shared.config.SecurityRoles.OnlyAuthors;
import de.schulung.samples.blog.shared.config.SecurityRoles.OnlyReaders;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.experimental.UtilityClass;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@UtilityClass
public class ApiSecurity {

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
    @OnlyAuthors
    @ApiResponse(responseCode = "403", description = "The current user does not have AUTHOR role.")
    @SecurityRequirement(name = OpenApiConstants.SECURITY_NAME)
    public @interface OnlyApiAuthors {

    }

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
    @OnlyReaders
    @ApiResponse(responseCode = "403", description = "The current user does not have READER role.")
    @SecurityRequirement(name = OpenApiConstants.SECURITY_NAME)
    public @interface OnlyApiReaders {

    }

}
