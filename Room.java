import java.io.PrintWriter;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Random;
import java.util.Set;

// a class to represent a room in a virtual world
public class Room {

    private boolean isSolid; // used to determine if room can be accessed
    private Hashtable<String, PrintWriter> users;
    private String description;
    // private Hashtable<Item, Integer> items; can be used if this room had items

    public Room() {
        Random r = new Random();
        isSolid = (r.nextInt(5) == 0); // 20% probability of room being solid
        users = new Hashtable<>();
        // give each room an arbitrary description (proof-of-concept)
        description = "#" + System.currentTimeMillis() * r.nextDouble();
    }

    // add a user to this room
    public void addUser(String name, PrintWriter out) {
        users.put(name, out);
    }

    // remove a user from this room
    public void removeUser(String name) {
        users.remove(name);
    }

    // return all usernames in this room for say commands
    public Set<String> getNames() {
        return users.keySet();
    }

    // return PrintWriter for a username for tell commands
    public PrintWriter getWriter(String name) {
        return users.get(name);
    }

    // return all PrintWriters for output of say commands
    public Collection<PrintWriter> getWriters() {
        return users.values();
    }

    // Output basic room data
    public String getDescription() {
        String result = "You have entered room " + description + ". ";
        result += "These users are in this room: ";
        for(String name : getNames()) {
            result += ("(" + name + ") ");
        }
        result += ".";
        return result;
    }

    public boolean isSolid() {
        return isSolid;
    }
}
