package resource;

import lpnu.Application;
import lpnu.dto.UserDTO;
import lpnu.entity.User;
import lpnu.mapper.UserToUserDTOMapper;
import lpnu.service.UserService;
import lpnu.util.JacksonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class UserTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserToUserDTOMapper userMapper;

    @Test
    public void saveUser_thenStatus200() throws Exception {
        final UserDTO userDTO = new UserDTO(null, "Maksym", "Danyliuk", "maksdaniluk140@gmail.com", null);

        mvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JacksonUtil.serialize(userDTO))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Maksym")));
    }

    @Test
    public void getAllUsers_thenStatus200() throws Exception {
        final User user = new User(null, "Maksym", "Danyliuk", "maksdaniluk140@gmail.com", null);
        userService.saveUser(userMapper.toDTO(user));

        mvc.perform(get("/api/v1/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("Maksym")));
    }

    @Test
    public void getUserById_thenStatus200() throws Exception {
        final User user = new User(null, "Maksym", "Danyliuk", "maksdaniluk140@gmail.com", null);
        final UserDTO userDTO = userService.saveUser(userMapper.toDTO(user));

        mvc.perform(get("/api/v1/users/" + userDTO.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Maksym")));
    }

    @Test
    public void updateUser_thenStatus200() throws Exception {
        final User user1 = new User(null, "Maksym", "Danyliuk", "maksdaniluk140@gmail.com", null);
        final User user2 = new User(1L, "Oleg", "Kalka", "oleg934@gmail.com", null);

        userService.saveUser(userMapper.toDTO(user1));

        mvc.perform(put("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JacksonUtil.serialize(user2))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Oleg")));
    }

    @Test
    public void deleteUserById_thenStatus200() throws Exception {
        final User user1 = new User(null, "Maksym", "Danyliuk", "maksdaniluk140@gmail.com", null);
        final User user2 = new User(null, "Oleg", "Kalka", "oleg934@gmail.com", null);

        userService.saveUser(userMapper.toDTO(user1));
        final UserDTO userDTO =  userService.saveUser(userMapper.toDTO(user2));

        mvc.perform(delete("/api/v1/users/"+userDTO.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(userService.getAllUsers().size(),1);
    }
}
