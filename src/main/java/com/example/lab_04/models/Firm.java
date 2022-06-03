package com.example.lab_04.models;

public class Firm {
    private int id;
    private String name;
    private int directorId;

    public Firm(int id, String name, int directorId) {
        this.id = id;
        this.name = name;
        this.directorId = directorId;
    }

    public Firm() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }
    //    public int maxSalaryInFirm() {
//        int max = departments.stream()
//                .mapToInt(Department::maxSalaryInDepartment)
//                .max()
//                .orElse(0);
//        return Math.max(max, director.getSalary());
//    }
//
//    public List<Department> workerWithBigSalary() {
//        List<Department> depart = departments.stream()
//                .map(Department::findWorkerWithBigSalary)
//                .filter(Objects::nonNull)
//                .toList();
//        return depart;
//    }
//
//    public List<Worker> allEmployees() {
//        List<Worker> allWorkers = new ArrayList<>();
//        return departments.stream().map(Department::allEmployees).flatMap(Collection::stream).toList();
//    }
}
