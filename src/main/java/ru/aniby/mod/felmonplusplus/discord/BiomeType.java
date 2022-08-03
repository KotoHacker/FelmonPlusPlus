package ru.aniby.mod.felmonplusplus.discord;

import java.util.Collection;
import java.util.Set;

public enum BiomeType {
    PLAINS("plains", "sunflower_plains"),
    SNOW("snowy_plains", "ice_spikes", "snowy_taiga"),
    HOT("desert", "savanna"),
    SWAMP("swamp", "mangrove_swamp"),
    FOREST("forest", "flower_forest", "birch_forest", "old_growth_birch_forest"),
    TAIGA("old_growth_pine_taiga", "old_growth_spruce_taiga", "taiga")
    ;

    private final Set<String> biomes;

    BiomeType(Collection<String> biomes) {
        this.biomes = (Set<String>) biomes;
    }

    BiomeType(String... biomes) {
        this.biomes = Set.of(biomes);
    }
}
