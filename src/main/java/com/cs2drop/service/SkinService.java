package com.cs2drop.service;

import com.cs2drop.entity.Skin;
import com.cs2drop.repository.SkinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SkinService {

    private final SkinRepository skinRepository;

    @Autowired
    public SkinService(SkinRepository skinRepository) {
        this.skinRepository = skinRepository;
    }

    // Retorna todas as skins cadastradas
    public List<Skin> getAllSkins() {
        return skinRepository.findAll();
    }

    // Busca uma skin pelo ID
    public Optional<Skin> getSkinById(long id) {
        return skinRepository.findById(id);
    }

    // Adiciona uma nova skin
    public Skin addSkin(Skin skin) {
        return skinRepository.save(skin);
    }

    // Deleta uma skin pelo ID
    public void deleteSkin(long id) {
        skinRepository.deleteById(id);
    }
}