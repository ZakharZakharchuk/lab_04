package com.example.lab_04.controller;

import com.example.lab_04.dao.DAO;
import com.example.lab_04.service.MaxSalaryService;
import com.example.lab_04.service.MaxSalaryServiceImpl;
import com.example.lab_04.dao.DAOImpl;
import com.example.lab_04.models.Department;
import com.example.lab_04.models.Worker;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tasks")
public class TaskController extends HttpServlet {
    private DAO dao = new DAOImpl();
    private MaxSalaryService maxSalary = new MaxSalaryServiceImpl(dao);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nameOfFirm = request.getParameter("nameOfFirm");
        List<Worker> workers = dao.findAllWorkers(nameOfFirm);
        int max = maxSalary.maxSalary(nameOfFirm);
        List<Department> departments = dao.departmentsWithBigSalary(nameOfFirm);
        request.setAttribute("workers", workers);
        request.setAttribute("max", max);
        request.setAttribute("departments", departments);
        RequestDispatcher dispatcher = request.getRequestDispatcher("answer.jsp");
        dispatcher.forward(request, response);
    }
}