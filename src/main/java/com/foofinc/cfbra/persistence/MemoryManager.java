package com.foofinc.cfbra.persistence;

import com.foofinc.cfbra.entity.model.School;

import java.io.*;
import java.util.List;

public class MemoryManager {

    private final File filePath = new File("src/main/java/com/foofinc/cfbra/persistence/fbs_data/team_data.ser");

    public MemoryManager() {
    }

    //For Testing
    public MemoryManager(String filePath) {
        this();
    }

    public List<School> loadSchools() {
        return deserialize(filePath);
    }

    public void saveSchools(List<School> schools) {
        serialize(schools, filePath);
    }

    private void serialize(List<School> schools, File file) {
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(schools);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<School> deserialize(File file) {
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (List<School>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean fileExists() {
        return filePath.exists();
    }
}
