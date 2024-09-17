package com.example.usalocalnews.csv;

import com.example.usalocalnews.entity.City;
import com.example.usalocalnews.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportService {

    @Autowired
    private CityRepository yourEntityRepository;

    public void importCsvToDb(List<String[]> records) {
        for (String[] record : records) {
            City entity = new City();
            entity.setCity(record[0]);  // Assuming your CSV has columns matching your entity
            entity.setCity_ascii(record[1]);
            entity.setState_id(record[2]);
            entity.setState_name(record[3]);
            entity.setCounty_fips(record[4]);
            entity.setCounty_name(record[5]);
            entity.setLat(record[6]);
            entity.setLng(record[7]);
            entity.setPopulation(record[8]);
            entity.setDensity(record[9]);
            entity.setSource(record[10]);
            entity.setMilitary(record[11]);
            entity.setIncorporated(record[12]);
            entity.setTimezone(record[13]);
            entity.setRanking(record[14]);
            entity.setZips(record[15]);
            entity.setId(record[16]);
            // set other fields accordingly
            yourEntityRepository.save(entity);  // Save the entity to the DB
        }
    }
}
