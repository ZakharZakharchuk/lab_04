package com.example.lab_04.dao;

import com.example.lab_04.models.Department;
import com.example.lab_04.models.Firm;
import com.example.lab_04.models.Worker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOImpl implements DAO{
    private String url;

    private final static String FIND_FIRM_ID_BY_NAME = "SELECT * FROM firms WHERE name=?";
    private final static String FIND_DEPARTMENTS_BY_FIRM = "SELECT * FROM departments WHERE firms_id=?";
    private final static String FIND_WORKERS_BY_DEPARTMENTS = "SELECT * FROM workers WHERE departments_id =?";
    private final static String FIND_WORKER_BY_ID = "SELECT * FROM workers WHERE id = ?";

//    private static final String CONNECTION_URL_PROPERTY_NAME = "connection.url";
//    private static final String CONNECTION_PROPERTIES_FILE_NAME = "app.properties";

    public DAOImpl() {
//        Properties properties = new Properties();
//        try {
//            FileReader reader = new FileReader(CONNECTION_PROPERTIES_FILE_NAME);
//            properties.load(reader);
//            url = properties.getProperty(CONNECTION_URL_PROPERTY_NAME);
//        } catch (IOException e) {
//            throw new IllegalStateException(e);
//        }
        try {
            url = "jdbc:mysql://localhost:3306/lab_db_04?user=root&password=root";
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Worker> findAllWorkers(String nameOfFirm) {
        List<Department> departments;
        List<Worker> workers = new ArrayList<>();
        try {
            Firm firm = findFirmByName(nameOfFirm);
            departments = listOfDepartments(firm);
            workers = listOfWorkers(departments);
            workers.add(findWorkerById(firm.getDirectorId()));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return workers;
    }

    public List<Department> departmentsWithBigSalary(String nameOfFirm) {
        List<Department> departments;
        Firm firm = findFirmByName(nameOfFirm);
        departments = listOfDepartments(firm);
        return listOfWorkersWithBigSalary(departments);
    }

    private List<Worker> listOfWorkers(List<Department> departments) {
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

    private Firm findFirmByName(String nameOfFirm) {
        Firm firm = new Firm();
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement st = con.prepareStatement(FIND_FIRM_ID_BY_NAME)
        ) {
            st.setString(1, nameOfFirm);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                firm.setId(rs.getInt("id"));
                firm.setName(rs.getString("name"));
                firm.setDirectorId(rs.getInt("workers_id"));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("There are not firm with that name", e);
        }
        return firm;
    }

    private List<Department> listOfDepartments(Firm firm) {
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

    private List<Department> listOfWorkersWithBigSalary(List<Department> departments) {
        List<Department> departmentsWithBigSalary = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement st = con.prepareStatement(FIND_WORKERS_BY_DEPARTMENTS)
        ) {
            for (Department i : departments) {
                Worker head = findWorkerById(i.getHeadId());
                st.setInt(1, i.getId());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Worker worker = mapWorker(rs);
                    if (worker.getSalary() > head.getSalary())
                        departmentsWithBigSalary.add(i);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departmentsWithBigSalary.stream().distinct().toList();
    }

    private Worker mapWorker(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        int salary = rs.getInt("salary");
        return new Worker(id, name, surname, salary);
    }

    private Department mapDepartment(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int headId = rs.getInt("workers_id");
        int firmId = rs.getInt("firms_id");
        return new Department(id, name, headId, firmId);
    }
}
