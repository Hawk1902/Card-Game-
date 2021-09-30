/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.interfaces.Player;


public class SummaryPanel extends JPanel {
    private final int FONT_SIZE = 14;
    private final int HOUSE_FONT_SIZE = 16;
    
    private Map<String, JLabel> labels;
    private Map<String, Integer> lastPoints;
    private Map<String, Integer> lastResult;
    private Map<String, Integer> lastWinLoss;
    
    private JLabel lblHouse;


    public SummaryPanel() {
        this.labels = new HashMap<>();
        this.lastPoints = new HashMap<>();
        this.lastResult = new HashMap<>();
        this.lastWinLoss = new HashMap<>();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Summery:"));
        setBackground(Color.RED);
        setAutoscrolls(true);

        lblHouse = new JLabel("House Card: N/A");
        lblHouse.setForeground(Color.WHITE);
        lblHouse.setFont(new Font("default", Font.ITALIC, HOUSE_FONT_SIZE));
        add(lblHouse);
    }
    
    // add new player to SummeryPanel
    public void addNewPlayer(Player player) {
        JLabel lbl = new JLabel(getPlayerInfo(player));
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("default", Font.PLAIN, FONT_SIZE));
        labels.put(player.getPlayerId(), lbl);
        lastPoints.put(player.getPlayerId(), player.getPoints());
        add(labels.get(player.getPlayerId()));
    }

    // remove player from panel
    public void removePlayer(Player player) {
        lastPoints.remove(player.getPlayerId());
        remove(labels.get(player.getPlayerId()));
        repaint();
    }
    
    // update text on placing bet
    public void placeBet(Player player) {
        labels.get(player.getPlayerId()).setText(getPlayerInfo(player));
    }
    
    // update text on publishing a player result 
    public void updateResult(Player player, int result) {
        lastResult.put(player.getPlayerId(), result);
        labels.get(player.getPlayerId()).setText(getPlayerInfo(player) + " | Card: "+result+" points");        
    }

    // show house card point
    public void updateHouseResult(int result) {
        lblHouse.setText("House Card: " + result + " points");
    }
    
    // update win/loss text by counting points with previous point 
    public void updateWinLoss(Player player) {
        lastWinLoss.put(player.getPlayerId(), player.getPoints()-lastPoints.get(player.getPlayerId()));        
        lastPoints.put(player.getPlayerId(), player.getPoints());
        labels.get(player.getPlayerId()).setText(getPlayerInfo(player) + " | Card: "+lastResult.get(player.getPlayerId())+" points");        
    }
    
    // private method for formatting player info string
    private String getPlayerInfo(Player player) {
        String playerInfo = "Player: "+ player.getPlayerName()+" | Points: " + player.getPoints() + " | Bet: " + player.getBet() + " Last Result: ";
        if(lastWinLoss.containsKey(player.getPlayerId())) {
            if(lastWinLoss.get(player.getPlayerId()) > 0) {
                return playerInfo + "WIN("+lastWinLoss.get(player.getPlayerId())+")";
            }
            else if(lastWinLoss.get(player.getPlayerId()) < 0) {
                return playerInfo + "LOSS("+lastWinLoss.get(player.getPlayerId())+")";
            }
            else {
                return playerInfo + "N/A";
            }
        }
        return playerInfo + "N/A";
    }
    
}
