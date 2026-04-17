package Objects;

import java.util.List;
import java.util.stream.Collectors;
import Drones.*;
import StaticMethods.*;

public class Crime {
    private String crimeDistrictName;
    private double spawnChance;
    private List<Object> empDroneList;
    private List<Object> gatlingDroneList;

    public Crime(String crimeDistrictName, double spawnChance, List<Object> empDroneList,
            List<Object> gatlingDroneList) {
        this.crimeDistrictName = crimeDistrictName;
        this.spawnChance = spawnChance;
        this.empDroneList = empDroneList;
        this.gatlingDroneList = gatlingDroneList;
    }
    /*
     * Generates a gatling drone to attack a delivery drone
     * @params drone being the targetted drone
     */
    public GatlingDrone generateGatlingDrone(DeliveryDrone drone) {
        double x = drone.getX();
        double y = drone.getY();
        int droneId = drone.getID();

        List<Double> doubleStats = gatlingDoubleList();
        List<Integer> intStats = gatlingIntegersList();

        int gatlingValue = intStats.get(0);
        double gatlingMinSpeed = doubleStats.get(1);
        double gatlingMaxSpeed = doubleStats.get(2);
        int gatlingMinHitPoints = intStats.get(1);
        int gatlingMaxHitPoints = intStats.get(2);
        int damage = intStats.get(3);

        double speed = RandomGeneratedNumbers.getRandomDouble(gatlingMinSpeed, gatlingMaxSpeed);
        int hitPoints = RandomGeneratedNumbers.getRandomInt(gatlingMinHitPoints, gatlingMaxHitPoints);
        return new GatlingDrone(droneId, speed, hitPoints, gatlingValue, damage, 1, x, y, false, false);
    }

    /*
     * Generates an emp drone to attack a delivery drone
     * @params drone being the targeted drone
     */
    public EmpDrone generateEmpDrone(DeliveryDrone drone) {
        double x = drone.getX();
        double y = drone.getY();
        int droneId = drone.getID();

        List<Double> doubleStats = empDoubleList();
        List<Integer> intStats = empIntegersList();

        int empValue = intStats.get(0);
        double empMinSpeed = doubleStats.get(1);
        double empMaxSpeed = doubleStats.get(2);
        int empMinHitPoints = intStats.get(1);
        int empMaxHitPoints = intStats.get(2);
        double volt = doubleStats.get(3);

        double speed = RandomGeneratedNumbers.getRandomDouble(empMinSpeed, empMaxSpeed);
        int hitPoints = RandomGeneratedNumbers.getRandomInt(empMinHitPoints, empMaxHitPoints);
        return new EmpDrone(droneId, speed, hitPoints, empValue, 1, volt, x, y, false, false);
    }

    /*
     * Checks if combat will begin against drone
     * @params drone being the targeted drone
     */
    public boolean combatCheck(DeliveryDrone drone) {
        double spawnChance = getSpawnChance();
        double check = RandomGeneratedNumbers.getRandomDouble(0.0, 1.0);
        if (check <= spawnChance) {
            return true;
        }
        return false;
    }

    /*
     * If a combat drone is to spawn, it checks which kind and spawns accordingly
     * @params drone, the drone combat drone will chahse¨
     * @quadrant which area the combat drone spawns in
     */
    public boolean spawnCrimeDrone(DeliveryDrone drone, int quadrant) {
        List<Double> gatlingChance = gatlingDoubleList();
        double gatlingSpawnChance = gatlingChance.get(quadrant);
        double check = RandomGeneratedNumbers.getRandomDouble(0.0, 1.0);

        if (check <= gatlingSpawnChance) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * next four methods will extract the needed data,
     * from the lists.
     */
    public List<Double> empDoubleList() {
        List<Object> empstats = getEmpDroneList();
        List<Double> empDouble = empstats.stream()
                .filter(Double.class::isInstance)
                .map(Double.class::cast)
                .collect(Collectors.toList());

        return empDouble;
    }

    public List<Integer> empIntegersList() {
        List<Object> empstats = getEmpDroneList();
        List<Integer> empInteger = empstats.stream()
                .filter(Integer.class::isInstance)
                .map(Integer.class::cast)
                .collect(Collectors.toList());

        return empInteger;
    }

    public List<Double> gatlingDoubleList() {
        List<Object> gatlingStats = getGatlingDroneList();
        List<Double> gatlingDouble = gatlingStats.stream()
                .filter(Double.class::isInstance)
                .map(Double.class::cast)
                .collect(Collectors.toList());

        return gatlingDouble;
    }

    public List<Integer> gatlingIntegersList() {
        List<Object> gatlingStats = getGatlingDroneList();
        List<Integer> gatlingIntegers = gatlingStats.stream()
                .filter(Integer.class::isInstance)
                .map(Integer.class::cast)
                .collect(Collectors.toList());

        return gatlingIntegers;
    }

    public String getCrimeDistrictName() {
        return crimeDistrictName;
    }

    public void setCrimeDistrictName(String crimeDistrictName) {
        this.crimeDistrictName = crimeDistrictName;
    }

    public double getSpawnChance() {
        return spawnChance;
    }

    public void setSpawnChance(double spawnChance) {
        this.spawnChance = spawnChance;
    }

    public List<Object> getEmpDroneList() {
        return empDroneList;
    }

    public void setEmpDroneList(List<Object> empDroneList) {
        this.empDroneList = empDroneList;
    }

    public List<Object> getGatlingDroneList() {
        return gatlingDroneList;
    }

    public void setGatlingDroneList(List<Object> gatlingDroneList) {
        this.gatlingDroneList = gatlingDroneList;
    }

}
