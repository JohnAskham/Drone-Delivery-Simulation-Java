package Drones;

import StaticMethods.RandomGeneratedNumbers;

public class HighSpeedDrone extends DeliveryDrone {
    private double speedMultiplier;
    private double dodgeChance;
    private int capacity;
    private boolean activeSpeedBoost;
    private boolean accelerationburst = false;

    public HighSpeedDrone(int id, double speed, int hitPoints, int value, int capacity, int type,
            double speedMultiplier, double dodgeChance, double x, double y) {
        super(id, speed, hitPoints, value, capacity, type, x, y);
        this.speedMultiplier = speedMultiplier;
        this.dodgeChance = dodgeChance;
        this.capacity = capacity;
        accelerationburst = false;
    }

    /*
     * Abstract defense method, which controls how deliverydrones act upon being attacked
     * @params damage, how much damage a delivery drone will take
     */
    @Override
    public void defense(int damage) {

        if (!accelerationburst) {
            // drone makes a speed burst
            this.speedBurst();

            if (!accelerationburst) {
                hitPoints = +hitPoints - damage;
                setHitPoints(hitPoints);
            } else if (accelerationburst) {
                System.out.println("DRONE ID " + this.getId() + ": USED ACCELERATION BURST");

            }
        }
    }
    /*
     * Abstract speedburst method, should not be abstract technically. Gives a highspeedrone upon being called
     */
    @Override
    public boolean speedBurst() {
        double failedBurst = RandomGeneratedNumbers.getRandomDouble(0.0, 1.0);
        if (dodgeChance > failedBurst) {
            this.setState(DroneState.IN_TRANSIT_ORDER);
            this.move(this, this.getOrder().getX(), this.getOrder().getY());
            this.accelerationburst = true;
            return true;
        }
        return false;
    }
    /*
     * Gives drone a speedmultiplier
     */
    public boolean speedMultiplier() {
        if (!activeSpeedBoost && this.getState() != DroneState.NEUTRALIZED) {
            double speed = this.getSpeed() * speedMultiplier;
            this.setSpeed(speed);

            activeSpeedBoost = true;
        }

        if (this.getState() == DroneState.NEUTRALIZED && activeSpeedBoost) {
            double speed = this.getSpeed() / speedMultiplier;
            this.setSpeed(speed);
            activeSpeedBoost = false;
            return false;
        }
        return activeSpeedBoost;

    }

    public double getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void setSpeedMultiplier(int speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public double getDodgeChance() {
        return dodgeChance;
    }

    public void setDodgeChance(int dodgeChance) {
        this.dodgeChance = dodgeChance;
    }

    public void setDodgeChance(double dodgeChance) {
        this.dodgeChance = dodgeChance;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    public void setSpeedMultiplier(double speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public boolean isActiveSpeedBoost() {
        return activeSpeedBoost;
    }

    public void setActiveSpeedBoost(boolean activeSpeedBoost) {
        this.activeSpeedBoost = activeSpeedBoost;
    }

    public boolean isAccelerationburst() {
        return accelerationburst;
    }

    public void setAccelerationburst(boolean accelerationburst) {
        this.accelerationburst = accelerationburst;
    }

}
