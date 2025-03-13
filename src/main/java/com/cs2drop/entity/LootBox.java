package com.cs2drop.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class LootBox {
	 public LootBox() {}

	public LootBox(Long lootBox_id, String name, List<Skin> skins) {
		super();
		this.lootBox_id = lootBox_id;
		this.name = name;
		this.skins = skins;
	}

	@Id
	 private Long lootBox_id;

	 @Column(nullable = false)
	 private String name;

	 @OneToMany(mappedBy = "lootbox", cascade = CascadeType.ALL, orphanRemoval = true)
	 private List<Skin> skins;

	public Long getLootBox_id() {
		return lootBox_id;
	}

	public void setLootBox_id(Long lootBox_id) {
		this.lootBox_id = lootBox_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Skin> getSkins() {
		return skins;
	}

	public void setSkins(List<Skin> skins) {
		this.skins = skins;
	}
}
