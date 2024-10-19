package com.klu.JdbcApp;
import java.sql.*;
import java.util.*;
public class SCRUDApp 
{
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/students_db";
		String username = "root";
		String password = "Yasaswini@11";
		Scanner scanner = new Scanner(System.in);
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
			int choice = 0;
			while (choice != 5) {
				System.out.println("\nCRUD Operations:");
				System.out.println("1. Create");
				System.out.println("2. Read");
				System.out.println("3. Update");
				System.out.println("4. Delete");
				System.out.println("5. Quit");
				System.out.print("Enter your choice: ");
				choice = scanner.nextInt();
				
				switch (choice) {
					case 1:
						System.out.print("Enter student ID: ");
						int sid = scanner.nextInt();
						System.out.print("Enter student name: ");
						String sname = scanner.next();
						createStudent(con, sid, sname);
						break;
					case 2:
						readStudents(con);
						break;
					case 3:
						System.out.print("Enter student ID: ");
						int updateSid = scanner.nextInt();
						System.out.print("Enter new student name: ");
						String updateSname = scanner.next();
						updateStudent(con, updateSid, updateSname);
						break;
					case 4:
						System.out.print("Enter student ID: ");
						int deleteSid = scanner.nextInt();
						deleteStudent(con, deleteSid);
						break;
					case 5:
						System.out.println("Exiting...");
						break;
					default:
						System.out.println("Invalid choice");
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					System.out.println("Error closing connection: " + e.getMessage());
				}
			}
		}
	}

	public static void createStudent(Connection con, int sid, String sname) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement("insert into klustudents values (?,?)");
		pstmt.setInt(1, sid);
		pstmt.setString(2, sname);
		pstmt.executeUpdate();
	}

	public static void readStudents(Connection con) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from klustudents");

		// Display headers
		System.out.printf("%-10s %-20s%n", "Student ID ", "Student Name");
		System.out.println("------------------------------");

		// Display each record
		while (rs.next()) {
			int id = rs.getInt("sid");
			String name = rs.getString("sname");
			System.out.printf("%-10d  %-20s%n", id, name);
		}
	}

	public static void updateStudent(Connection con, int sid, String sname) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement("update klustudents set sname = ? where sid = ?");
		pstmt.setString(1, sname);
		pstmt.setInt(2, sid);
		pstmt.executeUpdate();
	}

	public static void deleteStudent(Connection con, int sid) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement("delete from klustudents where sid = ?");
		pstmt.setInt(1, sid);
		pstmt.executeUpdate();
	}

}
