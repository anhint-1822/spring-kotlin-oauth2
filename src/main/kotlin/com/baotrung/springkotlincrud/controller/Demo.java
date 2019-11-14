package com.baotrung.springkotlincrud.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Demo {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(111, "Lisa");
        map.put(222, "Narayan");
        map.put(333, "Xiangh");
        map.put(444, "Arunkumar");
        map.put(555, "Jyous");
        map.put(666, "Klusener");

        Map<Integer,String> sort = map.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(s, s2) -> s, LinkedHashMap::new));



    }
}
