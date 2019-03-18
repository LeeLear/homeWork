package com.gupaoedu.vip.pattern.singleton.register;

public enum Color{
    RED(255,0,0),BLUE(0,255,0),GREEN(0,0,255);

    Color(int r, int b, int g) {
        this.r = r;
        this.b = b;
        this.g = g;
    }

    private int r;
    private int b;
    private int g;

    public int getR() {
        return r;
    }

    public int getB() {
        return b;
    }

    public int getG() {
        return g;
    }
}
