package jdbc.daoObjects;

import connection.JdbcUtil;
import jdbc.entities.JdbcEmployee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcEmployeeDAO extends JdbcUtil {

    Connection connection = getConnection();

    public JdbcEmployee save(JdbcEmployee employee) throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "INSERT INTO Employees (ID, NAME, DOB) VALUES (?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, employee.getId());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setDate(3, employee.getDate());
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
        return employee;
    }

    public void deleteById(long id) throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "DELETE FROM Employees WHERE ID = ?";

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

    public void deleteByEntity(JdbcEmployee employee) throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "DELETE FROM Employees WHERE ID = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, employee.getId());

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

        String sql = "DELETE FROM Employees";

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

    public JdbcEmployee update(JdbcEmployee employee) throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "UPDATE Employees SET NAME = ?, DOB = ? WHERE ID = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, employee.getName());
            preparedStatement.setDate(2, employee.getDate());
            preparedStatement.setLong(3, employee.getId());

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
        return employee;
    }

    public JdbcEmployee getById(long id) throws SQLException {
        PreparedStatement preparedStatement = null;
        JdbcEmployee employee = null;

        String sql = "SELECT ID, NAME, DOB FROM Employees WHERE ID = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                employee = new JdbcEmployee();
                employee.setId(resultSet.getLong("ID"));
                employee.setName(resultSet.getString("NAME"));
                employee.setDate(resultSet.getDate("DOB"));

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
        return employee;
    }

    public List<JdbcEmployee> getAll() throws SQLException {
        List<JdbcEmployee> employeeList = new ArrayList<>();

        String sql = "SELECT ID, NAME, DOB FROM Employees";

        Statement statement = null;
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                JdbcEmployee employee = new JdbcEmployee();
                employee.setId(resultSet.getLong("ID"));
                employee.setName(resultSet.getString("NAME"));
                employee.setDate(resultSet.getDate("DOB"));

                employeeList.add(employee);
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
        return employeeList;
    }
}
