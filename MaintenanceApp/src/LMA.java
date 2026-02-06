/*
Layout Maintenance Application
        ---------------------------------------
        Types -  Villa, Apartment, Independent House, Open Site
        Total Sites - 35 Sites
        First 10 sites are of 40x60 ft size
        Next 10 sites are of 30x50 ft size
        Last 15 sites are of 30x40 ft size
        Open sites are charged 6Rs/sqft
        Occupied sites are charged 9Rs./sqft

        Admin	-
        Can add/edit/remove the owner details and site details
        Can collect the maintenance and update
        Can see the pending details of all sites or the specific site
        Can approve/reject the site owners update about their own sites
        Site Owner -
        Can only see/update the details of his/her own site (but should be approved by Admin)
*/

package org.example;

public class LMA {
    public static void main(String[] args) throws Exception {
        int choice;
        OwnerDAO o = Owner.getInstance();
        AdminDAO a = Admin.getInstance();
        while(true) {
            Resource.mainMenu();
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(InputManager.readLine());
            switch (choice) {
                case 1: a.adminLogin(); break;
                case 2: o.ownerLogin(); break;
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