
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import Drones.*;
import Objects.*;

import java.util.HashMap;
import java.util.List;

public class ReadFiles {
    private ArrayList<DeliveryDrone> drones;
    private ArrayList<Order> orders;
    private ArrayList<Item> items;
    private ArrayList<Warehouse> warehouses;
    private int i;

    /*
     * Constructor to create the arraylists used
     */
    public ReadFiles() {
        drones = new ArrayList<DeliveryDrone>();
        orders = new ArrayList<Order>();
        items = new ArrayList<Item>();
        warehouses = new ArrayList<Warehouse>();
    }

    /* 
     * Reads information from a txt file and creates all the initial drones from it
     */
    public ArrayList<DeliveryDrone> droneReadFile() {
        String filePath = "Data\\drones.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(" ");

                // Parsing the fields that the drones have in common
                int id = Integer.parseInt(fields[0]);
                int speed = Integer.parseInt(fields[1]);
                int hitPoints = Integer.parseInt(fields[2]);
                int capacity = Integer.parseInt(fields[3]);
                int value = Integer.parseInt(fields[4]);
                int type = Integer.parseInt(fields[5]);

                // if the type field is 0 then ArmorChance is at field 6
                if (type == 0) {
                    double armorChance = Double.parseDouble(fields[6]);
                    ArmoredDrone drone = new ArmoredDrone(id, speed, hitPoints, value, capacity, type, armorChance, 15,
                            15);
                    drones.add(drone);

                    // if the type field is 1 then speedMultiplier is at field 6 and dodgeChance is
                    // at field 7
                } else if (type == 1) {
                    double speedMultiplier = Double.parseDouble(fields[6]);
                    double dodgeChance = Double.parseDouble(fields[7]);

                    // Creates a new HighSpeedDrone object and adds it to the drones list
                    HighSpeedDrone drone = new HighSpeedDrone(id, speed, hitPoints, value, capacity, type,
                            speedMultiplier, dodgeChance, 15, 15);
                    drones.add(drone);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return drones;
    }

    /*
     * Creates order objects from the given txt file
     */
    public ArrayList<Order> orderReadFile() {
        String orderFilePath = "Data\\orders.txt";
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(orderFilePath), "UTF-8"))) {
            String nextOrder;
            while ((nextOrder = br.readLine()) != null) {

                String[] orderList = nextOrder.split(" ");

                // Concatenating date and time into one string
                String tempTimeStamp = orderList[0] + " " + orderList[1];

                // Creating formatter, allowing us to convert the timestamp string into
                // LocalDateTime object
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                LocalDateTime timestamp = LocalDateTime.parse(tempTimeStamp, formatter);

                // Converting string into order positions on grid
                double orderX = Double.parseDouble(orderList[2]);
                double orderY = Double.parseDouble(orderList[3]);

                // Stringbuilder and for loop, allowing us to put together the rest of the list
                // into one string
                StringBuilder Builder = new StringBuilder(orderList[4]);
                for (i = 5; i < orderList.length; i++) {
                    Builder.append(" " + orderList[i]);
                }
                // Converting to string
                String orderItem = Builder.toString();

                // Making an order out of the String array contents
                Order order = new Order(timestamp, orderX, orderY, orderItem);

                // adding to list
                orders.add(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /*
    * creates item objects from the given txt file
    */
    public ArrayList<Item> itemReadFile() {
        String ItemFilePath = "Data\\items.txt";
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(ItemFilePath), "UTF-8"))) {
            String nextItem;
            while ((nextItem = br.readLine()) != null) {
                String[] currentItem = nextItem.split(" ");

                // Taking last two Strings in array and putting into variables
                double Value = Double.parseDouble(currentItem[currentItem.length - 1]);
                double Weight = Double.parseDouble(currentItem[currentItem.length - 2]);

                // Loop forward through list to currentitem.length-2, and concatenate together.
                StringBuilder itemBuilder = new StringBuilder(currentItem[0]);
                for (i = 1; i <= currentItem.length - 3; i++) {
                    itemBuilder.append(" " + currentItem[i]);
                }
                // Converting stringbuilder to string containing name
                String itemName = itemBuilder.toString();

                Item item = new Item(itemName, Weight, Value);
                items.add(item);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    /*
     * Creates warehouse objects from the given txt file
     */
    public ArrayList<Warehouse> warehouseReadFile() {
        // new warehouse HashMap, to store the werhouse data.
        // warehouse names are the keys, and values are warehouse objects
        String WarehouseFilePath = "Data\\warehouses.txt";
        /*
         * Because of how the warehouses.txt file is made, InputStreamreader
         * is added to the line so it is able to specify the encoding of the file
         * if this is not done here, then some items might get "??".
         * Example of that is Chrismas décor will become Christmas d??cor,
         * because UTF-8 is not specified, when reading the .txt file.
         */
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(WarehouseFilePath), "UTF-8"))) {
            String nextWarehouse;
            // reads each line, until a null returns
            while ((nextWarehouse = br.readLine()) != null) {
                // Split the line at the first occurrence of a parenthesis
                // this seperates the warehouse and it's coordinates, from the warehouse stock
                int firstParenIndex = nextWarehouse.indexOf('(');
                if (firstParenIndex == -1) {
                    continue;
                }

                // Extract the warehouse info part
                // first line extracts the the warehouse information befor the first parenteses
                String warehouseInfo = nextWarehouse.substring(0, firstParenIndex).trim();

                // splits the warehouseinfo into spaces
                String[] infoParts = warehouseInfo.split(" ");

                // seperates the x and y coordinates away from the name of the warehouse
                // a joinParts method was created for this, to better seperate the name of the
                // warehouse
                String warehouseName = joinParts(infoParts, 0, infoParts.length - 2);

                // extracts the x and y coordinates
                double x = Double.parseDouble(infoParts[infoParts.length - 2]);
                double y = Double.parseDouble(infoParts[infoParts.length - 1]);

                // starts from the first parentestes to the last one.
                String stockPart = nextWarehouse.substring(firstParenIndex);

                // splits the stock down into items
                String[] stockItems = stockPart.split("\\) \\(");

                // Process the stock items and removes the parenteses

                // initizlizes a hashmap to store the stock items and their quantities
                Map<String, Integer> stock = new HashMap<>();
                for (String stockItem : stockItems) {

                    // removes any remaining parentheses
                    stockItem = stockItem.replace("(", "").replace(")", "").trim();
                    // finds the last space in the stockItem string which
                    // separetes the item name from the quanity
                    int lastSpaceIndex = stockItem.lastIndexOf(' ');
                    if (lastSpaceIndex != -1) {
                        // extracts the stock name
                        String itemName = stockItem.substring(0, lastSpaceIndex).trim();
                        // converts the string number to an integer
                        int quantity = Integer.parseInt(stockItem.substring(lastSpaceIndex + 1).trim());
                        // stores the stock in the hashmap
                        stock.put(itemName, quantity);
                    }
                }

                // Create a new Warehouse object and add to the map
                Warehouse warehouse = new Warehouse(warehouseName, x, y, stock);
                warehouses.add(warehouse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return warehouses;
    }

    /*
     * Creates crime objects from the given crime file
     * @throws FileNotFoundException if the given txt file is not found
     * @throws IOException, if there is an input output error
     */
    public ArrayList<Crime> crimeReadFile() throws FileNotFoundException, IOException {
        ArrayList<Crime> crimeList = new ArrayList<>();
        String crimeFilePath = "Data\\crime.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(crimeFilePath))) {
            String nextDistrict;
            while ((nextDistrict = br.readLine()) != null) {
                if (nextDistrict.trim().isEmpty()) {
                    continue; // Skip empty lines
                }

                int beforParenIndex = nextDistrict.indexOf('(');
                if (beforParenIndex == -1) {
                    continue; // Skip lines without '('
                }

                // Find the first and second sets of parentheses
                int firstParenIndex = nextDistrict.indexOf('(');
                int secondParenIndex = nextDistrict.indexOf('(', firstParenIndex + 1);
                int firstParenEndIndex = nextDistrict.indexOf(')', firstParenIndex);
                int secondParenEndIndex = nextDistrict.indexOf(')', secondParenIndex);

                if (firstParenIndex == -1 || secondParenIndex == -1 || firstParenEndIndex == -1
                        || secondParenEndIndex == -1) {
                    continue; // Skip lines that don't have two sets of parentheses
                }
                // the distract of crime.
                String crimeDistricInfo = nextDistrict.substring(0, firstParenIndex).trim();
                // emp drone parentheses
                String empDroneStats = nextDistrict.substring(firstParenIndex + 1, firstParenEndIndex).trim();
                // gatling drone parenthese
                String gatlingDroneStats = nextDistrict.substring(secondParenIndex + 1, secondParenEndIndex).trim();

                // splits the distract name
                String[] infoParts = crimeDistricInfo.split(" ");
                String crimeDistrictName = joinParts(infoParts, 0, infoParts.length - 1);
                double spawnChance = Double.parseDouble(infoParts[infoParts.length - 1]);

                // splits the emp and gatling drone parentes and adds them to a list
                List<String> empDroneList = List.of(empDroneStats.split(" "));
                List<String> gatlingDroneList = List.of(gatlingDroneStats.split(" "));

                // drone chance variable for choosing which kind of crime drone is created

                // Extracting values one by one from the empDroneList
                double empDroneChance = Double.parseDouble(empDroneList.get(0));
                int empValue = Integer.parseInt(empDroneList.get(1));
                double empMinSpeed = Double.parseDouble(empDroneList.get(2));
                double empMaxSpeed = Double.parseDouble(empDroneList.get(3));
                int empMinHitPoints = Integer.parseInt(empDroneList.get(4));
                int empMaxHitPoints = Integer.parseInt(empDroneList.get(5));
                double volt = Double.parseDouble(empDroneList.get(6));

                // Extracting values one by one from the gatlingDroneList.
                double gatlingDroneChance = Double.parseDouble(gatlingDroneList.get(0));
                int gatlingvalue = Integer.parseInt(gatlingDroneList.get(1));
                double gatlingMinSpeed = Double.parseDouble(gatlingDroneList.get(2));
                double gatlingMaxSpeed = Double.parseDouble(gatlingDroneList.get(3));
                int gatlingMinHitPoints = Integer.parseInt(gatlingDroneList.get(4));
                int gatlingMaxHitPoints = Integer.parseInt(gatlingDroneList.get(5));
                int damage = Integer.parseInt(gatlingDroneList.get(6));

                /*
                 * Creates two new lists which will each hold its
                 * own drone value for emp and gatling
                 * then create a third list, which will hold
                 * distract name, and their spawn chance,
                 * and their respectiv drone lists, of value.
                 */
                List<Object> finalEmpDroneList = new ArrayList<>();
                List<Object> finalGatlingDroneList = new ArrayList<>();

                finalEmpDroneList.add(Double.parseDouble(empDroneList.get(0)));
                finalEmpDroneList.add(Integer.parseInt(empDroneList.get(1)));
                finalEmpDroneList.add(Double.parseDouble(empDroneList.get(2)));
                finalEmpDroneList.add(Double.parseDouble(empDroneList.get(3)));
                finalEmpDroneList.add(Integer.parseInt(empDroneList.get(4)));
                finalEmpDroneList.add(Integer.parseInt(empDroneList.get(5)));
                finalEmpDroneList.add(Double.parseDouble(empDroneList.get(6)));

                finalGatlingDroneList.add(Double.parseDouble(gatlingDroneList.get(0)));
                finalGatlingDroneList.add(Integer.parseInt(gatlingDroneList.get(1)));
                finalGatlingDroneList.add(Double.parseDouble(gatlingDroneList.get(2)));
                finalGatlingDroneList.add(Double.parseDouble(gatlingDroneList.get(3)));
                finalGatlingDroneList.add(Integer.parseInt(gatlingDroneList.get(4)));
                finalGatlingDroneList.add(Integer.parseInt(gatlingDroneList.get(5)));
                finalGatlingDroneList.add(Integer.parseInt(gatlingDroneList.get(6)));

                Crime crime = new Crime(crimeDistrictName, spawnChance, finalEmpDroneList, finalGatlingDroneList);
                crimeList.add(crime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return crimeList;
    }

    /*
     * Join method can only handle two variables, therefore this method was created
     * as it can handle several variables at the same time,
     */
    private static String joinParts(String[] parts, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            if (i > start) {
                sb.append(" ");
            }
            sb.append(parts[i]);
        }
        return sb.toString();
    }
}
