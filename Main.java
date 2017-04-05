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
	private static JComponent grid5;
	private static JComponent grid4;
	private static JComponent grid3;
	private static JComponent grid2;
	private static JComponent grid1;
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

		grid5 = new GridView(youtubeModel, 5);
		grid4 = new GridView(youtubeModel, 4);
		grid3 = new GridView(youtubeModel, 3);
		grid2 = new GridView(youtubeModel, 2);
		grid1 = new GridView(youtubeModel, 1);

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
				System.out.println(frame.getWidth());
				if (youtubeModel.isGrid()) {
					if (frame.getWidth() >= 1150) changeGrid(5);
					else if (frame.getWidth() < 1150 && frame.getWidth() >= 950) changeGrid(4);
					else if (frame.getWidth() < 950 && frame.getWidth() >= 750) changeGrid(3);
					else if (frame.getWidth() < 750 && frame.getWidth() >= 550) changeGrid(2);
					else if (frame.getWidth() < 550) changeGrid(1);
				}
			}
		});
		frame.setVisible(true);
	}

	public static void update() {
		if (youtubeModel.isGrid()) {
			if (frame.getWidth() >= 1150) changeGrid(5);
			else if (frame.getWidth() < 1150 && frame.getWidth() >= 950) changeGrid(4);
			else if (frame.getWidth() < 950 && frame.getWidth() >= 750) changeGrid(3);
			else if (frame.getWidth() < 750 && frame.getWidth() >= 550) changeGrid(2);
			else if (frame.getWidth() < 550) changeGrid(1);
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
	
	public static void changeGrid (int width) {
		frame.getContentPane().remove(scroll);
		if (width == 5) scroll = new JScrollPane(grid5);
		if (width == 4) scroll = new JScrollPane(grid4);
		if (width == 3) scroll = new JScrollPane(grid3);
		if (width == 2) scroll = new JScrollPane(grid2);
		if (width == 1) scroll = new JScrollPane(grid1);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scroll, BorderLayout.CENTER);
		frame.repaint();
		frame.validate();
	}

}