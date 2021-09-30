/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameEngineCallbackGUI;

/**
 *
 * @author Bashar
 */
public class BetController implements ActionListener {

    private GameEngine gameEngine;
    private GameEngineCallbackGUI gameUI;

    public BetController(GameEngine gameEngine, GameEngineCallbackGUI gameUI) {
        this.gameEngine = gameEngine;
        this.gameUI = gameUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // receiving data which pass through action event
        ArrayList<Object> betData = (ArrayList<Object>) e.getSource();
        String playerId = (String) betData.get(0);
        String betInput = (String) betData.get(1);

        // validating input
        if(!validateBet(betInput)) {
            gameUI.showMessage("Invalid bet amount.");
            return;
        }

        int bet = Integer.parseInt(betInput);
        Player player = gameEngine.getPlayer(playerId);
        if (player.getPoints() < bet) {
            gameUI.showMessage("Bet cannot be grater than points.");
            return;
        }

        // another thread to call engine's method
        new Thread() {
            @Override
            public void run() {
                gameEngine.placeBet(player, bet);
            }
        }.start();
        gameUI.placeBet(player, bet);

    }

    private boolean validateBet(String betInput) {
        try {
            int bet = Integer.parseInt(betInput);
            if (bet <= 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
