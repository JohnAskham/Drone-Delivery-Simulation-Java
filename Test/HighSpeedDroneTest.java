package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import Drones.DroneState;
import Drones.HighSpeedDrone;
import Objects.Order;

public class HighSpeedDroneTest {
    private HighSpeedDrone highSpeedDrone;

    @Before
    public void setUp() {

        highSpeedDrone = new HighSpeedDrone(1, 54.0, 20, 11, 127, 0, 1.5, 0.8, 15.0, 15.0);

    }




    @Test // tests damage and accelerated burst for the highspeed drone.
    public void testDefense() {

        // Scenario when drone dodges the attack
        highSpeedDrone.setOrder(new Order(LocalDateTime.now(), 1.0, 1.0, "Lemon"));
        highSpeedDrone.setAccelerationburst(false); // activets when high speed drone dodges
        highSpeedDrone.setDodgeChance(1);
        highSpeedDrone.defense(10);
        assertEquals(20, highSpeedDrone.getHitPoints());


        //Scenario when drone doesn't dodge the attack
        highSpeedDrone.setDodgeChance(0.0);
        highSpeedDrone.setAccelerationburst(false);
        highSpeedDrone.defense(10);
        assertEquals(10, highSpeedDrone.getHitPoints());
    }

    @Test
    public void testGetCapacity() {
        HighSpeedDrone drone = new HighSpeedDrone(1, 54.0, 50, 11, 127, 0, 1.5, 0.8, 15.0, 15.0);
        assertEquals(127, drone.getCapacity());
    }

    @Test
    public void testGetDodgeChance() {
        HighSpeedDrone drone = new HighSpeedDrone(1, 54.0, 50, 11, 127, 0, 1.5, 0.8, 15.0, 15.0);
        assertEquals(0.8, drone.getDodgeChance(), 0.01);
    }

    @Test
    public void testGetSpeedMultiplier() {
        HighSpeedDrone drone = new HighSpeedDrone(1, 54.0, 50, 11, 127, 0, 1.5, 0.8, 15.0, 15.0);
        assertEquals(1.5, drone.getSpeedMultiplier(), 0.01);

    }

    @Test
    public void testSetDodgeChance() {
        HighSpeedDrone drone = new HighSpeedDrone(1, 54.0, 50, 11, 127, 0, 1.5, 0.8, 15.0, 15.0);
        drone.setDodgeChance(0.9);
        assertEquals(0.9, drone.getDodgeChance(), 0.01);


    }

    @Test
    public void testSetSpeedMultiplier() {
        HighSpeedDrone drone = new HighSpeedDrone(1, 54.0, 50, 11, 127, 0, 1.5, 0.8, 15.0, 15.0);
        drone.setSpeedMultiplier(2);
        assertEquals(2, drone.getSpeedMultiplier(), 0.01);

        

    }

    @Test
    public void testSpeedBurst() {
        HighSpeedDrone drone = new HighSpeedDrone(1, 54.0, 20, 11, 127, 0, 1.5, 0.8, 15.0, 15.0);
        drone.setOrder(new Order(LocalDateTime.now(), 1.0, 1.0, "Lemon"));

        // Makes sure the drone doesn't dodge the attack
        drone.setDodgeChance(0);
        boolean result = drone.speedBurst();

        // Check the outcome of speedBurst matches the expected outcome
        if (drone.getDodgeChance() > 0.2) {
            assertTrue(result);
            assertEquals(DroneState.IN_TRANSIT_ORDER, drone.getState());
            assertTrue(drone.isAccelerationburst());
        } else {
            assertFalse(result);
        }
    }

    @Test
    public void testSpeedMultiplier() {
        HighSpeedDrone drone = new HighSpeedDrone(1, 54.0, 20, 11, 127, 0, 1.5, 0.8, 15.0, 15.0);
        assertEquals(true, drone.speedMultiplier());
        
    }
}
