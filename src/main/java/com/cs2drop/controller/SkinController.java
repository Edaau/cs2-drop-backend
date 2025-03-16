package com.cs2drop.controller;

import com.cs2drop.entity.Skin;
import com.cs2drop.service.SkinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/skins")
public class SkinController {

    @Autowired
    private SkinService skinService;

    @Value("${api.secret-key}")
    private String secretApiKey;

    private void validateApiKey(String apiKey) {
        if (!secretApiKey.equals(apiKey)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: Invalid API Key");
        }
    }

    //  List all skins
    @GetMapping
    public List<Skin> getAllSkins() {
        return skinService.getAllSkins();
    }

    // search by skin ID
    @GetMapping("/{id}")
    public Optional<Skin> getSkinById(@PathVariable int id) {
        return skinService.getSkinById(id);
    }

    // Search all skins in a lootbox by lootboxId
    @GetMapping("/lootbox/{lootboxId}")
    public List<Skin> getSkinsByLootbox(@PathVariable int lootboxId) {
        return skinService.getSkinsByLootbox(lootboxId);
    }

    // Add a new skin
    @PostMapping
    public Skin addSkin(@RequestBody Skin skin, @RequestHeader("API-Key") String apiKey) {
        validateApiKey(apiKey);
        return skinService.addSkin(skin);
    }

    // Update a new skin
    @PutMapping("/{id}")
    public Skin updateSkin(@PathVariable int id, @RequestBody Skin updatedSkin, @RequestHeader("API-Key") String apiKey) {
        validateApiKey(apiKey);
        return skinService.updateSkin(id, updatedSkin);
    }

    // Delete a skin 
    @DeleteMapping("/{id}")
    public void deleteSkin(@PathVariable int id, @RequestHeader("API-Key") String apiKey) {
        validateApiKey(apiKey);
        skinService.deleteSkin(id);
    }
}
