package Drones;

public class GatlingDrone extends CombatDrone {
    private int damage;

    public GatlingDrone(int id, double speed, int hitPoints, int value, int damage, int type, double x, double y,
            boolean firstAttack, boolean secoundAttack) {
        super(id, speed, hitPoints, value, type, x, y, firstAttack, firstAttack);
        this.damage = damage;

    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    /*
     * Abstract method specifying how the combat drone will attack a delivery drone
     * @params drone, targeted delivery drone
     */
    @Override
    public void attackTarget(DeliveryDrone drone) {
        double gatlingSpeed = getSpeed();

        if (firstAttack && !secoundAttack && gatlingSpeed > drone.getSpeed()) {
            // checks if Dilvery drone is faster then the crime drone
            System.out.println("DRONE ID " + drone.getId() + ": RECIVING FIRE");
            secoundAttack = true;
            drone.defense(damage);
        } else if (firstAttack) {
            secoundAttack = true;

        }

        // if crime drone has not made the first attack
        if (!firstAttack) {
            System.out.println("DRONE ID " + drone.getId() + ": RECIVING FIRE");
            firstAttack = true;
            drone.defense(damage);
        }

    }
    
}
