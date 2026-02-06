package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class Owner implements OwnerDAO {
    private static Owner obj;
    private Connection conn;
    private Owner() {
        try {
            this.conn = DBManager.getInstance().getConnection();
        }
        catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }
    public static Owner getInstance() {
        if (obj == null) {
            obj = new Owner();
        }
        return obj;
    }
    public void ownerLogin() {
        try {
            System.out.print("Username (Site ID): ");
            int username = Integer.parseInt(InputManager.readLine());
            System.out.print("Password: ");
            String password = InputManager.readLine();
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM sites WHERE id = ? AND ownerpassword = ?"
            );
            pstmt.setInt(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Login successful");
                ownerMenu(username);
            } else {
                System.out.println("Invalid credentials");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void ownerMenu(int username) {
        try {
            boolean owner = true;
            int choice;
            while (owner) {
                Resource.userMenu();
                System.out.print("Enter Choice: ");
                choice = Integer.parseInt(InputManager.readLine());
                switch (choice) {
                    case 1:
                        viewSites(username);
                        break;
                    case 2:
                        requestUpdate(username);
                        break;
                    case 3:
                        addDeposit(username);
                        break;
                    case 4:
                        System.out.println("= Owner Logout");
//                        db.closeConnection();
                        owner = false;
                        break;
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }

    // DONE: WORKING
    public void viewSites(int siteId) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM sites WHERE id = ?");
            pstmt.setInt(1, siteId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String siteType = rs.getString("type");
                String ownerName = rs.getString("ownername");
                double area = rs.getDouble("area");
                double deposit = rs.getDouble("deposit");
                double maintenanceRem = rs.getDouble("maintenanceRem");
                String update_type = rs.getString("update_type");
                String update_value = rs.getString("update_value");
                String status = rs.getString("update_status");
                double maintenance = Resource.calculateMaintenance(siteType, area);

                System.out.println("= MY SITE");
                System.out.println("Site ID: " + siteId + "\tOwner: " + ownerName);
                System.out.println("Site Type: " + siteType + "\tArea: " + area + " sqft");
                System.out.println("Monthly Maintenance: Rs." + maintenance + "\tDeposit Amount: "+ deposit);
                System.out.println("Maintenance Remaining: "+ maintenanceRem + "\tStatus: "+ status);
                System.out.println("Update Type: "+ update_type + "\tUpdate Value: "+ update_value);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // DONE: WORKING
    public void requestUpdate(int siteId) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT update_status FROM sites WHERE id = ?");
            pstmt.setInt(1, siteId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                String status = rs.getString("update_status");
                if(status.equals("PENDING")){
                    System.out.println("= Already one change is under PENDING status.");
                }
                else {
                    rs.close();
                    pstmt.close();
                    System.out.println("Request update for: 1.Password, 2. Site Type");
                    System.out.print("Choice: ");
                    int choice = Integer.parseInt(InputManager.readLine());

                    String update_type = "";
                    String update_value = "";

                    switch (choice) {
                        case 1:
                            System.out.print("New Password: ");
                            update_value = InputManager.readLine();
                            update_type = "Password";
                            break;
                        case 2:
                            System.out.println("Types: 1.Villa 2.Apartment 3.Independent House 4.Open Site");
                            System.out.print("Choice: ");
                            int typeChoice = Integer.parseInt(InputManager.readLine());
                            switch (typeChoice) {
                                case 1: update_value = "Villa"; update_type = "Site"; break;
                                case 2: update_value = "Apartment"; update_type = "Site"; break;
                                case 3: update_value = "IndependentHouse"; update_type = "Site"; break;
                                case 4: update_value = "OpenSite"; update_type = "Site"; break;
                            }
                            break;
                        default:
                            System.out.println("Invalid choice");
                            return;
                    }
                    PreparedStatement pstmt2 = conn.prepareStatement(
                            "UPDATE sites SET update_type = ?, update_value = ? ,update_status = 'PENDING' WHERE id = ?"
                    );
                    pstmt2.setString(1, update_type);
                    pstmt2.setString(2, update_value);
                    pstmt2.setInt(3, siteId);
                    pstmt2.executeUpdate();

                    System.out.println("= Update request submitted. Wait for admin approval.");
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // WORKING: FLOW TO BE MODIFIED
    public void addDeposit(int siteId) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT update_status FROM sites WHERE id = ?");
            pstmt.setInt(1, siteId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                String status = rs.getString("update_status");
                if(status.equals("PENDING")){
                    System.out.println("= Already one change is under PENDING status.");
                }
                else {
                    pstmt.close();
                    rs.close();
                    System.out.println("Enter amount to deposit: ");
                    double amount = Double.parseDouble(InputManager.readLine());
                    System.out.println("Deposit amount added: "+amount);
                    PreparedStatement ps = conn.prepareStatement("UPDATE sites SET deposit = deposit + ? WHERE id = ?");
                    ps.setDouble(1, amount);
                    ps.setInt(2, siteId);
                    ps.executeUpdate();
                    System.out.println("= Deposit amount added Successfully.");
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }
    }
    public void seeMaintenances() {
        try {
            System.out.println("See Maintenance history");
        }
        catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }
    }
}
