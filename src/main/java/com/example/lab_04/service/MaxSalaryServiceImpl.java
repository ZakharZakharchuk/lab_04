package com.example.lab_04.service;

import com.example.lab_04.dao.DAO;
import com.example.lab_04.dao.DAOImpl;
import com.example.lab_04.models.Worker;

public class MaxSalaryServiceImpl implements MaxSalaryService{
    private final DAO dao;

    public MaxSalaryServiceImpl(DAO dao) {
        this.dao = dao;
    }

    public int maxSalary(String name) {
        return dao.findAllWorkers(name).stream().mapToInt(Worker::getSalary).max().orElse(0);
    }
}
