package com.cs2drop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class Skin {
	public Skin() {}

	public Skin(int skin_id, String name, String rarity, double chance, Lootbox lootbox) {
		super();
		this.skin_id = skin_id;
		this.skin_name = name;
		this.rarity = rarity;
		this.chance = chance;
		this.lootbox = lootbox;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skin_skin_id_seq")
	@SequenceGenerator(name = "skin_skin_id_seq", sequenceName = "public.skin_skin_id_seq", allocationSize = 1)
    private int skin_id;

    @Column(nullable = false)
    private String skin_name;

    @Column(nullable = false)
    private String rarity;
    
    @Version
    private int version; 

    @Column(nullable = false)
    private double chance;

    @ManyToOne
    @JoinColumn(name = "lootbox_id", nullable = false)
    @JsonBackReference
    private Lootbox lootbox;

	public int getSkin_id() {
		return skin_id;
	}

	public void setSkin_id(int skin_id) {
		this.skin_id = skin_id;
	}

	public String getSkinName() {
		return skin_name;
	}

	public void setSkinName(String name) {
		this.skin_name = name;
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

	public Lootbox getLootbox() {
		return lootbox;
	}

	public void setLootbox(Lootbox lootbox) {
		this.lootbox = lootbox;
	}

}
