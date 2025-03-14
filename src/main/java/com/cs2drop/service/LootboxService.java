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

@Service
public class LootboxService {

    private final LootboxRepository lootboxRepository;

    @Autowired
    public LootboxService(LootboxRepository lootboxRepository) {
        this.lootboxRepository = lootboxRepository;
    }

    // Return all lootboxes
    public List<Lootbox> getAllLootboxes() {
    	// Pega todas as lootboxes do repositório
        List<Lootbox> lootboxes = lootboxRepository.findAll();

        // Itera sobre cada lootbox e limita as skins a 3
        for (Lootbox lootbox : lootboxes) {
            List<Skin> skins = lootbox.getSkins();
            
            // Limita as skins para 3 se houver mais de 3 skins
            if (skins.size() > 3) {
                lootbox.setSkins(skins.subList(0, 3)); // Atualiza com as 3 primeiras skins
            }
        }
        
        return lootboxes;
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
            existingLootbox.setName(updatedLootbox.getName()); // Update the name
            return lootboxRepository.save(existingLootbox); // Save and return
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lootbox with ID " + id + " not found");
        }
    }
    // Delete one lootbox by ID
    public void deleteLootbox(int id) {
        lootboxRepository.deleteById(id);
    }
}