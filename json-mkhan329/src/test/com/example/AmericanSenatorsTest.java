package com.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//It may be a good idea to rename/refactor depending on the focus of your assignment.
public class AmericanSenatorsTest {
    ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    File file = new File("src/main/resources/senate.json");
    AmericanSenators senators = objectMapper.readValue(file, AmericanSenators.class);
    List<Senator> senate = senators.getSenators();
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public AmericanSenatorsTest() throws IOException {
    }

    @Test
    public void testSenatorsByPartyNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.getSenatorsByParty(senate, null);
    }

    @Test
    public void testSenatorsByPartyEmpty() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.getSenatorsByParty(senate, "");
    }

    @Test
    public void testSenatorsByPartyNullSenate() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.getSenatorsByParty(null, "anythingliterally");
    }

    @Test
    public void testSenatorsByPartyEmptySenate() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.getSenatorsByParty(new ArrayList<Senator>(), "anythingliterally");
    }

    @Test
    public void testSenatorsByStateNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.getSenatorsByState(senate, null);
    }

    @Test
    public void testSenatorsByStateEmpty() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.getSenatorsByState(senate, "");
    }

    @Test
    public void testSenatorsByStateNullSenate() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.getSenatorsByState(null, "!@#$%anythingliterally!@#$%");
    }

    @Test
    public void testSenatorsByStateEmptySenate() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.getSenatorsByState(new ArrayList<Senator>(), "!@#$%anythingliterally!@#$%");
    }

    @Test
    public void testSenatorsByNameNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.getSenatorsByName(senate, null);
    }

    @Test
    public void testSenatorsByNameEmpty() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.getSenatorsByName(senate, "");
    }

    @Test
    public void testSenatorsByNameNullSenate() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.getSenatorsByName(null, "!@#$%anythingliterally!@#$%");
    }

    @Test
    public void testSenatorsByNameEmptySenate() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.getSenatorsByName(new ArrayList<Senator>(), "!@#$%anythingliterally!@#$%");
    }

    @Test
    public void testSenatorsByTermYearNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.getSenatorsByTermYear(senate, null);
    }

    @Test
    public void testSenatorsByTermYearNullSenate() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.getSenatorsByTermYear(null, -1011231230);
    }

    @Test
    public void testSenatorsByTermYearEmptySenate() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.getSenatorsByTermYear(new ArrayList<Senator>(), -12011230);
    }

    @Test
    public void testCountPartyByGenderNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.countPartyByGender(senate, null, null);
    }

    @Test
    public void testCountPartyByGenderEmpty() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.countPartyByGender(senate, "", "");
    }

    @Test
    public void testCountPartyByGenderNullSenate() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.countPartyByGender(null, "literallyanything!@#!#&(", "literallyanything!@#!#&(");
    }

    @Test
    public void testCountPartyByGenderEmptySenate() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        senators.countPartyByGender(new ArrayList<Senator>(), "literallyanything!@#!#&(", "literallyanything!@#!#&(");
    }

    @Test
    public void testSortByAgeNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        AmericanSenators.sortSenatorsByAge(null);
    }

    @Test
    public void testSortByAgeEmpty() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        AmericanSenators.sortSenatorsByAge(new ArrayList<Senator>());
    }

    @Test
    public void testSortByTermNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        AmericanSenators.sortSenatorsByTerm(null);
    }

    @Test
    public void testSortByTermEmpty() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        AmericanSenators.sortSenatorsByTerm(new ArrayList<Senator>());
    }

    @Test
    public void testSortByNameNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        AmericanSenators.sortSenatorsByName(null);
    }

    @Test
    public void testSortByNameEmpty() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Inputs cannot be null or empty");
        AmericanSenators.sortSenatorsByName(new ArrayList<Senator>());
    }

    @Test
    public void initialDataTest() {
        assert(senate.size() == 100);
    }

    @Test
    public void testSenatorsByPartyRepublican() {
        assert(senators.getSenatorsByParty(senate, "Republican").size() == 50);
    }

    @Test
    public void testSenatorsByPartyDemocrat() {
        assert(senators.getSenatorsByParty(senate, "Democrat").size() == 48);
    }

    @Test
    public void testSenatorsByPartyIndependent() {
        assert(senators.getSenatorsByParty(senate, "Independent").size() == 2);
    }

    @Test
    public void testSenatorsByState() {
        assert(senators.getSenatorsByState(senate, "Hawaii").size() == 2);
    }

    @Test
    public void testSenatorsByFirstName() {
        assert(senators.getSenatorsByName(senate, "Michael").size() == 2);
    }

    @Test
    public void testSenatorsByLastName() {
        assert(senators.getSenatorsByName(senate, "Burr").size() == 1);
    }

    @Test
    public void testSenatorsByFullName() {
        assert(senators.getSenatorsByName(senate, "Bernard Sanders").size() == 1);
    }

    @Test
    public void testSenatorsByTermCurrent() {
        assert(senators.getSenatorsByTermYear(senate, 2021).size() == 100);
    }

    @Test
    public void testSenatorsByTermFuture() {
        assert(senators.getSenatorsByTermYear(senate, 2027).size() == 33);
    }

    @Test
    public void testSenatorsByTermPast() {
        assert(senators.getSenatorsByTermYear(senate, 2017).size() == 31);
    }

    @Test
    public void testSenatorsByTermWayPast() {
        assert(senators.getSenatorsByTermYear(senate, 2001).size() == 0);
    }

    @Test
    public void testSenatorsByTermWayFuture() {
        assert(senators.getSenatorsByTermYear(senate, 2031).size() == 0);
    }

    @Test
    public void testCountPartyGenderRepublicanFemales() {
        assert(senators.countPartyByGender(senate, "Republican", "Female") == 8);
    }

    @Test
    public void testCountPartyGenderDemocratFemales() {
        assert(senators.countPartyByGender(senate, "Democrat", "Female") == 16);
    }

    @Test
    public void testCountPartyGenderIndependentFemales() {
        assert(senators.countPartyByGender(senate, "Independent", "Female") == 0);
    }

    @Test
    public void testCountPartyGenderRepublicanMale() {
        assert(senators.countPartyByGender(senate, "Republican", "Male") == 42);
    }

    @Test
    public void testCountPartyGenderDemocratMale() {
        assert(senators.countPartyByGender(senate, "Democrat", "Male") == 32);
    }

    @Test
    public void testCountPartyGenderIndependentMale() {
        assert(senators.countPartyByGender(senate, "Independent", "Male") == 2);
    }

    @Test
    public void testSortSenateByAge() {
        assert(AmericanSenators.sortSenatorsByAge(senate).size() == 100 && (AmericanSenators.sortSenatorsByAge(senate).get(99).getName().equals("Jon Ossoff")));
    }

    @Test
    public void testSortSenateByTerm() {
        assert(AmericanSenators.sortSenatorsByTerm(senate).size() == 100 && (AmericanSenators.sortSenatorsByTerm(senate).get(99).getName().equals("Raphael Warnock")));
    }

    @Test
    public void testSortSenateByName() {
        assert(AmericanSenators.sortSenatorsByName(senate).size() == 100 && (AmericanSenators.sortSenatorsByName(senate).get(99).getName().equals("Todd Young")));
    }

    @Test
    public void testSenatorsByPartyDeepCopy() {
        assert(!senators.getSenatorsByParty(senate, "Republican").equals(senate));
    }

    @Test
    public void testSenatorsByStateDeepCopy() {
        assert(!senators.getSenatorsByState(senate, "Texas").equals(senate));
    }

    @Test
    public void testSenatorsByNameDeepCopy() {
        assert(!senators.getSenatorsByName(senate, "Bernard").equals(senate));
    }

    @Test
    public void testSenatorsByYearDeepCopy() {
        assert(!senators.getSenatorsByTermYear(senate, 2027).equals(senate));
    }

    @Test
    public void testSortSenatorsByAgeDeepCopy() {
        assert(!AmericanSenators.sortSenatorsByAge(senate).equals(senate));
    }

    @Test
    public void testSortSenatorsByNameDeepCopy() {
        assert(!AmericanSenators.sortSenatorsByName(senate).equals(senate));
    }

    @Test
    public void testSortSenatorsByTermDeepCopy() {
        assert(!AmericanSenators.sortSenatorsByTerm(senate).equals(senate));
    }

    @Test
    public void testGetSenatorsByStateBadInput() {
        assert(senators.getSenatorsByState(senate, "!@#$ILIKNOODLESOOPU8190481-@#$*)Echicken@)*()@$*").size() == 0);
    }

    @Test
    public void testGetSenatorsByPartyBadInput() {
        assert(senators.getSenatorsByParty(senate, "!@#$ILIKEchi423432423n@)*()@$*!@#&$*(&)").size() == 0);
    }

    @Test
    public void testGetSenatorsByNameBadInput() {
        assert(senators.getSenatorsByName(senate, "!@#$ILIKEchasdfsf!%^@&%#&@6#&@^#ken@)*()@$*").size() == 0);
    }

    @Test
    public void testGetSenatorsByYearBadInput() {
        assert(senators.getSenatorsByTermYear(senate, -199999).size() == 0);
    }

    @Test
    public void testCountPartyByGenderBadInput() {
        assert(senators.countPartyByGender(senate, "Bchasdfsf!%^@&%#&@6#&@^#kAHAH", "12382381-ASDKLHALSDH!(**#()@") == 0);
    }

    @Test
    public void testCountFirstLetterOfNames() {
        Map<Character, Integer> map = senators.countFirstNameLetters(senate);
        int count = 0;
        for(char a : map.keySet()) {
            count += map.get(a);
        }
        assert(count == 100 && map.get('A') == 3);
    }

}