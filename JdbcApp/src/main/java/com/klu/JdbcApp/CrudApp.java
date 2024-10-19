package com.klu.JdbcApp;

import java.sql.*;

public class CrudApp {
	public static void main(String[] args) throws Exception {
		String url = "jdbc:mysql://localhost:3306/students_db";
		String username = "root";
		String password = "Yasaswini@11";

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, username, password);
		// Read operation
		System.out.println("Read Operation:");
		readStudents(con);
		// Update operation
		System.out.println("\nUpdate Operation:");
		updateStudent(con, 1, "John Doe");
		// Read operation after update
		System.out.println("\nRead Operation after update:");
		readStudents(con);
		// Delete operation
		System.out.println("\nDelete Operation:");
		deleteStudent(con, 1);
		// Read operation after delete
		System.out.println("\nRead Operation after delete:");
		readStudents(con);
		con.close();
	}

	public static void readStudents(Connection con) throws Exception {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from klustudents");
		while (rs.next()) {
			System.out.println("Student ID: " + rs.getInt("sid"));
			System.out.println("Student Name: " + rs.getString("sname"));
		}
	}

	public static void updateStudent(Connection con, int sid, String sname) throws Exception {
		PreparedStatement pstmt = con.prepareStatement("update klustudents set sname = ? where sid = ?");

		pstmt.setString(1, sname);
		pstmt.setInt(2, sid);
		pstmt.executeUpdate();
	}
	// TODO Auto-generated method stub

	public static void deleteStudent(Connection con, int sid) throws Exception {
		PreparedStatement pstmt = con.prepareStatement("delete from klustudents where sid = ?");

		pstmt.setInt(1, sid);
		pstmt.executeUpdate();
	}
}