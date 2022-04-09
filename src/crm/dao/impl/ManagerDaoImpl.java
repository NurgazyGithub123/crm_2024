package crm.dao.impl;


import crm.dao.DaoFactory;
import crm.dao.ManagerDao;
import crm.model.Manager;

import java.sql.*;

public class ManagerDaoImpl extends DaoFactory implements ManagerDao {

    public ManagerDaoImpl(){
             Connection connection = null;
             Statement statement = null;
        try {
            System.out.println("Connecting database...");
            connection = getConnection();
            System.out.println("Connected database");

            String ddlQuery = "CREATE TABLE IF NOT EXISTS tb_managers(" +
                    "id           BIGSERIAL      PRIMARY KEY, " +
                    "first_name   VARCHAR(50) NOT NULL, " +
                    "last_name    VARCHAR(50) NOT NULL, " +
                    "email        VARCHAR(50) NOT NULL UNIQUE, " +
                    "phone_number VARCHAR(13) NOT NULL, " +
                    "salary       MONEY       NOT NULL CHECK(salary > MONEY(0)), " +
                    "dob          DATE        NOT NULL CHECK(dob < NOW()) , " +
                    "date_created TIMESTAMP   NOT NULL DEFAULT NOW()" +
                    "" +
                    "CONSTRAINT pk_manager_id PRIMARY KEY(id)," +
                    "CONSTRAINT pk_manager_salary CHECK(salary > MONEY(0)), " +
                    ")";

            statement = connection.createStatement();
            statement.execute(ddlQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(statement);
            close(connection);
        }
    }

    public Manager save(Manager manager){

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            Manager savedManager = null;
        try {
            System.out.println("Connecting database...");
            connection = getConnection();
            System.out.println("Connected database");

            String createQuery = "INSERT INTO tb_managers(first_name, last_name, email, phone_number, salary, dob, date_created) " +
                    "VALUES(?, ?, ?, ?, MONEY(?), ?, ?)";

            preparedStatement = connection.prepareStatement(createQuery);
            preparedStatement.setString(1, manager.getFirst_name());
            preparedStatement.setString(2, manager.getLast_name());
            preparedStatement.setString(3,manager.getEmail());
            preparedStatement.setString(4,manager.getSalary() + "");
            preparedStatement.setDate(5, Date.valueOf(manager.getDob()));
            preparedStatement.setTimestamp(6,Timestamp.valueOf(manager.getDate_created()));

            preparedStatement.execute();
            close(preparedStatement);

            String readQuery = "SELECT * FROM tb_manager ORDER BY id DESC LIMIT 1";

            preparedStatement = connection.prepareStatement(readQuery);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();


            savedManager.setId(resultSet.getLong("id"));
            savedManager.setFirst_name(resultSet.getString("first_name"));
            savedManager.setLast_name(resultSet.getString("last_name"));
            savedManager.setEmail(resultSet.getString("email"));
            savedManager.setSalary(Double.valueOf(resultSet.getString("salary").replaceAll("[^\\d\\.]+", "")));
            savedManager.setDob(resultSet.getDate("dob").toLocalDate());
            savedManager.setDate_created(resultSet.getTimestamp("date_created"));


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(preparedStatement);
            close(connection);
        }return savedManager;
    }

    @Override
    public Manager findById(Long id) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Manager manager = null;
        try {
            connection = getConnection();
            
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
        return manager;
    }

    private void close(AutoCloseable closeable){
        try {
            System.out.println(closeable.getClass().getSimpleName() + "closing...");
            closeable.close();
            System.out.println(closeable.getClass().getSimpleName() + "closed");
        } catch (Exception e) {
            System.out.println("Could not close + " + closeable.getClass().getSimpleName());
            e.printStackTrace();
        }
    }
}
