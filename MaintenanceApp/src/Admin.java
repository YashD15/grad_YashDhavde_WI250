package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Admin implements AdminDAO{
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private Connection conn;
    private static Admin admin;
    private Admin() {
        try {
            this.conn = DBManager.getInstance().getConnection();
        }
        catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }
    public static Admin getInstance() {
        if (admin == null)
            admin = new Admin();
        return admin;
    }

    public void adminLogin() {
        try {
            System.out.print("Username: ");
            String username = InputManager.readLine();
            System.out.print("Password: ");
            String password = InputManager.readLine();
            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                System.out.println("Admin login successful");
                adminMenu();
            } else {
                System.out.println("Invalid credentials");
            }
        }
        catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }

    public void adminMenu() {
        try {
            boolean admin = true;
            int choice;
            while (admin) {
                Resource.adminMenu();
                System.out.print("Enter Choice: ");
                choice = Integer.parseInt(InputManager.readLine());
                switch (choice) {
                    case 1: addNewSite(); break;
                    case 2: editSite(); break;
                    case 3: removeSite(); break;
                    case 4: viewAllSites(); break;
                    case 5: viewPending(); break;
                    case 6: statusSetter(); break;
                    case 7: collectMaintenance(); break;
                    case 8:
                        System.out.println("= Admin Logout");
                        admin = false;
                        break;
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: " +e.getMessage());
        }
    }

    // DONE: WORKING
    public void addNewSite() {
        double area;
        int areaLimit;
        String siteType;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM sites");
            rs.next();
            int totalCount = rs.getInt(1);
            if (totalCount >= 35) {
                System.out.println("Maximum 35 sites reached");
                return;
            }

            System.out.println("\nArea Options Available:");
            Statement countStmt = conn.createStatement();
            ResultSet countRs = countStmt.executeQuery(
                    "SELECT area, COUNT(*) as count FROM sites GROUP BY area ORDER BY area DESC"
            );

            int i=1;
            while (countRs.next()) {
                area = countRs.getDouble("area");
                int count = countRs.getInt("count");
                int limit = (area == 2400) ? 10 : (area == 1500) ? 10 : 15;
                System.out.println(i+". "+area + " sqft: Limit " + count + "/" + limit);
                i++;
            }
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
                    "SELECT area, COUNT(*) as count FROM sites WHERE area = ? GROUP BY area"
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

            System.out.println("\nSite Types: 1.Villa 2.Apartment 3.Independent House 4.Open Site");
            System.out.print("Choice: ");
            int typeChoice = Integer.parseInt(InputManager.readLine());
            switch (typeChoice) {
                case 1: siteType = "Villa"; break;
                case 2: siteType = "Apartment"; break;
                case 3: siteType = "IndependentHouse"; break;
                case 4: siteType = "OpenSite"; break;
                default:
                    System.out.println("Invalid type");
                    return;
            }

            System.out.print("Owner Name: ");
            String ownerName = InputManager.readLine();
            System.out.print("Password: ");
            String ownerPassword = InputManager.readLine();
            System.out.print("Enter initial Deposit amount: ");
            double deposit = Integer.parseInt(InputManager.readLine());

            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO sites (type, area, ownername, ownerpassword, deposit) VALUES (?, ?, ?, ?, ?) RETURNING id"
            );
            pstmt.setString(1, siteType);
            pstmt.setDouble(2, area);
            pstmt.setString(3, ownerName);
            pstmt.setString(4, ownerPassword);
            pstmt.setDouble(5, deposit);

            ResultSet insertRs = pstmt.executeQuery();
            insertRs.next();
            int siteId = insertRs.getInt(1);

            double maintenance = Resource.calculateMaintenance(siteType, area);

            System.out.println("= Site added successfully wtih details: ");
            System.out.println("Site ID: " + siteId);
            System.out.println("Area: " + area + " sqft");
            System.out.println("Monthly Maintenance: Rs." + maintenance +"\n");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // DONE: WORKING
    public void viewAllSites() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sites ORDER BY id");
            System.out.println("= ALL SITES");
            System.out.printf("%-7s %-18s %-12s %-8s %-15s %-10s %-15s %-10s %-10s%n",
                    "SiteID", "Type", "Owner", "Area", "Maintenance", "Deposit", "MaintenanceRem", "Updates", "Status");

            while (rs.next()) {
                int siteId = rs.getInt("id");
                String siteType = rs.getString("type");
                String ownerName = rs.getString("ownername");
                double area = rs.getDouble("area");
                double maintenance = Resource.calculateMaintenance(siteType, area);
                double deposit = rs.getDouble("deposit");
                double maintenanceRem = rs.getDouble("maintenanceRem");
                String updates = rs.getString("update_type");
                String status = rs.getString("update_status");

                System.out.printf("%-7d %-18s %-12s %-8.1f %-15.1f %-10.1f %-15.1f %-10s %-10s%n",
                        siteId, siteType, ownerName, area, maintenance, deposit, maintenanceRem, updates, status);
            }

            System.out.println("\nArea-wise Site Count:");
            Statement countStmt = conn.createStatement();
            ResultSet countRs = countStmt.executeQuery(
                    "SELECT area, COUNT(*) as count FROM sites GROUP BY area ORDER BY area DESC"
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

    // WORKING: REQUIRED MORE UPDATES
    public void editSite() {
        String updateQuery;
        String newValue = "";
        try {
            System.out.print("Enter Site ID to Edit: ");
            int siteId = Integer.parseInt(InputManager.readLine());
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM sites WHERE id = ?");
            pstmt.setInt(1, siteId);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("= Site not found");
                return;
            }

            String siteType = rs.getString("type");
            String ownerName = rs.getString("ownername");
            double area = rs.getDouble("area");

            double maintenance = Resource.calculateMaintenance(siteType, area);

            System.out.println("= SITE TO EDIT");
            System.out.println("Site ID: " + siteId+ "\t\t\t\tSite Type: " + siteType);
            System.out.println("Owner: " + ownerName + "\t\t\tArea: " + area + " sqft");
            System.out.println("Monthly Maintenance: Rs." + maintenance);

            System.out.println("\nEdit: 1.Site Type 2.Owner Name");
            System.out.print("Choice: ");
            int choice = Integer.parseInt(InputManager.readLine());

            switch (choice) {
                case 1:
                    System.out.println("Types: 1.Villa 2.Apartment 3.Independent House 4.Open Site");
                    System.out.print("Choice: ");
                    int typeChoice = Integer.parseInt(InputManager.readLine());
                    switch (typeChoice) {
                        case 1: newValue = "Villa"; break;
                        case 2: newValue = "Apartment"; break;
                        case 3: newValue = "IndependentHouse"; break;
                        case 4: newValue = "OpenSite"; break;
                    }
                    updateQuery = "UPDATE sites SET type = ? WHERE id = ?";
                    break;
                case 2:
                    System.out.print("New Owner Name: ");
                    newValue = InputManager.readLine();
                    updateQuery = "UPDATE sites SET ownername = ? WHERE id = ?";
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

    // DONE: WORKING
    public void removeSite() {
        try {
            System.out.print("Enter Site ID to Remove: ");
            int siteId = Integer.parseInt(InputManager.readLine());
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM sites WHERE id = ?");
            pstmt.setInt(1, siteId);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("= Site not found");
                return;
            }

            String siteType = rs.getString("type");
            double area = rs.getDouble("area");
            double maintenance = Resource.calculateMaintenance(siteType, area);

            System.out.println("= SITE TO REMOVE");
            System.out.println("Site ID: " + siteId+ "\t\t\t\tSite Type: " + siteType);
            System.out.println("Owner: " + rs.getString("ownername") + "\t\t\tArea: " + area + " sqft");
            System.out.println("Monthly Maintenance: Rs." + maintenance);

            System.out.print("Confirm? 1.Yes 2.No: ");
            int ch = Integer.parseInt(InputManager.readLine());

            if (ch==1) {
                PreparedStatement pstmt2 = conn.prepareStatement("DELETE FROM sites WHERE id = ?");
                pstmt2.setInt(1, siteId);
                int rows = pstmt2.executeUpdate();
                if (rows > 0)
                    System.out.println("= Site removed");
                else
                    System.out.println("= Site not found");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // DONE: WORKING
    public void viewPending() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, ownername, update_type FROM sites WHERE update_status='PENDING'");
            if(!rs.next())
                System.out.println("= No record with status 'PENDING'.");
            else {
                System.out.println("= All site with status 'PENDING' are: ");
                do {
                    System.out.println("Site ID: "+rs.getInt("id")+"\t\tName: "+rs.getString("ownername")+"\t\tUpdate: "+rs.getString("update_type"));
                } while(rs.next());
            }
        }
        catch (Exception e) {
            System.out.println("Error: " +e.getMessage());
        }
    }

    // DONE: WORKING
    public void statusSetter() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sites WHERE update_status='PENDING'");
            if(!rs.next())
                System.out.println("= No record with status 'PENDING'.");
            else {
                System.out.println("= All site with status 'PENDING' are: ");
                do {
                    int id = rs.getInt("id");
                    String update_type = rs.getString("update_type");
                    String update_value = rs.getString("update_value");
                    if(update_type.equals("Password"))
                        System.out.println("Site ID: "+id+"\t\tName: "+rs.getString("ownername")+"\t\tType: "+rs.getString("type")+"\t\tDeposit: "+rs.getInt("deposit")+"\t\tUpdate Type: "+update_type);
                    else
                        System.out.println("Site ID: "+id+"\t\tName: "+rs.getString("ownername")+"\t\tType: "+rs.getString("type")+"\t\tDeposit: "+rs.getInt("deposit")+"\t\tUpdate Type: "+update_type+"\t\tUpdate Value: "+update_value);
                    System.out.print("Do you want to change status? 1.Approve 2.Reject 3.Ignore :: ");
                    int status = Integer.parseInt(InputManager.readLine());
                    PreparedStatement pstmt;
                    if(status == 1) {
                        if(update_type.equals("Password")) {
                            pstmt = conn.prepareStatement("UPDATE sites SET ownerpassword = ?, update_status = 'APPROVED', update_type = null, update_value = null WHERE id = ?");
                            pstmt.setString(1, update_value);
                        }
                        else {
                            pstmt = conn.prepareStatement("UPDATE sites SET type = ?, update_status = 'APPROVED', update_type = null, update_value = null WHERE id = ?");
                            pstmt.setString(1, update_value);
                        }
                        pstmt.setInt(2, id);
                        pstmt.executeUpdate();
                        return;
                    }
                    else if(status == 2) {
                        pstmt = conn.prepareStatement("UPDATE sites SET update_status = 'REJECTED', update_type = null, update_value = null WHERE id = ?");
                        pstmt.setInt(1, id);
                        pstmt.executeUpdate();
                    }
                    else
                        System.out.println("No action preformed.");
                } while (rs.next());
            }
        }
        catch (Exception e) {
            System.out.println("Error: " +e.getMessage());
        }
    }

    // WORKING: STATIC VALUE CALCULATION, NO TRACKING YET
    public void collectMaintenance() {
        double total = 0;
        double area;
        String site_type;
        int sites = 0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT area, type FROM sites");
            while(rs.next()) {
                area = rs.getDouble(1);
                site_type = rs.getString(2);
                total += Resource.calculateMaintenance(site_type, area);
                sites++;
            }
            System.out.println("= Total Maintenance collected from " +sites+" sites: "+total);
        }
        catch (Exception e) {
            System.out.println("Error: " +e.getMessage());
        }
    }
}