package model;

import model.interfaces.Player;

public class SimplePlayer implements Player
{
//	SimplePlayer("2", "The Shark", 1000)
	private String playerId;
	private String playerName;
	private int points;
	private int bet;
	private int result;
	

	public SimplePlayer(String id, String playerName, int initialPoints)
	{
		this.playerId = id;
		this.playerName = playerName;
		this.points = initialPoints;
	}


	@Override
	public boolean setBet(int bet)
	{
		// Negative bet does not make any sense but okayy
		if(bet < 0) resetBet();
		// Checking ability to bet
		if(bet <= this.points)
		{
			this.bet = bet;
			return true;
		}
		return false;
	}


	@Override
	public void resetBet()
	{
		this.bet = 0;
	}

	@Override
	public String getPlayerName()
	{
		return this.playerName ;
	}

	@Override
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}

	@Override
	public int getPoints()
	{
		return this.points;
	}

	@Override
	public void setPoints(int points)
	{
		this.points = points;
	}

	@Override
	public String getPlayerId()
	{
		return this.playerId ;
	}

	@Override
	public int getBet()
	{
		return this.bet;
	}

	@Override
	public int getResult()
	{
		return this.result;
	}

	@Override
	public void setResult(int result)
	{
		this.result = result;
	}

	@Override
	public boolean equals(Player player)
	{
		return this.playerId == player.getPlayerId() && 
				this.playerName == player.getPlayerName() && 
				this.points == player.getPoints();
	}

	@Override
	public int compareTo(Player player)
	{
		return this.playerId.compareTo(player.getPlayerId());
	}
	
	// Formatting toString according to specification 
	@Override
	public String toString()
	{
		return String.format("Player: id=%s, name=%s, bet=%d, points=%d, RESULT .. %d", 
							 this.getPlayerId(), this.getPlayerName(), this.getBet(), this.getPoints(), this.getResult());
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + bet;
		result = prime * result + ((playerId == null) ? 0 : playerId.hashCode());
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
		result = prime * result + points;
		result = prime * result + this.result;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimplePlayer other = (SimplePlayer) obj;
		if (bet != other.bet)
			return false;
		if (playerId == null)
		{
			if (other.playerId != null)
				return false;
		} else if (!playerId.equals(other.playerId))
			return false;
		if (playerName == null)
		{
			if (other.playerName != null)
				return false;
		} else if (!playerName.equals(other.playerName))
			return false;
		if (points != other.points)
			return false;
		if (result != other.result)
			return false;
		return true;
	}
	
	

}
