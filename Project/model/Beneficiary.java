package model;

public class Beneficiary extends CommunityMember {
    private double dailyIncome;
    private String vulnerabilityStatus;

    public Beneficiary(int id, String name, String contactNumber, double dailyIncome, String vulnerabilityStatus) {
        super(id, name, contactNumber); 
        this.dailyIncome = dailyIncome;
        this.vulnerabilityStatus = vulnerabilityStatus;
    }

    public boolean isExtremePoverty() {
        return this.dailyIncome < 1.25; 
    }

    public double getDailyIncome() { return dailyIncome; }
    
    public void setDailyIncome(double dailyIncome) {
        if (dailyIncome < 0) {
            throw new IllegalArgumentException("Income cannot be negative.");
        }
        this.dailyIncome = dailyIncome;
    }

    public String getVulnerabilityStatus() { return vulnerabilityStatus; }
    public void setVulnerabilityStatus(String status) { this.vulnerabilityStatus = status; }

    @Override
    public String getRoleDescription() {
        return "Beneficiary (Status: " + vulnerabilityStatus + ")";
    }
    
    @Override
    public String toString() {
        return getName() + " | Income: ₱" + dailyIncome;
    }
}