package application.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import application.domain.Card;
import application.domain.Gem;
import application.domain.Noble;

/**
 * This reads the cards from a csv file and converts it to objects
 * 
 * @author Sanchez
 *
 */
public class NobleReader {
	
	private final static String nobleFile = "resources/config/nobles.csv";
	private List<Noble> allNobles;
	
	public NobleReader() throws IOException {
		this.allNobles = new ArrayList<Noble>();
		this.generateNobles();
	}

	private void generateNobles() throws FileNotFoundException, IOException {
		try (
				FileInputStream file = new FileInputStream(nobleFile);
				InputStreamReader reader = new InputStreamReader(file);
				CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(2).build();
			){
			String[] nobleRecord;
			while ((nobleRecord = csvReader.readNext()) != null) {
				Noble noble = readNoble(nobleRecord);
				allNobles.add(noble);
				
			}
		}
		
	}
	public Stack<Noble> getNoble(){
		Stack<Noble> nobleArray = new Stack<>();	
		for(Noble noble : allNobles) {
			nobleArray.add(noble);
		}
		return nobleArray;
	}

	
	private Noble readNoble(String[] nobleRecord) {
		// TODO Auto-generated method stub
		return null;
	}

}
