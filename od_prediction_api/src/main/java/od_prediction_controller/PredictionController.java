package od_prediction_controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import od_prediction_model.ComparisonObject;
import od_prediction_model.Item;
import od_prediction_service.AccuracyService;
import od_prediction_service.PredictionService;
import od_prediction_service.PopularItemsService;

@RestController
public class PredictionController {

	private PredictionService comparisonList;
	private PopularItemsService popularItemsService;

	@GetMapping(path = "/od_prediction/getCompPrediction")
	public ResponseEntity<List<ComparisonObject>> getCompPrediction() {

		return ResponseEntity.ok(PredictionService.getComparisonList());
	}

	@GetMapping(path = "/od_prediction/getFuturePrediction")
	public ResponseEntity<List<ComparisonObject>> getFuturePrediction() {

		return ResponseEntity.ok(PredictionService.getPredictionList());
	}

	@GetMapping(path = "/od_prediction/getCurrentPrediction")
	public ResponseEntity<ComparisonObject> getCurrentPrediction() {

		return ResponseEntity.ok(PredictionService.getCurrentPrediction());
	}

	@GetMapping(path = "/od_prediction/getAccuracy")
	public ResponseEntity<Item> getAccuracy() {

		return ResponseEntity.ok(AccuracyService.getAccuracy());
	}

	@GetMapping(path = "/od_prediction/getPopItems")
	public ResponseEntity<String> getPopItems() throws ParseException {
		
		return ResponseEntity.ok(PopularItemsService.getClob());
	}

}
