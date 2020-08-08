package od_prediction_service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;
import od_prediction_controller.DataBaseController;

@Service
public class PopularItemsService {

	StringBuilder json;
	Connection conn;
	static Statement statement = null;
	static ResultSet result = null;
	private static String cachedPopItems = null;

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

	public String returnJson() {

		// Executes the given SQL statement, which returns a single ResultSet object
		try {
			this.result = this.statement
					.executeQuery("SELECT TIMESTAMP, POPULARITEMS FROM POPULAR_OFFERS WHERE TIMESTAMP = '31-12-19'");

			if (cachedPopItems != null) {
				return cachedPopItems;
			}
			this.json = new StringBuilder();
			// Executes the given SQL statement, which returns a single ResultSet object
			// try {
			/*
			 * this.result = this.statement
			 * .executeQuery("SELECT TIMESTAMP, POPULARITEMS FROM POPULAR_OFFERS WHERE TIMESTAMP = '31/12/19'"
			 * );
			 * 
			 * 
			 * 
			 * if (this.result.next()) { String date = this.result.getString("TIMESTAMP");
			 * json = this.result.getString("POPULARITEMS"); } this.conn.close();
			 * 
			 */

			this.result = this.statement.executeQuery(
					"with counting as ( select product, count(1) as counter from orders where timestamp > (select to_char(sysdate - (30 * 6), 'YYYY-MM-DD') from dual) group by product ) select product, round((counter/(select sum(counter) from counting)) * 100, 0) as perc from counting order by perc desc");
			int count = 0;
			this.json.append("[");
			while (this.result.next()) {
				if (count > 0) {
					this.json.append(",");
				}
				String poName = this.result.getString("product");
				String percentage = this.result.getString("perc");
				this.json.append("{");
				this.json.append("\"id\":" + count);
				this.json.append(",\"name\":\"" + poName + "\"");
				this.json.append(",\"rate\":" + percentage);
				this.json.append("}");
				count++;
			}
			this.json.append("]");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cachedPopItems = this.json.toString();
		return cachedPopItems;
	}

	public static String getClob() {
		return new PopularItemsService().returnJson();
	}

}
