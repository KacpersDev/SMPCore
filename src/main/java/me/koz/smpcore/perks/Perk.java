package me.koz.smpcore.perks;

import lombok.Getter;
import me.koz.smpcore.Main;

@Getter
public class Perk {

    private final Main main;

    public Perk(Main main) {
        this.main = main;
    }


}
