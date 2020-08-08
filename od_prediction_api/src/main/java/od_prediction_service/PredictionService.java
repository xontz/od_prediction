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
public class PredictionService {

	List<ComparisonObject> comparisonPredictionList;
	List<ComparisonObject> futurePredictionList;
	ComparisonObject currentPrediction;
	Connection conn;
	static Statement statement = null;
	static ResultSet result = null;

	private PredictionService() {
		try {
			this.conn = DataBaseController.getConnection();
			this.comparisonPredictionList = new ArrayList<ComparisonObject>();
			this.futurePredictionList = new ArrayList<ComparisonObject>();
			// Creates a Statement object for sending SQL statements to the database
			this.statement = this.conn.createStatement();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<ComparisonObject> searchComparisonList() {

		// Executes the given SQL statement, which returns a single ResultSet object
		try {
			this.result = this.statement.executeQuery(
					"SELECT DATA_ID,TIMESTAMP,PREDICTED_VALUE,ACTUAL_VALUE  from (select * from PREDICTION order by data_id DESC) WHERE ROWNUM <= 365");

			while (this.result.next()) {
				String date = this.result.getString("TIMESTAMP");
				double real_order_volume = this.result.getDouble("ACTUAL_VALUE");
				double predicted_order_volume = this.result.getDouble("PREDICTED_VALUE");
				addElement(this.comparisonPredictionList, date, predicted_order_volume, real_order_volume);

			}
			this.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.comparisonPredictionList;

	}

	public List<ComparisonObject> searchPredictionList() {

		// Executes the given SQL statement, which returns a single ResultSet object
		try {
			this.result = this.statement
					.executeQuery("SELECT DATA_ID,TIMESTAMP,PREDICTED_VALUE  from PREDICTION_FUTURE ORDER BY DATA_ID");

			while (this.result.next()) {
				String date = this.result.getString("TIMESTAMP");
				double real_order_volume = 0;
				double predicted_order_volume = this.result.getDouble("PREDICTED_VALUE");
				addElement(this.futurePredictionList, date, predicted_order_volume, real_order_volume);

			}
			this.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.futurePredictionList;

	}

	public ComparisonObject searchCurrentPrediction() {

		// Executes the given SQL statement, which returns a single ResultSet object
		try {
			this.result = this.statement.executeQuery(
					"select * from (select * from PREDICTION_FUTURE order by data_id ASC) where rownum = 1");

			if (this.result.next()) {
				String date = this.result.getString("TIMESTAMP");
				double real_order_volume = 0;
				double predicted_order_volume = this.result.getDouble("PREDICTED_VALUE");
				this.currentPrediction = new ComparisonObject(date, predicted_order_volume, real_order_volume);

			}
			this.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.currentPrediction;

	}

	public void addElement(List<ComparisonObject> list, String date, double predictedValue, double actualValue) {
		list.add(new ComparisonObject(date, predictedValue, actualValue));
	}

	public static List<ComparisonObject> getComparisonList() {
		return new PredictionService().searchComparisonList();
	}

	public static List<ComparisonObject> getPredictionList() {
		return new PredictionService().searchPredictionList();
	}

	public static ComparisonObject getCurrentPrediction() {
		return new PredictionService().searchCurrentPrediction();
	}

}
