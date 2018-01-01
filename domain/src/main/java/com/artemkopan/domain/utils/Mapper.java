package com.artemkopan.domain.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public abstract class Mapper<From, To> {

    public abstract To map(@NotNull From from);

    public From reverseMap(@NotNull To to) {
        throw notImplementedException();
    }

    public AbstractMethodError notImplementedException() {
        return new AbstractMethodError("Not implemented method");
    }

    public List<To> mapList(List<From> typeList) {

        List<To> list = new ArrayList<>();

        for (From type : typeList) {

            list.add(map(type));

        }

        return list;

    }

    public List<From> reverseMapList(List<To> typeList) {

        List<From> list = new ArrayList<>();

        for (To type : typeList) {

            list.add(reverseMap(type));

        }

        return list;

    }

}