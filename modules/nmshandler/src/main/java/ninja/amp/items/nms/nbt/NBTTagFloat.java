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

public interface NBTTagFloat extends NBTNumber {

    static NBTTagFloat create(float data) {
        return ((NBTTagFloat) NBTBase.NBT_INSTANCES[5]).newInstance(data);
    }

    NBTTagFloat newInstance(float data);

}
