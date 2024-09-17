package com.example.usalocalnews.csv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CsvImportRunner implements CommandLineRunner {

    @Autowired
    private CsvService csvService;

    @Autowired
    private ImportService importService;

    @Override
    public void run(String... args) throws Exception {
        String filePath = "./src/main/resources/uscities.csv";
        List<String[]> records = csvService.readCsv(filePath);
        importService.importCsvToDb(records);
    }
}
