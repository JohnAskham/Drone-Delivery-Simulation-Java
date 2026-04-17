package Objects;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {
    private LocalDateTime dateTime;
    private double x;
    private double y;
    private String item;
    private static Order oldestOrder;

    public Order(LocalDateTime TimeStamp, double x, double y, String item) {
        this.dateTime = TimeStamp;
        this.x = x;
        this.y = y;
        this.item = item;
    }

    /*
     * method to look through all available orders and finding the oldest ones and returning it
     * @params AvailableOrders a list of all available orders
     */
    public static Order oldestOrder(ArrayList<Order> AvailableOrders) {
        if (AvailableOrders.isEmpty()) {
            return null;
        }
        oldestOrder = AvailableOrders.get(0);
        for (Order order : AvailableOrders) {
            if (order != null && order.getTimeStamp().isBefore(oldestOrder.getTimeStamp())) {
                oldestOrder = order;
            }
        }
        return oldestOrder;
    }

    public LocalDateTime getTimeStamp() {
        return dateTime;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getItem() {
        return item;
    }

}