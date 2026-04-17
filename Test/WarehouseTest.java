package Test;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import Drones.HighSpeedDrone;
import Objects.Item;
import Objects.Warehouse;



public class WarehouseTest {
    private Warehouse warehouse;



    @Before
    public void setUp() {
        // Creating the initial stock map with the desired values
        Map<String, Integer> initialStock = new HashMap<>();
        initialStock.put("Loafers", 0);  // Current stock is 0
        initialStock.put("Tank top", 5); // Current stock is 5

        // Initializing the Warehouse object with the initial stock map
        warehouse = new Warehouse("Slums Warehouse", 10.5, 24.0, initialStock);
        
        // Setting the original stock manually
        warehouse.getStock().put("Loafers", 0);  // Current stock is 0
        warehouse.getStock().put("Tank top", 5); // Current stock is 5
        warehouse.originalStock.put("Loafers", 12);  // Original stock was 12
        warehouse.originalStock.put("Tank top", 5);  // Original stock was 5
    }

    @Test
    public void testRestockWarehouse() {
        warehouse.restockWarehouse();
        assertEquals(12, warehouse.getStock().get("Loafers").intValue());
        assertEquals(5, warehouse.getStock().get("Tank top").intValue()); // Should remain the same
    }


    @Test
    public void testGetItemByName() {
        // Creating a list of items
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("Loafers", 12, 50));
        items.add(new Item("Tank top", 5, 20));

        // Fetching the item by name
        Item fetchedItem = warehouse.getItemByName("Loafers", items);
        assertEquals("Loafers", fetchedItem.getName());

    }

    @Test
    public void testGetName() {
        assertEquals("Slums Warehouse", warehouse.getName());
    }

    @Test
    public void testGetOriginalStock() {
        assertEquals(12, warehouse.getOriginalStock().get("Loafers").intValue());
        assertEquals(5, warehouse.getOriginalStock().get("Tank top").intValue());
    }

    @Test
    public void testGetStock() {
        assertEquals(0, warehouse.getStock().get("Loafers").intValue());
        assertEquals(5, warehouse.getStock().get("Tank top").intValue());

    }

    @Test
    public void testGetX() {
        assertEquals(10.5, warehouse.getX(), 0.0);
        
    }

    @Test
    public void testGetY() {
        assertEquals(24.0, warehouse.getY(), 0.0);

    }

    @Test
    public void testHasItem() {
        assertEquals(true, warehouse.hasItem("Loafers"));
        assertEquals(true, warehouse.hasItem("Tank top"));
        assertEquals(false, warehouse.hasItem("Sneakers"));

    }


    @Test
    public void testRetrieveItem() {
        Item item = new Item("Loafers",2,2);
        HighSpeedDrone drone = new HighSpeedDrone(1, 54.0, 20, 11, 127, 0, 1.5, 0.8, 15.0, 15.0);
        Warehouse warehouse = new Warehouse("Slums Warehouse", 10.5, 24.0, new HashMap<>());
        warehouse.getStock().put("Loafers", 3);  // Current stock is 3 
        
        // Before retrieval
        assertEquals(3, warehouse.getStock().get("Loafers").intValue());

        // // Perform the retrieval
        warehouse.retrieveItem(item, drone);

        // // After retrieval the item is reduced by 1
        assertEquals(2, warehouse.getStock().get("Loafers").intValue());
        assertEquals(true, drone.getCarry());  // Drone should be carrying the item
    }

    @Test
    public void testSetName() {
        warehouse.setName("Slums Warehouse");
        assertEquals("Slums Warehouse", warehouse.getName());
    }

    @Test
    public void testSetOriginalStock() {
        Map<String, Integer> newStock = new HashMap<>();
        newStock.put("Loafers", 12);
        newStock.put("Tank top", 5);
        warehouse.setOriginalStock(newStock);
        assertEquals(12, warehouse.getOriginalStock().get("Loafers").intValue());
        assertEquals(5, warehouse.getOriginalStock().get("Tank top").intValue());
    }

    @Test
    public void testSetStock() {
        Map<String, Integer> newStock = new HashMap<>();
        newStock.put("Loafers", 12);
        newStock.put("Tank top", 5);
        warehouse.setStock(newStock);
        assertEquals(12, warehouse.getStock().get("Loafers").intValue());
        assertEquals(5, warehouse.getStock().get("Tank top").intValue());
    }

    @Test
    public void testSetX() {
        warehouse.setX(10.5);
        assertEquals(10.5, warehouse.getX(), 0.0);


    }

    @Test
    public void testSetY() {
        warehouse.setY(24.0);
        assertEquals(24.0, warehouse.getY(), 0.0);

    }

    @Test
    public void testWarehouseHasItem() {
        assertEquals(true, warehouse.hasItem("Loafers"));
        assertEquals(true, warehouse.hasItem("Tank top"));
        assertEquals(false, warehouse.hasItem("Sneakers"));

    }
}
