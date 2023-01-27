package com.foofinc.cfbra.persistence;

import com.foofinc.cfbra.entity.model.Schools;

import java.io.*;

public class MemoryManager {

    private final File filePath = new File("src/main/java/com/foofinc/cfbra/persistence/fbs_data/team_data.ser");

    public MemoryManager() {
    }

    //For Testing
    public MemoryManager(String filePath) {
        this();
    }

    public Schools loadSchools() {
        return deserialize(filePath);
    }

    public void saveSchools(Schools schools) {
        serialize(schools, filePath);
    }

    private void serialize(Schools schools, File file) {
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(schools);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Schools deserialize(File file) {
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (Schools) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean fileExists() {
        return filePath.exists();
    }
}
