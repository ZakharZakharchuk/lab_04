package com.example.lab_04.dao;

import com.example.lab_04.models.Department;
import com.example.lab_04.models.Worker;

import java.util.List;

public interface DAO {
    List<Worker> findAllWorkers(String nameOfFirm);
    List<Department> departmentsWithBigSalary(String nameOfFirm);
}
