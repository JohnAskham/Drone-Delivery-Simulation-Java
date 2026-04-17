package Drones;

public abstract class CombatDrone extends Drone {
    boolean firstAttack;
    boolean secoundAttack;

    public CombatDrone(int id, double speed, int hitPoints, int value, int type,
            double x, double y, boolean firstAttack, boolean secoundAttack) {
        super(id, speed, hitPoints, value, type, x, y);
        this.firstAttack = firstAttack;
        this.secoundAttack = secoundAttack;
    }

    /*
     * Abstract method that is meant to behave differently depending on which combat drone
     */
    public abstract void attackTarget(DeliveryDrone drone);

    public boolean isFirstAttack() {
        return firstAttack;
    }

    public void setFirstAttack(boolean firstAttack) {
        this.firstAttack = firstAttack;
    }

    public boolean isSecoundAttack() {
        return secoundAttack;
    }

    public void setSecoundAttack(boolean secoundAttack) {
        this.secoundAttack = secoundAttack;
    }

}
