import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ToolbarView extends JPanel implements View {

	private ModelYouTube model;
	private JButton gridLayout;
	private JButton listLayout;
	private JTextField searchBox;
	private JButton search;
	private JPanel loadSave;
	private JButton load;
	private JButton save;
	private JLabel [] stars;
	private ImageIcon starEmpty;
	private ImageIcon starFull;
	
	public ToolbarView(ModelYouTube model) {
		this.model = model;
		this.model.addView(this);
		
		ImageIcon grid = new ImageIcon("gridLayout.png");        
        Image gridResize = grid.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        grid.setImage(gridResize);
        
        ImageIcon list = new ImageIcon("listLayout.png");        
        Image listResize = list.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        list.setImage(listResize);
        
        ImageIcon searchIcon = new ImageIcon("search.png");        
        Image searchResize = searchIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        searchIcon.setImage(searchResize);
        
        starEmpty = new ImageIcon("starEmpty.png");        
        Image starEmptyResize = starEmpty.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        starEmpty.setImage(starEmptyResize);
        
        starFull = new ImageIcon("starFull.png");        
        Image starFullResize = starFull.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        starFull.setImage(starFullResize);
        
        gridLayout = new JButton(grid);
        listLayout = new JButton(list);
        searchBox = new JTextField();
        search = new JButton(searchIcon);
        loadSave = new JPanel();
        loadSave.setLayout(new GridLayout(2, 1));
        load = new JButton("Load");
        save = new JButton("Save");
        loadSave.add(load);
        loadSave.add(save);
        stars = new JLabel[5];
        for (int i =0 ; i < stars.length ; i++) {
        	stars[i] = new JLabel(starEmpty);
        }
        
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        
        c.insets = new Insets(0, 4, 0, 1);
        c.gridx = 2;
        c.weightx = 1;
        this.add(searchBox, c);

        c.gridx = 0;
        c.weightx = 0;
        c.insets = new Insets(0, 4, 0, 0);
        this.add(gridLayout, c);
        
        c.gridx = 1;
        c.insets = new Insets(0, 0, 0, 4);
        this.add(listLayout, c);
        
        c.gridx = 3;
        c.insets = new Insets(0, 1, 0, 4);
        this.add(search, c);
        
        c.gridx = 4;
        c.insets = new Insets(4, 4, 4, 8);
        this.add(loadSave, c);
        
        c.insets = new Insets(0, 1, 0, 1);
        for (int i = 0 ; i < stars.length ; i++) {
        	c.gridx = i + 5;
        	if (i == stars.length - 1) c.insets = new Insets(0, 0, 0, 4);
            this.add(stars[i], c);
        }
        
        for (int i = 0 ; i < stars.length ; i++) {
        	stars[i].addMouseListener(new MouseAdapter() {
    			public void mousePressed(MouseEvent e) {
    				e.getSource();
    		        for (int i = 0 ; i < stars.length ; i++) {
    		        	if (e.getComponent() == stars[i]) {
    		        		if (model.getFilter() == i + 1)
    		        			model.setFilter(0);
    		        		else
    		        			model.setFilter(i+1);
    		        		break;
    		        	}
    		        }
                }
    		});
        }
        
        gridLayout.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				gridLayout.setEnabled(false);
				listLayout.setEnabled(true);
                model.setGrid(true);
                Main.update();
            }
		});
        
        listLayout.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				gridLayout.setEnabled(true);
				listLayout.setEnabled(false);
                model.setGrid(false);
                Main.update();
            }
		});
        
        search.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
                model.setQuery(searchBox.getText());
                model.searchVideos();
            }
		});
        
        this.update();
	}

	@Override
	public void update() {		
		if (model.isGrid()) {
			gridLayout.setEnabled(false);
			listLayout.setEnabled(true);
		}
		else {
			gridLayout.setEnabled(true);
			listLayout.setEnabled(false);
		}
		
		for (int i = 0 ; i < stars.length ; i++) {
			if (i < model.getFilter()) {
				stars[i].setIcon(starFull);
			}
			else {
				stars[i].setIcon(starEmpty);
			}
		}
	}

}
