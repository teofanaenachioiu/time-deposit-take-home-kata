package org.ikigaidigital.domain.model;

public class TimeDeposit {
    private int id; // should be Long or UUID
    private String planType; // should be enum
    private Double balance; // should be BigDecimal
    private int days;

    public TimeDeposit(int id, String planType, Double balance, int days) {
        this.id = id;
        this.planType = planType;
        this.balance = balance;
        this.days = days;
    }

    public int getId() { return id; }

    public String getPlanType() {
        return planType;
    }

    public Double getBalance() {
        return balance;
    }

    public int getDays() {
        return days;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
