package com.foofinc.cfbra.persistence;

import com.foofinc.cfbra.util.TestUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileScribeTest {

    @Test
    void readFromFileTest() {
        String filePath = TestUtil.FILEPATH_READ;
        List<String> fileWords = FileScribe.readFromFile(filePath);
        assertEquals(fileWords.get(0),"hi");
        assertEquals(fileWords.get(1),"bye");
        assertEquals(fileWords.get(2),"boops");
    }

    @Test
    void writeToFileTest() {
        String filePath = TestUtil.FILEPATH_WRITE;
        List<String> wordsToBeOnFile = List.of(new String[]{"I", "Love", "Java"});
        FileScribe.writeToFile(filePath, wordsToBeOnFile);

        List<String> readBack = FileScribe.readFromFile(filePath);
        assertEquals(readBack.get(0),"I");
        assertEquals(readBack.get(1),"Love");
        assertEquals(readBack.get(2),"Java");
    }
}