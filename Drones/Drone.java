
package Drones;

public abstract class Drone {
    private int id;
    private double speed;
    public int hitPoints;
    private int value;
    private DroneState state;
    private double x, prevX;
    private double y, prevY;
    private int type;

    public Drone(int id, double speed, int hitPoints, int value, int type, double x, double y) {
        this.id = id;
        this.speed = speed;
        this.hitPoints = hitPoints;
        this.value = value;
        this.state = DroneState.DOCKED;
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.type = type;
    }
    /*
     * Move method for drones, to control which direction they go into by use of vectors
     * @params drone, which drone to move 
     * @params X, X value the drone is moving towards
     * @params Y, Y value the drone is moving towards
     */
    public void move(Drone drone, double X, double Y) {

        this.prevX = this.x;
        this.prevY = this.y;

        // Calculate distance vector
        double vectorX = X - this.x;
        double vectorY = Y - this.y;

        // Calculate euclidean distance
        double length = Math.sqrt(vectorX * vectorX + vectorY * vectorY);

        // if length is = 0, increase it by 5 meters, else the drone position will
        // become NAN
        if (length == 0) {
            length += 0.005;
        }

        // Calculate unit vector
        vectorX /= length;
        vectorY /= length;

        // Calculate distance per minute
        double distancePerMinute = (speed / 60.000);

        // If the actual distance to target is less than the distance per minute, move
        // the drone to the target
        double distanceToMove = Math.min(distancePerMinute, length);

        // Calculate new position
        double newX = this.x + vectorX * distanceToMove;
        double newY = this.y + vectorY * distanceToMove;

        // floors the values, to make a drone not enter an endless loop
        double truncatedLength = Math.floor(length * 1000) / 1000;
        double truncatedDistance = Math.floor(distanceToMove * 1000) / 1000;

        // Update the drone's position
        this.x = newX;
        this.y = newY;

        // Check if the drone has reached warehouse
        if (truncatedDistance >= truncatedLength && drone.getState() == DroneState.IN_TRANSIT_WH) {
            System.out.println("Drone ID " + drone.getID() + ": FETCHING");
            drone.setState(DroneState.FETCHING);

            // check if drone has reached order destination
        } else if (truncatedDistance >= truncatedLength && drone.getState() == DroneState.IN_TRANSIT_ORDER) {
            System.out.println("Drone ID " + drone.getID() + ": DELIVERING");
            drone.setState(DroneState.DELIVERING);

            // check if drone has reached hq
        } else if (truncatedDistance >= truncatedLength && drone.getState() == DroneState.IN_TRANSIT_HQ) {
            System.out.println("Drone ID " + drone.getID() + ": DOCKED");
            drone.setState(DroneState.DOCKED);

        }

    }

    public double getPrevX() {
        return prevX;
    }

    public double getPrevY() {
        return prevY;
    }


    public int getID() {
        return id;
    }

    public double getSpeed() {
        return speed;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getValue() {
        return value;
    }

    public int getType() {
        return type;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public DroneState getState() {
        return state;
    }

    public void setState(DroneState state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setPrevX(double prevX) {
        this.prevX = prevX;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setPrevY(double prevY) {
        this.prevY = prevY;
    }

    public void setType(int type) {
        this.type = type;
    }

}
