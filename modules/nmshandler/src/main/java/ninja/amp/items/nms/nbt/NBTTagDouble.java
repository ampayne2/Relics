/*
 * This file is part of AmpItems NMS API.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems NMS API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems NMS API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems NMS API.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.nms.nbt;

public interface NBTTagDouble extends NBTNumber {

    static NBTTagDouble create(double data) {
        return ((NBTTagDouble) NBTBase.NBT_INSTANCES[6]).newInstance(data);
    }

    NBTTagDouble newInstance(double data);

}
