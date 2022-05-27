package com.example.lab_04;

import com.example.lab_04.models.Worker;

public class MaxSalary {
    private final DAO dao;

    public MaxSalary(DAO dao) {
        this.dao = dao;
    }

    public int maxSalary(String name) {
        return dao.findAllWorkers(name).stream().mapToInt(Worker::getSalary).max().orElse(0);
    }
}
