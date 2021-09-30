package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameEngineCallbackGUI;

public class DealHouseController implements ActionListener {

    private GameEngine gameEngine;
    private GameEngineCallbackGUI gameUI;

    public DealHouseController(GameEngine gameEngine,
                                GameEngineCallbackGUI gameUI) {
        this.gameEngine = gameEngine;
        this.gameUI = gameUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {        
        // another thread to call engine's method
        new Thread() {
            @Override
            public void run() {
                gameEngine.dealHouse(100);
            }
        }.start();

        gameUI.startDeal();
    }

}
