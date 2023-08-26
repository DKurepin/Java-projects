package jdbc.daoObjects;

import connection.JdbcUtil;
import enums.TaskType;
import jdbc.entities.JdbcTask;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTaskDAO extends JdbcUtil {

    Connection connection = getConnection();

    public JdbcTask save(JdbcTask task) throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "INSERT INTO Tasks (ID, NAME, DEADLINE, DESCRIPTION, TYPE, EMPLOYEEID) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, task.getId());
            preparedStatement.setString(2, task.getName());
            preparedStatement.setDate(3, task.getDeadline());
            preparedStatement.setString(4, task.getDescription());
            preparedStatement.setString(5, task.getType().toString());
            preparedStatement.setLong(6, task.getEmployeeId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return task;
    }

    public void deleteById(long id) throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "DELETE FROM Tasks WHERE ID = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void deleteByEntity(JdbcTask task) throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "DELETE FROM Tasks WHERE ID = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, task.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void deleteAll() throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "DELETE FROM Tasks";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public JdbcTask update(JdbcTask task) throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "UPDATE Tasks SET NAME = ?, DEADLINE = ?, DESCRIPTION = ?, TYPE = ?, EMPLOYEEID = ? WHERE ID = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, task.getName());
            preparedStatement.setDate(2, task.getDeadline());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setString(4, task.getType().toString());
            preparedStatement.setLong(5, task.getEmployeeId());
            preparedStatement.setLong(6, task.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return task;
    }

    public JdbcTask getById(long id) throws SQLException {
        JdbcTask task = null;
        PreparedStatement preparedStatement = null;

        String sql = "SELECT ID, NAME, DEADLINE, DESCRIPTION, TYPE, EMPLOYEEID FROM Tasks WHERE ID = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                task = new JdbcTask();
                task.setId(resultSet.getLong("ID"));
                task.setName(resultSet.getString("NAME"));
                task.setDeadline(resultSet.getDate("DEADLINE"));
                task.setDescription(resultSet.getString("DESCRIPTION"));
                task.setType(TaskType.valueOf(resultSet.getString("TYPE")));
                task.setEmployeeId(resultSet.getLong("EMPLOYEEID"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return task;
    }

    public List<JdbcTask> getAll() throws SQLException {
        List<JdbcTask> taskList = new ArrayList<>();
        JdbcTask task = null;

        String sql = "SELECT ID, NAME, DEADLINE, DESCRIPTION, TYPE, EMPLOYEEID FROM Tasks";

        Statement statement = null;
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                task = new JdbcTask();
                task.setId(resultSet.getLong("ID"));
                task.setName(resultSet.getString("NAME"));
                task.setDeadline(resultSet.getDate("DEADLINE"));
                task.setDescription(resultSet.getString("DESCRIPTION"));
                task.setType(TaskType.valueOf(resultSet.getString("TYPE")));
                task.setEmployeeId(resultSet.getLong("EMPLOYEEID"));

                taskList.add(task);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return taskList;
    }

    public List<JdbcTask> getAllByEmployeeId(long employeeId) throws SQLException {
        List<JdbcTask> taskList = new ArrayList<>();
        JdbcTask task = null;

        String sql = "SELECT ID, NAME, DEADLINE, DESCRIPTION, TYPE, EMPLOYEEID FROM Tasks WHERE EMPLOYEEID = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, employeeId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                task = new JdbcTask();
                task.setId(resultSet.getLong("ID"));
                task.setName(resultSet.getString("NAME"));
                task.setDeadline(resultSet.getDate("DEADLINE"));
                task.setDescription(resultSet.getString("DESCRIPTION"));
                task.setType(TaskType.valueOf(resultSet.getString("TYPE")));
                task.setEmployeeId(resultSet.getLong("EMPLOYEEID"));

                taskList.add(task);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return taskList;
    }
}
