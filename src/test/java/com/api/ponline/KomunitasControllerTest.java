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
import com.api.ponline.dao.Request.KomunitasRequest;
import com.api.ponline.dao.Request.LoginRequest;
import com.api.ponline.model.Entity.komunitas.Komunitas;
import com.api.ponline.model.Entity.user.AuthProvider;
import com.api.ponline.model.Entity.user.User;
import com.api.ponline.model.Entity.user.UserRole;
import com.api.ponline.model.repository.komunitas.KomunitasRepo;
import com.api.ponline.model.repository.user.UserRepository;
import com.api.ponline.services.komunitas.KomunitasServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class KomunitasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

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
    public void ujiTambahKomunitas_berhasil() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }
        
        KomunitasRequest komunitasRequest = new KomunitasRequest();
        komunitasRequest.setNama("Nama Komunitas");
        komunitasRequest.setAlamat("Alamat Komunitas");

        String requestJson = new ObjectMapper().writeValueAsString(komunitasRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/komunitas")
                .header("Authorization", "Bearer " + token)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payLoad.nama").value("Nama Komunitas"));
    }

    @Test
    public void ujiTambahKomunitas_validasiError() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }
        
        KomunitasRequest komunitasRequest = new KomunitasRequest();
        komunitasRequest.setNama("");
        komunitasRequest.setAlamat("Alamat Komunitas");

        String requestJson = new ObjectMapper().writeValueAsString(komunitasRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/komunitas")
                .header("Authorization", "Bearer " + token)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]").value("Nama Harus Di isi"));
    }

    @Test
    public void ujiReadKomunitas_validasiError() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }        
        
        mockMvc.perform(MockMvcRequestBuilders.get("/komunitas")
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiUpdateKomunitas_berhasil() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }

        Komunitas komunitas = new Komunitas();
        komunitas = komunitasRepo.getAllKomunitas().get(0);
        
        Long idKomunitas = komunitas.getId();
        String namaKomunitas = komunitas.getNama();
        KomunitasRequest komunitasRequest = new KomunitasRequest();
        komunitasRequest.setId(idKomunitas);
        komunitasRequest.setNama(namaKomunitas +  " Edit ");
        komunitasRequest.setAlamat(komunitas.getAlamat());

        String requestJson = new ObjectMapper().writeValueAsString(komunitasRequest);
        mockMvc.perform(MockMvcRequestBuilders.put("/komunitas")
                .header("Authorization", "Bearer " + token)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payLoad.nama").value(namaKomunitas +  " Edit "));
    }

    @Test
    public void ujiUpdateKomunitas_validasiError() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }

        Komunitas komunitas = new Komunitas();
        komunitas = komunitasRepo.getAllKomunitas().get(0);
        
        Long idKomunitas = komunitas.getId();
        String namaKomunitas = komunitas.getNama();
        KomunitasRequest komunitasRequest = new KomunitasRequest();
        komunitasRequest.setId(idKomunitas);
        komunitasRequest.setNama("");
        komunitasRequest.setAlamat(komunitas.getAlamat());

        String requestJson = new ObjectMapper().writeValueAsString(komunitasRequest);
        mockMvc.perform(MockMvcRequestBuilders.put("/komunitas")
                .header("Authorization", "Bearer " + token)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]").value("Nama Harus Di isi"));
    }

    @Test
    public void ujiDeleteKomunitas_berhasil() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }

        Komunitas komunitas = new Komunitas();
        komunitas = komunitasRepo.getAllKomunitas().get(0);
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/komunitas/delete/"+komunitas.getId())
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
}
