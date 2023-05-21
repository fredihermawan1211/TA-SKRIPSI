package com.api.ponline;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.api.ponline.dao.Request.LoginRequest;
import com.api.ponline.model.Entity.user.AuthProvider;
import com.api.ponline.model.Entity.user.User;
import com.api.ponline.model.Entity.user.UserRole;
import com.api.ponline.model.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ObjectMapper objectMapper;

    private String emailUserEmployee = "user_employee@test.com";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void ujiLoginBerhasil() throws Exception {

        User user = userRepository.findOneByEmail(emailUserEmployee);
        if (user == null) {
            user = new User();
        }
        
        // Set up test data if user doesn'y exist
        user.setName("Nama Pengguna");
        user.setEmail(emailUserEmployee);
        user.setPassword(passwordEncoder.encode("password123"));
        user.setProvider( AuthProvider.local);
        user.setRole(UserRole.ROLE_USER);
        user.setEmailVerified(true);
        userRepository.save(user);

        // Set up request data
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(emailUserEmployee);
        loginRequest.setPassword("password123");
        

        // Perform request
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/login")
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").isNotEmpty());
                
    }

    @Test
    public void ujiLoginEmailBElumDiVerifikasi() throws Exception {
        
        User user = userRepository.findOneByEmail(emailUserEmployee);
        if (user == null) {
            user = new User();
        }

        user.setName("Nama Pengguna");
        user.setEmail(emailUserEmployee);
        user.setPassword(passwordEncoder.encode("password123"));
        user.setProvider( AuthProvider.local);
        user.setRole(UserRole.ROLE_USER);
        user.setEmailVerified(false);
        userRepository.save(user);

        // Set up request data
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(emailUserEmployee);
        loginRequest.setPassword("password123");

        Boolean actionRemove = false;
        User getUser = userRepository.findOneByEmail(user.getEmail());
        if (getUser != null) {
            actionRemove = true;
        }

        // Perform request
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Akun anda belum diverifikasi, Silahkan cek email untuk memverifikasi akun anda"));
        
        if (actionRemove) {
            userRepository.delete(getUser);
        }
    }

    @Test
    public void ujiLoginAkunBelumTerdaftar() throws Exception {
        // Set up request data
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("user_belum_terdaftar@test.com");
        loginRequest.setPassword("password");

        // Perform request
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Akun anda belum terdaftar"));
    }

}
