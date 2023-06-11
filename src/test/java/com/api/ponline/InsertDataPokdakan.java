package com.api.ponline;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.api.ponline.dao.Request.KomunitasRequest;
import com.api.ponline.dao.Request.LoginRequest;
import com.api.ponline.model.Entity.Anggota.Anggota;
import com.api.ponline.model.Entity.Anggota.StatusAnggota;
import com.api.ponline.model.Entity.jadwal.Jadwal;
import com.api.ponline.model.Entity.kolam.Kolam;
import com.api.ponline.model.Entity.komunitas.Komunitas;
import com.api.ponline.model.Entity.user.AuthProvider;
import com.api.ponline.model.Entity.user.User;
import com.api.ponline.model.Entity.user.UserRole;
import com.api.ponline.model.repository.Anggota.AnggotaRepo;
import com.api.ponline.model.repository.jadwal.JadwalRepo;
import com.api.ponline.model.repository.kolam.KolamRepo;
import com.api.ponline.model.repository.komunitas.KomunitasRepo;
import com.api.ponline.model.repository.user.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class InsertDataPokdakan {

    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private JadwalRepo jadwalRepo;

    @Autowired
    private KomunitasRepo komunitasRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ObjectMapper objectMapper;
    
    @Autowired
    private AnggotaRepo anggotaRepo;

    @Autowired
    private KolamRepo kolamRepo;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }
    
    @Test
    public void insertSemuaPengguna() throws Exception {

        String listName[] = {
            "Setiawan Adi Saputra","Andika","Andi Prasetyo","Joni Saputra","Mujiono","Hendrik",
            "Romadon","Maryanto","Agus","Marjono","Suratin","Slamet","Robi","Eko","Mahmudi","Tobi Ahmad Fajar",
            "Sunarman","Fredy Hermawan"
        };

        String listEmail[] = {
            "setiawan_adi_saputra@ponline.tech", "andika@ponline.tech", "andi_prasetyo@ponline.tech", 
            "joni_saputra@ponline.tech", "mujiono@ponline.tech", "hendrik@ponline.tech", "romadon@ponline.tech", 
            "maryanto@ponline.tech", "agus@ponline.tech", "marjono@ponline.tech", "suratin@ponline.tech", "slamet@ponline.tech", 
            "robi@ponline.tech", "eko@ponline.tech", "mahmudi@ponline.tech", "tobi_ahmad_fajar@ponline.tech", "sunarman@ponline.tech", 
            "fredy@ponline.tech"
        };

        for (int i = 0; i < listName.length; i++) {
            User user = new User();
            user.setEmail(listEmail[i]);
            user.setName(listName[i]);
            user.setPassword(passwordEncoder.encode("Paw.Ponline01"));
            user.setEmailVerified(true);
            user.setProvider(AuthProvider.local);
            user.setRole(listEmail[i].equals("setiawan_adi_saputra@ponline.tech") ? UserRole.ROLE_OWNER : UserRole.ROLE_EMPLOYEE);
            user.setId(null);

            userRepository.save(user);
        }
        
    }

    @Test
    public void insertCreateKomunitas() throws Exception {
        String bearerToken =  getBearerTokenUser("setiawan_adi_saputra@ponline.tech", "Paw.Ponline01");
        
        KomunitasRequest komunitasRequest = new KomunitasRequest();
        komunitasRequest.setNama("Pokdakan Karya Bersama");
        komunitasRequest.setAlamat("Jl.Gajah mada, Desa Jati Mulyo, Kec. Jati Agung, Kab. Lampung Selatan");

        String requestJson = new ObjectMapper().writeValueAsString(komunitasRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/komunitas")
                .header("Authorization", "Bearer " + bearerToken)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void insertSemuaAnggota() throws Exception {
        List<Komunitas> listKomunitas = komunitasRepo.getAllKomunitas();
        Komunitas pokdakanKom = new Komunitas();
        Boolean statusResult = false;
        for (Komunitas komunitas : listKomunitas) {
            if (komunitas.getNama().equals("Pokdakan Karya Bersama")) {
                pokdakanKom = komunitas;
                statusResult = true;

                break;
            }
        }

        if (statusResult) {    
    
            String listEmail[] = {
                "andika@ponline.tech", "andi_prasetyo@ponline.tech", 
                "joni_saputra@ponline.tech", "mujiono@ponline.tech", "hendrik@ponline.tech", "romadon@ponline.tech", 
                "maryanto@ponline.tech", "agus@ponline.tech", "marjono@ponline.tech", "suratin@ponline.tech", "slamet@ponline.tech", 
                "robi@ponline.tech", "eko@ponline.tech", "mahmudi@ponline.tech", "tobi_ahmad_fajar@ponline.tech", "sunarman@ponline.tech", 
                "fredy@ponline.tech"
            };
    
            for (int i = 0; i < listEmail.length; i++) {
                Anggota anggota = new Anggota();
                anggota.setUser(userRepository.findOneByEmail(listEmail[i]));
                anggota.setStatusAnggota(StatusAnggota.RESMI);
                anggota.setKomunitas(pokdakanKom);
                anggota.setId(null);

                anggotaRepo.save(anggota);
            }
        }
    }

    @Test
    public void insertKolam() throws Exception {

        List<Komunitas> listKomunitas = komunitasRepo.getAllKomunitas();
        Komunitas pokdakanKom = new Komunitas();
        for (Komunitas komunitas : listKomunitas) {
            if (komunitas.getNama().equals("Pokdakan Karya Bersama")) {
                pokdakanKom = komunitas;

                break;
            }
        }

        String listNamaKolam[] = {
            "Kolam 1", 
            "Kolam 2", 
            "Kolam 3", 
            "Kolam 4", 
            "Kolam 5", 
            "Kolam 6", 
            "Kolam 7", 
            "Kolam 8",
        };

        for (int i = 0; i < listNamaKolam.length; i++) {
            Kolam kolam = new Kolam();
            kolam.setKomunitas(pokdakanKom);
            kolam.setName(listNamaKolam[i]);
            kolam.setId(null);

            kolamRepo.save(kolam);
        }
    }

    @Test
    public void insertJadwal() throws Exception {

        String listEmail[] = {
            "andi_prasetyo@ponline.tech", "mujiono@ponline.tech", "suratin@ponline.tech", "suratin@ponline.tech", 
            "romadon@ponline.tech", "robi@ponline.tech", "andika@ponline.tech", "hendrik@ponline.tech", 
            "tobi_ahmad_fajar@ponline.tech", "agus@ponline.tech", "joni_saputra@ponline.tech", "maryanto@ponline.tech", 
            "slamet@ponline.tech"
        };

        List<Komunitas> listKomunitas = komunitasRepo.getAllKomunitas();
        Komunitas pokdakanKom = new Komunitas();
        for (Komunitas komunitas : listKomunitas) {
            if (komunitas.getNama().equals("Pokdakan Karya Bersama")) {
                pokdakanKom = komunitas;

                break;
            }
        }

        List<Kolam> listKolam = new ArrayList<Kolam>();
        for (Kolam kolam : kolamRepo.getAllKolam()) {
            if (kolam.getKomunitas().getId().equals(pokdakanKom.getId())) {
                listKolam.add(kolam);
            }
        }

        int tanggal = 5;
        for (int i = 0; i < listEmail.length; i++) {
            Date dateTodo = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                dateTodo = dateFormat.parse(tanggal < 10 ? "0"+tanggal+"/06/2023": tanggal + "/06/2023");
            } catch (Exception e) {
                System.out.println(e);
            }
            
            for (Kolam kolam : listKolam) {
                Jadwal jadwal = new Jadwal();
                jadwal.setKolam(kolam);
                jadwal.setAnggota(anggotaRepo.findByUser(userRepository.findOneByEmail(listEmail[i])).get(0));
                jadwal.setDateToDo(dateTodo);
                jadwal.setId(null);

                jadwalRepo.save(jadwal);
            }

            tanggal++;
        }

        
    }

    private String getBearerTokenUser(String email, String password) {
        
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
