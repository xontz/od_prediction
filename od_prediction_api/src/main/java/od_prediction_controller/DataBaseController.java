package od_prediction_controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseController {
	Connection conn;

	public static void main(String[] args) {

//Host: 146.250.183.92
//Port: 1521
//SID: EOCDB
//User: EOC_ARTIFICIAL_DATA
//Pass: EOC_ARTIFICIAL_DATA

		try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@146.250.183.92:1521:EOCDB", "EOC_ARTIFICIAL_DATA",
				"EOC_ARTIFICIAL_DATA")) {

			if (conn != null) {
				System.out.println("Connected to the database!");
			} else {
				System.out.println("Failed to make connection!");
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private DataBaseController() {
		try {
			this.conn = DriverManager.getConnection("jdbc:oracle:thin:@146.250.183.92:1521:EOCDB", "EOC_ARTIFICIAL_DATA",
					"EOC_ARTIFICIAL_DATA");

			if (conn != null) {
				System.out.println("Connected to the database!");
			} else {
				System.out.println("Failed to make connection!");
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return new DataBaseController().conn;
		
	}

}
