package org.example;

interface AdminDAO {
    void adminLogin();
    void addNewSite();
    void viewAllSites();
    void editSite();
    void removeSite();
    void viewPending();
    void collectMaintenance();
    void statusSetter();
}
