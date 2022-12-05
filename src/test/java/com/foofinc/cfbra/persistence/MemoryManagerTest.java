package com.foofinc.cfbra.persistence;

import com.foofinc.cfbra.util.TestUtil;
import com.foofinc.cfbra.api.jsondatastructures.Fixture;
import com.foofinc.cfbra.api.jsondatastructures.School;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class MemoryManagerTest {


    @Test
    void saveSchoolsTest() {

        MemoryManager memoryManager = new MemoryManager(TestUtil.FILEPATH_TEAMS);

        Map<School, List<Fixture>> testMap = TestUtil.createTestMap();

        memoryManager.saveSchools(testMap);
    }

    @Test
    void loadSchoolsTest() {

        MemoryManager memoryManager = new MemoryManager();
        memoryManager.loadSchools();
    }
}