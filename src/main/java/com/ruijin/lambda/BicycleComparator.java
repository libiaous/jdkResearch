package com.ruijin.lambda;

public class BicycleComparator {

    public int compare(Bicycle a, Bicycle b) {
        return a.getFrameSize().compareTo(b.getFrameSize());
    }
}
