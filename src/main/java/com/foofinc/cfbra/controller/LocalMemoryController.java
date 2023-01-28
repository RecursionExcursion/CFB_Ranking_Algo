package com.foofinc.cfbra.controller;

import com.foofinc.cfbra.persistence.MemorySerializationManager;

import java.io.Serializable;

public class LocalMemoryController<T extends Serializable> {

    private final String filePath = "src/main/java/com/foofinc/cfbra/persistence/fbs_data/team_data";
    private final MemorySerializationManager<T> memorySerializationManager = new MemorySerializationManager<>(filePath);


    public LocalMemoryController() {
    }

    public void save(T t) {
        memorySerializationManager.save(t);
    }

    public T load() {
        return memorySerializationManager.load();
    }

    public boolean fileExists() {
       return memorySerializationManager.fileExists();
    }
}
