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

import com.api.ponline.dao.Request.AnggotaRequest;
import com.api.ponline.dao.Request.AuthorizationRequest;
import com.api.ponline.dao.Request.KomunitasRequest;
import com.api.ponline.dao.Request.LoginRequest;
import com.api.ponline.model.Entity.komunitas.Anggota;
import com.api.ponline.model.Entity.komunitas.Komunitas;
import com.api.ponline.model.Entity.komunitas.StatusAnggota;
import com.api.ponline.model.Entity.user.AuthProvider;
import com.api.ponline.model.Entity.user.User;
import com.api.ponline.model.Entity.user.UserRole;
import com.api.ponline.model.repository.komunitas.AnggotaRepo;
import com.api.ponline.model.repository.komunitas.KomunitasRepo;
import com.api.ponline.model.repository.user.UserRepository;
import com.api.ponline.services.komunitas.KomunitasServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AnggotaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnggotaRepo anggotaRepo;

    @Autowired
    private KomunitasRepo komunitasRepo;

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
    public void ujiTambahAnggota_berhasil() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }
        
        AnggotaRequest anggotaRequest = new AnggotaRequest();
        anggotaRequest.setKomunitas(komunitasRepo.getAllKomunitas().get(0));
        anggotaRequest.setUser(user);
        anggotaRequest.setStatusAnggota(StatusAnggota.RESMI);

        String requestJson = new ObjectMapper().writeValueAsString(anggotaRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/anggota")
                .header("Authorization", "Bearer " + token)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiTambahAnggota_validasiError() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }
        
        AnggotaRequest anggotaRequest = new AnggotaRequest();
        anggotaRequest.setUser(user);

        String requestJson = new ObjectMapper().writeValueAsString(anggotaRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/anggota")
                .header("Authorization", "Bearer " + token)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void ujiReadAnggota_validasiError() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }        
        
        mockMvc.perform(MockMvcRequestBuilders.get("/anggota")
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiUpdateAnggota_berhasil() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }

        Anggota anggota = new Anggota();
        anggota = anggotaRepo.getAllAnggota().get(0);
        
        AnggotaRequest anggotaRequest = new AnggotaRequest();
        anggotaRequest.setId(anggota.getId());
        anggotaRequest.setUser(anggota.getUser());
        anggotaRequest.setKomunitas(anggota.getKomunitas());
        anggotaRequest.setStatusAnggota(anggota.getStatusAnggota().equals(StatusAnggota.RESMI) ? StatusAnggota.PENDING : StatusAnggota.RESMI);

        String requestJson = new ObjectMapper().writeValueAsString(anggotaRequest);
        mockMvc.perform(MockMvcRequestBuilders.put("/anggota")
                .header("Authorization", "Bearer " + token)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiUpdateAnggota_validasiError() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }
        
        Anggota anggota = new Anggota();
        anggota = anggotaRepo.getAllAnggota().get(0);
        
        AnggotaRequest anggotaRequest = new AnggotaRequest();
        anggotaRequest.setId(anggota.getId());

        String requestJson = new ObjectMapper().writeValueAsString(anggotaRequest);
        mockMvc.perform(MockMvcRequestBuilders.put("/anggota")
                .header("Authorization", "Bearer " + token)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void ujiDeleteAnggota_berhasil() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }
        
        Anggota anggota = new Anggota();
        anggota = anggotaRepo.getAllAnggota().get(0);
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/anggota/delete/"+anggota.getId())
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
}
