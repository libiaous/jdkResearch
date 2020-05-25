package com.ruijin.lambda;

import java.util.ArrayList;
import java.util.List;

public class Bicycle {
    private String brand;
    private String vendor;
    private Integer frameSize;

    public Bicycle(String brand, String vendor) {
        this.brand = brand;
        this.vendor = vendor;
    }

    public Bicycle(String brand) {
        this.brand = brand;
        this.frameSize = 0;
    }

    public Bicycle(String brand, Integer frameSize) {
        this.brand = brand;
        this.frameSize = frameSize;
    }

    // standard constructor, getters and setters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getFrameSize() {
        return frameSize;
    }

    public void setFrameSize(Integer frameSize) {
        this.frameSize = frameSize;
    }

    public static List<Bicycle> getBicycleList(int size) {
        List<Bicycle> list = new ArrayList<>(size);
        for (int i = size; i > 0; i--) {
            list.add(new Bicycle("brand-" + i, i));
        }
        return list;
    }

    @Override
    public String toString() {
        return "Bicycle{" +
                "brand='" + brand + '\'' +
                ", vendor='" + vendor + '\'' +
                ", frameSize=" + frameSize +
                '}';
    }
}
