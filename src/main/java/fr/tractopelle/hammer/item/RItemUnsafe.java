package fr.tractopelle.hammer.item;

public class RItemUnsafe {

    private ItemBuilder itemBuilder;

    public RItemUnsafe(ItemBuilder itemBuilder) {
        this.itemBuilder = itemBuilder;
    }

    public String getString(String tag){
        return new RNBItem(this.itemBuilder.toItemStack()).getString(tag);
    }

    public RItemUnsafe setString(String tag, String value){
        this.itemBuilder = new ItemBuilder(new RNBItem(this.itemBuilder.toItemStack()).setString(tag, value).build());
        return this;
    }

    public RItemUnsafe setByte(String tag, byte b){
        this.itemBuilder = new ItemBuilder(new RNBItem(this.itemBuilder.toItemStack()).setByte(tag, b).build());
        return this;
    }

    public byte getByte(String tag){
        return new RNBItem(this.itemBuilder.toItemStack()).getByte(tag);
    }

    public RItemUnsafe setShort(String tag, short b){
        this.itemBuilder = new ItemBuilder(new RNBItem(this.itemBuilder.toItemStack()).setShort(tag, b).build());
        return this;
    }

    public short getShort(String tag){
        return new RNBItem(this.itemBuilder.toItemStack()).getShort(tag);
    }

    public RItemUnsafe setInt(String tag, int b){
        this.itemBuilder = new ItemBuilder(new RNBItem(this.itemBuilder.toItemStack()).setInt(tag, b).build());
        return this;
    }

    public int getInt(String tag){
        return new RNBItem(this.itemBuilder.toItemStack()).getInt(tag);
    }

    public RItemUnsafe setByteArray(String tag, byte[] b){
        this.itemBuilder = new ItemBuilder(new RNBItem(this.itemBuilder.toItemStack()).setByteArray(tag, b).build());
        return this;
    }

    public byte[] getByteArray(String tag){
        return new RNBItem(this.itemBuilder.toItemStack()).getByteArray(tag);
    }

    public RItemUnsafe setIntArray(String tag, int[] b){
        this.itemBuilder = new ItemBuilder(new RNBItem(this.itemBuilder.toItemStack()).setIntArray(tag, b).build());
        return this;
    }

    public int[] getIntArray(String tag){
        return new RNBItem(this.itemBuilder.toItemStack()).getIntArray(tag);
    }

    public boolean containsTag(String s){
        return new RNBItem(this.itemBuilder.toItemStack()).containsTag(s);
    }

    public RItemUnsafe remove(String tag){
        this.itemBuilder = new ItemBuilder(new RNBItem(this.itemBuilder.toItemStack()).removeTag(tag).build());
        return this;
    }

    public ItemBuilder toItemBuilder(){
        return this.itemBuilder;
    }
}