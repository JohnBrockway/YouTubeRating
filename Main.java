import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Main {

	private static ModelYouTube youtubeModel;
	private static JFrame frame;
	private static JComponent grid;
	private static JComponent list;
	private static JScrollPane scroll;
	
    /*
     * API key obtained by following this guide:
     * https://developers.google.com/youtube/registering_an_application#create_project
     */ 
    public static void main(String[] args) {
        
        youtubeModel = new ModelYouTube("AIzaSyB4yqXu2ry3TImM_xzGMwkOCHM0IVeWwow");
        
        JComponent toolbar = new ToolbarView(youtubeModel);
        toolbar.setBorder(BorderFactory.createRaisedBevelBorder());
        
        JPanel p = new JPanel();
        
        grid = new GridView(youtubeModel, 3, p);
        
        list = new ListView(youtubeModel);
        
        scroll = new JScrollPane(list);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        frame = new JFrame("Youtube Search");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(toolbar, BorderLayout.NORTH);
        frame.getContentPane().add(scroll, BorderLayout.CENTER);
        frame.pack();
        frame.setSize(1280, 700);
        frame.setMinimumSize(new Dimension(400, 150));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                if (frame.getWidth() < 600) {
                	
                }
            }
        });
        frame.setVisible(true);
    }
    
    public static void update() {
    	if (youtubeModel.isGrid()) {
    		frame.getContentPane().remove(scroll);
            scroll = new JScrollPane(grid);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    		frame.getContentPane().add(scroll, BorderLayout.CENTER);
    		frame.repaint();
    		frame.validate();
    	}
    	else {
    		frame.getContentPane().remove(scroll);
            scroll = new JScrollPane(list);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    		frame.getContentPane().add(scroll, BorderLayout.CENTER);    
    		frame.repaint();
    		frame.validate();		
    	}
    }

}