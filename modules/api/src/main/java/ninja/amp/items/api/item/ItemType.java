/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems API,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.api.item;

import java.util.Objects;

public class ItemType {

    public static final ItemType OTHER = new ItemType("other");
    public static final ItemType UNIDENTIFIED = new ItemType("unidentified");
    public static final ItemType TOOL = new ItemType("tool");
    public static final ItemType TOOL_AXE = new ItemType("tool-axe");
    public static final ItemType TOOL_HOE = new ItemType("tool-hoe");
    public static final ItemType TOOL_PICKAXE = new ItemType("tool-pickaxe");
    public static final ItemType TOOL_SHEARS = new ItemType("tool-shears");
    public static final ItemType TOOL_SHOVEL = new ItemType("tool-shovel");
    public static final ItemType WEAPON = new ItemType("weapon");
    public static final ItemType WEAPON_AXE = new ItemType("weapon-axe");
    public static final ItemType WEAPON_BOW = new ItemType("weapon-bow");
    public static final ItemType WEAPON_HOE = new ItemType("weapon-hoe");
    public static final ItemType WEAPON_PICKAXE = new ItemType("weapon-pickaxe");
    public static final ItemType WEAPON_SHEARS = new ItemType("weapon-shears");
    public static final ItemType WEAPON_SHOVEL = new ItemType("weapon-shovel");
    public static final ItemType WEAPON_SWORD = new ItemType("weapon-sword");
    public static final ItemType HELMET = new ItemType("helmet");
    public static final ItemType CHESTPLATE = new ItemType("chestplate");
    public static final ItemType LEGGINGS = new ItemType("leggings");
    public static final ItemType BOOTS = new ItemType("boots");
    public static final ItemType SHIELD = new ItemType("shield");

    private final String name;

    public ItemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemType)) return false;
        ItemType itemType = (ItemType) o;
        return Objects.equals(name, itemType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
