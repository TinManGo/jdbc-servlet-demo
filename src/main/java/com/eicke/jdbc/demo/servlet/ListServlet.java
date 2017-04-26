package com.eicke.jdbc.demo.servlet;

import com.eicke.jdbc.demo.dao.MessageDao;
import com.eicke.jdbc.demo.domain.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 * 列表初始化控制
 */
@SuppressWarnings("serial")
public class ListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String command = req.getParameter("command");
        String description = req.getParameter("description");

        MessageDao messageDao = new MessageDao();
        List<Message> list = messageDao.getMessageList(command, description);
        req.setAttribute("messageList", list);
        req.getRequestDispatcher("/WEB-INF/jsp/back/list.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
