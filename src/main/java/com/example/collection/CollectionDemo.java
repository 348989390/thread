package com.example.collection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionDemo {
    public static void main(String[] args) {
        Map<String,String> map = Collections.synchronizedMap(new HashMap<String,String>());
    }
}
