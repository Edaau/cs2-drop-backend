package com.cs2drop.controller;

import com.cs2drop.entity.Lootbox;
import com.cs2drop.entity.Skin;
import com.cs2drop.service.LootboxService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(LootboxController.class)
class LootboxControllerTest {
	@Autowired
    private MockMvc mockMvc;
	
	@MockitoBean
    private LootboxService lootboxService;

    @InjectMocks
    private LootboxController lootboxController;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Value("${api.secret-key}")
    private String apiKey;

    @Test
    void testGetAllLootboxes() throws Exception {
    	List<Skin> skinMock = Arrays.asList(
    		    new Skin(1, "Dragon Lore", "Legendary", 0.05, new Lootbox()),
    		    new Skin(2, "AK-47 Redline", "Classified", 0.15, new Lootbox())
    		);
    	
        List<Lootbox> lootbox = Arrays.asList(
                new Lootbox(1, "Kilowatt Case", skinMock),
                new Lootbox(2, "Test Case", skinMock)
        );

        Mockito.when(lootboxService.getAllLootboxes()).thenReturn(lootbox);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/lootboxes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(lootbox.size()))
                .andExpect(jsonPath("$[0].caseId").value(1))
                .andExpect(jsonPath("$[0].caseName").value("Kilowatt Case"))
                .andExpect(jsonPath("$[1].caseId").value(2))
                .andExpect(jsonPath("$[1].caseName").value("Test Case"));
    }

    @Test
    void testGetLootboxById() throws Exception {
    	List<Skin> skinMock = Arrays.asList(
    		    new Skin(1, "Dragon Lore", "Legendary", 0.05, new Lootbox()),
    		    new Skin(2, "AK-47 Redline", "Classified", 0.15, new Lootbox())
    		);
        int lootboxId = 1;
        Lootbox lootbox = new Lootbox(lootboxId, "Kilowatt Case", skinMock);

        Mockito.when(lootboxService.getLootboxById(lootboxId)).thenReturn(Optional.of(lootbox));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/lootboxes/{id}", lootboxId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.caseId").value(1))
                .andExpect(jsonPath("$.caseName").value("Kilowatt Case"));
    }
    
    @Test
    void testGetLootboxById_NotFound() throws Exception {
        int lootboxId = 99;
        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Lootbox with ID " + lootboxId + " not found"))
        .when(lootboxService).getLootboxById(Mockito.eq(lootboxId));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/lootboxes/{id}", lootboxId))
                .andExpect(status().isNotFound());
    }
    @Test
    void testAddLootbox() throws JsonProcessingException, Exception {
    	List<Skin> skinMock = Arrays.asList(
    		    new Skin(1, "Dragon Lore", "Legendary", 0.05, new Lootbox()),
    		    new Skin(2, "AK-47 Redline", "Classified", 0.15, new Lootbox())
    		);
    	
    	 int lootboxId = 1;
         Lootbox lootbox = new Lootbox(lootboxId, "Kilowatt Case", skinMock);
        
        Mockito.when(lootboxService.addLootbox(Mockito.any(Lootbox.class))).thenReturn(lootbox);
   

        mockMvc.perform(MockMvcRequestBuilders.post("/api/lootboxes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("API-Key", apiKey)
                .content(objectMapper.writeValueAsString(lootbox)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.caseId").value(1))
                .andExpect(jsonPath("$.caseName").value("Kilowatt Case"));
    }

    @Test
    void testUpdateLootbox() throws JsonProcessingException, Exception {
    	List<Skin> skinMock = Arrays.asList(
    		    new Skin(1, "Dragon Lore", "Legendary", 0.05, new Lootbox()),
    		    new Skin(2, "AK-47 Redline", "Classified", 0.15, new Lootbox())
    		);
    	
    	 int lootboxId = 1;
         Lootbox updatedLootbox = new Lootbox(lootboxId, "Kilowatt Case", skinMock);
        

        Mockito.when(lootboxService.updateLootbox(Mockito.eq(lootboxId), Mockito.any(Lootbox.class)))
        .thenReturn(updatedLootbox);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/lootboxes/{id}", lootboxId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("API-Key", apiKey)
                .content(objectMapper.writeValueAsString(updatedLootbox)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.caseId").value(1))
                .andExpect(jsonPath("$.caseName").value("Kilowatt Case"));
    }
    
    @Test
    void testUpdateLootbox_NotFound() throws Exception {
    	List<Skin> skinMock = Arrays.asList(
    		    new Skin(1, "Dragon Lore", "Legendary", 0.05, new Lootbox()),
    		    new Skin(2, "AK-47 Redline", "Classified", 0.15, new Lootbox())
    		);
    	
    	 int lootboxId = 99;
         Lootbox updatedLootbox = new Lootbox(lootboxId, "Kilowatt Case", skinMock);

        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Lootbox with ID " + lootboxId + " not found"))
        .when(lootboxService).updateLootbox(Mockito.eq(lootboxId), Mockito.any(Lootbox.class));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/lootboxes/{id}", lootboxId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("API-Key", apiKey)
                .content(objectMapper.writeValueAsString(updatedLootbox)))
                .andExpect(status().isNotFound());
    }
    @Test
    void testDeleteLootbox() throws Exception {
    	int lootboxId = 1;
        Mockito.doNothing().when(lootboxService).deleteLootbox(lootboxId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/lootboxes/{id}", lootboxId)
        		.header("API-Key", apiKey))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void testDeleteSkin_NotFound() throws Exception {
        int lootboxId = 99;
        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Lootbox with ID " + lootboxId + " not found"))
        .when(lootboxService).deleteLootbox(Mockito.eq(lootboxId));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/lootboxes/{id}", lootboxId)
        		.header("API-Key", apiKey))
                .andExpect(status().isNotFound());
    }
    @Test
    void testOpenLootbox() throws Exception {
        int lootboxId = 1;
        Skin mockSkin = new Skin(1, "Dragon Lore", "Legendary", 0.10, new Lootbox());

        Mockito.when(lootboxService.openLootbox(lootboxId)).thenReturn(mockSkin);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/lootboxes/{id}/open", lootboxId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skin_id").value(mockSkin.getSkin_id()))
                .andExpect(jsonPath("$.skinName").value(mockSkin.getSkinName()))
                .andExpect(jsonPath("$.rarity").value(mockSkin.getRarity()))
                .andExpect(jsonPath("$.chance").value(mockSkin.getChance()));

        Mockito.verify(lootboxService, Mockito.times(1)).openLootbox(lootboxId);
    }
}