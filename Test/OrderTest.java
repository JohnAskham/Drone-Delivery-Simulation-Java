package Test;

import static org.junit.Assert.assertEquals;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.Test;

import Objects.Order;
public class OrderTest {


    @Test
    public void testGetItem() {
        Order order = new Order(LocalDateTime.of(2008, 3, 12, 10, 0), 10.0, 20.0, "hammer");
        assertEquals("hammer", order.getItem());

    }

    @Test
    public void testGetTimeStamp() {
        Order order = new Order(LocalDateTime.of(2008, 3, 12, 10, 0), 10.0, 20.0, "hammer");
        assertEquals(LocalDateTime.of(2008, 3, 12, 10, 0), order.getTimeStamp());

    }

    @Test
    public void testGetX() {
        Order order = new Order(LocalDateTime.of(2008, 3, 12, 10, 0), 10.0, 20.0, "hammer");
        assertEquals(10.0, order.getX(), 0.0);

    }

    @Test
    public void testGetY() {
        Order order = new Order(LocalDateTime.of(2008, 3, 12, 10, 0), 10.0, 20.0, "hammer");
        assertEquals(20.0, order.getY(), 0.0);

    }

    @Test
    public void testOldestOrder() {
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order(LocalDateTime.of(2008, 3, 12, 11, 0), 10.0, 20.0, "Item1"));
        orders.add(new Order(LocalDateTime.of(2008, 3, 12, 8, 0), 15.0, 25.0, "Item2")); // Oldest order
        orders.add(new Order(LocalDateTime.of(2008, 3, 12, 10, 0), 20.0, 30.0, "Item3"));

        // Assert that the oldestOrder is the actual oldest order
        Order expectedOldestOrder = orders.get(1); // This is the order with the earliest timestamp
        assertEquals(expectedOldestOrder, Order.oldestOrder(orders));
    }
}
