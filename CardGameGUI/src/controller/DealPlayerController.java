package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameEngineCallbackGUI;

public class DealPlayerController implements ActionListener {

    private GameEngine gameEngine;
    private GameEngineCallbackGUI gameUI;

    public DealPlayerController(GameEngine gameEngine,
                                GameEngineCallbackGUI gameUI) {
        this.gameEngine = gameEngine;
        this.gameUI = gameUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // receiving data through Action Event
        String playerId = (String) e.getSource();
        Player player = gameEngine.getPlayer(playerId);

        // another thread to call engine's method
        new Thread() {
            @Override
            public void run() {
                gameEngine.dealPlayer(player, 100);
            }
        }.start();

        gameUI.startDeal();
    }

}
