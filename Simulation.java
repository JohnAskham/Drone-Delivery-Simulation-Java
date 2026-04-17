import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import Drones.*;
import Objects.*;
import StaticMethods.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class Simulation {
    private ArrayList<DeliveryDrone> NeutralizedDrones;
    private ArrayList<DeliveryDrone> drones;
    private ArrayList<DeliveryDrone> crashDrones;
    private ArrayList<DeliveryDrone> robbedDrones;
    private ArrayList<CombatDrone> activeCombatDrones;
    private ArrayList<CombatDrone> combatDroneToDelete;
    private ArrayList<Warehouse> warehouses;
    private ArrayList<Order> orders;
    private ArrayList<Order> fulfilledOrders;
    private ArrayList<Order> AvailableOrders;
    private ArrayList<Item> items;
    private ArrayList<Crime> crime;
    private LocalDateTime startSim;
    private LocalDateTime endSim;
    private LocalDateTime currentTime;
    private double revenue;
    private double lostValueFromDrones;
    private double costOfNewDrones;
    private boolean query;
    private boolean WHExists;
    private String queryInput;
    private int activeDrones;
    private int queryInfo;
    private int startHour;
    private int missedCrashes;
    private int droneOrWH;
    private int crashOrRob;
    private int querydroneID;

    public Simulation() {
        robbedDrones = new ArrayList<DeliveryDrone>();
        AvailableOrders = new ArrayList<Order>();
        fulfilledOrders = new ArrayList<Order>();
        NeutralizedDrones = new ArrayList<DeliveryDrone>();
        activeCombatDrones = new ArrayList<CombatDrone>();
        combatDroneToDelete = new ArrayList<CombatDrone>();
        crashDrones = new ArrayList<DeliveryDrone>();
    }

    public void simSetup() {
        ReadFiles filereader = new ReadFiles();
        drones = filereader.droneReadFile();
        orders = filereader.orderReadFile();
        items = filereader.itemReadFile();
        warehouses = filereader.warehouseReadFile();
        try {
            crime = filereader.crimeReadFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*  Main loop
    * @InputMismatchException, throws an error if illegal input is fonud, such as when entering a string into an int variable
     */
    public void simLoop() {

        RandomGeneratedNumbers.setSeed();
        Scanner scanner = new Scanner(System.in);
        clearconsole.clearConsole();

        System.out.println("At what hour would you like the simulation to start? (1-23)");
        // Checking for legal time to start the simulation
        while (true) {
            try {
                startHour = scanner.nextInt();
                // Getting rid of residual newline
                scanner.nextLine();
                if (startHour >= 1 && startHour <= 23) {
                    startSim = LocalDateTime.of(2077, 5, 6, startHour, 0);
                    break;
                } else {
                    System.out.println("Illegal input, at what hour would you like the simulation to start? (1-23)");
                }
            } catch (InputMismatchException e) {
                System.out.println("Illegal input, input correct hour to start simulation (1-23)");
                scanner.next();
            }
        }
        // Checking if legal time for ending the simulation
        System.out.printf("%s%d%s%n", "At what hour would you like the simulation to end? (", startHour + 1, "-0)");
        while (true) {
            try {
                int endtime = scanner.nextInt();
                // Getting rid of residual newline
                scanner.nextLine();
                if (endtime > startHour && endtime < 24 || endtime == 0) {
                    endSim = LocalDateTime.of(2077, 5, 6, endtime, 0);
                    if (endtime == 0) {
                        int endDay = 7;
                        endSim = LocalDateTime.of(2077, 5, endDay, endtime, 0);
                    }
                    break;
                } else {
                    System.out.printf("%s%d%s%n", "At what hour would you like the simulation to end? (", startHour + 1,
                            "-0)");
                }
            } catch (InputMismatchException e) {
                System.out.printf("%s%d%s%n", "Illegal input, input correct hour to end simulation(", startHour + 1,
                        "-0)");
                scanner.next();
            }
        }
        currentTime = startSim;

        // Main loop
        while (!currentTime.isAfter(endSim) ||
                (currentTime.isAfter(endSim) && !drones.stream().allMatch(drone -> checkEnd(drone)))) {

            // Printing out each minute mark
            System.out.println("Current Time: " + currentTime.format(DateTimeFormatter.ofPattern("HH:mm")));

            // Adding all available orders to another list
            Iterator<Order> it = orders.iterator();
            while (it.hasNext()) {
                Order order = it.next();
                if (currentTime.isAfter(order.getTimeStamp())) {
                    AvailableOrders.add(order);
                    it.remove();
                }
            }

            // Menu for data
            query = true;
            while (query) {
                try {
                    System.out.println("1: List all drones");
                    System.out.println("2: Information on drones or warehouses");
                    System.out.println("3: Information how drones were neutralized");
                    System.out.println("4: See delivered orders of a specific drone");
                    System.out.println("5: Total revenue for the day");
                    System.out.println("Leave blank to continue to next iteration");
                    queryInput = scanner.nextLine().trim();
                    if (queryInput.isEmpty()) {
                        // To force default case if input is empty
                        queryInfo = -1;
                    } else {
                        // Otherwise convert into a number and let the catch handle it if it does not
                        queryInfo = Integer.parseInt(queryInput);
                    }
                    // Switch case for menu between iterations
                    switch (queryInfo) {
                        // Prints out State and ID of all drones
                        case 1:
                            for (DeliveryDrone drone : drones) {
                                System.out.println("Drone ID: " + drone.getID() + " STATUS: " + drone.getState());
                            }
                            System.out.println("Press enter to get back to menu");
                            scanner.nextLine();
                            break;
                        // Prints out the finer details about drones or Warehouses
                        case 2:
                            try {
                                // Decide drone or WH
                                System.out.println("Enter 1 for drones, 2 for warehouses");
                                droneOrWH = Integer.parseInt(scanner.nextLine());
                                if (droneOrWH == 1) {
                                    System.out.println("Which drone would you like to see information on? (1 - "
                                            + drones.size() + ")");
                                    querydroneID = scanner.nextInt();
                                    scanner.nextLine();
                                    // While loop to catch illegal input
                                    while (querydroneID > drones.size()) {
                                        System.out.println("This drone does not exist, please input a legal ID (1 - "
                                                + drones.size() + ")");
                                        querydroneID = scanner.nextInt();
                                        scanner.nextLine();
                                    }
                                    // Prints drone info
                                    for (DeliveryDrone drone : drones) {
                                        if (querydroneID == drone.getID()) {
                                            System.out.println("DRONE ID: " + drone.getID());
                                            System.out.println("DRONE HITPOINTS: " + drone.getHitPoints());
                                            System.out.println("DRONE SPEED: " + drone.getSpeed());
                                            System.out.println("DRONE VALUE: " + drone.getValue());
                                            System.out.println("DRONE STATE: " + drone.getState());
                                            if (drone instanceof HighSpeedDrone) {
                                                System.out.println("DRONE TYPE: HIGH SPEED DRONE");
                                            } else if (drone instanceof ArmoredDrone) {
                                                System.out.println("DRONE TYPE: ARMORED DRONE");
                                            }
                                            scanner.nextLine();
                                        }
                                    }
                                }
                                if (droneOrWH == 2) {
                                    // Specify warehouse
                                    System.out.println("List of warehouses:");
                                    warehouses.stream().forEach(warehouse -> System.out.println(warehouse.getName()));
                                    System.out.println(
                                            "Which warehouse would you like to inspect? (Example: Slums warehouse)");
                                    String chosenWH = scanner.nextLine();
                                    // Print stock from specified warehouse
                                    for (Warehouse warehouse : warehouses) {
                                        if (chosenWH.toUpperCase().equals(warehouse.getName().toUpperCase())) {
                                            warehouse.printStock(warehouse);
                                            WHExists = true;
                                            break;
                                        }
                                    }
                                    if (!WHExists) {
                                        System.out.println("This warehouse does not exist.");
                                    }
                                    scanner.nextLine();
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Illegal input, please try again");
                                scanner.next();
                            }
                            break;
                        // Prints out reason for neutralization of drone
                        case 3:
                            try {
                                System.out.println(
                                        "Please input 1 for all crashed drones, and 2 for all drones neutralized due to crime:");
                                crashOrRob = scanner.nextInt();
                                scanner.nextLine();
                                // Collision
                                if (crashOrRob == 1) {
                                    crashDrones.stream().forEach(
                                            drone -> System.out.println("DRONE ID: " + drone.getID() + " STATE: "
                                                    + drone.getState() + " DUE TO COLLISION WITH ANOTHER DRONE"));
                                    System.out.println("TOTAL CRASHES: " + crashDrones.size() + "\n");
                                    scanner.nextLine();
                                }
                                // Criminal event
                                else if (crashOrRob == 2) {
                                    robbedDrones.stream().forEach(drone -> System.out.println("DRONE ID: "
                                            + drone.getID() + " STATE: " + drone.getState() + " DUE TO CRIME"));
                                    System.out.println("TOTAL CRASHES: " + robbedDrones.size() + "\n");
                                    scanner.nextLine();
                                } else {
                                    System.out.println("Illegal input, please follow the menu: \n");
                                }

                            } catch (InputMismatchException e) {
                                System.out.println("Illegal input, please follow the menu: \n");
                                scanner.nextLine();
                            }
                            break;
                        case 4:
                            try {
                                // Input for which drone
                                System.out.println("Input ID for the drone you wish to see all delivered orders: (1-"
                                        + drones.size() + ")");
                                querydroneID = scanner.nextInt();
                                scanner.nextLine();
                                while (querydroneID > drones.size()) {
                                    System.out.println(
                                            "Illegal input, please enter a legal input:(1" + drones.size() + ")");
                                    querydroneID = scanner.nextInt();
                                    scanner.nextLine();
                                }
                                // Scanning list of delivered drones
                                for (DeliveryDrone drone : drones) {
                                    if (querydroneID == drone.getID()) {
                                        drone.printList();
                                        scanner.nextLine();
                                    }
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Illegal Input, please follow the menu \n");
                                scanner.nextLine();
                            }
                            break;
                        case 5:
                            // Total revenue
                            System.out.printf("%s%.2f%n","Total revenue this day so far: ", revenue);
                            scanner.nextLine();
                            break;
                        default:
                            query = false;
                            break;

                    }
                } catch (NumberFormatException e) {
                    System.out.println("Illegal input, please follow the menu");

                }
            }

            // Drone action loop
            for (DeliveryDrone drone : drones) {
                switch (drone.getState()) {
                    case DOCKED:
                        if (drone.getOrder() == null && currentTime.isBefore(endSim)) {
                            Order oldestOrder = Order.oldestOrder(AvailableOrders);
                            drone.assignOrder(drone, oldestOrder, AvailableOrders);
                        }
                        if (drone instanceof HighSpeedDrone) {
                            ((HighSpeedDrone) drone).speedMultiplier();
                        }

                        break;

                    case IN_TRANSIT_WH:
                        if (drone.getOrder() != null) {
                            Warehouse TargetWarehouse = findWarehouse(drone.getOrder().getItem());
                            drone.move(drone, TargetWarehouse.getX(), TargetWarehouse.getY());
                        }
                        break;

                    case IN_TRANSIT_ORDER:
                        boolean crimeCheck = spawnCrimeDrone(drone);
                        drone.move(drone, drone.getOrder().getX(), drone.getOrder().getY());
                        if (crimeCheck == true) {
                            drone.setState(DroneState.IN_COMBAT);
                            System.out.println("DRONE ID " + drone.getID() + ": IN COMBAT");

                        }

                        break;

                    case IN_TRANSIT_HQ:
                        drone.move(drone, 15, 15);
                        break;

                    case FETCHING:
                        Warehouse TargetWarehouse = findWarehouse(drone.getOrder().getItem());
                        drone.fetchOrder(TargetWarehouse, drone, items);
                        break;

                    case DELIVERING:
                        // Returning new value of revenue after order is delivered
                        revenue = drone.deliverOrder(drone, fulfilledOrders, items, revenue, currentTime);

                        break;

                    case IN_COMBAT:
                        // makes an attack on the delivery drone
                        strikeDeliveryDrone(drone);
                        // if delivery drone is killed
                        if (drone.getHitPoints() <= 0) {
                            System.out.println("DRONE " + drone.getID() + ": NEUTRALIZED BY CRIMINAL ACTIVITY");
                            robbedDrones.add(drone);
                            lostValueFromDrones += drone.getValue();
                            NeutralizedDrones.add(drone);
                            drone.setState(DroneState.NEUTRALIZED);
                        }
                        // checks if crime drone is able to attack again
                        checkIfValid();
                        break;

                    case NEUTRALIZED:
                        if (drone instanceof HighSpeedDrone) {
                            ((HighSpeedDrone) drone).speedMultiplier();
                        }
                        break;

                    default:
                        break;
                }
            }

            // deletes the inactive combat drones, from the activeCombatDrones list.
            for (CombatDrone dronesToDelete : combatDroneToDelete) {
                activeCombatDrones.remove(dronesToDelete);
            }
            // clears the inactive combatdrone Array
            combatDroneToDelete.clear();

            // Restocking warehouse every 30 minutes
            if (currentTime.getMinute() % 30 == 0) {
                for (Warehouse warehouse : warehouses) {
                    warehouse.restockWarehouse();
                }
            }

            // Creating new drones for neutralized ones
            if (currentTime.getMinute() == 0) {
                System.out.println(NeutralizedDrones.size() + " NEW DRONES HAVE BEEN CREATED");
                createNewDrone(NeutralizedDrones, drones);

            }

            // Checking for collisions
            checkCollisions();

            @SuppressWarnings("unused")
            String nextIteration = scanner.nextLine();
            currentTime = currentTime.plusMinutes(1);

        }
        showStats();
        scanner.close();
    }

    /*
     * Checks if a combat between combat drone and delivery drone is over.
     */
    private void checkIfValid() {
        for (CombatDrone combatDrone : activeCombatDrones) {
            for (DeliveryDrone drone : drones) {
                int cid = combatDrone.getId();
                int ddid = drone.getId();
                if (combatDrone.isSecoundAttack() && cid == ddid &&
                        drone.getState() == DroneState.IN_COMBAT) {

                    drone.setState(DroneState.IN_TRANSIT_ORDER);

                    System.out.println("DRONE ID " + drone.getId() + ": TRANSIT");
                    if (drone instanceof HighSpeedDrone) {
                        ((HighSpeedDrone) drone).setAccelerationburst(false);
                    }
                }
            }
        }
        // checks if the delivery drone has been destroyed.
        for (CombatDrone combatDrone : activeCombatDrones) {
            for (DeliveryDrone drone : drones) {
                int cid = combatDrone.getId();
                int did = drone.getId();
                if (cid == did && drone.getState() == DroneState.NEUTRALIZED || combatDrone.isSecoundAttack()) {
                    combatDroneToDelete.add(combatDrone);
                }
            }
        }

    }

    /*
     * Makes active combat drones strike their targetted delivery drone
     * @param drone the targetted delivery drone
     */
    private void strikeDeliveryDrone(DeliveryDrone drone) {
        activeCombatDrones.stream().filter(combatDrone -> combatDrone.getId() == (drone.getId())).findFirst()
                .ifPresent(combatDrone -> {
                    if (!combatDrone.isSecoundAttack()) {
                        combatDrone.attackTarget(drone);
                    }
                });

    }

     
    /* checks if a crime drone will spawn and chase a delivery drone
    * @param drone : the targetted delivery drone a combat drone will chase
    */ 
    public boolean spawnCrimeDrone(DeliveryDrone drone) {

        double check = RandomGeneratedNumbers.getRandomDouble(0.0, 1.0);
        double dronespawn = RandomGeneratedNumbers.getRandomDouble(0.0, 1.0);
        int quartrant = isDistrict(drone);
        crime.get(quartrant).getSpawnChance();
        if (check < crime.get(quartrant).getSpawnChance()) {

            if (dronespawn < (double) crime.get(quartrant).getGatlingDroneList().get(0)) {
                GatlingDrone gatlingDrone = crime.get(quartrant).generateGatlingDrone(drone);
                activeCombatDrones.add(gatlingDrone);
                return true;
            } else {
                EmpDrone empDrone = crime.get(quartrant).generateEmpDrone(drone);
                activeCombatDrones.add(empDrone);
                return true;
            }
        }
        return false;
    }

    /*
     * Checks which distroct a drone is in
     * @param drone, which drone is checked
     */
    private int isDistrict(DeliveryDrone drone) {
        double x = drone.getX();
        double y = drone.getY();
        if (x <= 15 && y >= 15) { // slums
            return 0;
        }
        if (x > 15 && y > 15) { // new holland
            return 1;
        }
        if (x <= 15 && y < 15) { // little italy
            return 2;
        }
        if (x > 15 && y < 15) { // crystal castle
            return 3;
        }

        return 4;

    }
    /*
     * Locates what warehouse stores the given item
     * @param itemname: the item searched for
     */
    private Warehouse findWarehouse(String itemName) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.hasItem(itemName)) {
                return warehouse;
            }
        }
        return null;
    }

    // Takes one drone and checks if any other drone is within 5m of it and
    // neutralizes both drones if they are
    public void checkCollisions() {
        for (int i = 0; i < drones.size(); i++) {
            DeliveryDrone d1 = drones.get(i);
            for (int j = i + 1; j < drones.size(); j++) {
                DeliveryDrone d2 = drones.get(j);
                // if both drones are in transit and within 5m of each other, neutralize both
                if (d1.getState() == DroneState.IN_TRANSIT_ORDER && d2.getState() == DroneState.IN_TRANSIT_ORDER) {
                    if (d1.distanceBetweenDrones(d2)) {
                        d1.setState(DroneState.NEUTRALIZED);
                        d2.setState(DroneState.NEUTRALIZED);
                        lostValueFromDrones += d1.getValue() + d2.getValue();
                        AvailableOrders.add(d1.getOrder());
                        AvailableOrders.add(d2.getOrder());
                        d1.setCarry(false);
                        d2.setCarry(false);
                        d1.setOrder(null);
                        d2.setOrder(null);
                        NeutralizedDrones.add(d1);
                        NeutralizedDrones.add(d2);
                        crashDrones.add(d1);
                        crashDrones.add(d2);
                        System.out.println("DRONE ID " + d1.getID() + " AND DRONE " + d2.getID()
                                + " COLLIDED AND ARE NEUTRALIZED");
                    }
                    // if drones vector intersect, + 1 to missedCrashes
                    else if (DeliveryDrone.linesIntersection(d1, d2)) {
                        missedCrashes += 1;
                    }
                }
            }
        }
    }

    /* Creates new drones by use of the neutralized ones
     * @param NeutralizedDrones, a list of neutralized drones used to create new ones
     * @param drones, the list containing all drones
     */
    public void createNewDrone(ArrayList<DeliveryDrone> NeutralizedDrones, ArrayList<DeliveryDrone> drones) {
        for (DeliveryDrone drone : NeutralizedDrones) {
            if (drone.getState() == DroneState.NEUTRALIZED) {
                costOfNewDrones += drone.getValue();
                switch (drone) {
                    case ArmoredDrone newDrone ->
                        drones.add(new ArmoredDrone(drones.size() + 1, newDrone.getSpeed(), newDrone.getOriginalHP(),
                                newDrone.getValue(), newDrone.getCapacity(), 0, newDrone.getArmor(), 15, 15));

                    case HighSpeedDrone newDrone ->
                        drones.add(new HighSpeedDrone(drones.size() + 1, newDrone.getSpeed(), newDrone.getOriginalHP(),
                                newDrone.getValue(), newDrone.getCapacity(), 1, newDrone.getSpeedMultiplier(),
                                newDrone.getDodgeChance(), 15, 15));

                    default -> throw new IllegalArgumentException();
                }
            }
        }
        NeutralizedDrones.clear();
    }

    /*
     * Shows stats at the end of a simulation
     */
    public void showStats() {
        // Loop to count how many drones aren't neutralized
        for (DeliveryDrone drone : drones) {
            if (drone.getState() == DroneState.DOCKED) {
                activeDrones++;
            }
        }

        // Calculating relevant information in regards to statst
        double totaldrones = activeDrones + crashDrones.size() + robbedDrones.size();
        double DronesDelivered = activeDrones / totaldrones * 100;
        double crashDronesPercent = crashDrones.size() / totaldrones * 100;
        double robbedDronesPercent = robbedDrones.size() / totaldrones * 100;
        double operationalCosts = lostValueFromDrones + costOfNewDrones;
        double profits = revenue - operationalCosts;
        System.out.printf("%n%s%d", "Number of drones that delievered all orders: ", activeDrones);
        System.out.printf("%n%s%.2f%s", "Percentage of drones that delivered all orders: ", DronesDelivered, "%");
        System.out.printf("%n%s%d", "Number of drones that were neutralized to crashing: ", crashDrones.size());
        System.out.printf("%n%s%.2f%s", "Percentage of drones that were neutralized to crashing: ", crashDronesPercent,
                "%");
        System.out.printf("%n%s%d", "Number of times drones intersected and missed a crash: ", missedCrashes);
        System.out.printf("%n%s%d", "Number of drones that were neutralized to robbers: ", robbedDrones.size());
        System.out.printf("%n%s%.2f%s", "Percentage of drones that were neturalized to robberies: ",
                robbedDronesPercent, "%");
        System.out.printf("%n%s%.2f", "Total revenue made by AmaZonaZZ: ", revenue);
        System.out.printf("%n%s%.2f", "Total operation costs: ", operationalCosts);
        System.out.printf("%n%s%.2f", "Total profits: ", profits);
    }

    public boolean checkEnd(DeliveryDrone drone) {
        return drone.getState() == DroneState.DOCKED || drone.getState() == DroneState.NEUTRALIZED;
    }

}

/*
 * Switch case loop checks:
 * if docked and no order, assign order = switch state: to transit
 * if in transit and no order = assign order
 * if in trans,has order = move to warehouse
 * if in trans, has order, and is carrying = move to order location
 * if in transit, but has no order and is no available order = move to hq
 */
