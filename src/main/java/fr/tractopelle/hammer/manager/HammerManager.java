package fr.tractopelle.hammer.manager;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;

public class HammerManager {

    private Material material;
    private String name;
    private List<String> lore;
    private List<Material> blacklistBlocks;

    public HammerManager(Material material, String name, List<String> lore, List<Material> blacklistBlocks) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.blacklistBlocks = blacklistBlocks;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public List<Material> getBlacklistBlocks() {
        return blacklistBlocks;
    }

    public void setBlacklistBlocks(List<Material> blacklistBlocks) {
        this.blacklistBlocks = blacklistBlocks;
    }
}

