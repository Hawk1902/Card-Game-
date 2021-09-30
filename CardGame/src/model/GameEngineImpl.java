package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.TimeUnit;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import model.interfaces.PlayingCard.Suit;
import model.interfaces.PlayingCard.Value;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine
{
    private List<GameEngineCallback>  gameEngineCallbacks = new ArrayList<GameEngineCallback>();
    private List<Player> players = new ArrayList<Player>();
    private Deque<PlayingCard> shuffledCards = null;
    
	@Override
	public void applyWinLoss(Player player, int houseResult)
	{
		// Comparing with house result if greater then points increases 
		// If less then house result then points decreases 
		if(player.getResult() > houseResult) player.setPoints(player.getPoints() + player.getBet());
		if(player.getResult() < houseResult) player.setPoints(player.getPoints() - player.getBet());	
	}

	@Override
	public void addPlayer(Player player)
	{
		// Adding player to the game
		players.add(player);
	}

	@Override
	public boolean removePlayer(Player player)
	{
		Player playerInGame = getPlayer(player.getPlayerId());
		// If player is in the game then remove and return true
		if(playerInGame != null)
		{
			this.players.remove(player);
			return true;
		}
		// If player is not in the game then returns false
		return false;
	}

	@Override
	public boolean placeBet(Player player, int bet)
	{
		return player.setBet(bet);
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback)
	{
		this.gameEngineCallbacks.add(gameEngineCallback);
	}

	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback)
	{
		// Checking gameEngineCallback in the gameEngine
		if(gameEngineCallbacks.contains(gameEngineCallback))
		{
			this.gameEngineCallbacks.remove(gameEngineCallback);
			return true;
		}
		return false;
	}

	@Override
	public void dealPlayer(Player player, int delay) throws IllegalArgumentException
	{
		int totalResult = 0;
		// Throws error message if delay is not properly set
		if (delay < 0 || delay > 1000)
		{
			throw new IllegalArgumentException("Delay cannot be more than 1000 or less than 0");
		}
		while(totalResult < BUST_LEVEL)
		{
			delay(delay);
		    PlayingCard card = getShuffledCard();
		    // Checking latest result with the BUST_LEVEL
			if(totalResult + card.getScore() < BUST_LEVEL) 
			{
				// Increasing total with the card's score
				totalResult = totalResult + card.getScore();
				for(GameEngineCallback gameEngineCallback : gameEngineCallbacks)
				{
					gameEngineCallback.nextCard(player, card, this);
				}	
			}
			else 
			{
				for(GameEngineCallback gameEngineCallback : gameEngineCallbacks)
				{
					gameEngineCallback.bustCard(player, card, this);
				}
				break;
			}
		}
		
		for(GameEngineCallback gameEngineCallback : gameEngineCallbacks)
		{
			gameEngineCallback.result(player, totalResult, this);
		}
		// Adds score of all the cards in the result
		player.setResult(totalResult);
		
		
		 
		
		
	}
	
	// More or less similar to dealPlayer
	@Override
	public void dealHouse(int delay) throws IllegalArgumentException
	{
		
		
		int houseResult = 0;
		// Throws error message if delay is not properly set
		if (delay < 0 || delay > 1000)
		{
			throw new IllegalArgumentException("Delay cannot be more than 1000 or less than 0");
		}
		// Checks result of house with the BUST_LEVEL
		while(houseResult < BUST_LEVEL)
		{
			delay(delay);
			PlayingCard card = getShuffledCard();			
			// Increasing result of house with the card's score
			if(houseResult + card.getScore() < BUST_LEVEL) 
			{
				houseResult = houseResult + card.getScore();
				for(GameEngineCallback gameEngineCallback : gameEngineCallbacks)
				{
					gameEngineCallback.nextHouseCard(card, this);
				}	
			}
			else 
			{
				for(GameEngineCallback gameEngineCallback : gameEngineCallbacks)
				{
					gameEngineCallback.houseBustCard(card, this);
				}
				break;
			}	
		}
		// Applying win/loss for all the players	
		for(Player player : players)
		{
			applyWinLoss(player, houseResult);
		}
		
		for(GameEngineCallback gameEngineCallback : gameEngineCallbacks)
		{
			gameEngineCallback.houseResult(houseResult, this);
		}	
			
		// Reseting the bet before staring a new round 
		players.forEach((player) -> {player.resetBet();});
		
	}

	@Override
	public Player getPlayer(String id)
	{
		for(Player player : players)
		{
			// Checking the existing player
			if(player.getPlayerId().equals(id))
			{
				return player;
			}
		}
		return null;
	}

	@Override
	public Collection<Player> getAllPlayers()
	{
		// Returns all the players after sorting them
		Collections.sort(this.players);
		Collections.unmodifiableList(this.players);
		return this.players;
	}

	@Override
	public Deque<PlayingCard> getShuffledHalfDeck()
	{
		List<PlayingCard> cards = new ArrayList<PlayingCard>();
		for(Suit suit : Suit.values())
		{
			for(Value value : Value.values())
			{
				// Adding new card to the list of cards 
				cards.add(new PlayingCardImpl(suit, value));
			}		
		}
		// Shuffles the cards 
		Collections.shuffle(cards);
		// Converting List to Queue
		Deque<PlayingCard> cardDeque = new ArrayDeque<PlayingCard>(cards);
		return cardDeque;
	}
	
	// Method for delay in milliseconds
	private void delay(int ms)
	{
		try
		{
			TimeUnit.MILLISECONDS.sleep(ms);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	// Method to provide a card from the shuffled deck
	private PlayingCard getShuffledCard()
	{
		// Picks a card from the top of the shuffled deck
		if(shuffledCards == null || shuffledCards.isEmpty())
		{
			shuffledCards = getShuffledHalfDeck();
		}
		return shuffledCards.pop();
	}

}
