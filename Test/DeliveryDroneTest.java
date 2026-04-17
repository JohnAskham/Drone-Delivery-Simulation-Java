package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import Drones.DeliveryDrone;
import Drones.DroneState;
import Drones.HighSpeedDrone;
import Objects.Item;
import Objects.Order;
import Objects.Warehouse;

public class DeliveryDroneTest {
    private HighSpeedDrone highSpeedDrone;
    private ArrayList<Order> fulfilledOrders;
    private ArrayList<Order> AvailableOrders;
    private ArrayList<Item> items;
    private double initialRevenue;
    private LocalDateTime currentTime;



    @Before
    public void setUp() {
        highSpeedDrone = new HighSpeedDrone(1, 54.0, 20, 11, 127, 0, 1.5, 0.8, 15.0, 15.0);

        Map<String, Integer> Stock = new HashMap<>();
        Stock.put("Loafers", 0);  // Current stock is 0
        Stock.put("Tank top", 5); // Current stock is 5
        Warehouse warehouse = new Warehouse("Slums Warehouse", 10.5, 24.0, Stock);


        fulfilledOrders = new ArrayList<>();
        items = new ArrayList<>();
        items.add(new Item("Lemon", 10.0,1));
        initialRevenue = 100.0;
        currentTime = LocalDateTime.now();
    }

    @Test
    public void testDeliverOrder() {

        Order order = new Order(currentTime, 1.0, 1.0, "Lemon");

        highSpeedDrone.setOrder(order);

        // Simulate multiple deliveries to reach the 5th delivery
        for (int i = 0; i < 4; i++) {
            highSpeedDrone.deliverOrder(highSpeedDrone, fulfilledOrders, items, initialRevenue, currentTime);
        }

        // Perform the actual test on the 5th delivery
        double updatedRevenue = highSpeedDrone.deliverOrder(highSpeedDrone, fulfilledOrders, items, initialRevenue, currentTime);

        assertEquals(1, fulfilledOrders.size()); // Check if the order was added to fulfilledOrders
        assertTrue(fulfilledOrders.contains(order)); // Check if the correct order was added
        assertEquals(initialRevenue + 1, updatedRevenue, 0.01); // Check if revenue was updated correctly 

        // Check if the drone state is updated
        assertEquals(DroneState.IN_TRANSIT_HQ, highSpeedDrone.getState());
    }

    @Test
    public void testAssignOrder() {
        Order order = new Order(currentTime, 1.0, 1.0, "Lemon");
        AvailableOrders = new ArrayList<>();
        AvailableOrders.add(order);

        assertTrue(AvailableOrders.contains(order)); // If it got the order

        highSpeedDrone.assignOrder(highSpeedDrone, order, AvailableOrders);

        // Check that the order is removed from AvailableOrders
        assertFalse(AvailableOrders.contains(order));

        // Verify the order is assigned to the drone
        assertEquals(order, highSpeedDrone.getOrder());

        // Verify the drone's state
        assertEquals(DroneState.IN_TRANSIT_WH, highSpeedDrone.getState());
    }


    @Test
    public void testDistanceBetweenDrones() {
        HighSpeedDrone drone1= new HighSpeedDrone(1, 54.0, 20, 11, 127, 0, 1.5, 0.8, 12.0, 12.0);
        HighSpeedDrone drone2 = new HighSpeedDrone(1, 54.0, 20, 11, 127, 0, 1.5, 0.8, 12.0, 12.0);
        HighSpeedDrone drone3 = new HighSpeedDrone(1, 54.0, 20, 11, 127, 0, 1.5, 0.8, 1.0, 2.0);

        // Same position so should be true
        assertTrue(drone1.distanceBetweenDrones(drone2));

        // Far away from each other so should be false
        assertFalse(drone1.distanceBetweenDrones(drone3));

    }

    @Test
    public void testFetchOrder() {
        Map<String, Integer> stock = new HashMap<>();
        stock.put("Lemon", 1);  // Stock available
        Warehouse warehouse = new Warehouse("Slums Warehouse", 10.5, 24.0, stock);

        Order order = new Order(currentTime, 1.0, 1.0, "Lemon");
        highSpeedDrone.setOrder(order);

        // Fetch order 5 times
        for (int i = 0; i < 4; i++) {
            highSpeedDrone.fetchOrder(warehouse, highSpeedDrone, items);
            assertEquals(DroneState.DOCKED, highSpeedDrone.getState());  // Check that state does not change prematurely
        }

        // 5th fetch should trigger the state change
        highSpeedDrone.fetchOrder(warehouse, highSpeedDrone, items);

        // Check if the drone is in transit to the order
        assertEquals(DroneState.IN_TRANSIT_ORDER, highSpeedDrone.getState());
    }

    @Test
    public void testGetCapacity() {
        assertEquals(127, highSpeedDrone.getCapacity());

    }

    @Test
    public void testGetCarry() {
        assertFalse(highSpeedDrone.getCarry());

    }

    @Test
    public void testGetOrder() {
        Order order = new Order(currentTime, 1.0, 1.0, "Lemon");
        highSpeedDrone.setOrder(order);

        assertEquals(order, highSpeedDrone.getOrder());

    }

    @Test
    public void testIsAtWarehouse() {
        highSpeedDrone.setX(10.5);
        highSpeedDrone.setY(24.0);

        // Location at warehouse
        assertTrue(highSpeedDrone.isAtWarehouse(highSpeedDrone.getX(), highSpeedDrone.getY()));
        
        // Location not at warehouse
        assertFalse(highSpeedDrone.isAtWarehouse(0.0, 0.0));

    }

    @Test
    public void testIsCarryingItem() {
        assertFalse(highSpeedDrone.isCarryingItem());

    }

    @Test
    public void testLinesIntersection() {
        HighSpeedDrone drone1 = new HighSpeedDrone(1, 54.0, 20, 11, 127, 0, 1.5, 0.8, 10, 10);
        HighSpeedDrone drone2 = new HighSpeedDrone(1, 54.0, 20, 11, 127, 0, 1.5, 0.8, 1.0, 10.0);

        // Should not intersect as they are at the same position (no movement)
        assertFalse(DeliveryDrone.linesIntersection(drone1, drone2));

        // Simulate movement and test intersection
        drone1.setPrevX(1.0);
        drone1.setPrevY(1.0);
        drone2.setPrevX(10.0);
        drone2.setPrevY(1.0);

        // Should intersect now based on their previous and current positions
        assertTrue(DeliveryDrone.linesIntersection(drone1, drone2));

        drone1.setPrevX(10.0);
        drone1.setPrevY(10.0);
        drone2.setPrevX(14.0);
        drone2.setPrevY(14.0);

        // Should not intersect as their paths do not cross
        assertFalse(DeliveryDrone.linesIntersection(drone1, drone2));


        drone1.setPrevX(1.0);
        drone1.setPrevY(1.0);
        drone1.setX(2.0); // Move drone1 slightly
        drone1.setY(2.0);
        drone2.setPrevX(2.0);
        drone2.setPrevY(1.0);
        drone2.setX(1.0); // Move drone2 slightly
        drone2.setY(2.0);

        // Should intersect now based on their previous and current positions
        assertTrue(DeliveryDrone.linesIntersection(drone1, drone2));

        drone1.setPrevX(1.0);
        drone1.setPrevY(1.0);
        drone1.setX(4.0);
        drone1.setY(4.0);
        drone2.setPrevX(5.0);
        drone2.setPrevY(5.0);
        drone2.setX(8.0);
        drone2.setY(8.0);

        // Should not intersect as their paths do not cross
        assertFalse(DeliveryDrone.linesIntersection(drone1, drone2));
    }


    @Test
    public void testSetCapacity() {
        highSpeedDrone.setCapacity(100);
        assertEquals(100, highSpeedDrone.getCapacity());

    }

    @Test
    public void testSetCarry() {
        highSpeedDrone.setCarry(true);
        assertTrue(highSpeedDrone.getCarry());

    }

    @Test
    public void testSetCarry2() {
        highSpeedDrone.setCarry(false);
        assertFalse(highSpeedDrone.getCarry());


    }

    @Test
    public void testSetOrder() {
        Order order = new Order(currentTime, 1.0, 1.0, "Lemon");
        highSpeedDrone.setOrder(order);
        assertEquals(order, highSpeedDrone.getOrder());

    }

}
