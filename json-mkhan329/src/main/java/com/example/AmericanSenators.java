package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

public class AmericanSenators {
    @JsonProperty("objects")
    private List<Senator> senators;

    public List<Senator> getSenators() {
        return senators;
    }

    public void setSenators(List<Senator> s) {
        assert s != null || s.size() != 0 : ("Cannot have empty or null senator list");
        senators = s;
    }

    /**
     * Returns a list of senators in the given Political party.
     * @param senators the list of senators to filter from
     * @param party the given political party
     * @return the filtered list of senators
     */
    public static List<Senator> getSenatorsByParty(List<Senator> senators, String party) {
        if (party == null || senators == null) {
            throw new IllegalArgumentException("Inputs cannot be null or empty");
        }
        if (party.length() == 0 || senators.size() == 0) {
            throw new IllegalArgumentException("Inputs cannot be null or empty");
        }
        party = party.toLowerCase();
        List<Senator> senatorsByParty = new ArrayList<Senator>();
        for (Senator s : senators) {
            if (s.getParty().toLowerCase().equals(party)) {
                senatorsByParty.add(s);
            }
        }
        return senatorsByParty;
    }

    /**
     * Returns a list of senators in the given state
     * @param senators the list of senators to filter from
     * @param state the given state in the USA
     * @return the filtered list of senators
     */
    public static List<Senator> getSenatorsByState(List<Senator> senators, String state) {
        if (state == null || senators == null) {
            throw new IllegalArgumentException("Inputs cannot be null or empty");
        }
        if (state.length() == 0 || senators.size() == 0) {
            throw new IllegalArgumentException("Inputs cannot be null or empty");
        }
        state = state.toLowerCase();
        List<Senator> senatorsByState = new ArrayList<Senator>();
        for (Senator s : senators) {
            if (s.getState().toLowerCase().equals(state)) {
                senatorsByState.add(s);
            }
        }
        return senatorsByState;
    }

    /**
     * Returns a list of senators with the same name
     * @param senators the list of senators to filter from
     * @param name the given name (full name, first, or just last)
     * @return the filtered list of senators
     */
    public static List<Senator> getSenatorsByName(List<Senator> senators, String name) {
        if (name == null || senators == null) {
            throw new IllegalArgumentException("Inputs cannot be null or empty");
        }
        if (name.length() == 0 || senators.size() == 0) {
            throw new IllegalArgumentException("Inputs cannot be null or empty");
        }
        name = name.toLowerCase();
        List<Senator> senatorsByName = new ArrayList<Senator>();
        for (Senator s : senators) {
            if (s.getName().toLowerCase().contains(name)) {
                senatorsByName.add(s);
            }
        }
        return senatorsByName;
    }

    /**
     * Returns a list of senators whose term includes the given year
     * @param senators the list of senators to filter from
     * @param year the given year to check
     * @return the filtered list of senators
     */
    public static List<Senator> getSenatorsByTermYear(List<Senator> senators, Integer year) {
        if (year == null || senators == null) {
            throw new IllegalArgumentException("Inputs cannot be null or empty");
        }
        if (senators.size() == 0) {
            throw new IllegalArgumentException("Inputs cannot be null or empty");
        }
        List<Senator> senatorsByTerm = new ArrayList<Senator>();
        for (Senator s : senators) {
            int startYear = Integer.parseInt(s.getStartDate().substring(0, 4));
            int endYear = Integer.parseInt(s.getEndDate().substring(0, 4));
            if (year >= startYear && year <= endYear) {
                senatorsByTerm.add(s);
            }
        }
        return senatorsByTerm;
    }

    /**
     * Returns the number of people in a political party with the given gender
     * @param senators the list of senators to filter from
     * @param party the given political party
     * @param gender the given gender to check for
     * @return the number of senators
     */
    public static int countPartyByGender(List<Senator> senators, String party, String gender) {
        if (party == null || gender == null || senators == null) {
            throw new IllegalArgumentException("Inputs cannot be null or empty");
        }
        if (party.length() == 0 || gender.length() == 0 || senators.size() == 0) {
            throw new IllegalArgumentException("Inputs cannot be null or empty");
        }
        int count = 0;
        party = party.toLowerCase();
        gender = gender.toLowerCase();
        for (Senator s: senators) {
            if (s.getParty().toLowerCase().equals(party) && s.getGender().toLowerCase().equals(gender)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Sorts the given list of senators by age, from oldest to youngest.
     * @param senators the list of senators to sort.
     * @return the sorted list of senators
     */
    public static List<Senator> sortSenatorsByAge(List<Senator> senators) {
        if (senators == null) {
            throw new IllegalArgumentException("Inputs cannot be null or empty");
        }
        if (senators.size() == 0) {
            throw new IllegalArgumentException("Inputs cannot be null or empty");
        }
        List<Senator> senatorsByAge = new ArrayList<Senator>();
        senatorsByAge.addAll(senators);
        senatorsByAge.sort(new SortByAge());
        return senatorsByAge;
    }

    /**
     * Sorts the given list of senators by term year, from earliest start date to latest.
     * @param senators the list of senators to sort.
     * @return the sorted list of senators
     */
    public static List<Senator> sortSenatorsByTerm(List<Senator> senators) {
        if (senators == null) {
            throw new IllegalArgumentException("Inputs cannot be null or empty");
        }
        if (senators.size() == 0) {
            throw new IllegalArgumentException("Inputs cannot be null or empty");
        }
        List<Senator> senatorsByTerm = new ArrayList<Senator>();
        senatorsByTerm.addAll(senators);
        senatorsByTerm.sort(new SortByTerm());
        return senatorsByTerm;
    }

    /**
     * Sorts the given list of senators by last name in alphabetical order.
     * @param senators the list of senators to sort.
     * @return the sorted list of senators
     */
    public static List<Senator> sortSenatorsByName(List<Senator> senators) {
        if (senators == null) {
            throw new IllegalArgumentException("Inputs cannot be null or empty");
        }
        if (senators.size() == 0) {
            throw new IllegalArgumentException("Inputs cannot be null or empty");
        }
        List<Senator> senatorsByName = new ArrayList<Senator>();
        senatorsByName.addAll(senators);
        senatorsByName.sort(new SortByName());
        return senatorsByName;
    }


    /**
     * Create a map that tracks counts of how many times a name starts with a certain letter.
     * @param senators the list of senators to check.
     * @return the map from each letter to its count.
     */
    public static Map<Character, Integer> countFirstNameLetters(List<Senator> senators) {
        Map<Character, Integer> map = new HashMap();
        List<Senator> senators1 = sortSenatorsByName(senators);
        for (Senator s : senators1) {
            char letter = s.getName().charAt(0);
            map.put(letter, map.get(letter) == null ? 1 : 1 + map.get(letter));
        }
        return map;
    }

    /**
     * These 3 comparator classes below are utilized by the 3 sort methods above.
     */
    public static class SortByAge implements Comparator<Senator> {
        public int compare(Senator a, Senator b) {
            LocalDate ageA = LocalDate.parse(a.getBirthday());
            LocalDate ageB = LocalDate.parse(b.getBirthday());
            return ageA.compareTo(ageB);
        }
    }

    public static class SortByTerm implements Comparator<Senator> {
        public int compare(Senator a, Senator b) {
            LocalDate termA = LocalDate.parse(a.getStartDate());
            LocalDate termB = LocalDate.parse(b.getStartDate());
            return termA.compareTo(termB);
        }
    }

    public static class SortByName implements Comparator<Senator> {
        public int compare(Senator a, Senator b) {
            String nameA = a.getLastFirstName();
            String nameB = b.getLastFirstName();
            return nameA.compareTo(nameB);
        }
    }

}
