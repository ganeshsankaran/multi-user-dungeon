import java.io.PrintWriter;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;

// a class to represent the virtual world
public class Dungeon {

    private Hashtable<String, PrintWriter> users; // users and writers to output information to
    private Room[][][] rooms; // 3D grid of Rooms
    private int size; // dimension of dungeon

    public Dungeon(int size) {
        this.users = new Hashtable<>();
        this.rooms = new Room[size][size][size];
        this.size = size;
        // individually construct rooms with uniformly distributed solid rooms
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                for(int k = 0; k < size; k++) {
                    rooms[i][j][k] = new Room();
                }
            }
        }
    }

    // add a user to the dungeon (upon connecting)
    public void addUser(String name, PrintWriter out) {
        users.put(name, out);
    }

    // remove a user from the dungeon (upon disconnecting)
    public void removeUser(String name) {
        users.remove(name);
    }

    public Hashtable<String, PrintWriter> getUsers() {
        return users;
    }

    // return all usernames in the dungeon when yelling at everyone
    public Set<String> getNames() {
        return users.keySet();
    }

    // return all PrintWriters in the dungeon to output yell dialogs to everyone
    public Collection<PrintWriter> getWriters() {
        return users.values();
    }

    // return a room in the 3D grid based on a Coordinate object
    public Room getRoom(Coordinates c) {
        return rooms[c.getX()][c.getY()][c.getZ()];
    }

    public int getSize() {
        return size;
    }
}
