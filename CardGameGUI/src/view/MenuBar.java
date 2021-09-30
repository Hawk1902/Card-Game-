package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import controller.AddPlayerController;
import java.util.ArrayList;

public class MenuBar extends JMenuBar {

    private JMenu menu;
    private JMenuItem miAddPlayer;
    private JMenuItem miExit;

    public MenuBar(AddPlayerController addPlayerController) {
        menu = new JMenu("Menu");
        miAddPlayer = new JMenuItem("Add Player");
        
        // set listener when chick addPlayer
        miAddPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPlayerPanel addPlayerPanel = new AddPlayerPanel();                
                int result = JOptionPane.showConfirmDialog(null, addPlayerPanel, "Add Player", JOptionPane.OK_CANCEL_OPTION);
                
                // checking yes option
                if (result == JOptionPane.YES_OPTION) {
                    // passing data to controller
                    ArrayList<Object> playerData = new ArrayList<Object>();
                    playerData.add(addPlayerPanel.getPlayerName());
                    playerData.add(addPlayerPanel.getPlayerPoint());
                    addPlayerController.actionPerformed(new ActionEvent(
                            playerData, 0, "Add Player"));
                }
            }
        });
        miExit = new JMenuItem("Exit");
        // listen and exit program on click exit
        miExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(miAddPlayer);
        menu.add(miExit);
        add(menu);
    }
}
