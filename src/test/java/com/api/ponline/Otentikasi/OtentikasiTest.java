package com.api.ponline.Otentikasi;

import org.json.JSONObject;
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
import com.api.ponline.dao.Request.ResetPasswordRequest;
import com.api.ponline.dao.Request.SignUpRequest;
import com.api.ponline.model.Entity.user.AuthProvider;
import com.api.ponline.model.Entity.user.User;
import com.api.ponline.model.Entity.user.UserRole;
import com.api.ponline.model.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OtentikasiTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    // REGISTER
    @Test
    public void ujiRegister_namaTidakAda() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("email_user@test.com");
        signUpRequest.setPassword("password123");

        String requestJson = new ObjectMapper().writeValueAsString(signUpRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/signup")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
    }

    @Test
    public void ujiRegister_emailSalahFormat() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("email_user.test.com");
        signUpRequest.setPassword("password123");

        String requestJson = new ObjectMapper().writeValueAsString(signUpRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/signup")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
    }

    @Test
    public void ujiRegister_valid() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("email_user@test.com");
        signUpRequest.setPassword("password123");
        signUpRequest.setName("Nama User");

        String requestJson = new ObjectMapper().writeValueAsString(signUpRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/signup")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());        
    }

    // VERIFIKASI EMAIL
    @Test
    public void ujiVerifikasiEmail_tokenSalah() throws Exception {
        User user = userRepository.findOneByEmail("email_user@test.com");
        String tokenEmailVerify = user.getTokEmailVerified()+"salah";

        mockMvc.perform(MockMvcRequestBuilders.get("/authentication/verifymail?token="+tokenEmailVerify)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
    }

    @Test
    public void ujiVerifikasiEmail_tokenBenar() throws Exception {
        User user = userRepository.findOneByEmail("email_user@test.com");
        String tokenEmailVerify = user.getTokEmailVerified();

        mockMvc.perform(MockMvcRequestBuilders.get("/authentication/verifymail?token="+tokenEmailVerify)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
    }

    // LOGIN
    @Test
    public void ujiLogin_formatEmailSalah() throws Exception {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("email_user.test.com");
        loginRequest.setPassword("password123");  
        
        String requestJson = new ObjectMapper().writeValueAsString(loginRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/login")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());        
    }

    @Test
    public void ujiLogin_valid() throws Exception {
        
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("email_user@test.com");
        loginRequest.setPassword("password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/login")
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());        
    }

    // FORGOT PASSWORD
    @Test
    public void ujiForgotPassword_emailTidakTerdaftar() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders.get("/authentication/forgotpassword?email=email_user_belum_terdaftar@test.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());        
    }

    @Test
    public void ujiForgotPassword_emailTerdaftar() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders.get("/authentication/forgotpassword?email=email_user@test.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());        
    }

    // REST PASSWORD
    @Test
    public void ujiResetPassword_tokenSalah() throws Exception {
        User user = userRepository.findOneByEmail("email_user@test.com");
        String tokenResetPassword = user.getTokResetPassword()+"salah";
        
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setPassword("passwordBaru123");

        String requestJson = new ObjectMapper().writeValueAsString(resetPasswordRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/resetpassword?token="+tokenResetPassword)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());  
    }

    @Test
    public void ujiResetPassword_valid() throws Exception {
        User user = userRepository.findOneByEmail("email_user@test.com");
        String tokenResetPassword = user.getTokResetPassword();
        
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setPassword("passwordBaru123");

        String requestJson = new ObjectMapper().writeValueAsString(resetPasswordRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/resetpassword?token="+tokenResetPassword)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());  
    }

    // USER DETAILS
    @Test
    public void ujiUserDetails_bearerTokenBenar() throws Exception {
        
        String beareToken = getBearerTokenUser("email_user@test.com", "passwordBaru123");

        mockMvc.perform(MockMvcRequestBuilders.get("/user/details")
                .header("Authorization", "Bearer " + beareToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    private String getBearerTokenUser(String email, String password) {
        User user = userRepository.findOneByEmail(email);

        if (user == null) {
            user = new User();
        }
        
        user.setName("Nama Pengguna");
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setProvider(AuthProvider.local);
        user.setEmailVerified(true);
        user.setRole(UserRole.ROLE_OWNER);
        userRepository.save(user);

        // Set up request data
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        
        try {
            // Perform request
            String result = mockMvc.perform(MockMvcRequestBuilders.post("/authentication/login")
            .content(objectMapper.writeValueAsString(loginRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").isNotEmpty())
            .andReturn().getResponse().getContentAsString();
            
            JSONObject jsonObject = new JSONObject(result);
            return jsonObject.getString("accessToken");
        } catch (Exception e) {
            return null;
        }
    }

}
