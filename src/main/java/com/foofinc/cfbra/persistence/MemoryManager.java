package com.foofinc.cfbra.persistence;

import com.foofinc.cfbra.api.jsondatastructures.Fixture;
import com.foofinc.cfbra.api.jsondatastructures.School;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryManager {

    private String filePath = "src/main/java/com/foofinc/cfbra/persistence/fbs_data";
    private List<String> dataIn;
    private List<String> dataOut;


    public MemoryManager() {
        this.dataIn = new ArrayList<>();
        this.dataOut = new ArrayList<>();
    }

    //For Testing
    public MemoryManager(String filePath) {
        this();
        this.filePath = filePath;
    }

    public Map<School, List<Fixture>> loadSchools() {
        Map<School, List<Fixture>> map = new HashMap<>();
        Map.Entry<School, List<Fixture>> entry;

        dataIn = FileScribe.readFromFile(filePath);
        for (String s : dataIn) {
            entry = SchoolStringMapper.mapStringToSchoolEntry(s);
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public void saveSchools(Map<School, List<Fixture>> schools) {
        for (Map.Entry<School, List<Fixture>> entry : schools.entrySet()) {
            dataOut.add(SchoolStringMapper.mapSchoolEntryToString(entry));
        }
        FileScribe.writeToFile(filePath, dataOut);
    }
}
