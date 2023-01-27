package com.foofinc.cfbra.persistence;

import com.foofinc.cfbra.entity.model.Schools;
import com.foofinc.cfbra.util.TestUtil;
import org.junit.jupiter.api.Test;

class MemoryManagerTest {


    @Test
    void saveSchoolsTest() {

        MemoryManager memoryManager = new MemoryManager(TestUtil.FILEPATH_TEAMS);

//        Map<SchoolDto, List<FixtureDto>> testMap = TestUtil.createTestMap();
//
//        SchoolAndFixturesDS sfDS = new SchoolAndFixturesDS(testMap);

        Schools schools = Schools.INSTANCE;

        memoryManager.saveSchools(schools);
    }

    @Test
    void loadSchoolsTest() {
        MemoryManager memoryManager = new MemoryManager();
        memoryManager.loadSchools();
    }
}