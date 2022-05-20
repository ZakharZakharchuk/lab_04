package com.example.lab_04.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Firm {
    private String name;
    private Employee director;
    private List<Department> departments;

    public Firm(String name, Employee director, List<Department> departments) {
        this.name = name;
        this.director = director;
        this.departments = departments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getDirector() {
        return director;
    }

    public void setDirector(Employee director) {
        this.director = director;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public int maxSalaryInFirm() {
        int max = departments.stream()
                .mapToInt(Department::maxSalaryInDepartment)
                .max()
                .orElse(0);
        return Math.max(max, director.getSalary());
    }

    public List<Department> workerWithBigSalary() {
        List<Department> depart = departments.stream()
                .map(Department::findWorkerWithBigSalary)
                .filter(Objects::nonNull)
                .toList();
        return depart;
    }

    public List<Employee> allEmployees() {
        List<Employee> allWorkers = new ArrayList<>();
        return departments.stream().map(Department::allEmployees).flatMap(Collection::stream).toList();
    }
}
