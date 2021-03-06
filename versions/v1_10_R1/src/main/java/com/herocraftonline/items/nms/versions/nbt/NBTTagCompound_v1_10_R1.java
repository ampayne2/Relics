/*
 * This file is part of Relics NMS 1_10_R1 Compatibility.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics NMS 1_10_R1 Compatibility,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.nms.versions.nbt;

import com.herocraftonline.items.api.storage.nbt.NBTBase;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.nbt.NBTTagObject;

import java.util.Set;

public class NBTTagCompound_v1_10_R1 extends net.minecraft.server.v1_10_R1.NBTTagCompound implements NBTTagCompound {

    public NBTTagCompound_v1_10_R1(net.minecraft.server.v1_10_R1.NBTTagCompound compound) {
        a(compound);
    }

    public NBTTagCompound_v1_10_R1() {
    }

    @Override
    public NBTTagCompound newInstance() {
        return new NBTTagCompound_v1_10_R1();
    }

    @Override
    public Set<String> getKeySet() {
        return c();
    }

    @Override
    public Object getObject(String key) {
        net.minecraft.server.v1_10_R1.NBTBase object = super.get(key);
        if (object instanceof NBTTagObject) {
            return ((NBTTagObject) object).getObject();
        }
        return null;
    }

    @Override
    public NBTTagList_v1_10_R1 getList(String key, int typeId) {
        net.minecraft.server.v1_10_R1.NBTTagList list = super.getList(key, typeId);
        if (list instanceof NBTTagList_v1_10_R1) {
            return (NBTTagList_v1_10_R1) list;
        } else {
            NBTTagList_v1_10_R1 apiList = new NBTTagList_v1_10_R1(list);
            setBase(key, apiList);
            return apiList;
        }
    }

    @Override
    public NBTTagCompound_v1_10_R1 getCompound(String key) {
        net.minecraft.server.v1_10_R1.NBTTagCompound compound = super.getCompound(key);
        if (compound instanceof NBTTagCompound_v1_10_R1) {
            return (NBTTagCompound_v1_10_R1) compound;
        } else {
            NBTTagCompound_v1_10_R1 apiCompound = new NBTTagCompound_v1_10_R1(compound);
            setBase(key, apiCompound);
            return apiCompound;
        }
    }

    @Override
    public NBTBase getBase(String key) {
        net.minecraft.server.v1_10_R1.NBTBase base = get(key);
        if (base instanceof NBTBase) {
            return (NBTBase) base;
        } else {
            NBTBase apiBase = NBTUtil_v1_10_R1.fromNMSBase(base);
            setBase(key, apiBase);
            return apiBase;
        }
    }

    @Override
    public void setByte(String key, byte value) {
        set(key, new NBTTagByte_v1_10_R1(value));
    }

    @Override
    public void setShort(String key, short value) {
        set(key, new NBTTagShort_v1_10_R1(value));
    }

    @Override
    public void setInt(String key, int value) {
        set(key, new NBTTagInt_v1_10_R1(value));
    }

    @Override
    public void setLong(String key, long value) {
        set(key, new NBTTagLong_v1_10_R1(value));
    }

    @Override
    public void setFloat(String key, float value) {
        set(key, new NBTTagFloat_v1_10_R1(value));
    }

    @Override
    public void setDouble(String key, double value) {
        set(key, new NBTTagDouble_v1_10_R1(value));
    }

    @Override
    public void setByteArray(String key, byte[] value) {
        set(key, new NBTTagByteArray_v1_10_R1(value));
    }

    @Override
    public void setIntArray(String key, int[] value) {
        set(key, new NBTTagIntArray_v1_10_R1(value));
    }

    @Override
    public void setString(String key, String value) {
        set(key, new NBTTagString_v1_10_R1(value));
    }

    @Override
    public void setObject(String key, Object value) {
        set(key, new NBTTagObject_v1_10_R1<>(value));
    }

    @Override
    public void setBase(String key, NBTBase base) {
        set(key, (net.minecraft.server.v1_10_R1.NBTBase) base);
    }

    @Override
    public NBTTagCompound_v1_10_R1 g() {
        NBTTagCompound_v1_10_R1 clone = new NBTTagCompound_v1_10_R1();

        for (String key : getKeySet()) {
            net.minecraft.server.v1_10_R1.NBTBase value = get(key);
            if (value != null) {
                clone.set(key, value.clone());
            }
        }

        return clone;
    }

    @Override
    public NBTTagCompound_v1_10_R1 clone() {
        return this.g();
    }

}
