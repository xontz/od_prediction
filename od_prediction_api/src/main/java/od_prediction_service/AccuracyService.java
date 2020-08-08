package od_prediction_service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import od_prediction_controller.DataBaseController;
import od_prediction_model.Item;

public class AccuracyService {

	Connection conn;
	double sucessRate;
	double totalPrediction;
	static Statement statement = null;
	static ResultSet result = null;

	private AccuracyService() {
		try {
			this.conn = DataBaseController.getConnection();
			this.statement = this.conn.createStatement();
			this.sucessRate = 0;
			this.totalPrediction = 0;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public double calculateAccuracy() {
		try {
			this.result = this.statement.executeQuery("SELECT COUNT(*)  from PREDICTION ");

			if (this.result.next()) {
				this.totalPrediction = this.result.getDouble(1);

			}
			this.result = this.statement.executeQuery("select * from PREDICTION");

			while (this.result.next()) {
				double tempPredictedValue = this.result.getDouble("PREDICTED_VALUE");
				double tempActualValue = this.result.getDouble("ACTUAL_VALUE");
				double tempLower = this.result.getDouble("PREDICTED_VALUE_LOWER");
				double tempUpper = this.result.getDouble("PREDICTED_VALUE_UPPER");
				
				//if ((tempPredictedValue - tempActualValue) <= (0.3 * tempActualValue)) {
				if ((tempLower <= tempActualValue) && (tempActualValue <= tempUpper)){
					this.sucessRate++;
				}
			}
			this.conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (this.sucessRate / this.totalPrediction);
	}

	public static Item getAccuracy() {
		return new Item("accuracy", "" + new AccuracyService().calculateAccuracy());
	}
}