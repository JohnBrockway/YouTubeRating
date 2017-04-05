This was tested, and the JAR compiled, using JavaSE 1.8 execution environment.

This is an incomplete version. Loading and saving are not working.

Filtering is working; however, filtering when in the Grid View (if there are fewer items than that width is designed for) will cause the items to be larger to fill in that space, which is not ideal.
To reset back to an unfiltered state, simply press the highest star that's currently selected on the filter selector.

Currently, you can search and get 25 items, viewable in either View. The system fetches the data asynchronously, and displays to the user when available.

The date is in the relative format, approximating publish date in years, months, and days.

MVC principles were used; there is a single Model class, and JComponent subclasses such as ToolbarView.java and GridView.java acting as Views.
