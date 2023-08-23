package de.schulung.samples.blog.domain;

import jakarta.validation.Valid;
import java.util.Collection;

public interface HashTagSink {

    /**
     * Searches for the given hash tag name.
     * @param name the hash tag name
     * @return a flag indicating, whether the tag exists within the database
     */
    boolean exists(String name);

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

    Collection<HashTag> findAll();

}
