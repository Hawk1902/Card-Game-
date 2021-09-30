package view;

import controller.BetController;
import controller.ButtonController;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import controller.DealPlayerController;
import controller.RemovePlayerController;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class TopPanel extends JPanel {

    private JComboBox playerBox;
    private Vector players;
    private final DefaultComboBoxModel model;
    private JToolBar toolBar;
    private JButton btnDeal;
    private JButton btnBet;
    private JButton btnRemovePlayer;
    private RemovePlayerController removePlayerController;
    private DealPlayerController dealPlayerController;
    private BetController betController;
    private ButtonController buttonController;

    public TopPanel(RemovePlayerController removePlayerController,
                    DealPlayerController dealPlayerController,
                    BetController betController,
                    ButtonController buttonController) {
        this.removePlayerController = removePlayerController;
        this.dealPlayerController = dealPlayerController;
        this.betController = betController;
        this.buttonController = buttonController;

        toolBar = new JToolBar();
        toolBar.setRollover(true);

        players = new Vector();
        model = new DefaultComboBoxModel(players);
        playerBox = new JComboBox(model);
        playerBox.setPreferredSize(new Dimension(200, 20));
        model.addElement("House");
        toolBar.add(playerBox);

        btnDeal = new JButton("Deal");
        btnDeal.setEnabled(false);
        toolBar.add(btnDeal);

        btnBet = new JButton("Bet");
        btnBet.setEnabled(false);
        toolBar.add(btnBet);

        btnRemovePlayer = new JButton("Remove");
        btnRemovePlayer.setEnabled(false);
        toolBar.add(btnRemovePlayer);

        this.add(toolBar);

        // adding listener on player combobox
        playerBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // passing data to controller
                buttonController.actionPerformed(new ActionEvent(getSelectedPlayerId(), 0, "Update Button"));
            }
        });

        // adding listener to button
        btnDeal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // passing data to controller
                dealPlayerController.actionPerformed(new ActionEvent(getSelectedPlayerId(), 0, "Deal Player"));
            }
        });

        // adding listener to button
        btnBet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputBet = JOptionPane.showInputDialog(null, "Enter bet:", "");

                // checking input not cancelled
                if (inputBet != null) {
                    ArrayList<Object> betData = new ArrayList<Object>();
                    betData.add(getSelectedPlayerId());
                    betData.add(inputBet);

                    // passing data to controller
                    betController.actionPerformed(new ActionEvent(betData, 0, "Set Bet"));
                }
            }
        });

        // adding lister to button
        btnRemovePlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // passing data to controller
                removePlayerController.actionPerformed(new ActionEvent(getSelectedPlayerId(), 0, "Remove Player"));
            }
        });
    }

    // add new player to the combobox model
    public void addNewPlayer(String name) {
        model.addElement(name);
    }

    // remove player from model
    public void playerRemoved(String playerName) {
        model.removeElement(playerName);
    }

    // get selected player id from combobox by replacing space as to did in addPlayerController
    private String getSelectedPlayerId() {
        return playerBox.getSelectedItem().toString().replaceAll("\\s", "_").toUpperCase();
    }

    // select player name
    public void setPlayerBox(String playerName) {
        model.setSelectedItem(playerName);
    }

    // change button state
    public void dealBtnUpdate(boolean state) {
        btnDeal.setEnabled(state);
    }

    // change button state
    public void betBtnUpdate(boolean state) {
        btnBet.setEnabled(state);
    }

    // change button state
    public void removeBtnUpdate(boolean state) {
        btnRemovePlayer.setEnabled(state);
    }
}
