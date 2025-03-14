package com.cs2drop.repository;

import com.cs2drop.entity.Skin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SkinRepository extends JpaRepository<Skin,Integer> {
	@Query(value = "SELECT * FROM skin WHERE lootbox_id = :lootboxId", nativeQuery = true)
	List<Skin> findByLootbox_id(@Param("lootboxId") int lootbox_id);
}

