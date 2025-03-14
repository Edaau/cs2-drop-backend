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
@RequestMapping("/skins")
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

    // 1️⃣ Listar todas as skins
    @GetMapping
    public List<Skin> getAllSkins() {
        return skinService.getAllSkins();
    }

    // 2️⃣ Buscar uma skin por ID
    @GetMapping("/{id}")
    public Optional<Skin> getSkinById(@PathVariable Long id) {
        return skinService.getSkinById(id);
    }

    // 3️⃣ Buscar skins de uma lootbox específica
    @GetMapping("/lootbox/{lootboxId}")
    public List<Skin> getSkinsByLootbox(@PathVariable Long lootboxId) {
        return skinService.getSkinsByLootbox(lootboxId);
    }

    // 4️⃣ Adicionar uma nova skin (protegido por API Key)
    @PostMapping
    public Skin addSkin(@RequestBody Skin skin, @RequestHeader("X-API-KEY") String apiKey) {
        validateApiKey(apiKey);
        return skinService.addSkin(skin);
    }

    // 5️⃣ Atualizar uma skin (protegido por API Key)
    @PutMapping("/{id}")
    public Skin updateSkin(@PathVariable Long id, @RequestBody Skin updatedSkin, @RequestHeader("X-API-KEY") String apiKey) {
        validateApiKey(apiKey);
        return skinService.updateSkin(id, updatedSkin);
    }

    // 6️⃣ Deletar uma skin (protegido por API Key)
    @DeleteMapping("/{id}")
    public void deleteSkin(@PathVariable Long id, @RequestHeader("X-API-KEY") String apiKey) {
        validateApiKey(apiKey);
        skinService.deleteSkin(id);
    }
}
