package Controller;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Dao.AdminDAO;

@WebServlet("/signupServlet")


public class signupServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(signupServlet.class.getName());
	private static final String DB_URL = "jdbc:mysql://localhost:3306/BankingSystem";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Mysql@618";
    
    
    public signupServlet() {
		super();
	}
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
    
    request.getRequestDispatcher("Signup.jsp").forward(request,response);
    }
    
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        
        Connection connection = null;

        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            if (isEmailExists(connection,email)) {
                
                request.setAttribute("errorMessage", "email already exists.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
                return; 
            }
            

            String sql = "INSERT INTO Admin (name ,email, password) VALUES (?, ?, ?)";
           


            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, password);
                 
                int rowsAffected = statement.executeUpdate();
                logger.info("Rows affected: " + rowsAffected); 
                
                if (rowsAffected > 0) {
                    logger.info("User signed up successfully ");
                    
                    
                    HttpSession session = request.getSession();
                    session.setAttribute("username", name);
                    
                    logger.info("Session set: username=" + session.getAttribute("username"));
                    
                    response.sendRedirect("login.jsp?signup=success");
                } else {
                	 logger.warning("Error: Unable to signup user.");
                    response.getWriter().println("Error: Unable to signup user.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred during signup.");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
      
    }
	private boolean isEmailExists(Connection connection, String email) throws SQLException {
        String sql =  "SELECT COUNT(*) FROM Admin WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
          
            statement.setString(1, email);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; 
                }
            }
        }
        return false;
    }
}
