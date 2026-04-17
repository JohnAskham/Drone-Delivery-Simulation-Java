package Objects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Drones.DeliveryDrone;

public class Warehouse {
    private String name;
    private double x;
    private double y;
    public Map<String, Integer> stock;
    public Map<String, Integer> originalStock;

    public Warehouse(String name, double x, double y, Map<String, Integer> stock) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.stock = new HashMap<>(stock);
        this.originalStock = new HashMap<>(stock);

    }

    /*
     * Checks if a warehouse has 0 stock on any item and resupplies it
     */
    public void restockWarehouse() {
        // iterates though the Map, and check which stocks are 0
        for (Map.Entry<String, Integer> item : stock.entrySet()) {
            if (item.getValue() == 0) {
                // restocks key's which have a value of 0 to their original stock.
                stock.put(item.getKey(), originalStock.get(item.getKey()));

            }
        }
    }

    /*
     * Retrieves the designated item from warehouse and assigning it to a drone
     * @params item being the designated item
     * @parms drone being the assigned drone
     */
    public void retrieveItem(Item item, DeliveryDrone drone) {
        if (stock.keySet().contains(item.getName())) {
            if (stock.get(item.getName()) > 0) {
                stock.put(item.getName(), stock.get(item.getName()) - 1);
                drone.setCarry(true);

            } else {
                System.out.println("DRONE " + drone.getID() + ": FAILED TO FETCH ITEM DUE TO EMPTY STOCK");
            }
        }
    }

    /*
     * Retrieves an item from a list
     * @params itemName being the searched for item
     * @params items being the list of all items
     */
    public Item getItemByName(String itemName, ArrayList<Item> items) {
        for (Item item : items) {
            if (item.getName().equals(itemName)) {

                return item;
            }
        }
        return null;
    }

    /*
     * Checks if a warehouse has any left of the searched for item
     * @params itemName being the searched for item
     */
    public boolean hasItem(String itemName) {
        return stock.containsKey(itemName);
    }

    /*
     * Checks if a specific warehouse has the item
     * @params warehouse being the searched warehouse
     * @params itemName being the item searched for in a warehouse
     */
    public Warehouse warehouseHasItem(Warehouse warehouse, String itemName) {
        if (warehouse.hasItem(itemName)) {
            return warehouse;
        }
        return null;
    }

    /*
     * Prints out entire stock of a warehouse
     */
    public void printStock(Warehouse warehouse) {
        warehouse.getStock().forEach((String, Integer) -> {
            System.out.println("Name of product: " + String);
            System.out.println("Current Stock: " + Integer);
        });
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Map<String, Integer> getStock() {
        return stock;
    }

    public void setStock(Map<String, Integer> stock) {
        this.stock = stock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Map<String, Integer> getOriginalStock() {
        return originalStock;
    }

    public void setOriginalStock(Map<String, Integer> originalStock) {
        this.originalStock = originalStock;
    }

}
