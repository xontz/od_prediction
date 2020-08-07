package od_prediction_service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import org.springframework.stereotype.Service;
import od_prediction_controller.DataBaseController;

@Service
public class PopularItemsService {

	String json;
	Connection conn;
	static Statement statement = null;
	static ResultSet result = null;

	private PopularItemsService() {
		try {
			this.conn = DataBaseController.getConnection();
			// Creates a Statement object for sending SQL statements to the database
			this.statement = this.conn.createStatement();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String returnJson(){

		// Executes the given SQL statement, which returns a single ResultSet object
		try {
			this.result = this.statement
					.executeQuery("SELECT TIMESTAMP, POPULARITEMS FROM POPULAR_OFFERS WHERE TIMESTAMP = '31/12/19'");

			if (this.result.next()) {
				String date = this.result.getString("TIMESTAMP");
				json = this.result.getString("POPULARITEMS");
			}
			this.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public static String getClob(){
		return new PopularItemsService().returnJson();
	}

}
