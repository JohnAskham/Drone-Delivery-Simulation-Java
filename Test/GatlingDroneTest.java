package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;

import Drones.ArmoredDrone;
import Drones.GatlingDrone;
import Drones.HighSpeedDrone;
;

public class GatlingDroneTest {
    private GatlingDrone gatlingdrone;
    private ArmoredDrone armoredDrone;
    private HighSpeedDrone highSpeedDrone;

    @Before
    public void setUp() {

        gatlingdrone = new GatlingDrone(1, 100, 100, 100, 10, 0, 15, 15, false, false);
        highSpeedDrone = new HighSpeedDrone(1, 54.0, 10, 11, 127, 1, 0, 0.0, 0.0, 0.0);
        armoredDrone = new ArmoredDrone(1, 30, 10, 100, 30, 0, 0.0, 15, 15);

    }

    @Test
    public void strikeArmoredDrone() {

        assertFalse(gatlingdrone.isFirstAttack() == true);

        gatlingdrone.attackTarget(armoredDrone);
        assertEquals(0, armoredDrone.getHitPoints());
        assertEquals(true, gatlingdrone.isFirstAttack() == true);

        assertFalse(gatlingdrone.isSecoundAttack() == true);
        gatlingdrone.attackTarget(armoredDrone);
        assertTrue(gatlingdrone.isSecoundAttack() == true);

    }

    @Test
    public void strikeHighSpeedDrone() {

        assertFalse(gatlingdrone.isFirstAttack() == true);

        gatlingdrone.attackTarget(highSpeedDrone);
        assertEquals(0, highSpeedDrone.getHitPoints());
        assertEquals(true, gatlingdrone.isFirstAttack() == true);

        assertFalse(gatlingdrone.isSecoundAttack() == true);
        gatlingdrone.attackTarget(highSpeedDrone);
        assertTrue(gatlingdrone.isSecoundAttack() == true);
    }
    @Test
    public void setVoltChance() {

        assertEquals(10, gatlingdrone.getDamage());

        gatlingdrone.setDamage(1);

        assertEquals(1, gatlingdrone.getDamage(), 0.0);

    }
    @Test
    public void setSpeed() {
        
        assertEquals(100.0, gatlingdrone.getSpeed(), 0.0);

        gatlingdrone.setSpeed(5000.0);

        assertEquals(5000.0, gatlingdrone.getSpeed(), 0.0);
    }

    @Test
    public void setHitPoints() {
        
        assertEquals(100, gatlingdrone.getHitPoints());

        gatlingdrone.setHitPoints(0);

        assertEquals(0, gatlingdrone.getHitPoints());
    }

    @Test
    public void setId() {
        assertEquals(1, gatlingdrone.getID());

        gatlingdrone.setId(1234);

        assertEquals(1234, gatlingdrone.getID());
    }
}