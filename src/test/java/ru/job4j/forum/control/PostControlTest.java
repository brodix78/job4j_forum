package ru.job4j.forum.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.Test;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.job4j.forum.Main;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class PostControlTest {

    @Autowired
    private MockMvc mockMvc;

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
}