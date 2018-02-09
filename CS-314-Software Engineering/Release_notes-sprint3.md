DTR-14

Overview:
        TripCo is a trip planning program that creates a short trip 
        from a database of airports

Optional arguments:
            -i : shows the ID of the locations on the map
            -d : Display distances of legs on map
            -m/k : Use miles or kilometers
            -n : shows the names of the locations on the map
        -i and -n cannot be used in conjunction. 
        EX: TripCo -mn list.csv

Output:
        an XML itinerary of the trip, 
        including the sequence, start location, end location, 
        and mileage of each leg of the trip
        
        an SVG of the trip, drawn over 
        a map of Colorado. If options are specified, text labels
        for each one will be added to the map

        A web page of the trip will automatically be 
        opened displaying the SVG and a link to the raw XML data

Purpose:
        This release adds database connectivity and moves from Colorado-only
        trips to trips across the world. 
        
Issues:
        - 3-opt is not implemented
        
        - Naming output files is not fully supported
        
        - The current release only supports the web presentation
        locally. Future releases will allow client-server interaction.

        - The unit tests are not as robust as they could be, and
        there is no master test runner

Notes:
        - Assistance for great circle distance from:
            http://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
        - Understanding of nearest neighbor:
            https://en.wikipedia.org/wiki/Nearest_neighbor_search
        - ReactJS and Grommet were used to build the web page:
            https://facebook.github.io/react/
            https://grommet.github.io/
        - 2-opt and 3-opt understanding:
            http://stackoverflow.com/questions/21205261/3-opt-local-search-for-tsp
            http://www.technical-recipes.com/2012/applying-c-implementations-of-2-opt-to-travelling-salesman-problems/
``      - JavaFX used to build input GUI
