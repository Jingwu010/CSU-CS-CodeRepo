DTR-14

Overview:
        TripCo is a trip planning program that creates a short trip 
        from a database of airports. The interface is all on the web
        with four tabs in it. A Search tab to find airports and add 
        them to the selection; A selection tab to refine selection
        and upload/download a selection XML; A rich Itinerary tab 
        to display the itineray that also has downloadable KMl link;
        A Map tab that shows the full trip over a SVG of the world.

Input:
        The Search tab and Selection tab serves as the input. In the
        Search tab, users can search from a wide variety of airports
        and add them to their selection. In the Selection tab a user
        can upload a Selection XML, refine the selection list, or 
        download their current selection as an XML.

Output:
        An XML of the selection, downloadable from the Selection tab
        
        An SVG of the trip, drawn over a map of the world, displayed in the map tab 


Purpose:
        This release puts the whole interface on the web, refines 3opt
        
Issues:


        - Selection between units couldn't be added in time

	- Couldn't add the KML in time

        - Web Interface could have more color in it

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
``      
