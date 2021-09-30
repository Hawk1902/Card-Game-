package view;

import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddPlayerPanel extends JPanel
{

	private JTextField tfPlayerName;
	private JTextField tfPlayerPoint;
	public AddPlayerPanel()
	{
		setLayout(new GridLayout(0, 2, 2, 2));
		tfPlayerName = new JTextField();
		tfPlayerPoint = new JTextField();
		add(new Label("Name"));
		add(tfPlayerName);
		add(new Label("Points"));
		add(tfPlayerPoint);
		
	}
	
	public String getPlayerName() 
	{
		return this.tfPlayerName.getText();
	}
	
	public String getPlayerPoint()
	{
            return this.tfPlayerPoint.getText();
	}

}
