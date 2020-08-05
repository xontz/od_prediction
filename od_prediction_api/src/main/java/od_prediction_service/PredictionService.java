package od_prediction_service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import od_prediction_controller.DataBaseController;
import od_prediction_model.ComparisonObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class ComparisonListService {

	List<ComparisonObject> comparisonList;
	Connection conn;
	static Statement statement = null;
	static ResultSet result = null;

	private ComparisonListService() {
		try {
			this.conn = DataBaseController.getConnection();
			this.comparisonList = new ArrayList<ComparisonObject>();

			// Creates a Statement object for sending SQL statements to the database
			this.statement = this.conn.createStatement();

			// Executes the given SQL statement, which returns a single ResultSet object
			this.result = this.statement.executeQuery(
					"SELECT DATA_ID,TIMESTAMP,REAL_ORDER_VOLUME,PREDICTED_ORDER_VOLUME, UPPER_ESTIMATE from PREDICTIONS ORDER BY DATA_ID");

			while (this.result.next()) {
				String date = this.result.getString("TIMESTAMP");
				double real_order_volume = this.result.getDouble("REAL_ORDER_VOLUME");
				double predicted_order_volume = this.result.getDouble("PREDICTED_ORDER_VOLUME");
				double upper_estimate = this.result.getDouble("UPPER_ESTIMATE");
				addElement(date, (predicted_order_volume + upper_estimate), real_order_volume);

			}
			this.conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addElement(String date, double predictedValue, double actualValue) {
		this.comparisonList.add(new ComparisonObject(date, predictedValue, actualValue));
	}

	public static List<ComparisonObject> getList() {
		return new ComparisonListService().comparisonList;
	}

}
