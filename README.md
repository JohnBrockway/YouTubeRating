This was tested, and the JAR compiled, using JavaSE 1.8 execution environment.

This is an application which uses the Youtube API v3 to query the video database based on keywords, and displays their to the user in a number of formats including direct links to the video (to access, click on a thumbnail; if your security permissions allow it, the video will open directly in your default browser).

The main concepts worked on were working with a production-scale API, and dynamically altering layouts during runtime.

This is an incomplete version. Loading and saving are not working.

Filtering is working; give any video a star rating, and then select a "minimum star rating" in the top right corner. However, filtering when in the Grid View (if there are fewer items than that width is designed for) will cause the items to be larger to fill in that space, which is not ideal.
To reset back to an unfiltered state, simply press the highest star that's currently selected on the filter selector.

Currently, you can search and get 25 items, viewable in either a grid or list format. The system fetches the data asynchronously to enable smooth responsiveness, and displays to the user when available.

The date is in relative format, approximating publish date in years, months, and days from the current date.

MVC principles were used; there is a single Model class, and JComponent subclasses such as ToolbarView.java and GridView.java acting as Views.
