package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is the senator class, which takes information about senator objects. This includes personal info such as:
 * name, birthday, gender, political party, state, term start and term end.
 */

public class Senator {

    public class Person{
        @JsonProperty("firstname")
        private String firstName;

        @JsonProperty("lastname")
        private String lastName;

        @JsonProperty("gender")
        private String gender;

        @JsonProperty("birthday")
        private String birthday;

        public String getName() {
            return firstName + " " + lastName;
        }

        public String getLastnameFirstName() {
            return lastName + " " + firstName;
        }

        public String getGender() {
            return gender;
        }

        public String getBirthday() {
            return birthday;
        }
    }

    @JsonProperty("person")
    private Person person;

    @JsonProperty("party")
    private String party;

    @JsonProperty("description")
    private String description;

    @JsonProperty("startdate")
    private String startDate;

    @JsonProperty("enddate")
    private String endDate;

    public String getParty() {
        return party;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getState() {
        return (description.split("\\s+"))[3];
    } //state is taken from last word of description ie "Senior Senator for Idaho" --> "Idaho"

    public String getName() {
        return person.getName();
    }

    public String getLastFirstName() {
        return person.getLastnameFirstName();
    }

    public String getGender() {
        return person.getGender();
    }

    public String getBirthday() {
        return person.getBirthday();
    }
}
