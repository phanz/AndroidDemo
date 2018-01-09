package com.example.library.rxjava;

import java.util.List;

/**
 * Created by phanz on 2017/5/2.
 */

class Fish {
    public String type;
    public int weight;
    public String sex;

    public List<Fish> fishs;

    public Fish(String type, int weight, String sex) {
        this.type = type;
        this.weight = weight;
        this.sex = sex;

    }

    @Override
    public String toString() {
        return "Fish{" +
                "type='" + type + '\'' +
                ", weight=" + weight +
                ", sex='" + sex + '\'' +
                '}';
    }
}