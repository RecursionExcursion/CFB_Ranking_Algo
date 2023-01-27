package com.foofinc.cfbra.persistence;

import com.foofinc.cfbra.entity.model.SchoolList;
import com.foofinc.cfbra.util.TestUtil;
import org.junit.jupiter.api.Test;

class MemoryManagerTest {


    @Test
    void saveSchoolsTest() {

        MemoryManager memoryManager = new MemoryManager(TestUtil.FILEPATH_TEAMS);

//        Map<SchoolDto, List<FixtureDto>> testMap = TestUtil.createTestMap();
//
//        SchoolAndFixturesDS sfDS = new SchoolAndFixturesDS(testMap);

        SchoolList schoolList = SchoolList.INSTANCE;

        memoryManager.saveSchools(schoolList.getSchools());
    }

    @Test
    void loadSchoolsTest() {
        MemoryManager memoryManager = new MemoryManager();
        memoryManager.loadSchools();
    }
}