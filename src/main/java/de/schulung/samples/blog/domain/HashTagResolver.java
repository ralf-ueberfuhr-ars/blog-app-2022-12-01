package de.schulung.samples.blog.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
class HashTagResolver {

    private final HashTagService hashTagService;
    private final HashTagMapper mapper;

    public void resolve(HashTag hashTag) {
        mapper.copy(hashTagService.resolveByName(hashTag.getName()), hashTag);
    }

}
