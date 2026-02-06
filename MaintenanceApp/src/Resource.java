package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class InputManager {
    public static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private InputManager() {}
    public static String readLine() throws Exception {
        return READER.readLine();
    }
}

class Resource {

    public static void mainMenu() {
        System.out.println("----Layout Maintenance App----");
        System.out.println("1. Admin login");
        System.out.println("2. Owner login");
        System.out.println("3. Exit");
        System.out.println("------------------------------");
    }

    public static void adminMenu() {
        System.out.println("-------------------------");
        System.out.println("1. Add Site");
        System.out.println("2. Edit Site");
        System.out.println("3. Remove Site");
        System.out.println("4. View All Sites");
        System.out.println("5. View All Pending");
        System.out.println("6. Approve Status");
        System.out.println("7. Collect Maintenance");
        System.out.println("8. Logout");
        System.out.println("-------------------------");
    }

    public static void userMenu() {
        System.out.println("-------------------------");
        System.out.println("1. View Sites");
        System.out.println("2. Update details");
        System.out.println("3. Add Deposit amount");
        System.out.println("4. Logout");
        System.out.println("-------------------------");
    }

    public static double calculateMaintenance(String siteType, double area) {
        if (siteType.equalsIgnoreCase("OpenSite")) {
            return area * 6;
        } else {
            return area * 9;
        }
    }
}
