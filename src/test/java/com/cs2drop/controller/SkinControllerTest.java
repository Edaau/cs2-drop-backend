package com.cs2drop.controller;

import com.cs2drop.entity.Skin;
import com.cs2drop.entity.Lootbox;
import com.cs2drop.service.SkinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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

    @Test
    void testAddSkin() throws Exception {
    	Lootbox mockLootbox = new Lootbox(1L, "Caixa de Elite");
        Skin skin = new Skin(1, "Dragon Lore", "Legendary", 0.05, 1);
        
        Mockito.when(skinService.addSkin(Mockito.any(Skin.class))).thenReturn(skin);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/skins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(skin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skin_name").value("Dragon Lore"))
                .andExpect(jsonPath("$.rarity").value("Legendary"));
    }

    @Test
    void testGetSkinById() throws Exception {
        int skinId = 1;
        Skin skin = new Skin(skinId, "Dragon Lore", "Legendary", 0.05, 1);

        Mockito.when(skinService.getSkinById(skinId)).thenReturn(Optional.of(skin));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/skins/{id}", skinId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skin_id").value(skinId))
                .andExpect(jsonPath("$.skin_name").value("Dragon Lore"));
    }

    @Test
    void testGetSkinById_NotFound() throws Exception {
        int skinId = 99;
        Mockito.when(skinService.getSkinById(skinId)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/skins/{id}", skinId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllSkins() throws Exception {
        List<Skin> skins = Arrays.asList(
                new Skin(1, "Dragon Lore", "Legendary", 0.05, 1),
                new Skin(2, "AK-47 Redline", "Classified", 0.15, 1)
        );

        Mockito.when(skinService.getAllSkins()).thenReturn(skins);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/skins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(skins.size()))
                .andExpect(jsonPath("$[0].skin_name").value("Dragon Lore"))
                .andExpect(jsonPath("$[1].skin_name").value("AK-47 Redline"));
    }

    @Test
    void testUpdateSkin() throws Exception {
        int skinId = 1;
        Skin updatedSkin = new Skin(skinId, "Dragon Lore", "Legendary", 0.10, 2);

        Mockito.when(skinService.updateSkin(Mockito.eq(skinId), Mockito.any(Skin.class)))
                .thenReturn(Optional.of(updatedSkin));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/skins/{id}", skinId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedSkin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wear").value(0.10))
                .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test
    void testUpdateSkin_NotFound() throws Exception {
        int skinId = 99;
        Skin updatedSkin = new Skin(skinId, "Dragon Lore", "Legendary", 0.10, 2);

        Mockito.when(skinService.updateSkin(Mockito.eq(skinId), Mockito.any(Skin.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/skins/{id}", skinId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedSkin)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteSkin() throws Exception {
        int skinId = 1;
        Mockito.when(skinService.deleteSkin(skinId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/skins/{id}", skinId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteSkin_NotFound() throws Exception {
        int skinId = 99;
        Mockito.when(skinService.deleteSkin(skinId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/skins/{id}", skinId))
                .andExpect(status().isNotFound());
    }
}