package student.adventure;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
/**
 * This class is used to describe rooms in the Krusty Krab JSON.
 * It includes the room name, description, items, directions, and methods to change the items in the room.
 */
public class Room {
    @JsonProperty("youtubeLink")
    private String youtubeLink;

    @JsonProperty("imageLink")
    private String imageLink;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("items")
    private List<String> items;

    @JsonProperty("directions")
    private List<Direction> directions;

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getItems() {
        return items;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public void removeItem(String item) {
        items.remove(item);
    }

    public List<Direction> getDirections() {
        return directions;
    }

    @Override
    public String toString() {
        return "\n" + description + "\n" + "From here, you can go: " + directions.toString() + "\n" + "Items visible: " + items.toString() + "\n";
    }

}
