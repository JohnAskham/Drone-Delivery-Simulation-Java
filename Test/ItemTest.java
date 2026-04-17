package Test;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import Objects.Item;



public class ItemTest {
    @Test
    public void testGetName() {
        Item item = new Item("lemon", 1.0, 1.0);
        assertEquals("lemon", item.getName());
    }

    @Test
    public void testGetValue() {
        Item item = new Item("lemon",2.3,50);
        assertEquals(50, item.getValue(), 0.0);

    }

    @Test
    public void testGetWeight() {
        Item item = new Item("mug", 2.2, 60);
        assertEquals(2.2, item.getWeight(), 0.0);

    }
}
