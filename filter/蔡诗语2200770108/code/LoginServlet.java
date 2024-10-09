package com.gzu.logindemo.code;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String USERNAME = "15120159948";
    private static final String PASSWORD = "123456";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // 验证用户名和密码
        if (isValidCredentials(username, password)) {
            // 设置用户会话属性
            HttpSession session = req.getSession();
            session.setAttribute("user", username);
            // 重定向到公共页面
            resp.sendRedirect("code/welcome.html");
        } else {
            // 设置错误消息
            req.setAttribute("errorMessage", "账号或密码错误");
            // 重定向回登录页面并显示错误消息
            resp.sendRedirect("code/login.html");
        }
    }

    /**
     * 验证用户名和密码是否正确
     *
     * @param username 用户名
     * @param password 密码
     * @return 如果用户名和密码正确，则返回true；否则返回false
     */
    private boolean isValidCredentials(String username, String password) {
        return USERNAME.equals(username) && PASSWORD.equals(password);
    }
}