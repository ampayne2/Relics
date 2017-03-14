/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.item.attribute.attributes.effects;

import com.herocraftonline.items.api.item.attribute.Attribute;
import org.bukkit.entity.Player;

public interface Effect<T extends Attribute<T>> extends Attribute<T> {

    void play(Player player);

    void stop(Player player);

}