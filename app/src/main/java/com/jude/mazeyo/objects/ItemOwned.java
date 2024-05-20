package com.jude.mazeyo.objects;

public class ItemOwned {

    private String name;
    private boolean inUseNow;
    private int image;

    public ItemOwned(String name, boolean inUseNow, int image) {
        this.name = name;
        this.inUseNow = inUseNow;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInUseNow() {
        return inUseNow;
    }

    public void setInUseNow(boolean inUseNow) {
        this.inUseNow = inUseNow;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
