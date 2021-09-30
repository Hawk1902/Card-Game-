package client;

import model.GameEngineImpl;
import model.interfaces.GameEngine;
import view.GameEngineCallbackGUI;
import view.GameEngineCallbackImpl;
import view.interfaces.GameEngineCallback;
public class MainGame {


	public static void main(String[] args) 
	{
		final GameEngine gameEngine = new GameEngineImpl();
                gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());
		GameEngineCallbackGUI gameUI = new GameEngineCallbackGUI(gameEngine);
		gameEngine.addGameEngineCallback(gameUI);
		gameUI.start();
	}

}
