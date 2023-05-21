package com.api.ponline;

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

import com.api.ponline.dao.Request.AuthorizationRequest;
import com.api.ponline.dao.Request.KolamRequest;
import com.api.ponline.dao.Request.KomunitasRequest;
import com.api.ponline.dao.Request.LoginRequest;
import com.api.ponline.model.Entity.kolam.Kolam;
import com.api.ponline.model.Entity.komunitas.Komunitas;
import com.api.ponline.model.Entity.user.AuthProvider;
import com.api.ponline.model.Entity.user.User;
import com.api.ponline.model.Entity.user.UserRole;
import com.api.ponline.model.repository.kolam.KolamRepo;
import com.api.ponline.model.repository.komunitas.KomunitasRepo;
import com.api.ponline.model.repository.user.UserRepository;
import com.api.ponline.services.komunitas.KomunitasServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class KolamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KomunitasRepo komunitasRepo;

    @Autowired
    private KolamRepo kolamRepo;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String token;

    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private LoginRequest loginRequest;

    private User user;
    

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
        
        token = getSetupToken("user_dummy@test.com", "password123", UserRole.ROLE_OWNER);
    }

    private String getSetupToken(String email, String password, UserRole userRole) {

        user = userRepository.findOneByEmail(email);

        if (user == null) {
            user = new User();
        }
        
        user.setName("Nama Pengguna");
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setProvider(AuthProvider.local);
        user.setRole(userRole);
        user.setEmailVerified(true);
        userRepository.save(user);

        // Set up request data
        loginRequest = new LoginRequest();
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

    @Test
    public void ujiTambahKolam_berhasil() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }
        
        KolamRequest kolamRequest = new KolamRequest();
        kolamRequest.setKomunitas(komunitasRepo.getAllKomunitas().get(0));
        kolamRequest.setName("Nama Kolam");

        String requestJson = new ObjectMapper().writeValueAsString(kolamRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/kolam")
                .header("Authorization", "Bearer " + token)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiTambahKolam_validasiError() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }
        
        KolamRequest kolamRequest = new KolamRequest();
        kolamRequest.setKomunitas(null);
        kolamRequest.setName("Nama Kolam");

        String requestJson = new ObjectMapper().writeValueAsString(kolamRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/kolam")
                .header("Authorization", "Bearer " + token)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void ujiReadKolam_validasiError() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }        
        
        mockMvc.perform(MockMvcRequestBuilders.get("/kolam")
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiUpdateKolam_berhasil() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }

        Kolam kolam = new Kolam();
        kolam = kolamRepo.getAllKolam().get(0);
        
        KolamRequest kolamRequest = new KolamRequest();
        kolamRequest.setId(kolam.getId());
        kolamRequest.setKomunitas(kolam.getKomunitas());
        kolamRequest.setName("Nama Kolam - Edit");

        String requestJson = new ObjectMapper().writeValueAsString(kolamRequest);
        mockMvc.perform(MockMvcRequestBuilders.put("/kolam")
                .header("Authorization", "Bearer " + token)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiUpdateKolam_validasiError() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }

        Kolam kolam = new Kolam();
        kolam = kolamRepo.getAllKolam().get(0);
        
        KolamRequest kolamRequest = new KolamRequest();
        kolamRequest.setId(kolam.getId());
        kolamRequest.setKomunitas(null);
        kolamRequest.setName("Nama Kolam - Edit");

        String requestJson = new ObjectMapper().writeValueAsString(kolamRequest);
        mockMvc.perform(MockMvcRequestBuilders.put("/komunitas")
                .header("Authorization", "Bearer " + token)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void ujiDeleteKolam_berhasil() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }

        Kolam kolam = new Kolam();
        kolam = kolamRepo.getAllKolam().get(0);
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/kolam/delete/"+kolam.getId())
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
}
