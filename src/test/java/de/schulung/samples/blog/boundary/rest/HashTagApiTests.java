package de.schulung.samples.blog.boundary.rest;

import de.schulung.samples.blog.boundary.BlogAppMockMvcTest;
import de.schulung.samples.blog.domain.HashTag;
import de.schulung.samples.blog.domain.HashTagService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = HashTagRestController.class)
//@ComponentScan(basePackages = "de.schulung.samples.blog.shared.config")
//@Import(TestSecurityConfiguration.class)
//@WithMockUser(roles = "READER")
@BlogAppMockMvcTest
class HashTagApiTests {

    @Autowired
    MockMvc mvc;
    //@MockBean
    @Autowired
    HashTagService service;

    // PUT tags/xyz + AUTHOR + BODY + not existing -> 201
    @Test
    @WithMockUser(value = "test-user", roles = "AUTHOR")
    void shouldReturn201CreatedOnSaveNonExistingTag() throws Exception {
        when(service.exists("xyz")).thenReturn(false);
        mvc.perform(
            put("/api/v1/tags/xyz")
              .contentType(MediaType.APPLICATION_JSON)
              .content("{\"description\":  \"lorem ipsum\"}")
          )
          .andExpect(status().isCreated());
        ArgumentCaptor<HashTag> captor = ArgumentCaptor.forClass(HashTag.class);
        verify(service).save(captor.capture());
        assertThat(captor.getValue())
          .extracting(HashTag::getName, HashTag::getDescription)
          .containsExactly("xyz", "lorem ipsum");
    }

    // PUT tags/xyz + AUTHOR + BODY + existing -> 204
    @Test
    @WithMockUser(value = "test-user", roles = "AUTHOR")
    void shouldReturn204NoContentOnSaveNonExistingTag() throws Exception {
        when(service.exists("xyz")).thenReturn(true);
        mvc.perform(
            put("/api/v1/tags/xyz")
              .contentType(MediaType.APPLICATION_JSON)
              .content("{\"description\":  \"lorem ipsum\"}")
          )
          .andExpect(status().isNoContent());
        ArgumentCaptor<HashTag> captor = ArgumentCaptor.forClass(HashTag.class);
        verify(service).save(captor.capture());
        assertThat(captor.getValue())
          .extracting(HashTag::getName, HashTag::getDescription)
          .containsExactly("xyz", "lorem ipsum");
    }

    // PUT tags/xyz + READER + BODY -> 403 + service not invoked
    @Test
    void shouldReturn403ForbiddenOnMissingAuthorRoleOnSaveTag() throws Exception {
        mvc.perform(
            put("/api/v1/tags/xyz")
              .contentType(MediaType.APPLICATION_JSON)
              .content("{\"description\":  \"lorem ipsum\"}")
          )
          .andExpect(status().isForbidden());
        verifyNoInteractions(service);
    }

}
