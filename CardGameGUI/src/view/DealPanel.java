package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import model.interfaces.PlayingCard;

public class DealPanel extends JPanel 
{

    private final int NUMBER_OF_CARDS = 6;
    private final double SPACING_RATIO = 0.05;
    private final double HEIGHT_RATIO = 1.5;
    private final double SUIT_RATIO = 0.25;
    private final double NAME_RATIO = 0.1;

    
    private ArrayList<PlayingCard> cards = new ArrayList<PlayingCard>();
    
    
    public DealPanel() 
    {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }
    
    public void clearCards() 
    {
        cards.clear();
        repaint();
    }
    
    public void drawNextCard(PlayingCard card) {
        cards.add(card);
        repaint();
    }

    public void drawBustCard(PlayingCard card) {
        cards.add(card);
        repaint();
    }
    


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(cards.isEmpty()) {
            return;
        }

        int cardSpacing = (int) ((this.getWidth() / NUMBER_OF_CARDS) * SPACING_RATIO);
        double cardWidth = (this.getWidth() - cardSpacing * (NUMBER_OF_CARDS + 1)) / NUMBER_OF_CARDS;
        double cardHeight = cardWidth * HEIGHT_RATIO;
        Dimension cardDimension = new Dimension((int) cardWidth, (int) cardHeight);
        Point topLeftCorner = new Point(cardSpacing, cardSpacing);

        PlayingCard bustCard = cards.get(cards.size()-1);
        
        for(PlayingCard card : cards) {
            if(card == bustCard) {
                drawGrayCard(g, topLeftCorner, cardDimension);                
            }
            else {
                drawCard(g, topLeftCorner, cardDimension);
            }
            drawCardValue(g, card, topLeftCorner, cardDimension);
            drawCardSuit(g, card, topLeftCorner, cardDimension);
            topLeftCorner.x += cardDimension.width + cardSpacing;                
        }

        g.dispose();
    }

    private void drawCard(Graphics g, Point topLeftCorner, Dimension cardDimension) {
        g.setColor(Color.WHITE);
        g.fillRoundRect(topLeftCorner.x, topLeftCorner.y, cardDimension.width, cardDimension.height, 25, 25);
    }

    private void drawGrayCard(Graphics g, Point topLeftCorner, Dimension cardDimension) {
        g.setColor(Color.GRAY);
        g.fillRoundRect(topLeftCorner.x, topLeftCorner.y, cardDimension.width, cardDimension.height, 25, 25);
    }

    private void drawCardValue(Graphics g, PlayingCard card, Point topLeftCorner, Dimension cardDimension) {
        String cardName = getCardName(card);
        g.setColor(getCardColor(card));
        int space = (int) (cardDimension.width * NAME_RATIO);
        g.setFont(new Font("default", Font.BOLD, space));
        Dimension cardNameSize = new Dimension(g.getFontMetrics().stringWidth(cardName),
                                    (int) g.getFontMetrics().getLineMetrics(cardName, g).getHeight());

        g.drawString(cardName, topLeftCorner.x + space, topLeftCorner.y + space + (int) (cardNameSize.height / 2));
        g.drawString(cardName, topLeftCorner.x + cardDimension.width - cardNameSize.width - space, topLeftCorner.y + cardDimension.height - space);
    }

    private void drawCardSuit(Graphics g, PlayingCard card, Point topLeftCorner, Dimension cardDimension) {
        BufferedImage image;
        try {
            image = ImageIO.read(getSuitUrl(card));
            int width = (int) (cardDimension.width * SUIT_RATIO);
            int x = topLeftCorner.x + cardDimension.width / 2 - width / 2;
            int y = topLeftCorner.y + cardDimension.height / 2 - width / 2;
            g.drawImage(image, x, y, width, width, null);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private URL getSuitUrl(PlayingCard card) {

        String suit;
        switch (card.getSuit()) {
            case DIAMONDS:
                suit = "diamonds.png";
                break;

            case HEARTS:
                suit = "hearts.png";
                break;

            case CLUBS:
                suit = "clubs.png";
                break;

            default:
                suit = "spades.png";
                break;
        }
        return this.getClass().getResource("/img/" + suit);
    }

    private String getCardName(PlayingCard card) {
        switch (card.getValue()) {
            case EIGHT:
                return "8";
            case NINE:
                return "9";
            case TEN:
                return "10";
            default:
                return card.getValue().toString().substring(0, 1);
        }
    }

    private Color getCardColor(PlayingCard card) {
        switch (card.getSuit()) {
            case DIAMONDS:
            case HEARTS:
                return Color.RED;
            case CLUBS:
            case SPADES:
            default:
                return Color.BLACK;
        }
    }

    private void delay(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
