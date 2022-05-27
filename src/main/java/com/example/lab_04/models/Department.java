package com.example.lab_04.models;

public class Department {
    private int id;
    private String name;
    private int headId;
    private int firmId;

    public Department() {
    }

    public Department(int id, String name, int headId, int firmId) {
        this.id = id;
        this.name = name;
        this.headId = headId;
        this.firmId = firmId;
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

    public int getHeadId() {
        return headId;
    }

    public void setHeadId(int headId) {
        this.headId = headId;
    }

    public int getFirmId() {
        return firmId;
    }

    public void setFirmId(int firmId) {
        this.firmId = firmId;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", headId=" + headId +
                ", firmId=" + firmId +
                '}';
    }
    //    public int maxSalaryInDepartment() {
//        int max = workers.stream()
//                .mapToInt(Worker::getSalary)
//                .max()
//                .orElse(0);
//        return Math.max(max, head.getSalary());
//    }
//
//    public Department findWorkerWithBigSalary() {
//        int max = workers.stream().mapToInt(Worker::getSalary).max().orElse(0);
//        return max > head.getSalary() ? this : null;
//    }
//    public List<Worker>allEmployees(){
//        List<Worker>allWorkers = new ArrayList<>();
//        allWorkers.add(head);
//        allWorkers.addAll(workers);
//        return allWorkers;
//    }
}
