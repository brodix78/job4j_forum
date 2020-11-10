package ru.job4j.forum.control;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.UserRepository;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class RegControlTest {

    @MockBean
    UserRepository users;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void whenLoginWithoutMistakesNoErrorsReturn() throws Exception {
        this.mockMvc.perform(get("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("reg"));
    }

    @Test
    @WithMockUser
    public void shouldReturnRedirectToLoginPageAndSaveUser() throws Exception {
        this.mockMvc.perform(post("/reg")
                .param("username","user")
                .param("password", "1234"))
                .andDo(print())
                .andExpect(redirectedUrl("/login"));
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(users).save(argument.capture());
        assertThat(argument.getValue().getUsername(), is("user"));
    }
}