package com.foofinc.cfbra.persistence;

import com.foofinc.cfbra.entity.SchoolAndFixturesDS;

import java.io.*;

public class MemoryManager {

    private final File filePath = new File("src/main/java/com/foofinc/cfbra/persistence/fbs_data/team_data.ser");

    public MemoryManager() {
    }

    //For Testing
    public MemoryManager(String filePath) {
        this();
    }

    public SchoolAndFixturesDS loadSchools() {
        return deserialize(filePath);
    }

    public void saveSchools(SchoolAndFixturesDS sfDS) {
        serialize(sfDS, filePath);
    }

    private void serialize(SchoolAndFixturesDS sfDS, File file) {
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(sfDS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SchoolAndFixturesDS deserialize(File file) {
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (SchoolAndFixturesDS) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean fileExists() {
        return filePath.exists();
    }
}
