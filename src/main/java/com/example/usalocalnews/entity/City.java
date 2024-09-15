package com.example.usalocalnews.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity  // Marks this class as a JPA entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment primary key
    private Long id;

    private String city;
    private String city_ascii;
    private String state_id;
    private String state_name;
    private String county_fips;
    private String county_name;
    private String lat;
    private String lng;
    private String population;
    private String density;
    private String source;
    private boolean military;
    private boolean incorporated;
    private String timezone;
    private String ranking;
    @Column(length = 2000)
    private String zips;

    public City() {

    }

    public City(Long id, String city, String city_ascii, String state_id, String state_name, String county_fips, String county_name, String lat, String lng, String population, String density, String source, boolean military, boolean incorporated, String timezone, String ranking, String zips) {
        this.id = id;
        this.city = city;
        this.city_ascii = city_ascii;
        this.state_id = state_id;
        this.state_name = state_name;
        this.county_fips = county_fips;
        this.county_name = county_name;
        this.lat = lat;
        this.lng = lng;
        this.population = population;
        this.density = density;
        this.source = source;
        this.military = military;
        this.incorporated = incorporated;
        this.timezone = timezone;
        this.ranking = ranking;
        this.zips = zips;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_ascii() {
        return city_ascii;
    }

    public void setCity_ascii(String city_ascii) {
        this.city_ascii = city_ascii;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getCounty_fips() {
        return county_fips;
    }

    public void setCounty_fips(String county_fips) {
        this.county_fips = county_fips;
    }

    public String getCounty_name() {
        return county_name;
    }

    public void setCounty_name(String county_name) {
        this.county_name = county_name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isMilitary() {
        return military;
    }

    public void setMilitary(boolean military) {
        this.military = military;
    }

    public boolean isIncorporated() {
        return incorporated;
    }

    public void setIncorporated(boolean incorporated) {
        this.incorporated = incorporated;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getZips() {
        return zips;
    }

    public void setZips(String zips) {
        this.zips = zips;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City city1)) return false;
        return isMilitary() == city1.isMilitary() && isIncorporated() == city1.isIncorporated() && Objects.equals(getId(), city1.getId()) && Objects.equals(getCity(), city1.getCity()) && Objects.equals(getCity_ascii(), city1.getCity_ascii()) && Objects.equals(getState_id(), city1.getState_id()) && Objects.equals(getState_name(), city1.getState_name()) && Objects.equals(getCounty_fips(), city1.getCounty_fips()) && Objects.equals(getCounty_name(), city1.getCounty_name()) && Objects.equals(getLat(), city1.getLat()) && Objects.equals(getLng(), city1.getLng()) && Objects.equals(getPopulation(), city1.getPopulation()) && Objects.equals(getDensity(), city1.getDensity()) && Objects.equals(getSource(), city1.getSource()) && Objects.equals(getTimezone(), city1.getTimezone()) && Objects.equals(getRanking(), city1.getRanking()) && Objects.equals(getZips(), city1.getZips());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCity(), getCity_ascii(), getState_id(), getState_name(), getCounty_fips(), getCounty_name(), getLat(), getLng(), getPopulation(), getDensity(), getSource(), isMilitary(), isIncorporated(), getTimezone(), getRanking(), getZips());
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", city_ascii='" + city_ascii + '\'' +
                ", state_id='" + state_id + '\'' +
                ", state_name='" + state_name + '\'' +
                ", county_fips='" + county_fips + '\'' +
                ", county_name='" + county_name + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", population='" + population + '\'' +
                ", density='" + density + '\'' +
                ", source='" + source + '\'' +
                ", military=" + military +
                ", incorporated=" + incorporated +
                ", timezone='" + timezone + '\'' +
                ", ranking='" + ranking + '\'' +
                ", zips='" + zips + '\'' +
                '}';
    }
}
