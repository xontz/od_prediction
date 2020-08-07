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
			this.result = this.statement.executeQuery("select * from PREDICTION where PREDICTED_VALUE >= ACTUAL_VALUE");

			while (this.result.next()) {
				double tempPredictedValue = this.result.getDouble("PREDICTED_VALUE");
				double tempActualValue = this.result.getDouble("ACTUAL_VALUE");
				
				/* 
				* O aloritmo por padrao nos da uma janela de erro (linhas azul claro) que é em media 15% de margem pro valor previsto por ele.
				* Se nós considerassemos o valor previsto pelo algoritmo e usassemos a margem sup. e inf. pra medir acuracia -> 93% de acuracia.
				*
				*Porem o que estamos fazendo é salvar no bd justamente a estimativa superior (esses 15% a mais que o algoritmo nos dá).
				* pra então, analisarmos se o dado real está numa janela de 30% desse valor. O resultado -> 96% de acuracia.
				*
				* Não seria melhor usar logo a janela de erro dada pelo algoritmo pra medir a acurácia, inves de propor uma janela fixa de 30%?
				*De qualquer forma, segue a implementação usando o limite superior e uma janela fixa de 30%
				*/
				
				//if ((tempPredictedValue - tempActualValue) <= (0.3 * tempActualValue)) {
				if (0.7 * tempActualValue <= tempPredictedValue && tempPredictedValue <= 1.3 * tempActualValue )
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
