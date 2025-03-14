package com.cs2drop.controller;

import com.cs2drop.entity.Lootbox;
import com.cs2drop.service.LootboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lootboxes")
public class LootboxController {

    private final LootboxService lootboxService;

    @Autowired
    public LootboxController(LootboxService lootboxService) {
        this.lootboxService = lootboxService;
    }

    // 1️⃣ Listar todas as lootboxes
    @GetMapping
    public List<Lootbox> getAllLootboxes() {
        return lootboxService.getAllLootboxes();
    }

    // 2️⃣ Buscar lootbox por ID
    @GetMapping("/{id}")
    public Optional<Lootbox> getLootboxById(@PathVariable Long id) {
        return lootboxService.getLootboxById(id);
    }

    // 3️⃣ Criar uma nova lootbox
    @PostMapping
    public Lootbox createLootbox(@RequestBody Lootbox lootbox) {
        return lootboxService.addLootbox(lootbox);
    }

    // 4️⃣ Atualizar uma lootbox existente
    @PutMapping("/{id}")
    public Lootbox updateLootbox(@PathVariable Long id, @RequestBody Lootbox lootbox) {
        return lootboxService.updateLootbox(id, lootbox);
    }

    // 5️⃣ Deletar uma lootbox
    @DeleteMapping("/{id}")
    public void deleteLootbox(@PathVariable Long id) {
        lootboxService.deleteLootbox(id);
    }
}