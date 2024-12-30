package Dao; // Correct package name

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Model.Admin;

public class AdminDAO {

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/BankingSystem";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Mysql@618";

    private static final String INSERT_ADMIN_SQL="INSERT INTO Admin"+"(name,email,password) VALUES "+"(?,?,?);";
	private static final String SELECT_ADMIN_BY_ID="SELECT * FROM Admin where id=?;";
	private static final String SELECT_ALL_ADMIN="select * from Admin;";
	private static final String DELETE_ADMIN_SQL="delete from Admin where id=?;";
	private static final String UPDATE_ADMIN_SQL="update Admin set first_name=?,last_name=? ,email=? where candidate_id=?;";
	private static final String SELECT_ADMIN_BY_EMAIL="SELECT * FROM Admin where email=?;";
    // Method to store data in the database
    
	public Connection getConnection()
	{
		Connection connection=null;
		
		try
		{
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return connection;
	}
	public void insertAdmin(Admin admin)
	{
		AdminDAO dao=new AdminDAO();
		try(Connection connection=dao.getConnection())
		{
			PreparedStatement preparedStatement=connection.prepareStatement(INSERT_ADMIN_SQL);
			preparedStatement.setString(1, admin.getName());
			preparedStatement.setString(2, admin.getEmail());
			preparedStatement.setString(3, admin.getPassword());
			
			preparedStatement.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
    
}
