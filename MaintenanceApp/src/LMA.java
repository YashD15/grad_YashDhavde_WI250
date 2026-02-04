package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Resource {
    public static void mainMenu() {
        System.out.println("1. Admin login");
        System.out.println("2. Owner login");
        System.out.println("3. Exit");
    }
    public static void adminMenu() {
        System.out.println("-------------------------");
        System.out.println("1. Add Site");
        System.out.println("2. Edit Site");
        System.out.println("3. Remove Site");
        System.out.println("4. View All Sites");
        System.out.println("5. View Pending (Specific)");
        System.out.println("6. View Pending (All)");
        System.out.println("7. Approve Status");
        System.out.println("8. Collect Maintenance");
        System.out.println("9. Logout");
        System.out.println("-------------------------");
    }
    public static void userMenu() {
        System.out.println("-------------------------");
        System.out.println("1. View Sites");
        System.out.println("2. Update details");
        System.out.println("3. Logout");
        System.out.println("-------------------------");
    }
    public static double calculateMaintenance(String siteType, double area) {
        if (siteType.equalsIgnoreCase("open_site")) {
            return area * 6;
        } else {
            return area * 9;
        }
    }
}

//DONE
class InputManager {
    public static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private InputManager() {}
    public static String readLine() throws Exception {
        return READER.readLine();
    }
}

class Site {
    private Site(){}
    private static Site site = null;
    public static Site getInstance() {
        if (site == null)
            site = new Site();
        return site;
    }

    public void addNewSite() {
        double area = 0;
        int areaLimit = 0;
        String siteType = "";
        try {
            Connection conn = DBManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM layout_sites");
            rs.next();
            int totalCount = rs.getInt(1);
            if (totalCount >= 35) {
                System.out.println("Maximum 35 sites reached");
                return;
            }
            System.out.println("\nArea Options:");
            System.out.println("1. 2400 sqft (40x60) - Limit: 10 sites");
            System.out.println("2. 1500 sqft (30x50) - Limit: 10 sites");
            System.out.println("3. 1200 sqft (30x40) - Limit: 15 sites");
            System.out.print("Choose area: ");
            int areaChoice = Integer.parseInt(InputManager.readLine());
                switch (areaChoice) {
                    case 1: area = 2400; areaLimit = 10; break;
                    case 2: area = 1500; areaLimit = 10; break;
                    case 3: area = 1200; areaLimit = 15; break;
                    default:
                        System.out.println("Invalid area choice");
                        return;
                }

                PreparedStatement areaCheck = conn.prepareStatement(
                        "SELECT area, COUNT(*) as count FROM layout_sites WHERE area = ? GROUP BY area"
                );
                areaCheck.setDouble(1, area);
                ResultSet areaRs = areaCheck.executeQuery();

                if (areaRs.next()) {
                    int currentCount = areaRs.getInt("count");
                    if (currentCount >= areaLimit) {
                        System.out.println("Area limit reached: " + area + " sqft sites = " + currentCount + "/" + areaLimit);
                        return;
                    }
                }

                System.out.println("Site Types: 1.Villa 2.Apartment 3.Independent House 4.Open Site");
                System.out.print("Choice: ");
                int typeChoice = Integer.parseInt(InputManager.readLine());
                switch (typeChoice) {
                    case 1: siteType = "villa"; break;
                    case 2: siteType = "apartment"; break;
                    case 3: siteType = "independent_house"; break;
                    case 4: siteType = "open_site"; break;
                    default:
                        System.out.println("Invalid type");
                        return;
                }

                System.out.print("Owner Name: ");
                String ownerName = InputManager.readLine();
                System.out.print("Password: ");
                String ownerPassword = InputManager.readLine();

                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO layout_sites (site_type, area, owner_name, owner_password) VALUES (?, ?, ?, ?) RETURNING id"
                );
                pstmt.setString(1, siteType);
                pstmt.setDouble(2, area);
                pstmt.setString(3, ownerName);
                pstmt.setString(4, ownerPassword);

                ResultSet insertRs = pstmt.executeQuery();
                insertRs.next();
                int siteId = insertRs.getInt(1);

                double maintenance = Resource.calculateMaintenance(siteType, area);

                System.out.println("Site added successfully");
                System.out.println("Site ID: " + siteId);
                System.out.println("Area: " + area + " sqft");
                System.out.println("Monthly Maintenance: Rs." + maintenance);

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewAllSites() {
        try {
            Connection conn = DBManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM layout_sites ORDER BY id");

            System.out.println("\nALL SITES");
            System.out.printf("%-8s %-20s %-15s %-12s %-15s%n", "Site ID", "Type", "Owner", "Area", "Maintenance");

            while (rs.next()) {
                int siteId = rs.getInt("id");
                String siteType = rs.getString("site_type");
                String ownerName = rs.getString("owner_name");
                double area = rs.getDouble("area");

                double maintenance = Resource.calculateMaintenance(siteType, area);

                System.out.printf("%-8d %-20s %-15s %-12.0f Rs.%-12.2f%n",
                        siteId, siteType, ownerName, area, maintenance);
            }

            System.out.println("\nArea-wise Site Count:");
            Statement countStmt = conn.createStatement();
            ResultSet countRs = countStmt.executeQuery(
                    "SELECT area, COUNT(*) as count FROM layout_sites GROUP BY area ORDER BY area DESC"
            );

            while (countRs.next()) {
                double area = countRs.getDouble("area");
                int count = countRs.getInt("count");
                int limit = (area == 2400) ? 10 : (area == 1500) ? 10 : 15;
                System.out.println(area + " sqft: " + count + "/" + limit);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void editSite() throws Exception{
        String updateQuery = "";
        String newValue = "";
        System.out.print("Enter Site ID to Edit: ");
        int siteId = Integer.parseInt(InputManager.readLine());

        try {
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM layout_sites WHERE id = ?");
            pstmt.setInt(1, siteId);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Site not found");
                return;
            }

            String siteType = rs.getString("site_type");
            String ownerName = rs.getString("owner_name");
            double area = rs.getDouble("area");
            double totalCollected = rs.getDouble("total_collected");
            int timesCollected = rs.getInt("times_collected");

            double maintenance = Resource.calculateMaintenance(siteType, area);

            System.out.println("\nSITE TO EDIT");
            System.out.println("Site ID: " + siteId+ "\t\t\t\tSite Type: " + siteType);
            System.out.println("Owner: " + ownerName + "\t\t\tArea: " + area + " sqft");
            System.out.println("Monthly Maintenance: Rs." + maintenance);
            System.out.println("Total Collected: Rs." + totalCollected + "\t\tTimes Collected: " + timesCollected);

            System.out.println("\nEdit: 1.Site Type 2.Owner Name");
            System.out.print("Choice: ");
            int choice = Integer.parseInt(InputManager.readLine());

            switch (choice) {
                case 1:
                    System.out.println("Types: 1.Villa 2.Apartment 3.Independent House 4.Open Site");
                    System.out.print("Choice: ");
                    int typeChoice = Integer.parseInt(InputManager.readLine());
                    switch (typeChoice) {
                        case 1: newValue = "villa"; break;
                        case 2: newValue = "apartment"; break;
                        case 3: newValue = "independent_house"; break;
                        case 4: newValue = "open_site"; break;
                    }
                    updateQuery = "UPDATE layout_sites SET site_type = ? WHERE id = ?";
                    break;
                case 2:
                    System.out.print("New Owner Name: ");
                    newValue = InputManager.readLine();
                    updateQuery = "UPDATE layout_sites SET owner_name = ? WHERE id = ?";
                    break;
                default:
                    System.out.println("Invalid choice");
                    return;
            }

            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
            updateStmt.setString(1, newValue);
            updateStmt.setInt(2, siteId);
            updateStmt.executeUpdate();
            System.out.println("= Site updated");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeSite() throws Exception {
        System.out.print("Enter Site ID to Remove: ");
        int siteId = Integer.parseInt(InputManager.readLine());

        try {
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM layout_sites WHERE id = ?");
            pstmt.setInt(1, siteId);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Site not found");
                return;
            }

            String siteType = rs.getString("site_type");
            String ownerName = rs.getString("owner_name");
            double area = rs.getDouble("area");
            double totalCollected = rs.getDouble("total_collected");
            int timesCollected = rs.getInt("times_collected");
            double maintenance = Resource.calculateMaintenance(siteType, area);

        System.out.println("\nSITE TO REMOVE");
        System.out.println("Site ID: " + siteId+ "\t\t\t\tSite Type: " + siteType);
        System.out.println("Owner: " + ownerName + "\t\t\tArea: " + area + " sqft");
        System.out.println("Monthly Maintenance: Rs." + maintenance);
        System.out.println("Total Collected: Rs." + totalCollected + "\t\tTimes Collected: " + timesCollected);

        System.out.println("\nEdit: 1.Site Type 2.Owner Name");

        System.out.print("Confirm (yes/no): ");
        String confirm = InputManager.readLine();

        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Cancelled");
            return;
        }

            PreparedStatement pstmt2 = conn.prepareStatement("DELETE FROM layout_sites WHERE id = ?");
            pstmt2.setInt(1, siteId);

            int rows = pstmt2.executeUpdate();

            if (rows > 0)
                System.out.println("= Site removed");
            else
                System.out.println("= Site not found");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewPending() {
        try {
            System.out.print("Enter SiteID to check status: ");
            int id = Integer.parseInt(InputManager.readLine());
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT id, owner_name, update_status FROM layout_sites WHERE id=?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                System.out.println("Site ID: "+rs.getInt(1)+"\t\tName: "+rs.getString(2)+"\t\tStatus: "+rs.getString(3));
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public void viewPendingAll() {
        try {
            Connection conn = DBManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, owner_name, pending_update FROM layout_sites WHERE update_status='pending'");
            System.out.println("= All site with status 'Pending' are: ");
            while(rs.next()) {
                System.out.println("Site ID: "+rs.getInt(1)+"\t\tName: "+rs.getString(2)+"\t\tUpdate: "+rs.getString(3));
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public void statusSetter() {
        try {
            Connection conn = DBManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, owner_name, pending_update FROM layout_sites WHERE update_status='pending'");
            System.out.println("= All site with status 'Pending' are: ");
            while(rs.next()) {
                int id = rs.getInt(1);
                System.out.println("Site ID: "+id+"\t\tName: "+rs.getString(2)+"\t\tUpdate: "+rs.getString(3));
                System.out.print("Do you want to change status? 1.Approve 2.Reject 3.Ignore :: ");
                int status = Integer.parseInt(InputManager.readLine());
                if(status == 1) {
                    PreparedStatement pstmt = conn.prepareStatement("UPDATE layout_sites SET update_status = 'APPROVED', pending_update = null WHERE id = ?");
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                    return;
                }
                else if(status == 2) {
                    PreparedStatement pstmt = conn.prepareStatement("UPDATE layout_sites SET update_status = 'REJECTED', pending_update = null WHERE id = ?");
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                }
                else
                    System.out.println("No action preformed.");
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void collectMaintenance() {
        double total = 0;
        double area;
        String site_type;
        int sites = 0;
        try {
            Connection conn = DBManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT area, site_type FROM layout_sites");
            while(rs.next()) {
                area = rs.getDouble(1);
                site_type = rs.getString(2);
                total += Resource.calculateMaintenance(site_type, area);
                sites++;
            }
            System.out.println("= Total Maintenance collected from " +sites+" sites: "+total);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}

//DONE
class AdminLogin {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    static void adminLogin() throws Exception{
        System.out.print("Username: ");
        String username = InputManager.readLine();
        System.out.print("Password: ");
        String password = InputManager.readLine();
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            System.out.println("Admin login successful");
            AdminMenu.adminMenu();
        } else {
            System.out.println("Invalid credentials");
        }
    }
}

class AdminMenu {
    public static void adminMenu() throws Exception{
        boolean admin = true;
        int choice;
        Site site = Site.getInstance();
        while (admin) {
            Resource.adminMenu();
            System.out.print("Enter Choice: ");
            choice = Integer.parseInt(InputManager.readLine());
            switch (choice) {
                case 1: site.addNewSite(); break;
                case 2: site.editSite(); break;
                case 3: site.removeSite(); break;
                case 4: site.viewAllSites(); break;
                case 5: site.viewPending(); break;
                case 6: site.viewPendingAll(); break;
                case 7: site.statusSetter(); break;
                case 8: site.collectMaintenance(); break;
                case 9:
                    System.out.println("= Admin Logout");
                    admin = false;
                    break;
            }
        }
    }
}


//DONE
class OwnerLogin {
    public static void ownerLogin() throws Exception {
        System.out.print("Username (Site ID): ");
        int username = Integer.parseInt(InputManager.readLine());
        System.out.print("Password: ");
        String password = InputManager.readLine();

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","wi250");
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM layout_sites WHERE id = ? AND owner_password = ?"
            );
            pstmt.setInt(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Login successful");
                OwnerMenu.ownerMenu(username);
            } else {
                System.out.println("Invalid credentials");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

//DONE
class OwnerMenu {
    public static void ownerMenu(int username) throws Exception {
        boolean owner = true;
        int choice;
        while (owner) {
            Resource.userMenu();
            System.out.print("Enter Choice: ");
            choice = Integer.parseInt(InputManager.readLine());
            switch (choice) {
                case 1:
                    Owner.viewSites(username);
                    break;
                case 2:
                    Owner.requestUpdate(username);
                    break;
                case 3:
                    System.out.println("= Owner Logout");
                    owner = false;
                    break;
            }
        }
    }
}

class Owner {
    public static void viewSites(int siteId) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","wi250");
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM layout_sites WHERE id = ?");
            pstmt.setInt(1, siteId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String siteType = rs.getString("site_type");
                String ownerName = rs.getString("owner_name");
                double area = rs.getDouble("area");
                double totalCollected = rs.getDouble("total_collected");
                int timesCollected = rs.getInt("times_collected");

                double maintenance = Resource.calculateMaintenance(siteType, area);

                System.out.println("\nMY SITE");
                System.out.println("Site ID: " + siteId);
                System.out.println("Site Type: " + siteType);
                System.out.println("Area: " + area + " sqft");
                System.out.println("Monthly Maintenance: Rs." + maintenance);
                System.out.println("Owner: " + ownerName);
                System.out.println("Total Collected: Rs." + totalCollected);
                System.out.println("Times Collected: " + timesCollected);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void requestUpdate(int siteId) throws Exception{
        System.out.println("Request update for: 1.Password, 2. Site Type");
        System.out.print("Choice: ");
        int choice = Integer.parseInt(InputManager.readLine());

        String updateRequest = "";

        switch (choice) {
            case 1:
                System.out.print("New Password: ");
                String newPass = InputManager.readLine();
                updateRequest = "Password: " + newPass;
                break;
            case 2:
                System.out.println("Types: 1.Villa 2.Apartment 3.Independent House 4.Open Site");
                System.out.print("Choice: ");
                int typeChoice = Integer.parseInt(InputManager.readLine());
                switch (typeChoice) {
                    case 1: updateRequest = "villa"; break;
                    case 2: updateRequest = "apartment"; break;
                    case 3: updateRequest = "independent_house"; break;
                    case 4: updateRequest = "open_site"; break;
                }
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","wi250");
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE layout_sites SET pending_update = ?, update_status = 'pending' WHERE id = ?"
            );
            pstmt.setString(1, updateRequest);
            pstmt.setInt(2, siteId);
            pstmt.executeUpdate();

            System.out.println("Update request submitted. Wait for admin approval.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

















//DONE
public class LMA {
    public static void main(String args[]) throws Exception {
        int choice;
        while(true) {
            Resource.mainMenu();
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(InputManager.readLine());
            switch (choice) {
                case 1: AdminLogin.adminLogin(); break;
                case 2: OwnerLogin.ownerLogin(); break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
}