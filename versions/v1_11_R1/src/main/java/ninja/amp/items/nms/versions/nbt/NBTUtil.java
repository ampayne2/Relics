package ninja.amp.items.nms.versions.nbt;

import net.minecraft.server.v1_11_R1.NBTTagByte;
import net.minecraft.server.v1_11_R1.NBTTagByteArray;
import net.minecraft.server.v1_11_R1.NBTTagDouble;
import net.minecraft.server.v1_11_R1.NBTTagFloat;
import net.minecraft.server.v1_11_R1.NBTTagInt;
import net.minecraft.server.v1_11_R1.NBTTagIntArray;
import net.minecraft.server.v1_11_R1.NBTTagLong;
import net.minecraft.server.v1_11_R1.NBTTagShort;
import net.minecraft.server.v1_11_R1.NBTTagString;
import ninja.amp.items.nms.nbt.NBTBase;

public final class NBTUtil {

    private NBTUtil() {
    }

    public static NBTBase fromNMSBase(net.minecraft.server.v1_11_R1.NBTBase base) {
        if (base instanceof NBTBase) {
            return (NBTBase) base;
        } else {
            switch (base.getTypeId()) {
                case 1:
                    return new NBTTagByte_v1_11_R1((NBTTagByte) base);
                case 2:
                    return new NBTTagShort_v1_11_R1((NBTTagShort) base);
                case 3:
                    return new NBTTagInt_v1_11_R1((NBTTagInt) base);
                case 4:
                    return new NBTTagLong_v1_11_R1((NBTTagLong) base);
                case 5:
                    return new NBTTagFloat_v1_11_R1((NBTTagFloat) base);
                case 6:
                    return new NBTTagDouble_v1_11_R1((NBTTagDouble) base);
                case 7:
                    return new NBTTagByteArray_v1_11_R1((NBTTagByteArray) base);
                case 8:
                    return new NBTTagString_v1_11_R1((NBTTagString) base);
                case 9:
                    return new NBTTagList_v1_11_R1((net.minecraft.server.v1_11_R1.NBTTagList) base);
                case 10:
                    return new NBTTagCompound_v1_11_R1((net.minecraft.server.v1_11_R1.NBTTagCompound) base);
                case 11:
                    return new NBTTagIntArray_v1_11_R1((NBTTagIntArray) base);
                default:
                    return null;
            }
        }
    }

}
