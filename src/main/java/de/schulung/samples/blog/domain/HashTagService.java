package de.schulung.samples.blog.domain;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@RequiredArgsConstructor
public class HashTagService {

    @Delegate
    private final HashTagSink sink;

}
