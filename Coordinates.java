import java.util.Random;

// a class to store spatial data
public class Coordinates {

    private int max;
    private int x; // east-west axis
    private int y; // north-south axis
    private int z; // up-down axis

    // Create random coordinates for a user to spawn at, 
    // making sure that the coordinates do not exceed the dungeon's dimension
    public Coordinates(Dungeon d) {
        Random r = new Random();
        this.max = d.getSize();
        this.x = r.nextInt(max);
        this.y = r.nextInt(max);
        this.z = r.nextInt(max);
    }

    // Copy constructor
    public Coordinates(Coordinates c) {
        max = c.max;
        x = c.x;
        y = c.y;
        z = c.z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    // return coordinates one step to the east/west/north/south/up/down
    // if a user oversteps the dungeon's bounds, 
    // an ArrayOutOfBoundsException is avoided by transporting the user
    // to the other end of the dungeon
    public Coordinates getNewCoordinates(String direction) {
        Coordinates c = new Coordinates(this);
        if (direction.equals("east")) {
            c.x = Math.floorMod(this.x + 1, max);
        }
        else if (direction.equals("west")) {
            c.x = Math.floorMod(this.x - 1, max);
        }
        else if (direction.equals("north")) {
            c.y = Math.floorMod(this.y + 1, max);
        }
        else if (direction.equals("south")) {
            c.y = Math.floorMod(this.y - 1, max);
        }
        else if (direction.equals("up")) {
            c.z = Math.floorMod(this.z + 1, max);
        }
        else if (direction.equals("down")) {
            c.z = Math.floorMod(this.z - 1, max);
        }
        return c;
    }
}
