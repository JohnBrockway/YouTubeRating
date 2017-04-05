import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class ModelYouTube {

	private List<View> views;

	private static long MAX_NUM_RESULTS = 25;

	private String apiKey;
	private YouTube youtube;

	private boolean grid;
	private int filter;
	private int numResults;
	private String query;

	public String[] videoTitles;
	public BufferedImage[] videoImages;
	public int[] videoDates;
	public URL[] videoLinks;
	public int[] ratings;

	// good idea to dis-allow default constructor usage because an API key is strictly necessary
	private ModelYouTube() {}

	/*
	 * Primary constructor to be used, this requires the creation of a YouTube API key.
	 * see: https://developers.google.com/youtube/registering_an_application#create_project
	 * returns null or raises an exception if something went wrong.
	 */
	public ModelYouTube(String apiKey) {
		this.views = new ArrayList();
		this.grid = false;
		this.filter = 0;
		this.videoTitles = new String[(int) MAX_NUM_RESULTS];
		this.videoImages = new BufferedImage[(int) MAX_NUM_RESULTS];
		this.videoDates = new int[(int) MAX_NUM_RESULTS];
		this.videoLinks = new URL[(int) MAX_NUM_RESULTS];
		this.ratings = new int[(int) MAX_NUM_RESULTS];
		this.numResults = 0;

		this.apiKey = apiKey;

		try {
			YouTube.Builder builder = new YouTube.Builder(
					new NetHttpTransport(), new JacksonFactory(),new HttpRequestInitializer() {
						@Override 
						public void initialize(HttpRequest httpRequest) throws IOException {}
					});
			builder.setApplicationName("Youtube Video Search - jcbrockw");
			youtube = builder.build();

		} catch (Exception e) {
			e.printStackTrace();
			this.throwModelInitializationException();
		}

	}

	/*
	 * Use this method as a template to search and extract video information for your data model.
	 * Searches for videos and prints some metadata.
	 * Refer to the API documentation to see how the API can be used to extract information.
	 * https://developers.google.com/youtube/v3/code_samples/java#search_by_keyword
	 *
	 */
	public void searchVideos() {
		numResults = 0;
		
		new Thread() {
			public void run() {
				if (youtube == null) {
					throwModelInitializationException();
				}

				try {
					YouTube.Search.List search = youtube.search().list("id,snippet");

					search.setKey(apiKey);
					search.setQ(query);
					search.setType("video");
					search.setSafeSearch("strict");
					search.setMaxResults(MAX_NUM_RESULTS);

					SearchListResponse searchResponse = search.execute();
					List<SearchResult> resultsList = searchResponse.getItems();

					if (resultsList != null) {
						Iterator<SearchResult> resultsIterator = resultsList.iterator();

						while (resultsIterator.hasNext()) {

							SearchResult searchResult = resultsIterator.next();
							numResults++;

							SearchResultSnippet snippet = searchResult.getSnippet();

							
							String title = snippet.getTitle();
							String link = searchResult.getId().getVideoId();
							URL linkURL = new URL ("https://www.youtube.com/watch?v=" + link);
							int year = Integer.parseInt(snippet.getPublishedAt().toString().substring(0, 4));
							int month = Integer.parseInt(snippet.getPublishedAt().toString().substring(5, 7));
							int day = Integer.parseInt(snippet.getPublishedAt().toString().substring(8, 10));
							Date date = new Date(year - 1900, month - 1, day);
							URL url = new URL(snippet.getThumbnails().getDefault().getUrl());
							BufferedImage img = ImageIO.read(url);		                    

							videoTitles[numResults-1] = title;
							videoImages[numResults-1] = img;
							videoDates[numResults-1] = (int)((System.currentTimeMillis() - date.getTime())
									/ (1000 * 60 * 60 * 24));
							videoLinks[numResults-1] = linkURL;
							ratings[numResults-1] = 0;
						}

						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								notifyViews();
							}
						});
					}

				} catch (Exception e) {
					e.printStackTrace();
					throwModelInitializationException();
				}
			}
		}.start();
	}

	public void addView(View view) {
		views.add(view);
	}

	public void notifyViews() {
		for (View view: views) {
			view.update();
		}
		Main.update();
	}

	public boolean isGrid() {
		return grid;
	}

	public void setGrid(boolean grid) {
		this.grid = grid;
		this.notifyViews();
	}

	public int getFilter() {
		return filter;
	}

	public void setFilter(int filter) {
		this.filter = filter;
		this.notifyViews();
	}

	public int getResults() {
		return numResults;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	// helper calls for error reporting and debugging
	private void throwModelInitializationException() {

		throw new RuntimeException("Couldn't initialize the YouTube object. You may want to check your API Key.");

	}

}