package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.AddPlayerController;
import controller.BetController;
import controller.ButtonController;
import controller.DealHouseController;
import controller.DealPlayerController;
import controller.RemovePlayerController;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

public class GameEngineCallbackGUI implements GameEngineCallback {

    private final GameEngine gameEngine;

    private MainFrame mainFrame;
    private MenuBar menuBar;
    private TopPanel topPanel;
    private JPanel contentPane;
    private DealPanel dealPanel;
    private SummaryPanel summaryPanel;

    private ArrayList<Player> dealtPlayers = new ArrayList<Player>();
    private ArrayList<Player> betPlayers = new ArrayList<Player>();

    public GameEngineCallbackGUI(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void start() {
        
        // creating Main JFrame
        mainFrame = new MainFrame();

        // adding ContentPane
        contentPane = new JPanel(new BorderLayout());
        mainFrame.setContentPane(contentPane);

        // adding MenuBar
        menuBar = new MenuBar(new AddPlayerController(this.gameEngine, this));
        mainFrame.setJMenuBar(menuBar);

        // adding TopPanel on top(NORTH) of ContentPane
        topPanel = new TopPanel(new RemovePlayerController(gameEngine, this),
                                new DealPlayerController(gameEngine, this),
                                new BetController(gameEngine, this),
                                new ButtonController(gameEngine, this));
        contentPane.add(topPanel, BorderLayout.NORTH);

        // adding DealPanel(Cards) on middle(CENTER) of ContentPane
        dealPanel = new DealPanel();
        contentPane.add(dealPanel, BorderLayout.CENTER);
        
        // adding Summary on botton(SOUTH) of ContentPane
        summaryPanel = new SummaryPanel();
        JScrollPane scroll = new JScrollPane(summaryPanel);
        scroll.setPreferredSize(new Dimension(500, 100));
        contentPane.add(scroll, BorderLayout.SOUTH);

        // adding DealPanel(Cards) on middle(CENTER) of ContentPane
        dealPanel = new DealPanel();
        contentPane.add(dealPanel, BorderLayout.CENTER);

        // visible the Main JFrame
        mainFrame.setVisible(true);
    }

    // Add new player to UI
    public void addNewPlayer(Player player) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                topPanel.addNewPlayer(player.getPlayerName());
                topPanel.setPlayerBox(player.getPlayerName());
                summaryPanel.addNewPlayer(player);
            }
        });
    }

    // remove player add new player
    public void removePlayer(Player player) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                topPanel.playerRemoved(player.getPlayerName());
                summaryPanel.removePlayer(player);
            }
        });
        dealtPlayers.remove(player);
        betPlayers.remove(player);
    }

    // start deal so clear the Desk
    public void startDeal() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dealPanel.clearCards();
                dealPanel.repaint();
            }
        });
    }

    // place bet and update UI
    public void placeBet(Player player, int bet) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                summaryPanel.placeBet(player);
            }
        });
        betPlayers.add(player);
        updateButton(player, false);
    }

    // drawing card
    @Override
    public void nextCard(Player player, PlayingCard card, GameEngine engine) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dealPanel.drawNextCard(card);
            }
        });
    }

    // drawing gray card
    @Override
    public void bustCard(Player player, PlayingCard card, GameEngine engine) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dealPanel.drawBustCard(card);
            }
        });
    }

    // update player result
    @Override
    public void result(Player player, int result, GameEngine engine) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                summaryPanel.updateResult(player, result);
            }
        });
        
        dealtPlayers.add(player);
        
        // Automatic deal house if all player dealt
        if(isAllPlayerDealt()) {
            dealtPlayers.clear();
            betPlayers.clear();
            showMessage("All player dealt. Now its House deal");
            topPanel.setPlayerBox("House");
            new DealHouseController(gameEngine, this).actionPerformed(
                new ActionEvent("", 0, "Deal House"));            
        }
    }

    // drawing house card
    @Override
    public void nextHouseCard(PlayingCard card, GameEngine engine) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dealPanel.drawNextCard(card);
            }
        });

    }

    // drawing gray card
    @Override
    public void houseBustCard(PlayingCard card, GameEngine engine) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dealPanel.drawBustCard(card);
            }
        });
    }

    // apply win/loss by updating house result
    @Override
    public void houseResult(int result, GameEngine engine) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                summaryPanel.updateHouseResult(result);
                for (Player player : engine.getAllPlayers()) {
                    summaryPanel.updateWinLoss(player);
                }
            }
        });
    }
    
    

    // update buttons on basis on selected player all disable if it is House 
    public void updateButton(Player player, boolean isHouse) {
        if(isHouse) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    topPanel.dealBtnUpdate(false);
                    topPanel.betBtnUpdate(false);
                    topPanel.removeBtnUpdate(false);
                }
            });
        }
        else if(isBet(player) ) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // only button available for bet player is deal button
                    topPanel.dealBtnUpdate(true);
                    topPanel.betBtnUpdate(false);
                    topPanel.removeBtnUpdate(false);
                }
            });
        }
        else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // button for regular player
                    topPanel.dealBtnUpdate(false);
                    topPanel.betBtnUpdate(true);
                    topPanel.removeBtnUpdate(true);
                }
            });
            
        }
    }

    // private function for check player is set bet or not
    private boolean isBet(Player player) {
        return betPlayers.contains(player);
    }

    // private function for checking players dealt
    private boolean isAllPlayerDealt() {
        for(Player player : gameEngine.getAllPlayers()) {
            if(!dealtPlayers.contains(player)) {
                return false;
            }
        }
        return true;
    }

    // Private function for showing message
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

}
