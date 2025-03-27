package com.cs2drop.service;

import com.cs2drop.entity.Lootbox;
import com.cs2drop.entity.Skin;
import com.cs2drop.repository.LootboxRepository;
import com.cs2drop.repository.SkinRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) 
class SkinServiceTest {

    @Mock
    private SkinRepository skinRepository;

    @Mock
    private LootboxRepository lootboxRepository;

    @InjectMocks
    private SkinService skinService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        skinService = new SkinService(skinRepository,lootboxRepository);
    }
    
    @Test
    void testGetAllSkins() {
    	Lootbox lootboxMock = Mockito.mock(Lootbox.class);
    	
        List<Skin> mockSkins = Arrays.asList(
            new Skin("Dragon Lore", "Legendary", 0.10, lootboxMock),
            new Skin("AK-47 Redline", "Classified", 0.15, lootboxMock)
        );

        when(skinRepository.findAll()).thenReturn(mockSkins);

        List<Skin> result = skinService.getAllSkins();

        assertEquals(2, result.size());
        verify(skinRepository, times(1)).findAll();
    }

    @Test
    void testGetSkinById_Found() {
    	Lootbox lootboxMock = Mockito.mock(Lootbox.class);
    	
        Skin mockSkin = new Skin("Dragon Lore", "Legendary", 0.10, lootboxMock);
        when(skinRepository.findById(1)).thenReturn(Optional.of(mockSkin));

        Optional<Skin> result = skinService.getSkinById(1);

        assertTrue(result.isPresent());
        assertEquals("Dragon Lore", result.get().getSkinName());
        verify(skinRepository).findById(1);
    }

    @Test
    void testGetSkinById_NotFound() {
        when(skinRepository.findById(1)).thenReturn(Optional.empty());

        try {
            skinService.getSkinById(1);
            fail("should have thrown ResponseStatusException");
        } catch (ResponseStatusException e) {
            assertEquals("404 NOT_FOUND \"Skin with ID 1 not found\"", e.getMessage());
        }

        verify(skinRepository).findById(1);
    }

    @Test
    void testAddSkin_Success() {
    	Lootbox lootboxMock = new Lootbox();
        lootboxMock.setCaseId(1);
        Skin newSkin = new Skin("Dragon Lore", "Legendary", 0.10, lootboxMock);

        when(lootboxRepository.findById(1)).thenReturn(Optional.of(lootboxMock));
        when(skinRepository.save(any(Skin.class))).thenReturn(newSkin);

        Skin result = skinService.addSkin(newSkin);

        assertEquals("Dragon Lore", result.getSkinName());
        verify(lootboxRepository).findById(1);
        verify(skinRepository).save(newSkin);
    }

    @Test
    void testAddSkin_LootboxNotFound() {
    	Lootbox lootboxMock = new Lootbox();
    	lootboxMock.setCaseId(1);

        Skin newSkin = new Skin("Dragon Lore", "Legendary", 0.10, lootboxMock);

        when(lootboxRepository.findById(1)).thenReturn(Optional.empty());

        try {
            skinService.addSkin(newSkin);
            fail("should have thrown ResponseStatusException");
        } catch (ResponseStatusException e) {
            assertEquals("400 BAD_REQUEST \"Lootbox not found\"", e.getMessage());
        }

        verify(lootboxRepository).findById(1);
        verify(skinRepository, never()).save(any(Skin.class));
    }

    @Test
    void testUpdateSkin_Found() {
    	Lootbox lootboxMock = Mockito.mock(Lootbox.class);
        Skin existingSkin = new Skin("Old Name", "Legendary", 0.10, lootboxMock);
        Skin updatedSkin = new Skin("New Name", "Legendary", 0.10, lootboxMock);

        when(skinRepository.findById(1)).thenReturn(Optional.of(existingSkin));
        when(skinRepository.save(existingSkin)).thenReturn(updatedSkin);

        Skin result = skinService.updateSkin(1, updatedSkin);

        assertEquals("New Name", result.getSkinName());

        verify(skinRepository).findById(1);
        verify(skinRepository).save(existingSkin);
    }

    @Test
    void testUpdateSkin_NotFound() {
    	Lootbox lootboxMock = Mockito.mock(Lootbox.class);
        Skin updatedSkin = new Skin("New Name", "Legendary", 0.10, lootboxMock);

        when(skinRepository.findById(1)).thenReturn(Optional.empty());

        try {
            skinService.updateSkin(1, updatedSkin);
            fail("should have thrown ResponseStatusException");
        } catch (ResponseStatusException e) {
            assertEquals("404 NOT_FOUND \"Skin with ID 1 not found\"", e.getMessage());
        }

        verify(skinRepository).findById(1);
        verify(skinRepository, never()).save(any(Skin.class));
    }

    @Test
    void testDeleteSkin_Found() {
    	Lootbox lootboxMock = Mockito.mock(Lootbox.class);
    	Skin existingSkin = new Skin("Dragon Lore", "Legendary", 0.10, lootboxMock);
        when(skinRepository.findById(1)).thenReturn(Optional.of(existingSkin));

        skinService.deleteSkin(1);

        verify(skinRepository).findById(1);
        verify(skinRepository).deleteById(1);
    }

    @Test
    void testDeleteSkin_NotFound() {
        when(skinRepository.findById(1)).thenReturn(Optional.empty());

        try {
            skinService.deleteSkin(1);
            fail("should have thrown ResponseStatusException");
        } catch (ResponseStatusException e) {
            assertEquals("404 NOT_FOUND \"Skin with ID 1 not found\"", e.getMessage());
        }

        verify(skinRepository).findById(1);
        verify(skinRepository, never()).deleteById(anyInt());
    }

    @Test
    void testGetSkinsByLootbox_Found() {
    	Lootbox lootboxMock = Mockito.mock(Lootbox.class);
        List<Skin> mockSkins = new ArrayList<>();
        mockSkins.add(new Skin("Dragon Lore", "Legendary", 0.10, lootboxMock));
        mockSkins.add(new Skin("AK-47 Redline", "Classified", 0.15, lootboxMock));

        when(skinRepository.findByLootbox_id(1)).thenReturn(mockSkins);

        List<Skin> result = skinService.getSkinsByLootbox(1);

        assertEquals(2, result.size());
        assertEquals("Dragon Lore", result.get(0).getSkinName());

        verify(skinRepository).findByLootbox_id(1);
    }

    @Test
    void testGetSkinsByLootbox_NotFound() {
        List<Skin> emptyList = new ArrayList<>();
        when(skinRepository.findByLootbox_id(1)).thenReturn(emptyList);

        try {
            skinService.getSkinsByLootbox(1);
            fail("should have thrown ResponseStatusException");
        } catch (ResponseStatusException e) {
            assertEquals("404 NOT_FOUND \"No skins found for this lootbox\"", e.getMessage());
        }

        verify(skinRepository).findByLootbox_id(1);
    }
}