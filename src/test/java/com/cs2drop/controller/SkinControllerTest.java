package com.cs2drop.controller;

import com.cs2drop.entity.Skin;
import com.cs2drop.entity.Lootbox;
import com.cs2drop.service.SkinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SkinController.class)
public class SkinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SkinService skinService;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Value("${api.secret-key}")
    private String apiKey;

    @Test
    void testAddSkin() throws Exception {
    	Lootbox lootboxMock = Mockito.mock(Lootbox.class);
        Skin skin = new Skin(1, "Dragon Lore", "Legendary", 0.05, lootboxMock);
        
        Mockito.when(skinService.addSkin(Mockito.any(Skin.class))).thenReturn(skin);
   

        mockMvc.perform(MockMvcRequestBuilders.post("/api/skins")
                .contentType(MediaType.APPLICATION_JSON)
                .header("API-Key", apiKey)
                .content(objectMapper.writeValueAsString(skin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skin_id").value(1))
                .andExpect(jsonPath("$.skinName").value("Dragon Lore"))
                .andExpect(jsonPath("$.rarity").value("Legendary"))
                .andExpect(jsonPath("$.chance").value(0.05));
    }

    @Test
    void testGetSkinById() throws Exception {
    	Lootbox lootboxMock = Mockito.mock(Lootbox.class);
        int skinId = 1;
        Skin skin = new Skin(skinId, "Dragon Lore", "Legendary", 0.05, lootboxMock);

        Mockito.when(skinService.getSkinById(skinId)).thenReturn(Optional.of(skin));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/skins/{id}", skinId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skin_id").value(1))
                .andExpect(jsonPath("$.skinName").value("Dragon Lore"))
                .andExpect(jsonPath("$.rarity").value("Legendary"))
                .andExpect(jsonPath("$.chance").value(0.05));
    }

    @Test
    void testGetSkinById_NotFound() throws Exception {
        int skinId = 99;
        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Skin with ID " + skinId + " not found"))
        .when(skinService).getSkinById(Mockito.eq(skinId));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/skins/{id}", skinId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllSkins() throws Exception {
    	Lootbox lootboxMock = Mockito.mock(Lootbox.class);
        List<Skin> skins = Arrays.asList(
                new Skin(1, "Dragon Lore", "Legendary", 0.05, lootboxMock),
                new Skin(2, "AK-47 Redline", "Classified", 0.15, lootboxMock)
        );

        Mockito.when(skinService.getAllSkins()).thenReturn(skins);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/skins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(skins.size()))
                .andExpect(jsonPath("$[0].skin_id").value(1))
                .andExpect(jsonPath("$[0].skinName").value("Dragon Lore"))
                .andExpect(jsonPath("$[0].rarity").value("Legendary"))
                .andExpect(jsonPath("$[0].chance").value(0.05))
                .andExpect(jsonPath("$[1].skin_id").value(2))
                .andExpect(jsonPath("$[1].skinName").value("AK-47 Redline"))
                .andExpect(jsonPath("$[1].rarity").value("Classified"))
                .andExpect(jsonPath("$[1].chance").value(0.15));
    }

    @Test
    void testUpdateSkin() throws Exception {
    	Lootbox lootboxMock = Mockito.mock(Lootbox.class);
        int skinId = 1;
        Skin updatedSkin = new Skin(skinId, "Dragon Lore", "Legendary", 0.10, lootboxMock);

        Mockito.when(skinService.updateSkin(Mockito.eq(skinId), Mockito.any(Skin.class)))
        .thenReturn(updatedSkin);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/skins/{id}", skinId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("API-Key", apiKey)
                .content(objectMapper.writeValueAsString(updatedSkin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skin_id").value(1))
                .andExpect(jsonPath("$.skinName").value("Dragon Lore"))
                .andExpect(jsonPath("$.rarity").value("Legendary"))
                .andExpect(jsonPath("$.chance").value(0.10));
    }

    @Test
    void testUpdateSkin_NotFound() throws Exception {
    	Lootbox lootboxMock = Mockito.mock(Lootbox.class);
        int skinId = 99;
        Skin updatedSkin = new Skin(skinId, "Dragon Lore", "Legendary", 0.10, lootboxMock);

        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Skin with ID " + skinId + " not found"))
        .when(skinService).updateSkin(Mockito.eq(skinId), Mockito.any(Skin.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/skins/{id}", skinId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("API-Key", apiKey)
                .content(objectMapper.writeValueAsString(updatedSkin)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteSkin() throws Exception {
        int skinId = 1;
        Mockito.doNothing().when(skinService).deleteSkin(skinId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/skins/{id}", skinId)
        		.header("API-Key", apiKey))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteSkin_NotFound() throws Exception {
        int skinId = 99;
        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Skin with ID " + skinId + " not found"))
        .when(skinService).deleteSkin(Mockito.eq(skinId));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/skins/{id}", skinId)
        		.header("API-Key", apiKey))
                .andExpect(status().isNotFound());
    }
}