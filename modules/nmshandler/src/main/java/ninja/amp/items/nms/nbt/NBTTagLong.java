/*
 * This file is part of AmpItems NMS API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems NMS API,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.nms.nbt;

public interface NBTTagLong extends NBTNumber {

    static NBTTagLong create(long data) {
        return ((NBTTagLong) NBTBase.NBT_INSTANCES[4]).newInstance(data);
    }

    NBTTagLong newInstance(long data);

}
