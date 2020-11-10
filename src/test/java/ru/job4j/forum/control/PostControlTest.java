package ru.job4j.forum.control;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class PostControlTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService posts;

    @Test
    @WithMockUser
    public void shouldReturnCreatePage() throws Exception {
               this.mockMvc.perform(get("/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("post/create"));
    }

    @Test
    @WithMockUser
    public void idExistShouldReturnPostView() throws Exception {
        Mockito.when(posts.findById(1)).thenReturn(new Post());
        MultiValueMap<String, String> paraMap =new LinkedMultiValueMap<>();
        paraMap.add("id", "1");
        this.mockMvc.perform(get("/post").params(paraMap))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("post/view"));
    }

    @Test
    @WithMockUser
    public void idNotExistShouldReturnRedirect() throws Exception {
        Mockito.when(posts.findById(1)).thenReturn(new Post());
        MultiValueMap<String, String> paraMap =new LinkedMultiValueMap<>();
        paraMap.add("id", "400");
        this.mockMvc.perform(get("/post").params(paraMap))
                .andDo(print())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser
    public void shouldReturnPostEditPage() throws Exception {
        this.mockMvc.perform(get("/post/edit"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("post/edit"));
    }

    @Test
    @WithMockUser
    public void shouldReturnRedirectAndSavePost() throws Exception {
        this.mockMvc.perform(post("/post/save")
                .param("name","Куплю машину")
                .param("desc", "Куплю машину дорого"))
                .andDo(print())
                .andExpect(redirectedUrl("/"));
        ArgumentCaptor<Post> argument = ArgumentCaptor.forClass(Post.class);
        verify(posts).save(argument.capture());
        assertThat(argument.getValue().getName(), is("Куплю машину"));
        assertThat(argument.getValue().getDesc(), is("Куплю машину дорого"));
    }

    @Test
    @WithMockUser
    public void shouldReturnRedirectAndUpdatePost() throws Exception {
        this.mockMvc.perform(post("/post/update")
                .param("id","10")
                .param("name","Куплю машину")
                .param("desc", "Куплю машину дорого"))
                .andDo(print())
                .andExpect(redirectedUrl("/"));
        ArgumentCaptor<Post> argument = ArgumentCaptor.forClass(Post.class);
        verify(posts).update(argument.capture());
        assertThat(argument.getValue().getId(), is(10));
        assertThat(argument.getValue().getName(), is("Куплю машину"));
        assertThat(argument.getValue().getDesc(), is("Куплю машину дорого"));
    }
}