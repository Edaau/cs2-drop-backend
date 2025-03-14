package com.cs2drop.controller;

import com.cs2drop.entity.Lootbox;
import com.cs2drop.entity.Skin;
import com.cs2drop.service.LootboxService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lootboxes")
public class LootboxController {

    private final LootboxService lootboxService;
    
    @Value("${api.secret-key}")
    private String secretApiKey;

    private void validateApiKey(String apiKey) {
        if (!secretApiKey.equals(apiKey)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: Invalid API Key");
        }
    }
    @Autowired
    public LootboxController(LootboxService lootboxService) {
        this.lootboxService = lootboxService;
    }

    //  List all lootboxes
    @GetMapping
    public List<Lootbox> getAllLootboxes() {
    	// just show 3 skins for each lootbox
    	List<Lootbox> lootboxes = lootboxService.getAllLootboxes();
    	for (Lootbox lootbox : lootboxes) {
            if (lootbox.getSkins().size() > 3) {
                List<Skin> limitedSkins = lootbox.getSkins().subList(0, 3);
                lootbox.setSkins(limitedSkins);  
            }
        }
        return lootboxService.getAllLootboxes();
    }

    //  Search a lootbox by ID
    @GetMapping("/{id}")
    public Optional<Lootbox> getLootboxById(@PathVariable int id) {
        return lootboxService.getLootboxById(id);
    }

    // Create one Lootbox
    @PostMapping
    public Lootbox createLootbox(@RequestBody Lootbox lootbox, @RequestHeader("API-Key") String apiKey) {
        validateApiKey(apiKey); // 
        return lootboxService.addLootbox(lootbox);
    }

 //  Update a lootbox
    @PatchMapping("/{id}")
    public Lootbox updateLootbox(@PathVariable int id, @RequestBody Lootbox lootbox, @RequestHeader("API-Key") String apiKey) {
        validateApiKey(apiKey); // 
        return lootboxService.updateLootbox(id, lootbox);
    }

 // Delete a lootbox
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLootbox(@PathVariable int id, @RequestHeader("API-Key") String apiKey) {
        validateApiKey(apiKey); // 
        lootboxService.deleteLootbox(id);
        return ResponseEntity.noContent().build();
    }
}