package Test;


import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


import Drones.*;
import Objects.Crime;

public class CrimeTest {
    private Crime slumsCrime;
    private Crime newHollandCrime;
    private Crime littleItalyCrime;
    private Crime crystalCastlesCrime;
    private DeliveryDrone drone;
    private EmpDrone emp;
    private GatlingDrone gat;
    private Crime crime;
    private String testString;
    // Slums
    public static final List<Integer> slumsEmpIntList = Arrays.asList(100, 10, 20, 10); // (maxValue, minHitPoints,
                                                                                        // maxHitPoints, otherInt)
    public static final List<Double> slumsEmpDoubleList = Arrays.asList(0.1, 38.0, 65.0, 0.20); // (probability,
                                                                                                // minSpeed, maxSpeed,
                                                                                                // otherDouble)

    public static final List<Integer> slumsGatlingIntList = Arrays.asList(75, 10, 20, 10); // (maxValue, minHitPoints,
                                                                                           // maxHitPoints, otherInt)
    public static final List<Double> slumsGatlingDoubleList = Arrays.asList(0.9, 26.0, 75.0, 10.0); // (probability,
                                                                                                    // minSpeed,
                                                                                                    // maxSpeed,
                                                                                                    // otherDouble)

    // New Holland
    public static final List<Integer> newHollandEmpIntList = Arrays.asList(100, 10, 20, 15); // (maxValue, minHitPoints,
                                                                                             // maxHitPoints, otherInt)
    public static final List<Double> newHollandEmpDoubleList = Arrays.asList(0.3, 38.0, 65.0, 0.40);// (probability,
                                                                                                    // minSpeed,
                                                                                                    // maxSpeed,
                                                                                                    // otherDouble)

    public static final List<Integer> newHollandGatlingIntList = Arrays.asList(75, 10, 20, 15); // (maxValue,
                                                                                                // minHitPoints,
                                                                                                // maxHitPoints,
                                                                                                // otherInt)
    public static final List<Double> newHollandGatlingDoubleList = Arrays.asList(0.7, 26.0, 75.0, 15.0); // (probability,
                                                                                                         // minSpeed,
                                                                                                         // maxSpeed,
                                                                                                         // otherDouble)

    // Little Italy
    public static final List<Integer> littleItalyEmpIntList = Arrays.asList(100, 10, 20, 13); // (maxValue,
                                                                                              // minHitPoints,
                                                                                              // maxHitPoints, otherInt)
    public static final List<Double> littleItalyEmpDoubleList = Arrays.asList(0.4, 38.0, 65.0, 0.50); // (probability,
                                                                                                      // minSpeed,
                                                                                                      // maxSpeed,
                                                                                                      // otherDouble)

    public static final List<Integer> littleItalyGatlingIntList = Arrays.asList(75, 10, 20, 13); // (maxValue,
                                                                                                 // minHitPoints,
                                                                                                 // maxHitPoints,
                                                                                                 // otherInt)
    public static final List<Double> littleItalyGatlingDoubleList = Arrays.asList(0.6, 26.0, 75.0, 13.0);// (probability,
                                                                                                         // minSpeed,
                                                                                                         // maxSpeed,
                                                                                                         // otherDouble)

    // Crystal Castles
    public static final List<Integer> crystalCastlesEmpIntList = Arrays.asList(100, 10, 20, 10); // (maxValue,
                                                                                                 // minHitPoints,
                                                                                                 // maxHitPoints,
                                                                                                 // otherInt)
    public static final List<Double> crystalCastlesEmpDoubleList = Arrays.asList(0.6, 38.0, 65.0, 0.75);// (probability,
                                                                                                        // minSpeed,
                                                                                                        // maxSpeed,
                                                                                                        // otherDouble)

    public static final List<Integer> crystalCastlesGatlingIntList = Arrays.asList(75, 10, 20, 10); // (maxValue,
                                                                                                    // minHitPoints,
                                                                                                    // maxHitPoints,
                                                                                                    // otherInt)
    public static final List<Double> crystalCastlesGatlingDoubleList = Arrays.asList(0.4, 26.0, 75.0, 10.0);// (probability,
                                                                                                            // minSpeed,
                                                                                                            // maxSpeed,
                                                                                                            // otherDouble)

    @Before
    public void setUp() {
        // Initialize your DeliveryDrone instance here
        drone = new HighSpeedDrone(0, 50, 255,
                100, 10, 1, 1.25, 0.25, 15, 15);

        emp = new EmpDrone(0, 0, 0, 0, 0,
                0, 0, 0, false, false);

        gat = new GatlingDrone(0, 0, 0, 0,
                0, 0, 0, 0, false, false);

        double[] empArrayLaist = { 0.1, 100, 38.0, 65.0, 10, 20, 0.20 };
        ArrayList<Object> empArrayList = new ArrayList<>(Arrays.asList(0.1, 100, 38.0, 65.0, 10, 20, 0.20));
        ArrayList<Object> gatArrayList = new ArrayList<>(Arrays.asList(0.9, 75, 26.0, 75.0, 10, 20, 10));

        crime = new Crime("Slums", 0.10, empArrayList, gatArrayList);
        slumsCrime = new Crime("Slums", 0.01,
                Arrays.asList(0.1, 100, 38.0, 65.0, 10, 20, 0.20),
                Arrays.asList(0.9, 75, 26.0, 75.0, 10, 20, 10));

        newHollandCrime = new Crime("New Holland", 0.0135,
                Arrays.asList(0.3, 100, 38.0, 65.0, 10, 20, 0.40),
                Arrays.asList(0.7, 75, 26.0, 75.0, 10, 20, 15));

        littleItalyCrime = new Crime("Little Italy", 0.0142,
                Arrays.asList(0.4, 100, 38.0, 65.0, 10, 20, 0.50),
                Arrays.asList(0.6, 75, 26.0, 75.0, 10, 20, 13));

        crystalCastlesCrime = new Crime("Crystal Castles", 0.0075,
                Arrays.asList(0.6, 100, 38.0, 65.0, 10, 20, 0.75),
                Arrays.asList(0.4, 75, 26.0, 75.0, 10, 20, 10));
    }

    @Test
    public void testcombatCheck() {
        double spawnChance = 0.10;
        double check = 0.25;

        // Use the drone instance as needed in your test
        assertFalse(check <= spawnChance);
    }


    @Test
    public void gatlingDroneHpSlums() {
        GatlingDrone gatlingDrone = crime.generateGatlingDrone(drone);
        assertTrue(gatlingDrone.getHitPoints() >= slumsGatlingIntList.get(1)
                && gatlingDrone.getHitPoints() <= slumsGatlingIntList.get(2));

    }

    @Test
    public void empDroneHpSlums() {
        EmpDrone empDrone = crime.generateEmpDrone(drone);
        assertTrue(
                empDrone.getHitPoints() >= slumsEmpIntList.get(1) && empDrone.getHitPoints() <= slumsEmpIntList.get(2));

    }

    @Test
    public void gatlingDroneHpCrystalPalace() {
        GatlingDrone gatlingDrone = crime.generateGatlingDrone(drone);
        assertTrue(gatlingDrone.getHitPoints() >= crystalCastlesEmpIntList.get(1)
                && gatlingDrone.getHitPoints() <= crystalCastlesEmpIntList.get(2));

    }

    @Test
    public void empDroneHpCrystalPalace() {
        EmpDrone empDrone = crime.generateEmpDrone(drone);
        assertTrue(empDrone.getHitPoints() >= crystalCastlesGatlingIntList.get(1)
                && empDrone.getHitPoints() <= crystalCastlesGatlingIntList.get(2));

    }

    @Test
    public void gatlingDroneHpLittleItaly() {
        GatlingDrone gatlingDrone = crime.generateGatlingDrone(drone);
        assertTrue(gatlingDrone.getHitPoints() >= littleItalyGatlingIntList.get(1)
                && gatlingDrone.getHitPoints() <= littleItalyGatlingIntList.get(2));

    }

    @Test
    public void empDroneHpLittleItaly() {
        EmpDrone empDrone = crime.generateEmpDrone(drone);
        assertTrue(empDrone.getHitPoints() >= littleItalyGatlingIntList.get(1)
                && empDrone.getHitPoints() <= littleItalyGatlingIntList.get(2));

    }

    @Test
    public void gatlingDroneHpNewHolland() {
        GatlingDrone gatlingDrone = crime.generateGatlingDrone(drone);
        assertTrue(gatlingDrone.getHitPoints() >= newHollandGatlingIntList.get(1)
                && gatlingDrone.getHitPoints() <= newHollandGatlingIntList.get(2));

    }

    @Test
    public void empDroneHpNewHolland() {
        EmpDrone empDrone = crime.generateEmpDrone(drone);
        assertTrue(empDrone.getHitPoints() >= newHollandEmpIntList.get(1)
                && empDrone.getHitPoints() <= newHollandEmpIntList.get(2));
    }

    @Test
    public void gatlingDroneDamageSlums() {
        GatlingDrone gatlingDrone = crime.generateGatlingDrone(drone);
        assertEquals(slumsGatlingIntList.get(3).intValue(), gatlingDrone.getDamage());

    }

    @Test
    public void gatlingDroneDamageCrystalPalace() {
        GatlingDrone gatlingDrone = crime.generateGatlingDrone(drone);
        assertEquals(crystalCastlesGatlingIntList.get(3).intValue(), gatlingDrone.getDamage());

    }
    // for some reason the test method dose not take the correct drone stats. but they are generated correctly to their placement on the grid in the application
    @Test
    public void gatlingDroneDamageNewHolland() {
        GatlingDrone gatlingDrone = crime.generateGatlingDrone(drone);
        assertEquals(newHollandGatlingIntList.get(3).intValue(), gatlingDrone.getDamage());

    }

    @Test
    public void gatlingDroneDamageLittleItaly() {
        GatlingDrone gatlingDrone = crime.generateGatlingDrone(drone);
        assertEquals(littleItalyGatlingIntList.get(3).intValue(), gatlingDrone.getDamage());

    }

    @Test
    public void empDroneVoltSlums() {
        EmpDrone empDrone = crime.generateEmpDrone(drone);
        assertEquals(slumsEmpDoubleList.get(3), empDrone.getVolt(), 0.001);
    }

    @Test
    public void empDroneVoltNewHolland() {
        EmpDrone empDrone = crime.generateEmpDrone(drone);
        assertEquals(newHollandEmpDoubleList.get(3), empDrone.getVolt(), 0.001);
    }

    @Test
    public void empDroneVoltLittleItaly() {
        EmpDrone empDrone = crime.generateEmpDrone(drone);
        assertEquals(littleItalyEmpDoubleList.get(3), empDrone.getVolt(), 0.001);
    }

    @Test
    public void empDroneVoltCrystalCastle() {
        EmpDrone empDrone = crime.generateEmpDrone(drone);
        assertEquals(crystalCastlesEmpDoubleList.get(3), empDrone.getVolt(), 0.001);
    }

    @Test
    public void gatlingDroneXSlums() {
        GatlingDrone gatlingDrone = crime.generateGatlingDrone(drone);
        assertEquals(15.0, gatlingDrone.getX(), 0.001);

    }

    @Test
    public void empDroneXSlums() {
        EmpDrone empDrone = crime.generateEmpDrone(drone);
        assertEquals(15.0, empDrone.getX(), 0.001);

    }

    @Test
    public void gatlingDroneYSlums() {
        GatlingDrone gatlingDrone = crime.generateGatlingDrone(drone);
        assertEquals(15.0, gatlingDrone.getY(), 0.001);

    }

    @Test
    public void empDroneYSlums() {
        EmpDrone empDrone = crime.generateEmpDrone(drone);
        assertEquals(15.0, empDrone.getY(), 0.001);

    }

    @Test
    public void gatlingDroneSpeedNewHolland() {
        GatlingDrone gatlingDrone = crime.generateGatlingDrone(drone);
        assertTrue(gatlingDrone.getSpeed() >= newHollandGatlingDoubleList.get(1)
                && gatlingDrone.getSpeed() <= newHollandGatlingDoubleList.get(2));

    }

    @Test
    public void empDroneSpeedNewHolland() {
        EmpDrone empDrone = crime.generateEmpDrone(drone);
        assertTrue(empDrone.getSpeed() >= newHollandEmpDoubleList.get(1)
                && empDrone.getSpeed() <= newHollandEmpDoubleList.get(2));

    }

    @Test
    public void gatlingDroneSpeedCrystalCastle() {
        GatlingDrone gatlingDrone = crime.generateGatlingDrone(drone);
        assertTrue(gatlingDrone.getSpeed() >= crystalCastlesGatlingDoubleList.get(1)
                && gatlingDrone.getSpeed() <= crystalCastlesGatlingDoubleList.get(2));

    }

    @Test
    public void empDroneSpeedCrystalCastle() {
        EmpDrone empDrone = crime.generateEmpDrone(drone);
        assertTrue(empDrone.getSpeed() >= crystalCastlesEmpDoubleList.get(1)
                && empDrone.getSpeed() <= crystalCastlesEmpDoubleList.get(2));

    }

    @Test
    public void gatlingDroneSpeedLittleItaly() {
        GatlingDrone gatlingDrone = crime.generateGatlingDrone(drone);
        assertTrue(gatlingDrone.getSpeed() >= littleItalyGatlingDoubleList.get(1)
                && gatlingDrone.getSpeed() <= littleItalyGatlingDoubleList.get(2));

    }

    @Test
    public void empDroneSpeedLittleItaly() {
        EmpDrone empDrone = crime.generateEmpDrone(drone);
        assertTrue(empDrone.getSpeed() >= littleItalyEmpDoubleList.get(1)
                && empDrone.getSpeed() <= littleItalyEmpDoubleList.get(2));

    }

    @Test
    public void gatlingDroneSpeedSlums() {
        GatlingDrone gatlingDrone = crime.generateGatlingDrone(drone);
        assertTrue(gatlingDrone.getSpeed() >= slumsGatlingDoubleList.get(1)
                && gatlingDrone.getSpeed() <= slumsEmpDoubleList.get(2));

    }

    @Test
    public void empDroneSpeedSlums() {
        EmpDrone empDrone = crime.generateEmpDrone(drone);
        assertTrue(empDrone.getSpeed() >= slumsEmpDoubleList.get(1) && empDrone.getSpeed() <= slumsEmpDoubleList.get(2));

    }

    @Test
    public void testEmpDoubleListSlums() {
        List<Double> expected = Arrays.asList(0.1, 38.0, 65.0, 0.20);
        List<Double> actual = slumsCrime.empDoubleList();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testEmpIntegersListSlums() {
        List<Integer> expected = Arrays.asList(100, 10, 20);
        List<Integer> actual = slumsCrime.empIntegersList();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testGatlingDoubleListSlums() {
        List<Double> expected = Arrays.asList(0.9, 26.0, 75.0);
        List<Double> actual = slumsCrime.gatlingDoubleList();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testGatlingIntegersListSlums() {
        List<Integer> expected = Arrays.asList(75, 10, 20, 10);
        List<Integer> actual = slumsCrime.gatlingIntegersList();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testEmpDoubleListNewHolland() {
        List<Double> expected = Arrays.asList(0.3, 38.0, 65.0, 0.40);
        List<Double> actual = newHollandCrime.empDoubleList();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testEmpIntegersListNewHolland() {
        List<Integer> expected = Arrays.asList(100, 10, 20);
        List<Integer> actual = newHollandCrime.empIntegersList();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testGatlingDoubleListNewHolland() {
        List<Double> expected = Arrays.asList(0.7, 26.0, 75.0);
        List<Double> actual = newHollandCrime.gatlingDoubleList();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testGatlingIntegersListNewHolland() {
        List<Integer> expected = Arrays.asList(75, 10, 20, 15);
        List<Integer> actual = newHollandCrime.gatlingIntegersList();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testEmpDoubleListLittleItaly() {
        List<Double> expected = Arrays.asList(0.4, 38.0, 65.0, 0.50);
        List<Double> actual = littleItalyCrime.empDoubleList();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testEmpIntegersListLittleItaly() {
        List<Integer> expected = Arrays.asList(100, 10, 20);
        List<Integer> actual = littleItalyCrime.empIntegersList();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testGatlingDoubleListLittleItaly() {
        List<Double> expected = Arrays.asList(0.6, 26.0, 75.0);
        List<Double> actual = littleItalyCrime.gatlingDoubleList();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testGatlingIntegersListLittleItaly() {
        List<Integer> expected = Arrays.asList(75, 10, 20, 13);
        List<Integer> actual = littleItalyCrime.gatlingIntegersList();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testEmpDoubleListCrystalCastles() {
        List<Double> expected = Arrays.asList(0.6, 38.0, 65.0, 0.75);
        List<Double> actual = crystalCastlesCrime.empDoubleList();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testEmpIntegersListCrystalCastles() {
        List<Integer> expected = Arrays.asList(100, 10, 20);
        List<Integer> actual = crystalCastlesCrime.empIntegersList();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testGatlingDoubleListCrystalCastles() {
        List<Double> expected = Arrays.asList(0.4, 26.0, 75.0);
        List<Double> actual = crystalCastlesCrime.gatlingDoubleList();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testGatlingIntegersListCrystalCastles() {
        List<Integer> expected = Arrays.asList(75, 10, 20, 10);
        List<Integer> actual = crystalCastlesCrime.gatlingIntegersList();
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testGatlingSpawnChanceSlums() {
        double expected = 0.9;
        assertEquals(expected, slumsGatlingDoubleList.get(0), 0.001);
    }

    @Test
    public void testGatlingSpawnChanceCrystalCastle() {
        double expected = 0.4;
        assertEquals(expected, crystalCastlesGatlingDoubleList.get(0), 0.001);
    }
    @Test
    public void testGatlingSpawnChanceLittleItaly() {
        double expected = 0.6;
        assertEquals(expected, littleItalyGatlingDoubleList.get(0), 0.001);
    }
    @Test
    public void testGatlingSpawnChanceNewHolland() {
        double expected = 0.7;
        assertEquals(expected, newHollandGatlingDoubleList.get(0), 0.001);
    }
    @Test
    public void testEmpSpawnChanceSlums() {
        double expected = 0.1;
        assertEquals(expected, slumsEmpDoubleList.get(0), 0.001);
    }

    @Test
    public void testEmpSpawnChanceCrystalCastle() {
        double expected = 0.6;
        assertEquals(expected, crystalCastlesEmpDoubleList.get(0), 0.001);
    }
    @Test
    public void testEmpSpawnChanceLittleItaly() {
        double expected = 0.4;
        assertEquals(expected, littleItalyEmpDoubleList.get(0), 0.001);
    }
    @Test
    public void testEmpSpawnChanceNewHolland() {
        double expected = 0.3;
        assertEquals(expected, newHollandEmpDoubleList.get(0), 0.001);
    }

    @Test
    public void testspawnCrimeDroneSlums() {
        assertEquals(0.01, slumsCrime.getSpawnChance(), 0.001);
    }
    @Test
    public void testspawnCrimeDroneCrystalCastle() {
        assertEquals(0.0135, newHollandCrime.getSpawnChance(), 0.001);
    }
    @Test
    public void testspawnCrimeDroneLittleItaly() {
        assertEquals(0.0142, littleItalyCrime.getSpawnChance(), 0.001);
    }
    @Test
    public void testspawnCrimeDroneNewHolland() {
        assertEquals(0.0075, crystalCastlesCrime.getSpawnChance(), 0.001);
    }

    @Test
    public void testGetCrimeDistrictNameSlums() {
        assertEquals("Slums", slumsCrime.getCrimeDistrictName());
    }
    @Test
    public void testGetDistractNameNewHolland() {
        assertEquals("New Holland", newHollandCrime.getCrimeDistrictName());
    }
    @Test
    public void testGetDistractNameCrystalCastle() {
        assertEquals("Crystal Castles", crystalCastlesCrime.getCrimeDistrictName());
    }
    @Test
    public void testGetDistractNameLittleItaly() {
        assertEquals("Little Italy", littleItalyCrime.getCrimeDistrictName());
    }
}

