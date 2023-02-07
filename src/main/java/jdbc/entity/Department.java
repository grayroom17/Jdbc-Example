package main.java.jdbc.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class Department {
    Long id;
    String name;
    String city;
    Long locationId;

    public Department() {
    }

    public Department(Long id, String name, String city, Long locationId) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.locationId = locationId;
    }

    public Department(String name, String city, Long locationId) {
        this.name = name;
        this.city = city;
        this.locationId = locationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "Department{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", city='" + city + '\'' +
               ", locationId=" + locationId +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(city, that.city) && Objects.equals(locationId, that.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, city, locationId);
    }
}
