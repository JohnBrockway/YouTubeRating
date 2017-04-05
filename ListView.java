import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ListView extends JPanel implements View{

	ModelYouTube model;

	private ImageIcon starEmpty;
	private ImageIcon starFull;
	
	public ListView(ModelYouTube model) {
		this.model = model;
		this.model.addView(this);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
	
	@Override
	public void update() {
		this.removeAll();
		for (int i = 0 ; i < model.getResults() ; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 2));
			
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
			
			panel.add(l);
			
			JPanel right = new JPanel();
			right.setLayout(new GridLayout(3, 1));
			JPanel topRight = new JPanel();
			
			starEmpty = new ImageIcon("starEmpty.png");        
			Image starEmptyResize = starEmpty.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			starEmpty.setImage(starEmptyResize);

			starFull = new ImageIcon("starFull.png");        
			Image starFullResize = starFull.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			starFull.setImage(starFullResize);

			JLabel [] stars = new JLabel[5];
			for (int j = 0 ; j < stars.length ; j++) {
				stars[j] = new JLabel(starEmpty);
				topRight.add(stars[j]);
			}
			
			right.add(topRight);
			
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
			right.add(title);
			right.add(date);
			panel.add(right);
			
			panel.setPreferredSize(new Dimension(380, 200));
			panel.setMaximumSize(new Dimension (380, 200));
			panel.setBorder(BorderFactory.createEtchedBorder());
			this.add(panel);
//			repaint();
//			validate();
		}
		repaint();
		validate();
		setVisible(true);
		
	}

}
