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
package com.herocraftonline.items.api.item.trigger.sources;

import com.herocraftonline.items.api.item.Item;
import org.bukkit.entity.HumanEntity;

public class HumanEntitySource extends LivingEntitySource {

    private final HumanEntity source;

    public HumanEntitySource(Item item, HumanEntity source) {
        super(item, source);

        this.source = source;
    }

    @Override
    public HumanEntity getSource() {
        return source;
    }

}