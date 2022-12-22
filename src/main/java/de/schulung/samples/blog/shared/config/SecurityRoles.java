package de.schulung.samples.blog.shared.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RequiredArgsConstructor
public enum SecurityRoles {

    READER("READER"), AUTHOR("AUTHOR");

    @Getter
    private final String name;

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
    @PreAuthorize("hasRole('AUTHOR')")
    public @interface OnlyAuthors {

    }

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
    @PreAuthorize("hasRole('READER')")
    public @interface OnlyReaders {

    }

}
