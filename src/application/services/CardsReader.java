package application.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import application.domain.Card;
import application.domain.CardImpl;
import application.domain.CardLevel;
import application.domain.Gem;
import application.util.Util;


/**
 * This reads the cards from a csv file and converts it to objects.
 *
 * @author Sanchez
 */
public class CardsReader {

	private final static String cardsFile = "resources/config/cards.csv";
	private final static int bonusStartingIndex = 3; // At which index does the list of bonusses start
	private final static Gem[] gemList = new Gem[] { Gem.DIAMOND, Gem.SAPPHIRE, Gem.EMERALD, Gem.RUBY, Gem.ONYX }; // Follows the same order as in the cardsFile
	
	private List<Card> allCards;
	
	/**
	 * Instantiates a new cards reader.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public CardsReader() throws IOException
	{
		this.allCards = new ArrayList<Card>();
		this.generateCards();
	}
	
	/**
	 * Generate cards.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void generateCards()  throws IOException
	{
		try (
	    		FileInputStream file = new FileInputStream(cardsFile);
    		 	InputStreamReader reader = new InputStreamReader(file);
	    		CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(2).build();
	        ) 
	    {
			String[] cardRecord;
			while ((cardRecord = csvReader.readNext()) != null) 
			{
				Card card = readCard(cardRecord);
				allCards.add(card);
			}
	    }
	}
	
	
	/**
	 * Gets the cards.
	 *
	 * @param level
	 * @return a stack of cards corresponding to the given level.
	 */
	public Stack<Card> getCards(CardLevel level)
	{
		Stack<Card> cardsArray = new Stack<>(); 
		
		// Fill cardsArray with all cards with the corresponding level
		for(Card card : allCards)
		{
			try {
				if(card.getLevel().equals(level)) cardsArray.add(card);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		return cardsArray;
	}
	
	
	/**
	 * Read card.
	 *
	 * @param record
	 * @return Card
	 */
	// TODO: To add or not to add: private enum for record constants. e.g. [0] = CardRow.LEVEL_IDX, [1] = CardRow.PRESTIGE_IDX
	private static Card readCard(String[] record) throws RemoteException
	{
		CardLevel cardLevel = CardLevel.getLevel(Util.StringToInt(record[0], 1));
		
		int prestigeValue = Util.StringToInt(record[1], 0);
		String illustration = record[2];
		
		Gem bonus = readBonus(record);
		Map<Gem, Integer> costs = readCosts(record);
		
		Card card = new CardImpl(cardLevel, prestigeValue, illustration, bonus, costs);
		
		return card;
	}
	
	/**
	 * Read costs.
	 *
	 * @param record
	 * @return Map<Gem, Integer> the costs
	 */
	private static Map<Gem, Integer> readCosts(String[] record)
	{
		Map<Gem, Integer> costs = new LinkedHashMap<>();
		
		int startingIndex = bonusStartingIndex + gemList.length;
		
		for(int i = 0; i < gemList.length; i++)
		{
			int cost = Util.StringToInt(record[startingIndex + i], 0);
			costs.put(gemList[i], cost);
		}
		
		return costs;
	}
	
	/**
	 * Read bonus.
	 *
	 * @param record
	 * @return Gem
	 */
	private static Gem readBonus(String[] record)
	{
		int idx = 0;
		for(int i = 0; i < gemList.length; i++)
		{
			if(record[bonusStartingIndex + i].equals("1"))
			{
				idx = i;
				break;
			}
		}
		
		return gemList[idx];
	}
	
}
