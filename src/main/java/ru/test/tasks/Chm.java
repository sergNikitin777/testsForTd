package ru.test.tasks;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Chm {
    public static void main(String[] args) {

        //concurrentHashMap
        ConcurrentHashMap<String, String> chm = new ConcurrentHashMap<String, String>();
        chm.put("1","One");
        chm.put("2","Two");
        Iterator itchm = chm.keySet().iterator();
        while(itchm.hasNext()){
            String key = (String) itchm.next();
            if(key.equals("1")) chm.put(key+"newObj", "newObject");
        }
        System.out.println("After Iterating Hashmap");

        //Hashmap
        Map<Object, Object> hashMap = Collections.synchronizedMap(new HashMap());
        hashMap.put("1","One");
        hashMap.put("2","Two");
        Iterator it = hashMap.keySet().iterator();
        while(it.hasNext()){
            synchronized(hashMap) {
                String key = (String) it.next();
                if (key.equals("1")) hashMap.put(key + "newObj", "newObject");
            }
        }
        System.out.println("After Iterating consurrentHashmap");
    }
}
