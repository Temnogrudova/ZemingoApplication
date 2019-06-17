package com.zemingo.ekaterinatemnogrudova.zemingo.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelResponse {
    public List<Model> getList() {
        return list;
    }

    public void setList(List<Model> list) {
        this.list = list;
    }

    @SerializedName("list")
    private List<Model> list;
}
