package com.eicke.jdbc.demo.dao;

import com.eicke.jdbc.demo.domain.Message;
import com.eicke.jdbc.demo.utils.JDBCUtils;
import com.mysql.jdbc.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */
public class MessageDao {

    public List<Message> getMessageList(String command, String description) {
        List<Message> messageList = new ArrayList<Message>();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Connection connection= null;
        try {
            StringBuilder sql = new StringBuilder(" select id,command,description,content from message where 1 = 1");
            List<String> paramList = new ArrayList<String>();
            if (!StringUtils.isNullOrEmpty(command) && !command.trim().equals("")) {
                sql.append(" and command like ? ");
                paramList.add(command);
            }
            if (!StringUtils.isNullOrEmpty(description) && !description.trim().equals("")) {
                sql.append(" and  description like ? ");
                paramList.add(description);
            }
            connection  = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());
            for (int i = 0; i < paramList.size(); i++) {
                preparedStatement.setString(i + 1, "%"+paramList.get(i)+"%");
            }
             resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getLong("id"));
                message.setCommand(resultSet.getString("command"));
                message.setDescription(resultSet.getString("description"));
                message.setContent(resultSet.getString("content"));
                messageList.add(message);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResultSet(resultSet);
            JDBCUtils.closeStatement(preparedStatement);
            JDBCUtils.closeConnection(connection);
            return messageList;
        }
    }
}
