package com.happyfoods.database;

import com.happyfoods.data.Nutrient;
import com.happyfoods.data.controller.Controller;
import com.happyfoods.utilities.exception.ExceptionUtilities;
import com.happyfoods.utilities.io.FileUtilities;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.happyfoods.utilities.Converters.BooleanConverter;
import static java.util.logging.Logger.getLogger;

public class NutrientController implements Controller<Nutrient> {

	private static final Logger logger = getLogger(NutrientController.class.getSimpleName());

	@Override
	public List<Nutrient> loadAll() {
		CSVFormat csvFormat = CSVFormat.newFormat(',').withCommentMarker('#');
		Optional<CSVParser> csvParser = FileUtilities.createCsvParser("Nutrients.csv", csvFormat);
		List<CSVRecord> records = csvParser.map(parser -> ExceptionUtilities.uncheckThrowable(parser::getRecords)).orElse(Collections.emptyList());
		List<Nutrient> nutrients = new ArrayList<>(records.size());
		for (CSVRecord record : records) {
			int i = 0;
			try {
				Nutrient.Builder nutrientBuilder = Nutrient.builder(record.get(i++).trim());
				nutrientBuilder.causesInflammation(BooleanConverter.toBoolean(record.get(i++)));
				nutrientBuilder.causesHeartDisease(BooleanConverter.toBoolean(record.get(i++)));
				nutrientBuilder.causesMentalIllness(BooleanConverter.toBoolean(record.get(i++)));
				nutrientBuilder.causesDepression(BooleanConverter.toBoolean(record.get(i++)));
				nutrientBuilder.causesStress(BooleanConverter.toBoolean(record.get(i++)));
				nutrientBuilder.causesObesity(BooleanConverter.toBoolean(record.get(i++)));
				nutrientBuilder.causesOxidation(BooleanConverter.toBoolean(record.get(i++)));
				nutrientBuilder.description(record.get(i).trim());
				nutrients.add(nutrientBuilder.build());
			} catch (ArrayIndexOutOfBoundsException e) {
				logger.warning(String.format("Malformed nutrient; Expected a #%d column for Nutrient '%s'", i, record.size() > 0 ? record.get(0) : "?"));
			}

		}
		return nutrients;
	}
}
