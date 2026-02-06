package org.example;

interface OwnerDAO {
    void ownerLogin();
    void ownerMenu(int username);
    void viewSites(int siteId);
    void requestUpdate(int siteId);
    void addDeposit(int siteId);
    void seeMaintenances();
}