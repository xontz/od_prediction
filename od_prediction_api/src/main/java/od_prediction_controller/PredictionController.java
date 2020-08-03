package od_prediction_controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import od_prediction_model.ComparisonObject;
import od_prediction_service.ComparisonListService;

@RestController
public class PredictionController {

	private ComparisonListService comparisonList;

	@GetMapping(path = "/od_prediction/getCompPrediction")
	public ResponseEntity<List<ComparisonObject>> getCompPrediction() {

		return ResponseEntity.ok(ComparisonListService.getList());
	}
	
	@GetMapping(path = "/od_prediction/getFuturePrediction")
	public ResponseEntity<List<ComparisonObject>> getFuturePrediction() {

		return ResponseEntity.ok(ComparisonListService.getList());
	}

	
	@GetMapping(path = "/od_prediction/getCurrentPrediction")
	public ResponseEntity<List<ComparisonObject>> getCurrentPrediction() {

		return ResponseEntity.ok(ComparisonListService.getList());
	}

	
	@GetMapping(path = "/od_prediction/getAccuracy")
	public ResponseEntity<String> getAccuracy() {

		return ResponseEntity.ok("80");
	}

	
	@GetMapping(path = "/od_prediction/getPopItems")
	public ResponseEntity<String> getPopItems() {

		return ResponseEntity.ok("PO_IPHONE");
	}


}
