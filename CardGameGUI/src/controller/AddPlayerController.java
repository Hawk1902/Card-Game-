package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameEngineCallbackGUI;

public class AddPlayerController implements ActionListener {

    private GameEngine gameEngine;
    private GameEngineCallbackGUI gameUI;

    public AddPlayerController(GameEngine gameEngine, GameEngineCallbackGUI gameUI) {
        this.gameEngine = gameEngine;
        this.gameUI = gameUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<Object> addPlayer = (ArrayList<Object>) e.getSource();

        String playerName = (String) addPlayer.get(0);
        String points = (String) addPlayer.get(1);
        
        // validate input data
        if (!validateData(playerName, points)) {
            gameUI.showMessage("Invalid Input");
            return;
        }

        Player player = new SimplePlayer(playerName.replaceAll("\\s", "_").toUpperCase(),
                                         playerName, Integer.parseInt(points));

        // another thread to call engine's method
        new Thread() {
            @Override
            public void run() {
                gameEngine.addPlayer(player);
            }
        }.start();

        gameUI.addNewPlayer(player);

    }

    private boolean validateData(String playerName, String points) {
        if (playerName.isEmpty()) {
            return false;
        }
        try {
            int p = Integer.parseInt(points);
            if (p <= 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

}
