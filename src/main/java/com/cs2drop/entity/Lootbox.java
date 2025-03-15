package com.cs2drop.entity;
import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Lootbox {
	 public Lootbox() {}

	public Lootbox(int lootbox_id, String lootbox_name , List<Skin> skins) {
		super();
		this.lootbox_id = lootbox_id;
		this.lootbox_name = lootbox_name;
		this.skins = skins;
	}

	@Id
	 private int lootbox_id;

	 @Column(nullable = false)
	 private String lootbox_name;

	 @OneToMany(mappedBy = "lootbox", cascade = CascadeType.ALL, orphanRemoval = true)
	 @JsonIgnore
	 private List<Skin> skins;

	public int getCaseId() {
		return lootbox_id;
	}

	public void setCaseId(int lootbox_id) {
		this.lootbox_id = lootbox_id;
	}

	public String getCaseName() {
		return lootbox_name;
	}

	public void setCaseName(String lootbox_name ) {
		this.lootbox_name = lootbox_name ;
	}

	public List<Skin> getSkins() {
		return skins;
	}

	public void setSkins(List<Skin> skins) {
		this.skins = skins;
	}
}
