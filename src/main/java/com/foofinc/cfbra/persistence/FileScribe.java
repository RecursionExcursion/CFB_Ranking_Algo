package com.foofinc.cfbra.persistence;

import java.io.*;
import java.util.List;

public final class FileScribe {

    private FileScribe() {
    }

    public static List<String> readFromFile(String fileName) {
        try {
            return readFile(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> readFile(String fileName) throws IOException {

        List<String> data;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            data = br.lines().toList();
        }
        return data;

    }

    public static void writeToFile(String fileName, List<String> data) {
        try {
            writeFile(fileName, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeFile(String fileName, List<String> data) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String lineOfData : data) {
                bw.write(lineOfData);
                bw.newLine();
            }
        }
    }
}
