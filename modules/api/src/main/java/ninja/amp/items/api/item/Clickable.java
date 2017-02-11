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

import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Represents an item or attribute that can do something when clicked.
 *
 * @author Austin Payne
 */
public interface Clickable {

    /**
     * Handles a player interact event on the clickable.
     *
     * @param event    the player interact event
     * @param equipped {@code true} if the item of the clickable is equipped, else false
     */
    void onClick(PlayerInteractEvent event, boolean equipped);

}
