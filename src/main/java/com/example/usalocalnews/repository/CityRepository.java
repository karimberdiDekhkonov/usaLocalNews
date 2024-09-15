package com.example.usalocalnews.repository;

import com.example.usalocalnews.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Set<City> getCitiesByCityStartsWith(String startsWith);
}
