package application.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

public class CardsReader {

	private final static String cardsFile = "resources/config/cards.csv";
	private final static int bonusStartingIndex = 3; // At which index does the list of bonusses start
	private final static Gem[] gemList = new Gem[] { Gem.DIAMOND, Gem.SAPPHIRE, Gem.EMERALD, Gem.RUBY, Gem.ONYX }; // Follows the same order as in the cardsFile
	
	private List<Card> allCards;
	
	public CardsReader() throws IOException
	{
		this.allCards = new ArrayList<Card>();
		this.generateCards();
	}
	
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
				// The cards get read/parsed here
				// They will then need to be stored somewhere.
				// 1. Do we create a Deck (and which one?) in a method and fill it immediately?
				// 2. Or do we create three member lists, one for each card level, which represent decks and store the cards in there -
				// and then create methods to retrieve these member arrays? 
				// 3. Or make 3 lists of Cards in a method and return them from a method immediately. 
				// 4. Likewise, we could create 3 filled Decks in a method and return them.
				// Are there any other alternatives?
				
				// a PlayingField will contain the 3 decks. I want the PlayingField to initiate the construction of the three decks.
				// Returning an array of decks from a single method would mean the order in which the level1-2-3 decks are returned is hardcoded, thus no good.
				// A more elegant solution is required. Something like: Deck deck1 = myDeckFactory.create(CardLevel.LEVEL1);
				// However, in the create() function, we wouldn't want to read the whole cards.csv again because that is an expensive IO operation.
				// Instead, we only wish to read the cards.csv in the constructor of the factory. Thus we HAVE to store the cards in memory after that's done.
				
				// So, the choice is option 5, one similar to 2.: We create a member map with CardLevels as keys and List<Card> as values. ( Map<CardLevel,List<Card>> )
				// At the time of writing this (15-05-18, 23:07), this current class is called DecksReader. I'm going to create a DeckFactory class as suggested above, 
				// however, should the DeckFactory class also have the responsibility of reading the cards file?
				
				// Suggestion: Rename DecksReader to CardsReader (have a NoblesReader later). Make that do the reading and have it contain the map of card sets. 
				// The DeckFactory class can then focus on what it needs to do: Create a Deck containing cards with the correct CardLevel.
				// Note: Typically, there will only be one DeckFactory instance that creates multiple decks. So don't think code reuse is the benefit here.
				// The only benefit would be some mild SOP. (Should a DeckFactory be reading files? -> No?)
				
				// 1:52am - Why bother having a DeckFactory if you'll have CardsReader returning an array of Cards. 
				// Construction logic of a Deck is simple enough that it won't really benefit from having a DeckFactory
				// Instead, the list can be passed through the constructor of the Deck instance directly.
				// The only benefit I'd see would be an extra layer of abstraction.
				
				// Lastly, considering the CardsReader actually creates Cards, wouldn't it be more of a CardFactory?
				// Possibly not because it creates arrays of cards - instead of single instances of them. 
				// Secondly, it produces a determined (final) amount of cards, as opposed to an infinite amount of them.
				// Thirdly, once again, should a Factory be reading/parsing files? -> No? (separation of concerns)
				
				Card card = readCard(cardRecord);
				allCards.add(card);
			}
	    }
	}
	
	
	/**
	 * @param level - CardLevel
	 * @return Returns a stack of cards corresponding to the given level. 
	 */
	public Stack<Card> getCards(CardLevel level)
	{
		Stack<Card> cardsArray = new Stack<>(); 
		
		// Fill cardsArray with all cards with the corresponding level
		for(Card card : allCards)
		{
			if(card.getLevel().equals(level)) cardsArray.add(card);
		}
		
		return cardsArray;
	}
	
	
	// TODO: To add or not to add: private enum for record constants. e.g. [0] = CardRow.LEVEL_IDX, [1] = CardRow.PRESTIGE_IDX
	private static Card readCard(String[] record)
	{
		CardLevel cardLevel = CardLevel.getLevel(Util.StringToInt(record[0], 1));
		
		int prestigeValue = Util.StringToInt(record[1], 0);
		String illustration = record[2];
		
		Gem bonus = readBonus(record);
		Map<Gem, Integer> costs = readCosts(record);
		
		Card card = new CardImpl(cardLevel, prestigeValue, illustration, bonus, costs);
		
		return card;
	}
	
	private static Map<Gem, Integer> readCosts(String[] record)
	{
		Map<Gem, Integer> costs = new HashMap<>();
		
		int startingIndex = bonusStartingIndex + gemList.length;
		
		for(int i = 0; i < gemList.length; i++)
		{
			int cost = Util.StringToInt(record[startingIndex + i], 0);
			costs.put(gemList[i], cost);
		}
		
		return costs;
	}
	
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
