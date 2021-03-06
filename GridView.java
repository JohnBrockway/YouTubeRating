import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class GridView extends JPanel implements View {

	private ModelYouTube model;
	
	private int width;

	private ImageIcon starEmpty;
	private ImageIcon starFull;

	private GridLayout layout;

	public GridView(ModelYouTube model, int width) {
		this.model = model;
		this.model.addView(this);
		
		this.width = width;

		this.setLayout(new GridLayout((int)Math.ceil(25.0/width), width));
	}

	@Override
	public void update() {
		this.removeAll();
		int numToDisplay = 0;
		for (int i = 0 ; i < model.getResults() ; i++) {
			if (model.ratings[i] >= model.getFilter()) numToDisplay++;
		}
		this.setLayout(new GridLayout((int)Math.ceil((numToDisplay / 1.0)/width), width));
		for (int i = 0 ; i < model.getResults() ; i++) {
			if (model.ratings[i] >= model.getFilter()) {
				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout());
				JPanel top = new JPanel();
				FlowLayout lo = new FlowLayout();
				lo.setAlignment(FlowLayout.RIGHT);
				top.setLayout(lo);

				starEmpty = new ImageIcon("starEmpty.png");        
				Image starEmptyResize = starEmpty.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				starEmpty.setImage(starEmptyResize);

				starFull = new ImageIcon("starFull.png");        
				Image starFullResize = starFull.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				starFull.setImage(starFullResize);

				JLabel [] stars = new JLabel[5];
				for (int j = 0 ; j < stars.length ; j++) {
					if (model.ratings[i] > j) stars[j] = new JLabel(starFull);
					else stars[j] = new JLabel(starEmpty);
					int curStar = j + 1;
					int curVid = i;
					stars[j].addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent e) {
							e.getSource();
							for (int m = 0 ; m < stars.length ; m++) {
								if (e.getComponent() == stars[m]) {
									if (model.ratings[curVid] == curStar) model.setRating(curVid, 0);
									else model.setRating(curVid, curStar);
									break;
								}
							}
						}
					});
					top.add(stars[j]);
				}

				panel.add(top, BorderLayout.NORTH);
				ImageIcon ico = new ImageIcon(model.videoImages[i]);
				Image icoResize = ico.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
				ico.setImage(icoResize);
				JLabel l = new JLabel(ico);
				URL link = model.videoLinks[i];
				l.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
						if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
							try {
								desktop.browse(link.toURI());
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					}
				});

				panel.add(l, BorderLayout.CENTER);

				JPanel bottom = new JPanel();
				bottom.setLayout(new GridLayout(2, 1));
				JLabel title = new JLabel(model.videoTitles[i]);
				int daysSince = model.videoDates[i];
				int years = daysSince / 365;
				daysSince %= 365;
				int months = daysSince / 30;
				daysSince %= 30;
				int days = daysSince;
				String dateString = "";
				if (years != 0 && months != 0) {
					if (years == 1) dateString += "1 year, ";
					else dateString += years + " years, ";
					if (months == 1) dateString += "1 month ago";
					else dateString += months + " months ago";			
				}
				else if (years != 0) {
					if (years == 1) dateString += "1 year ago";
					else dateString += years + " years ago";		
				}
				else if (months != 0) {
					if (months == 1) dateString += "1 month";
					else dateString += months + " months";		
					if (days != 0) {
						if (days == 1) dateString += ", 1 day ago";
						else dateString += ", " + days + " days ago";
					}
					else dateString += " ago";
				}
				else {
					if (days == 0) dateString += "Today";
					else if (days == 1) dateString += "1 day ago";
					else dateString += days + " days ago";
				}

				JLabel date = new JLabel(dateString);
				bottom.add(title);
				bottom.add(date);
				panel.add(bottom, BorderLayout.SOUTH);

				panel.setPreferredSize(new Dimension(200, 200));
				panel.setMinimumSize(new Dimension (200, 200));
				panel.setMaximumSize(new Dimension (200, 200));
				panel.setBorder(BorderFactory.createEtchedBorder());
				this.add(panel);
			}
		}
		repaint();
		validate();
		setVisible(true);
	}

}
