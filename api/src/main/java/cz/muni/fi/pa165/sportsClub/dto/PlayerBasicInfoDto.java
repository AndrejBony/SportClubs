package cz.muni.fi.pa165.sportsClub.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.*;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cz.muni.fi.pa165.sportsClub.rest.serializers.LocalDateDeserializer;
import cz.muni.fi.pa165.sportsClub.rest.serializers.LocalDateSerializer;

/**
 * Created by Simon on 30.12.2016.
 */

public class PlayerBasicInfoDto {
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Min(0)
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerBasicInfoDto)) return false;

        PlayerBasicInfoDto that = (PlayerBasicInfoDto) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
