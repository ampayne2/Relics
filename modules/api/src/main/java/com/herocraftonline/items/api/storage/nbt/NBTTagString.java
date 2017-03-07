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
package com.herocraftonline.items.api.storage.nbt;

public interface NBTTagString extends NBTBase {

    static NBTTagString create() {
        return ((NBTTagString) NBT_INSTANCES[8]).newInstance();
    }

    static NBTTagString create(String data) {
        return ((NBTTagString) NBT_INSTANCES[8]).newInstance(data);
    }

    NBTTagString newInstance();

    NBTTagString newInstance(String data);

    String getString();

}