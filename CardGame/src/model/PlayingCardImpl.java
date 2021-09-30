package model;

import model.interfaces.PlayingCard;

public class PlayingCardImpl implements PlayingCard
{

	private Suit suit;
	private Value value;

	public PlayingCardImpl(Suit suit, Value value)
	{
		this.suit = suit;
		this.value = value;
	}

	@Override
	public Suit getSuit()
	{
		return this.suit;
	}

	@Override
	public Value getValue()
	{
		return this.value;
	}

	@Override
	public int getScore()
	{
		// Returning score according to face value 
		switch (this.value)
		{
			case EIGHT : return 8; 
			case NINE :  return 9;
			case TEN :   return 10; 
			case JACK :  return 10; 
			case QUEEN : return 10;
			case KING :  return 10; 
			default : return 11;
		}
	}

	@Override
	public boolean equals(PlayingCard card)
	{
		return this.suit.equals(card.getSuit()) && this.value.equals(card.getValue());
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj == null)
		{
			return false;
		}
		if(obj instanceof PlayingCard)
		{
			PlayingCard card = (PlayingCard)obj;
			return this.suit.equals(card.getSuit()) && this.value.equals(card.getValue());
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return String.format("Suit: %s, Value: %s, Score: %d",
				              this.getSuit(), this.getValue(), this.getScore());
	}
	
	
	
	
}
