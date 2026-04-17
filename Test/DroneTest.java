package Test;
import static org.junit.Assert.assertEquals;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import Drones.DroneState;
import Drones.HighSpeedDrone;
import Objects.Order;

public class DroneTest {
    private HighSpeedDrone highSpeedDrone;

    @Before
    public void setUp() {

        highSpeedDrone = new HighSpeedDrone(1, 54.0, 20, 11, 127, 0, 1.5, 0.8, 15.0, 15.0);

    }


    @Test
    public void testGetHitPoints() {    
        assertEquals(20, highSpeedDrone.getHitPoints());
    }

    @Test
    public void testGetID() {
        assertEquals(1, highSpeedDrone.getID());
    }

    @Test
    public void testGetId() {
        assertEquals(1, highSpeedDrone.getId());

    }

    @Test
    public void testGetPrevX() {
        assertEquals(15.0, highSpeedDrone.getPrevX(), 0.01);
    }

    @Test
    public void testGetPrevY() {
        assertEquals(15.0, highSpeedDrone.getPrevY(), 0.01);
    }

    @Test
    public void testGetSpeed() {
        assertEquals(54.0, highSpeedDrone.getSpeed(), 0.01);

    }

    @Test
    public void testGetState() {
        assertEquals(DroneState.DOCKED, highSpeedDrone.getState());
    }

    @Test
    public void testGetType() {
        assertEquals(0, highSpeedDrone.getType());

    }

    @Test
    public void testGetValue() {
        assertEquals(11, highSpeedDrone.getValue());

    }

    @Test
    public void testGetX() {
        assertEquals(15.0, highSpeedDrone.getX(), 0.01);

    }

    @Test
    public void testGetY() {
        assertEquals(15.0, highSpeedDrone.getY(), 0.01);

    }

    @Test
    public void testMove() {

        HighSpeedDrone drone = new HighSpeedDrone(1, 60.0, 50, 11, 127, 0, 1.5, 0.8, 1.0, 1.0);

        drone.move(drone,10.0, 10.0);

        // After calculating the distance vetor and normalizing it, the drone should move to the new position
        assertEquals(1.707, drone.getX(), 0.01);

        assertEquals(1.707, drone.getY(), 0.01);

    }

    @Test
    public void testSetHitPoints() {
        highSpeedDrone.setHitPoints(50);
        assertEquals(50, highSpeedDrone.getHitPoints());

    }

    @Test
    public void testSetId() {
        highSpeedDrone.setId(2);
        assertEquals(2, highSpeedDrone.getId());

    }

    @Test
    public void testSetPrevX() {
        highSpeedDrone.setPrevX(10.0);
        assertEquals(10.0, highSpeedDrone.getPrevX(), 0.01);
    }

    @Test
    public void testSetPrevY() {
        highSpeedDrone.setPrevY(10.0);
        assertEquals(10.0, highSpeedDrone.getPrevY(), 0.01);
    }

    @Test
    public void testSetSpeed() {
        highSpeedDrone.setSpeed(60.0);
        assertEquals(60.0, highSpeedDrone.getSpeed(), 0.01);
    }

    @Test
    public void testSetState() {
        highSpeedDrone.setState(DroneState.DELIVERING);
        assertEquals(DroneState.DELIVERING, highSpeedDrone.getState());

    }

    @Test
    public void testSetType() {
        highSpeedDrone.setType(1);
        assertEquals(1, highSpeedDrone.getType());

    }

    @Test
    public void testSetValue() {
        highSpeedDrone.setValue(20);
        assertEquals(20, highSpeedDrone.getValue());

    }

    @Test
    public void testSetX() {
        highSpeedDrone.setX(10.0);
        assertEquals(10.0, highSpeedDrone.getX(), 0.01);
    }

    @Test
    public void testSetY() {    
        highSpeedDrone.setY(10.0);
        assertEquals(10.0, highSpeedDrone.getY(), 0.01);

    }
}
