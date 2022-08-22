package me.koz.smpcore.backpack;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Data
public class Backpack {

    private UUID owner;
    private ItemStack[] items;

    int size;

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public Backpack(UUID owner) {
        this.owner = owner;
        this.items = new ItemStack[0];
    }

}
