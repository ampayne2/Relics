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
package com.herocraftonline.items.api.item.attribute;

import com.herocraftonline.items.api.storage.config.Config;

import java.util.function.Predicate;

/**
 * The type of an item attribute.<br>
 * Used so the item plugin can create new attribute instances without knowing implementation details.
 *
 * @author Austin Payne
 */
public interface AttributeType extends Config, Predicate<ItemAttribute> {

    /**
     * Gets the name of the attribute type.
     *
     * @return the attribute type's name
     */
    String getName();

    /**
     * Gets the sorted position in lore of the attribute type.
     *
     * @return the attribute type's lore position
     */
    int getLorePosition();

    /**
     * Sets the sorted position in lore of the attribute type.
     *
     * @param position the lore position
     */
    void setLorePosition(int position);

    /**
     * Gets the attribute factory that can create item attribute's of this attribute type.
     *
     * @return the attribute type's attribute factory
     */
    AttributeFactory<? extends ItemAttribute> getFactory();

}
