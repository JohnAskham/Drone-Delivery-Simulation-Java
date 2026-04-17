package Drones;

import StaticMethods.RandomGeneratedNumbers;

public class ArmoredDrone extends DeliveryDrone {
    private double armor;
    private int capacity;

    public ArmoredDrone(int id, double speed, int hitPoints, int value, int capacity, int type, double armor, double x,
            double y) {
        super(id, speed, hitPoints, value, capacity, type, x, y);
        this.armor = armor;
        this.capacity = capacity;
    }

    public double getArmor() {
        return armor;
    }

    public void setArmorChance(double armor) {
        this.armor = armor;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    /*
     * Abstract method for armored drone, that has a chance to ignore damage
     * @params damage, the damage drone may take
     */
    @Override
    public void defense(int damage) {

        double ignoreDamageChance = RandomGeneratedNumbers.getRandomDouble(0.0, 1.0);
        if (armor >= ignoreDamageChance) {
            System.out.println("DRONE ID " + this.getId() + ": WAS HIT BUT RECEIVED NO DAMAGE");
        } else {
            hitPoints = +hitPoints - damage;

            setHitPoints(hitPoints);
        }

    }

    // not used in armored drone
    @Override
    public boolean speedBurst() {
        return false;
    }

}
