package com.foofinc.cfbra.json.jsondatastructures;

import com.foofinc.cfbra.api.dto.SchoolDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SchoolTest {

    private final SchoolDto testSchool;

    public SchoolTest(){
        testSchool = new SchoolDto("Miami", "Hurricanes", "UM");
    }

    @Test
    void getSchool() {
        assertEquals(testSchool.getSchool(),"Miami");
    }

    @Test
    void setSchool() {
        testSchool.setSchool("Florida");
        assertEquals(testSchool.getSchool(),"Florida");
    }

    @Test
    void getMascot() {
        assertEquals(testSchool.getMascot(),"Hurricanes");
    }

    @Test
    void setMascot() {
        testSchool.setSchool("Gators");
        assertEquals(testSchool.getSchool(),"Gators");
    }

    @Test
    void getAbbreviation() {
        assertEquals(testSchool.getAbbreviation(),"UM");
    }

    @Test
    void setAbbreviation() {
        testSchool.setSchool("UF");
        assertEquals(testSchool.getSchool(),"UF");
    }
}