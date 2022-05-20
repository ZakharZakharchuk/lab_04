package com.example.lab_04.models;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private String name;
    private Employee head;
    private List<Employee> workers;

    public Department(String name, Employee head, List<Employee> workers) {
        this.name = name;
        this.head = head;
        this.workers = workers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getHead() {
        return head;
    }

    public void setHead(Employee head) {
        this.head = head;
    }

    public List<Employee> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Employee> workers) {
        this.workers = workers;
    }

    public int maxSalaryInDepartment() {
        int max = workers.stream()
                .mapToInt(Employee::getSalary)
                .max()
                .orElse(0);
        return Math.max(max, head.getSalary());
    }

    public Department findWorkerWithBigSalary() {
        int max = workers.stream().mapToInt(Employee::getSalary).max().orElse(0);
        return max > head.getSalary() ? this : null;
    }
    public List<Employee>allEmployees(){
        List<Employee>allWorkers = new ArrayList<>();
        allWorkers.add(head);
        allWorkers.addAll(workers);
        return allWorkers;
    }
}
