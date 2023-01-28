package com.foofinc.cfbra.persistence;

import com.foofinc.cfbra.controller.LocalMemoryController;
import com.foofinc.cfbra.entity.SchoolList;
import com.foofinc.cfbra.util.TestUtil;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

class MemorySerializationManagerTest {


    @Test
    void saveSchoolsTest() {

        MemorySerializationManager memorySerializationManager = new MemorySerializationManager(TestUtil.FILEPATH_TEAMS);

//        Map<SchoolDto, List<FixtureDto>> testMap = TestUtil.createTestMap();
//
//        SchoolAndFixturesDS sfDS = new SchoolAndFixturesDS(testMap);

        SchoolList schoolList = SchoolList.INSTANCE;

        memorySerializationManager.save((Serializable) schoolList.getSchools());
    }

    @Test
    void loadSchoolsTest() {
        LocalMemoryController memorySerializationManager = new LocalMemoryController();
        memorySerializationManager.load();
    }
}