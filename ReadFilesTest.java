
import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
//import StaticMethods.ReadFiles;
import org.junit.Before;
import org.junit.Test;

import Drones.DeliveryDrone;
import Objects.Crime;
import Objects.Item;
import Objects.Order;
import Objects.Warehouse;

public class ReadFilesTest {
    

    private List<DeliveryDrone> drones;
    private List<Order> orders;
    private List<Item> items;
    private List<Warehouse> warehouses;
    private List<Crime> crimes;

    @Before
    public void setUp() {
    
        ReadFiles filereader = new ReadFiles();
        drones = filereader.droneReadFile();
        orders = filereader.orderReadFile();
        items = filereader.itemReadFile();
        warehouses = filereader.warehouseReadFile();   
        try {
            crimes = filereader.crimeReadFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

}

    @Test
    public void testDrone() {
        assertTrue(true);
    }



    @Test
    public void testDroneReadFile() {
        ReadFiles filereader = new ReadFiles();
        ArrayList<DeliveryDrone> drones = filereader.droneReadFile();
        assertEquals(160, drones.size());
        assertEquals(11, drones.get(0).getCapacity());
        assertEquals(13, drones.get(1).getCapacity()); 
        
    }


    @Test
    public void testOrderReadFile() {
        ReadFiles filereader = new ReadFiles();
        ArrayList<Order> orders = filereader.orderReadFile();
        assertEquals(2500, orders.size());

        assertEquals("Leather conditioner", orders.get(0).getItem());
        assertEquals(6.68, orders.get(1).getX(), 0.01);
    }

    @Test
    public void testItemReadFile() {
        ReadFiles filereader = new ReadFiles();
        ArrayList<Item> items = filereader.itemReadFile();
        assertEquals(148, items.size());

        assertEquals("Yoga sock", items.get(1).getName());
        assertEquals(3.6, items.get(1).getWeight(), 0.01);
    }

    @Test
    public void testWarehouseReadFile() {
        ReadFiles filereader = new ReadFiles();
        ArrayList<Warehouse> warehouses = filereader.warehouseReadFile();
        assertEquals(4, warehouses.size());

        assertEquals("Slums Warehouse", warehouses.get(0).getName());
        assertEquals(10.5, warehouses.get(0).getX(), 0.01);
        
        
    }

    @Test
    public void testCrimeReadFile() {
        
        try {
            ReadFiles filereader = new ReadFiles();
            ArrayList<Crime> crimes;
            crimes = filereader.crimeReadFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        assertEquals(4, crimes.size());
        assertEquals(0.0135, crimes.get(1).getSpawnChance(), 0.01);
        assertEquals("New Holland", crimes.get(1).getCrimeDistrictName());
        
    }


}
