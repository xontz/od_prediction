package od_prediction_model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ComparisonObject {

	private double predictedOrderVolume;
	private double actualOrderVolume;
	private String date;

	public ComparisonObject(String date, double predictedOrderVolume, double actualOrderVolume) {
		this.date = date;
		this.actualOrderVolume = actualOrderVolume;
		this.predictedOrderVolume = predictedOrderVolume;

	}

	public double getPredictedOrderVolume() {
		return predictedOrderVolume;
	}

	public void setPredictedOrderVolume(double predictedOrderVolume) {
		this.predictedOrderVolume = predictedOrderVolume;
	}

	public double getActualOrderVolume() {
		return actualOrderVolume;
	}

	public void setActualOrderVolume(double actualOrderVolume) {
		this.actualOrderVolume = actualOrderVolume;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
