package com.joe.crowd.mvc.handler;

import java.util.List;

//Test
public class ParamData {
    private List<Integer> array;

    public ParamData() {
    }

    public ParamData(List<Integer> array) {
        this.array = array;
    }

    public List<Integer> getArray() {
        return array;
    }

    public void setArray(List<Integer> array) {
        this.array = array;
    }
}
