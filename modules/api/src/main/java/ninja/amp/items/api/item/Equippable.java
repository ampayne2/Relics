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

import org.bukkit.entity.Player;

/**
 * Represents an item or attribute that has to be equipped to use.
 *
 * @author Austin Payne
 */
public interface Equippable {

    /**
     * Checks if the equippable can be equipped to a player.
     *
     * @param player the player
     * @return {@code true} if the equippable can be equipped, else {@code false}
     */
    boolean canEquip(Player player);

    /**
     * Equips the equippable to a player and checks if the item should be updated.
     *
     * @param player the player
     * @return {@code true} if the item should be updated, else {@code false}
     */
    boolean onEquip(Player player);

    /**
     * UnEquips the equippable from a player and checks if the item should be updated.
     *
     * @param player the player
     * @return {@code true} if the item should be updated, else {@code false}
     */
    boolean onUnEquip(Player player);

}
