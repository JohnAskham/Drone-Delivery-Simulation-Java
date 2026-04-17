package Drones;

import java.time.LocalDateTime;
import java.util.ArrayList;

import Objects.*;

public abstract class DeliveryDrone extends Drone {
    @SuppressWarnings("unused")
    private int capacity;
    private boolean carryingItem;
    private Order currentOrder;
    private Boolean carrying;
    private int fetchCounter = 0;
    private int deliverCounter = 0;
    private double revenueThisItem;
    private ArrayList<Order> deliveredList;
    private int originalhp;

    public DeliveryDrone(int id, double speed, int hitPoints, int value, int capacity, int type, double x, double y) {
        super(id, speed, hitPoints, value, type, x, y);
        this.capacity = capacity;
        this.currentOrder = null;
        this.carrying = false;
        this.carryingItem = false;
        deliveredList = new ArrayList<Order>();
        originalhp = hitPoints;
    }

    /*
     * Method to deliver order from drone to customer
     * @params drone, drone that is delivering
     * @params fulfilledOrders, list of orders that have been delivered
     * @params Items, list of all existing items
     * @params revenue, total amount of revenue made so far
     * @params currentTime, current time the simulation has reached to
     */
    public double deliverOrder(Drone drone, ArrayList<Order> fulfilledOrders, ArrayList<Item> Items, double revenue, LocalDateTime currentTime) {
        deliverCounter++;
        if (deliverCounter % 5 == 0) {
            Order deliveredOrder = currentOrder;
            fulfilledOrders.add(deliveredOrder);

            // Loping through list of items to find the value of delivered item, and adding
            // it to revenue
            for (Item item : Items) {
                if (item.getName().equals(currentOrder.getItem())) {
                    revenueThisItem += item.getValue();
                    if (currentOrder.getTimeStamp().getDayOfMonth() <= currentTime.getDayOfMonth()-2) {
                        revenueThisItem = revenueThisItem * 0.75;
                        deliveredList.add(currentOrder);
                        break;
                    }
                    deliveredList.add(currentOrder);
                    revenue += revenueThisItem;
                    break;

                }
            }

            // Changing state of drone
            carrying = false;
            currentOrder = null;
            System.out.println(
                    "DRONE ID " + drone.getID() + ": DELIVERED: " + deliveredOrder.getItem().toUpperCase());
            drone.setState(DroneState.IN_TRANSIT_HQ);
            System.out.println("DRONE ID " + drone.getID() + ": TRANSIT TO HQ");
        }
        return revenue;
    }
    /*
     * Method to check for intersection between two drones pertaining to extension 6
     * @params drone1, the first drone
     * @params drone2, the second drone
     */
    public static boolean linesIntersection(DeliveryDrone drone1, DeliveryDrone drone2) {
        // Get the previous position of the drones
        double[] c0 = { drone1.getPrevX(), drone1.getPrevY() };
        double[] c1 = { drone2.getPrevX(), drone2.getPrevY() };
        // Calculate movement vectors
        double[] v0 = { drone1.getX() - drone1.getPrevX(), drone1.getY() - drone1.getPrevY() };
        double[] v1 = { drone2.getX() - drone2.getPrevX(), drone2.getY() - drone2.getPrevY() };
        double r0 = 0.0025; // Effective radius for collision detection (half of 5 meters)
        double r1 = 0.0025;

        double[] s = { c0[0] - c1[0], c0[1] - c1[1] };
        double[] v = { v0[0] - v1[0], v0[1] - v1[1] };
        // Define radii for collision
        double r = r0 + r1;

        // Calculate coefficients for the quadratic equation
        double a = dotProduct(v, v);
        double b = dotProduct(v, s);
        double c = dotProduct(s, s) - r * r;

        double discriminant = b * b - a * c;
        return (discriminant > 0);
             // No real roots, the spheres do not intersect
        }


    private static double dotProduct(double[] v1, double[] v2) {
        return v1[0] * v2[0] + v1[1] * v2[1];
    }

    /*
     * Fetches an order from a warehouse and hands it to the drone
     * @params warehouse, the warehouse being fetched from
     * @params drone, the drone fetching
     * @params items, list of all items
     */
    public void fetchOrder(Warehouse warehouse, DeliveryDrone drone, ArrayList<Item> items) {
        fetchCounter++;
        if (fetchCounter % 5 == 0) {
            Item itemToFetch = warehouse.getItemByName(currentOrder.getItem(), items);
            warehouse.retrieveItem(itemToFetch, drone);
            drone.setState(DroneState.IN_TRANSIT_ORDER);
        }
    }

    /*
     * Method to check distance between two drones
     * @params otherDrone, the other drone location being checked
     */
    public boolean distanceBetweenDrones(DeliveryDrone otherDrone) {
        // Calculates the euclidean distance between two drones
        double distance = Math
                .sqrt(Math.pow(this.getX() - otherDrone.getX(), 2) + Math.pow(this.getY() - otherDrone.getY(), 2));

        // Checks if the drones are within 5 meters and not at a warehouse
        if (distance < 0.005 && !isAtWarehouse(this.getX(), this.getY())
                && !isAtWarehouse(otherDrone.getX(), otherDrone.getY())) {
            return true;
        }
        return false;
    }
    /*
     * Method to assign oldest order to a drone
     * @params drone, drone being assigned an order to
     * @params oldestOrder, oldest currently existing order
     * @params AvailableOrders, list of all available orders
     */
    public void assignOrder(Drone drone, Order oldestOrder, ArrayList<Order> AvailableOrders) {
        if (oldestOrder != null) {
            currentOrder = oldestOrder;
            System.out.println("DRONE ID " + drone.getID() + ": TRANSIT");
            drone.setState(DroneState.IN_TRANSIT_WH);
            AvailableOrders.remove(oldestOrder);
        }
        return;
    }

    /*
     *Method to check if a drone has reached a warehouse
     *@params x, the x value being checked against warehouse x coordinate
     *@params y, the y value being checked against warehouse y coordinate
     */
    public static boolean isAtWarehouse(double x, double y) {
        return (x == 10.5 && y == 24.0) ||
                (x == 18.0 && y == 11.1) ||
                (x == 4.5 && y == 3.0) ||
                (x == 15 && y == 15) ||
                (x == 23.4 && y == 18.0);
    }

    public abstract int getCapacity();

    /*
     * Abstract defense method, meant to act differently according to what type of delivery drone
     */
    public abstract void defense(int damage);

    public abstract boolean speedBurst();

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isCarryingItem() {
        return carryingItem;
    }

    public void setCarry(boolean carrying) {
        this.carrying = carrying;
    }

    public void setOrder(Order order) {
        this.currentOrder = order;
    }

    public Order getOrder() {
        return currentOrder;
    }

    public void setCarry(Boolean carrying) {
        this.carrying = carrying;
    }

    public Boolean getCarry() {
        return carrying;
    }

    public ArrayList<Order> getDeliverList() {
        return deliveredList;
    }
    
    public int getOriginalHP() {
        return originalhp;
    }

    public void printList() {
        for (Order order : deliveredList) {
            System.out.println("ITEM: " + order.getItem() + " DATE ORDERED: " + order.getTimeStamp());
        }
    }
}
