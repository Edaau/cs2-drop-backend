package com.cs2drop.repository;

import com.cs2drop.entity.Skin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SkinRepository extends JpaRepository<Skin, Long> {
	List<Skin> findByLootboxId(Long lootboxId);
}

