package com.cs2drop.entity;

import jakarta.persistence.*;

@Entity
public class Skin {
	public Skin() {}

	public Skin(Long skin_id, String name, String rarity, double chance, LootBox lootbox) {
		super();
		this.skin_id = skin_id;
		this.name = name;
		this.rarity = rarity;
		this.chance = chance;
		this.lootbox = lootbox;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skin_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String rarity;

    @Column(nullable = false)
    private double chance;

    @ManyToOne
    @JoinColumn(name = "lootbox_id", nullable = false)
    private LootBox lootbox;

	public Long getSkin_id() {
		return skin_id;
	}

	public void setSkin_id(Long skin_id) {
		this.skin_id = skin_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public double getChance() {
		return chance;
	}

	public void setChance(double chance) {
		this.chance = chance;
	}

	public LootBox getLootbox() {
		return lootbox;
	}

	public void setLootbox(LootBox lootbox) {
		this.lootbox = lootbox;
	}

}
