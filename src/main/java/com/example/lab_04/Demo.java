package com.example.lab_04;

public class Demo {
    public static void main(String[] args) {
        DAO dao = new DAO();
        MaxSalary maxSalary = new MaxSalary(dao);
        System.out.println(dao.findAllWorkers("Firm1"));
        System.out.println(maxSalary.maxSalary("Firm1"));
    }
}
