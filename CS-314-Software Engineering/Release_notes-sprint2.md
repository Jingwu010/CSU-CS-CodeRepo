DTR-14

Overview:
        TripCo is a trip planning program that creates a short trip 
        from a given list of locations. TripCo takes a .csv file of longitude 
        and latitude coordinates to construct a trip from. 

        .csv Location files should have the first line as a template line 
        with labels for subsequent lines' data. 

Increment additions:
        - If no map SVG is provided, the trip will be written to a blank SVG
        - An input GUI is now available
        - Experimental NN 2-point and 3-point optimizations (2-opt/3-opt) are now available on a trip.
        - The project is now built using Maven
        - A subset of locations in a trip can be chosen via the GUI, and these selections can be loaded from the command line or GUI via XML

Usage:
        If not using the GUI, TripCo should be run from the folder above src/
        Basic usage:
        java TripCo [optional arguments] file.csv [optionalColoradoSVG] [[optionalSelectionXML]] 

Optional arguments:
            -g : use a GUI to select options
            -i : shows the ID of the locations on the map
            -m : Display mileage of legs on map
            -n : shows the names of the locations on the map
            -2 : use 2-opt on the trip
            -3 : use 3-opt on the trip (currently not working)
        -i and -n cannot be used in conjunction. 
        EX: TripCo -mn list.csv

Output:
        All files are output to /src/main/resources 

        (csv root name).xml - an XML itinerary of the trip, 
        including the sequence, start location, end location, 
        and mileage of each leg of the trip
        
        (csv root name).svg - an SVG of the trip, drawn over 
        a map of Colorado. If options are specified, text labels
        for each one will be added to the map

        View.html - A web page of the trip will automatically be 
        opened displaying the SVG and a link to the raw XML data

Purpose:
        The first release introduces many features. The main feature
        of the TripCo program is the ability to read in a list of 
        locations and produce a short trip between all of them. 
        This trip is then displayed (locally) in a web browser. 
        
Issues:
        - Because of the way Google Chrome handles local files, you must set your default browser to one other than Chrome. Firefox, as well as any non-Chromium browser should work
        - 3-opt does not produce the correct results
        - 2-opt is only run on the best nn tour, not all tours
        - Specifying a filenames with the -g argument does not prepopulate the GUI file selections
        - GUI may allow illegal files
        - The current release only supports the web presentation locally. Future releases will allow client-server interaction.
        - The SVG and web page are fairly basically formatted
        - The unit tests are not as robust as they could be, and there is no master test runner

Notes:
        - 2-opt and 3-opt understanding:
            http://stackoverflow.com/questions/21205261/3-opt-local-search-for-tsp
            http://www.technical-recipes.com/2012/applying-c-implementations-of-2-opt-to-travelling-salesman-problems/
            http://stackoverflow.com/questions/21205261/3-opt-local-search-for-tsp
        - Assistance for great circle distance from:
            http://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
        - Understanding of nearest neighbor:
            https://en.wikipedia.org/wiki/Nearest_neighbor_search
        - ReactJS and Grommet were used to build the web page:
            https://facebook.github.io/react/
            https://grommet.github.io/
        - JavaFX used to build GUI
