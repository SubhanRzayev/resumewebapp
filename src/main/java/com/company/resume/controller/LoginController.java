package com.company.resume.controller;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.company.dao.inter.UserDaoInter;
import com.company.entity.User;
import com.company.main.Context;
import com.company.resume.util.ControllerUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {

    private UserDaoInter userDao = Context.instanceUserDao();
    private static BCrypt.Verifyer verifyer = BCrypt.verifyer();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            User userObj = userDao.findByEmail(email);

            if (userObj == null) {
                throw new IllegalArgumentException("User doesn't existt!! ");
            }
            BCrypt.Result verify = verifyer.verify(password.toCharArray(), userObj.getPassword().toCharArray());
            if (!verify.verified) {
                throw new IllegalArgumentException("password is incorrect!!!");
            }
            request.getSession().setAttribute("loggedUser", userObj);

//            request.getSession().setAttribute("loggedInUser", userObj);
            response.sendRedirect("users");

        } catch (Exception ex) {
            ControllerUtil.errorPage(response, ex);
        }
    }

}