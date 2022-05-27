package com.example.lab_04;

import com.example.lab_04.models.Department;
import com.example.lab_04.models.Firm;
import com.example.lab_04.models.Worker;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//TODO change frim on firm
public class DAO {
    private static final String CONNECTION_URL_PROPERTY_NAME = "connection.url";
    private static final String CONNECTION_PROPERTIES_FILE_NAME = "app.properties";
    private final String url;

    private final static String FIND_FIRM_ID_BY_NAME = "SELECT * FROM firms WHERE name=?";
    private final static String FIND_DEPARTMENTS_BY_FIRM = "SELECT * FROM departments WHERE firms_frim_id=?";
    private final static String FIND_WORKERS_BY_DEPARTMENTS = "SELECT * FROM workers WHERE departments_department_id =?";
    private final static String FIND_WORKER_BY_ID = "SELECT * FROM workers WHERE worker_id = ?";
//    private final static String FIND_BY_JOINS = "Select workers.worker_id, workers.name, workers.surname from 'firms' " +
//            "inner join 'departments' on 'firms.frim_id' = 'departments.firms_frim_id' " +
//            "inner join workers on 'departments.department_id' = 'workers.departments_department_id'" +
//            "where 'frim_id' = ?";

    public DAO() {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader(CONNECTION_PROPERTIES_FILE_NAME)) {
            properties.load(reader);
            url = properties.getProperty(CONNECTION_URL_PROPERTY_NAME);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public Firm findFirmByName(String name) {
        Firm firm = new Firm();
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement st = con.prepareStatement(FIND_FIRM_ID_BY_NAME)
        ) {
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                firm.setId(rs.getInt("frim_id"));
                firm.setName(rs.getString("name"));
                firm.setDirectorId(rs.getInt("workers_worker_id"));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("There are not firm with that name", e);
        }
        return firm;
    }

    public List<Department> listOfDepartments(Firm firm) {
        List<Department> departments = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement st = con.prepareStatement(FIND_DEPARTMENTS_BY_FIRM)
        ) {
            st.setInt(1, firm.getId());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                departments.add(mapDepartment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    public List<Worker> listOfWorkers(List<Department> departments) {
        List<Worker> workers = new ArrayList<>();
        List<Integer> departmentIds = departments.stream().map(Department::getId).toList();
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement st = con.prepareStatement(FIND_WORKERS_BY_DEPARTMENTS)
        ) {
            for (Integer i : departmentIds) {
                st.setInt(1, i);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    workers.add(mapWorker(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workers;
    }

    private Worker findWorkerById(int id) {
        Worker worker = new Worker();
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement st = con.prepareStatement(FIND_WORKER_BY_ID)
        ) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                worker = mapWorker(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return worker;
    }

    public List<Worker> findAllWorkers(String nameOfFirm) {
        Firm firm = new Firm();
        List<Department> departments = new ArrayList<>();
        List<Worker> workers = new ArrayList<>();
        try {
            firm = findFirmByName(nameOfFirm);
            departments = listOfDepartments(firm);
            workers = listOfWorkers(departments);
            workers.add(findWorkerById(firm.getDirectorId()));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return workers;
    }

    private Worker mapWorker(ResultSet rs) throws SQLException {
        int id = rs.getInt("worker_id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        int salary = rs.getInt("salary");
        return new Worker(id, name, surname, salary);
    }

    private Department mapDepartment(ResultSet rs) throws SQLException {
        int id = rs.getInt("department_id");
        String name = rs.getString("name");
        int headId = rs.getInt("workers_worker_id");
        //TODO remove the firmId from that method
        int firmId = rs.getInt("firms_frim_id");
        return new Department(id, name, headId, firmId);
    }

//    public List<Worker> allWorkersInFirm(String nameOfFirm) {
//        Firm firm = new Firm();
//        try {
//            firm = findFirmByName(nameOfFirm);
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        }
//        List<Worker>workers = new ArrayList<>();
//        try (Connection con = DriverManager.getConnection(url);
//             PreparedStatement st = con.prepareStatement(FIND_BY_JOINS)
//        ) {
//            st.setInt(1, firm.getId());
//            ResultSet rs = st.executeQuery();
//            if (rs.next()) {
//                workers.add(mapWorker(rs));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return workers;
//    }


    //    public int maxSalary() {
//        int max = 0;
//        try (Connection con = DriverManager.getConnection(url);
//             PreparedStatement st = con.prepareStatement(MAX_SALARY)
//        ) {
//            ResultSet rs = st.executeQuery();
//            if (rs.next()) {
//                max = rs.getInt("max");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return max;
//    }
}
