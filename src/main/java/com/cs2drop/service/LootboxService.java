package com.cs2drop.service;

import com.cs2drop.entity.Lootbox;
import com.cs2drop.entity.Skin;
import com.cs2drop.repository.LootboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    public Optional<Lootbox> getLootboxById(int id) {
        return lootboxRepository.findById(id);
    }

    // add new Lootbox
    public Lootbox addLootbox(Lootbox lootbox) {
        return lootboxRepository.save(lootbox);
    }
    public Lootbox updateLootbox(int id, Lootbox updatedLootbox) {
        Optional<Lootbox> optionalLootbox = lootboxRepository.findById(id);
        
        if (optionalLootbox.isPresent()) { // Verify if find a lootbox
            Lootbox existingLootbox = optionalLootbox.get(); // get lootbox 
            existingLootbox.setCaseName(updatedLootbox.getCaseName()); // Update the name
            return lootboxRepository.save(existingLootbox); // Save and return
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lootbox with ID " + id + " not found");
        }
    }
    // Delete one lootbox by ID
    public void deleteLootbox(int id) {
    	Optional<Lootbox> optionalLootbox = lootboxRepository.findById(id);
    	
    	if (optionalLootbox.isPresent()) { // Verify if find a lootbox 
    		lootboxRepository.deleteById(id); //delete lootbox
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lootbox with ID " + id + " not found");
        }
    }
    
    public Skin openLootbox(int lootbox_id) {
        Lootbox lootbox = lootboxRepository.findById(lootbox_id).orElse(null);
        
        if (lootbox == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lootbox not found");
        }

        List<Skin> skins = lootbox.getSkins();
        if (skins.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lootbox has no skins");
        }

        // Sum all chances to normalize
        double totalChance = 0;
        for (Skin skin : skins) {
            totalChance += skin.getChance();
        }

        Random random = new Random();
        double roll = random.nextDouble() * totalChance; // now, use a real sum
        double cumulativeChance = 0;

        for (int i = 0; i < skins.size(); i++) {
            cumulativeChance += skins.get(i).getChance();
            if (roll <= cumulativeChance) {
                return skins.get(i);
            }
        }

        return skins.get(skins.size() - 1); // fallback return(rare case)
    }
}