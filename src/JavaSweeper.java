import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coordinate;
import sweeper.Ranges;
import sweeper.Game;

public class JavaSweeper extends JFrame {
    private Game game;
    private JPanel panel;
    private JLabel label;
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;
    public static void main(String[] args) {
        new JavaSweeper();
    }
    private JavaSweeper(){
       game = new Game(COLS,ROWS, BOMBS);
       game.startGame();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    // Frame initialization function
    private void initFrame () {
        pack(); // change form size
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // close program
        setTitle("Sweeper"); // Program title
        setLocationRelativeTo(null); // position window on center
        setResizable(false); // no change default window size
        setVisible(true); // set visible for program window
    }

    private void setImages (){
        for (Box box : Box.values())
            box.image = getImage(box.name());
    }

    private void initLabel(){
        label = new JLabel("Начало игры!");
        add(label,BorderLayout.SOUTH);
    }
    // Panel initialization function
    private void initPanel(){
        // init panel
        panel = new JPanel(){
            // paint Image
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                    for(Coordinate coordinate:Ranges.getAllCoordinate())
                        g.drawImage((Image) game.getBox(coordinate).image, coordinate.x * IMAGE_SIZE, coordinate.y * IMAGE_SIZE, this);
            }
        };

        //add mouse control
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coordinate coordinate = new Coordinate(x,y);
                if(e.getButton() == MouseEvent.BUTTON1){
                    game.pressLeftButton(coordinate);
                    panel.repaint();
                }
                if(e.getButton() == MouseEvent.BUTTON3){
                    game.pressRightButton(coordinate);
                    panel.repaint();
                }
                if(e.getButton() == MouseEvent.BUTTON2){
                    game.startGame();
                    panel.repaint();
                }
                label.setText(getMessage());
            }

            private String getMessage() {
                switch (game.getState()){
                    case PLAYED: return "Удачи!";
                    case BOMBED: return "Вы проиграли!";
                    case WINNER: return "Вы победили!";
                    default: return "";
                }
            }
        });
        // panel window size
        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE,Ranges.getSize().y * IMAGE_SIZE));
        // add panel from JFrame class
        add(panel);
    }
    private Image getImage (String name){
        String filename = "img/" + name.toLowerCase() + ".png";
        // get Image in getResource
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
