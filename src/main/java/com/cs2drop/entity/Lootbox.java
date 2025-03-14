package com.cs2drop.entity;
import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Lootbox {
	 public Lootbox() {}

	public Lootbox(int lootbox_id, String name, List<Skin> skins) {
		super();
		this.lootbox_id = lootbox_id;
		this.lootbox_name = name;
		this.skins = skins;
	}

	@Id
	 private int lootbox_id;

	 @Column(nullable = false)
	 private String lootbox_name;

	 @OneToMany(mappedBy = "lootbox", cascade = CascadeType.ALL, orphanRemoval = true)
	 @JsonIgnore
	 private List<Skin> skins;

	public int getLootbox_id() {
		return lootbox_id;
	}

	public void setLootbox_id(int lootbox_id) {
		this.lootbox_id = lootbox_id;
	}

	public String getName() {
		return lootbox_name;
	}

	public void setName(String name) {
		this.lootbox_name = name;
	}

	public List<Skin> getSkins() {
		return skins;
	}

	public void setSkins(List<Skin> skins) {
		this.skins = skins;
	}
}
