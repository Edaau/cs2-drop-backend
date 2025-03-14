package com.cs2drop.service;

import com.cs2drop.entity.Lootbox;
import com.cs2drop.entity.Skin;
import com.cs2drop.repository.SkinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SkinService {

    private final SkinRepository skinRepository;

    @Autowired
    public SkinService(SkinRepository skinRepository) {
        this.skinRepository = skinRepository;
    }

    // Return all skins registered 
    public List<Skin> getAllSkins() {
        return skinRepository.findAll();
    }

    // Search one skin by Id
    public Optional<Skin> getSkinById(long id) {
        return skinRepository.findById(id);
    }

    // Add a new Skin
    public Skin addSkin(Skin skin) {
        return skinRepository.save(skin);
    }
    public Skin updateSkin(long id, Skin updatedSkin) {
        Optional<Skin> optionalSkin = skinRepository.findById(id);
        
        if (optionalSkin.isPresent()) { // Verify if find a lootbox
            Skin existingSkin = optionalSkin.get(); // get lootbox 
            existingSkin.setName(updatedSkin.getName()); // Update the name
            return skinRepository.save(existingSkin); // Save and return
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "S with ID " + id + " not found");
        }
    }
    // Delete one skin by Id
    public void deleteSkin(long id) {
        skinRepository.deleteById(id);
    }
}