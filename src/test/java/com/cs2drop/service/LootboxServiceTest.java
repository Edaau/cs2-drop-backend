package com.cs2drop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.cs2drop.entity.Lootbox;
import com.cs2drop.entity.Skin;
import com.cs2drop.repository.LootboxRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class LootboxServiceTest {

    @Mock
    private LootboxRepository lootboxRepository;

    @InjectMocks
    private LootboxService lootboxService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        lootboxService = new LootboxService(lootboxRepository);
    }

    @Test
    void testGetAllLootboxes() {
    	List<Lootbox> mockLootboxes = List.of(new Lootbox(1, "Starter Case", List.of()), new Lootbox(2, "Pro Case", List.of()));
    	when(lootboxRepository.findAll()).thenReturn(mockLootboxes);

        when(lootboxRepository.findAll()).thenReturn(mockLootboxes);

        List<Lootbox> result = lootboxService.getAllLootboxes();

        assertEquals(2, result.size());
        assertEquals("Starter Case", result.get(0).getCaseName());
        assertEquals("Pro Case", result.get(1).getCaseName());
        verify(lootboxRepository).findAll();
    }

    @Test
    void testGetLootboxById_Found() {
        Lootbox lootbox = new Lootbox(1, "Elite Case", List.of());

        when(lootboxRepository.findById(1)).thenReturn(Optional.of(lootbox));

        Optional<Lootbox> result = lootboxService.getLootboxById(1);

        assertTrue(result.isPresent());
        assertEquals("Elite Case", result.get().getCaseName());
        verify(lootboxRepository).findById(1);
    }

    @Test
    void testGetLootboxById_NotFound() {
        when(lootboxRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Lootbox> result = lootboxService.getLootboxById(1);

        assertFalse(result.isPresent());
        verify(lootboxRepository).findById(1);
    }

    @Test
    void testAddLootbox() {
        Lootbox lootbox = new Lootbox(1, "New Case", List.of());

        when(lootboxRepository.save(any(Lootbox.class))).thenReturn(lootbox);

        Lootbox result = lootboxService.addLootbox(lootbox);

        assertNotNull(result);
        assertEquals("New Case", result.getCaseName());
        verify(lootboxRepository).save(lootbox);
    }

    @Test
    void testUpdateLootbox_Success() {
        Lootbox existingLootbox = new Lootbox(1, "Old Name", List.of());
        Lootbox updatedLootbox = new Lootbox(1, "Updated Name", List.of());

        when(lootboxRepository.findById(1)).thenReturn(Optional.of(existingLootbox));
        when(lootboxRepository.save(any(Lootbox.class))).thenReturn(updatedLootbox);

        Lootbox result = lootboxService.updateLootbox(1, updatedLootbox);

        assertNotNull(result);
        assertEquals("Updated Name", result.getCaseName());
        assertEquals(1, result.getCaseId());
        verify(lootboxRepository).findById(1);
        verify(lootboxRepository).save(any(Lootbox.class));
    }

    @Test
    void testUpdateLootbox_NotFound() {
        when(lootboxRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            lootboxService.updateLootbox(1, new Lootbox(1, "New Name", List.of()));
        });

        assertEquals("404 NOT_FOUND \"Lootbox with ID 1 not found\"", exception.getMessage());
        verify(lootboxRepository).findById(1);
        verify(lootboxRepository, never()).save(any());
    }

    @Test
    void testDeleteLootbox_Success() {
        Lootbox lootbox = new Lootbox(1, "To be deleted", List.of());

        when(lootboxRepository.findById(1)).thenReturn(Optional.of(lootbox));

        lootboxService.deleteLootbox(1);

        verify(lootboxRepository).deleteById(1);
    }

    @Test
    void testDeleteLootbox_NotFound() {
        when(lootboxRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            lootboxService.deleteLootbox(1);
        });

        assertEquals("404 NOT_FOUND \"Lootbox with ID 1 not found\"", exception.getMessage());
        verify(lootboxRepository).findById(1);
        verify(lootboxRepository, never()).deleteById(anyInt());
    }

    @Test
    void testOpenLootbox_Success() {
        Lootbox lootbox = new Lootbox(1, "Lucky Case", List.of(
                new Skin("Common Skin", "Common", 0.7, null),
                new Skin("Rare Skin", "Rare", 0.3, null)
        ));

        when(lootboxRepository.findById(1)).thenReturn(Optional.of(lootbox));

        Skin result = lootboxService.openLootbox(1);

        assertNotNull(result);
        assertTrue(result.getSkinName().equals("Common Skin") || result.getSkinName().equals("Rare Skin"));
        verify(lootboxRepository).findById(1);
    }

    @Test
    void testOpenLootbox_LootboxNotFound() {
        when(lootboxRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            lootboxService.openLootbox(1);
        });

        assertEquals("404 NOT_FOUND \"Lootbox not found\"", exception.getMessage());
        verify(lootboxRepository).findById(1);
    }

    
}