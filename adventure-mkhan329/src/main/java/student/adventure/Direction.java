package student.adventure;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * This class is used to hold information about the directions in a given room ie north, south, etc
 */
public class Direction {
    @JsonProperty("directionName")
    private String directionName;

    @JsonProperty("room")
    private String room;

    public String getDirectionName() {
        return directionName;
    }

    public String getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return directionName;
    }
}
