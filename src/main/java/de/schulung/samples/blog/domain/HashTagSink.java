package de.schulung.samples.blog.domain;

import javax.validation.Valid;

public interface HashTagSink {

    /**
     * Searches for a hash tag with a given name.
     * If there isn't any metadata existing, a hash tag with the name is
     * returned nevertheless.
     * @param name the hash tag name
     * @return the hash tag
     */
    HashTag resolveByName(String name);

    /**
     * Saves the hash tag metadata. If a metadata already exists, it is overwritten.
     * @param hashtag the hash tag
     */
    void save(@Valid HashTag hashtag);

}
