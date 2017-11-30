/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.item.triggers.sources;

import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.trigger.source.BaseSource;
import org.bukkit.command.CommandSender;

public class CommandSenderSource extends BaseSource implements com.herocraftonline.items.api.item.trigger.source.CommandSenderSource {

    private CommandSender sender;

    public CommandSenderSource(Item item, CommandSender sender) {
        super(item);

        this.sender = sender;
    }

    @Override
    public CommandSender getSender() {
        return sender;
    }

}
