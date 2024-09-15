package com.example.usalocalnews.service;

import com.example.usalocalnews.entity.City;
import com.example.usalocalnews.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.*;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public Set<String> getCitiesList(String startsWith) {
        Set<City> citiesList = cityRepository.getCitiesByCityStartsWith(startsWith);
        Set<String> citiesOutput = convertCityListToStringList(citiesList);
        return citiesOutput;
    }

    private Set<String> convertCityListToStringList(Set<City> citiesInput){
        Set<String> citiesOutput = new HashSet<>();
        for (City city : citiesInput) {
            citiesOutput.add(city.getCity());
        }

        return citiesOutput;
    }
}
