package com.cs2drop.service;

import com.cs2drop.entity.Lootbox;
import com.cs2drop.repository.LootboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class LootboxService {

    private final LootboxRepository lootboxRepository;

    @Autowired
    public LootboxService(LootboxRepository lootboxRepository) {
        this.lootboxRepository = lootboxRepository;
    }

    // Return all lootboxes
    public List<Lootbox> getAllLootboxes() {
        return lootboxRepository.findAll();
    }

    // search by ID
    public Optional<Lootbox> getLootboxById(long id) {
        return lootboxRepository.findById(id);
    }

    // add new Lootbox
    public Lootbox addLootbox(Lootbox lootbox) {
        return lootboxRepository.save(lootbox);
    }
    public Lootbox updateLootbox(long id, Lootbox updatedLootbox) {
        Optional<Lootbox> optionalLootbox = lootboxRepository.findById(id);
        
        if (optionalLootbox.isPresent()) { // Verify if find a lootbox
            Lootbox existingLootbox = optionalLootbox.get(); // get lootbox 
            existingLootbox.setName(updatedLootbox.getName()); // Update the name
            return lootboxRepository.save(existingLootbox); // Save and return
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lootbox with ID " + id + " not found");
        }
    }
    // Delete one lootbox by ID
    public void deleteLootbox(long id) {
        lootboxRepository.deleteById(id);
    }
}