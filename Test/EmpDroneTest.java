package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import Drones.ArmoredDrone;
import Drones.EmpDrone;
import Drones.HighSpeedDrone;


public class EmpDroneTest {
    private EmpDrone empDrone;
    private ArmoredDrone armoredDrone;
    private HighSpeedDrone highSpeedDrone;

    @Before
    public void setUp() {

        empDrone = new EmpDrone(1, 10.0, 100, 50, 1, 1.0, 0.0, 0.0, false, false);
        highSpeedDrone = new HighSpeedDrone(1, 54.0, 20, 11, 127, 1, 0, 0.0, 0.0, 0.0);
        armoredDrone = new ArmoredDrone(1, 30, 25, 100, 30, 0, 0.0, 15, 15);

    }

    @Test
    public void strikeArmoredDrone() {

        assertFalse(empDrone.isFirstAttack() == true);

        empDrone.attackTarget(armoredDrone);
        assertEquals(0, armoredDrone.getHitPoints());
        assertEquals(true, empDrone.isFirstAttack() == true);

        assertFalse(empDrone.isSecoundAttack() == true);
        empDrone.attackTarget(armoredDrone);
        assertTrue(empDrone.isSecoundAttack() == true);

    }

    @Test
    public void strikeHighSpeedDrone() {

        assertFalse(empDrone.isFirstAttack() == true);

        empDrone.attackTarget(highSpeedDrone);
        assertEquals(0, highSpeedDrone.getHitPoints());
        assertEquals(true, empDrone.isFirstAttack() == true);

        assertFalse(empDrone.isSecoundAttack() == true);
        empDrone.attackTarget(highSpeedDrone);
        assertTrue(empDrone.isSecoundAttack() == true);
    }
    @Test
    public void setVoltChance() {

        assertEquals(1.0, empDrone.getVolt(),0.0);

        empDrone.setVolt(0.0);

        assertEquals(0.0, empDrone.getVolt(), 0.0);

    }
    @Test
    public void setSpeed() {
        
        assertEquals(10.0, empDrone.getSpeed(), 0.0);

        empDrone.setSpeed(5000.0);

        assertEquals(5000.0, empDrone.getSpeed(), 0.0);
    }

    @Test
    public void setHitPoints() {
        
        assertEquals(100, empDrone.getHitPoints());

        empDrone.setHitPoints(0);

        assertEquals(0, empDrone.getHitPoints());
    }

    @Test
    public void setId() {
        assertEquals(1, empDrone.getID());

        empDrone.setId(1234);

        assertEquals(1234, empDrone.getID());
    }

    // @Test
    // void testFirstAttackSuccess() {
    // empDrone.setZapFailChance(0.4);
    // empDrone.attackTarget(deliveryDrone);
    // assertEquals(0, deliveryDrone.getHitPoints()); // Assuming defense method
    // reduces hit points to 0
    // assertTrue(empDrone.isFirstAttack());
    // assertFalse(empDrone.isSecoundAttack());
    // }

    // @Test
    // void testFirstAttackFail() {
    // empDrone.setZapFailChance(0.6);
    // empDrone.attackTarget(deliveryDrone);
    // assertEquals(100, deliveryDrone.getHitPoints());
    // assertTrue(empDrone.isFirstAttack());
    // assertFalse(empDrone.isSecoundAttack());
    // }

    // @Test
    // void testSecondAttackSuccess() {
    // empDrone.setFirstAttack(true);
    // empDrone.setZapFailChance(0.4);
    // empDrone.attackTarget(deliveryDrone);
    // assertEquals(0, deliveryDrone.getHitPoints());
    // assertTrue(empDrone.isFirstAttack());
    // assertTrue(empDrone.isSecoundAttack());
    // }

    // @Test
    // void testSecondAttackFail() {
    // empDrone.setFirstAttack(true);
    // empDrone.setZapFailChance(0.6);
    // empDrone.attackTarget(deliveryDrone);
    // assertEquals(100, deliveryDrone.getHitPoints());
    // assertTrue(empDrone.isFirstAttack());
    // assertTrue(empDrone.isSecoundAttack());
    // }

    // @Test
    // void testHighSpeedEscape() {
    // empDrone.setFirstAttack(true);
    // empDrone.setZapFailChance(0.4);
    // deliveryDrone.setSpeed(15.0);
    // empDrone.attackTarget(deliveryDrone);
    // assertEquals(100, deliveryDrone.getHitPoints());
    // assertTrue(empDrone.isFirstAttack());
    // assertTrue(empDrone.isSecoundAttack());
    // }
}
