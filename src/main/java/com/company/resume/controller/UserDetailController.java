package com.company.resume.controller;


import com.company.dao.inter.UserDaoInter;
import com.company.entity.User;
import com.company.main.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "UserDetailController", value = "/userdetail")
public class UserDetailController extends HttpServlet {

    private final UserDaoInter userDao = Context.instanceUserDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String action = request.getParameter("action");
        if (action.equals("update")) {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String date_updated = request.getParameter("date");


            User u = userDao.getUserById(id);
            u.setName(name);
            u.setSurname(surname);
            u.setPhone(phone);
            u.setEmail(email);
            u.setBirthdate(Date.valueOf(date_updated));

            userDao.updateUser(u);
        } else if (action.equals("delete")) {
            userDao.removeUser(id);
        }


        response.sendRedirect("users");

    }

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        try {
            String userIdStr = request.getParameter("id");
            if (userIdStr == null || userIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("id is not specified");
            }
            Integer userId = Integer.parseInt(userIdStr);

            UserDaoInter userDao = Context.instanceUserDao();
            String action = request.getParameter("action");

            User u = userDao.getUserById(userId);


            if (u == null) {
                throw new IllegalArgumentException("There is no user this id");
            }
            if (action.equals("detail")) {
                request.setAttribute("action", action);
            }

            request.setAttribute("user", u);

            request.getRequestDispatcher("userdetail.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error?msg=" + e.getMessage());

        }
    }
}