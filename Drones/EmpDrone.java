package Drones;

import StaticMethods.RandomGeneratedNumbers;

public class EmpDrone extends CombatDrone {
    private double volt;

    public EmpDrone(int id, double speed, int hitPoints, int value, int type, double volt, double x, double y,
            boolean firstAttack, boolean secoundAttack) {
        super(id, speed, hitPoints, value, type, x, y, firstAttack, firstAttack);
        this.volt = volt;
    }

    public double getVolt() {
        return volt;
    }

    public void setVolt(double volt) {
        this.volt = volt;

    }
    /*
     * Abstract method specifying how the combat drone will attack a delivery drone
     * @params drone, targeted delivery drone
     */
    @Override
    public void attackTarget(DeliveryDrone drone) {
        double speed = getSpeed();
        int damage = drone.getHitPoints();
        // a check if a high speed drone, will get burst of speed, and leave combat.
        double zapFailChance = RandomGeneratedNumbers.getRandomDouble(0.0, 1.0);

        // double zapFailChance = RandomGeneratedNumbers.getRandomDouble()
        if (firstAttack && !secoundAttack && speed > drone.getSpeed()) {
            secoundAttack = true;
            if (volt >= zapFailChance) {
                System.out.println("DRONE ID " + drone.getId() + ": RECIVING FIRE");
                drone.defense(damage);
            }

        } else if (firstAttack){
            secoundAttack = true;
        }
        
        if (!firstAttack && volt >= zapFailChance) {
            System.out.println("DRONE ID " + drone.getId() + ": RECIVING FIRE");
            drone.defense(damage);
            firstAttack = true;
        } else if (volt <= zapFailChance) {
            firstAttack = true;
        }

    }
}
