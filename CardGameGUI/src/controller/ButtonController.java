/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameEngineCallbackGUI;


public class ButtonController implements ActionListener {

    private GameEngine gameEngine;
    private GameEngineCallbackGUI gameUI;

    public ButtonController(GameEngine gameEngine, GameEngineCallbackGUI gameUI) {
        this.gameEngine = gameEngine;
        this.gameUI = gameUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String playerId = (String) e.getSource();
        if(playerId.equalsIgnoreCase("House")) {
            gameUI.updateButton(null, true);
        }
        else {
            Player player = gameEngine.getPlayer(playerId);
            gameUI.updateButton(player, false);            
        }

    }

}
