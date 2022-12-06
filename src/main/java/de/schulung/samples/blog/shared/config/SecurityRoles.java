package de.schulung.samples.blog.shared.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SecurityRoles {

    READER("READER"), AUTHOR("AUTHOR");

    @Getter
    private final String name;

}
