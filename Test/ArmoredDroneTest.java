package Test;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import Drones.ArmoredDrone;
import Drones.GatlingDrone;



public class ArmoredDroneTest {
    private ArmoredDrone armoredDrone;
    private GatlingDrone gatlingdrone;

    @Before
    public void setUp() {
        gatlingdrone = new GatlingDrone(1, 100, 100, 100, 10, 0, 15, 15, false, false);
        armoredDrone = new ArmoredDrone(1, 54.0, 20, 11, 127, 1, 1.0, 15.0, 15.0);
    }

    @Test
    public void testDefense() {
            
        ArmoredDrone drone = new ArmoredDrone(1, 54.0, 50, 11, 127, 1, 0, 15.0, 15.0);        
        drone.defense(40);
        assertEquals(10, drone.getHitPoints());
    }

    @Test
    public void testArmor() {
        assertEquals(1.0, armoredDrone.getArmor(), 0.00);
        assertEquals(20, armoredDrone.getHitPoints());
        armoredDrone.defense(20);
        assertEquals(20, armoredDrone.getHitPoints());
        armoredDrone.setArmorChance(0.0);
        armoredDrone.defense(20);
        assertEquals(0, armoredDrone.getHitPoints());
    }

    @Test
    public void testGetCapacity() {
        assertEquals(127, armoredDrone.getCapacity());
        
    }

    @Test
    public void testSetArmorChance() {
        armoredDrone.setArmorChance(2.0);
        assertEquals(2.0, armoredDrone.getArmor(), 0.01);
        
    }


    
}
