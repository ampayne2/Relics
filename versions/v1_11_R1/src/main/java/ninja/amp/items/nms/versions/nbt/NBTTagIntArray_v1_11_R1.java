package ninja.amp.items.nms.versions.nbt;


import ninja.amp.items.nms.nbt.NBTTagIntArray;

public class NBTTagIntArray_v1_11_R1 extends net.minecraft.server.v1_11_R1.NBTTagIntArray implements NBTTagIntArray {

    public NBTTagIntArray_v1_11_R1(net.minecraft.server.v1_11_R1.NBTTagIntArray aint) {
        super(aint.d());
    }

    public NBTTagIntArray_v1_11_R1(int[] aint) {
        super(aint);
    }

    @Override
    public NBTTagIntArray newInstance(int[] data) {
        return new NBTTagIntArray_v1_11_R1(data);
    }

    @Override
    public int[] getIntArray() {
        return d();
    }

    @Override
    public NBTTagIntArray_v1_11_R1 c() {
        return new NBTTagIntArray_v1_11_R1(d());
    }

    @Override
    public NBTTagIntArray_v1_11_R1 clone() {
        return this.c();
    }

}
