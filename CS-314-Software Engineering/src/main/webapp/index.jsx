var Anchor = Grommet.Anchor;
var Accordion = Grommet.Accordion;
var AccordionPanel = Grommet.AccordionPanel;
var App = Grommet.App;
var Box = Grommet.Box;
var Button = Grommet.Button;
var CheckBox = Grommet.CheckBox;
var Heading = Grommet.Heading;
var Headline = Grommet.Headline;
var Label = Grommet.Label;
var List = Grommet.List;
var ListItem = Grommet.ListItem;
var Menu = Grommet.Menu;
var Paragraph = Grommet.Paragraph;
var Select = Grommet.Select;
var Search = Grommet.Search;
var Split = Grommet.Split;
var Tab = Grommet.Tab;
var Table = Grommet.Table;
var TableRow = Grommet.TableRow;
var Tabs = Grommet.Tabs;
var Toast = Grommet.Toast;
var Topology = Grommet.Topology;
var AddIcon = Grommet.Icons.Base.Add;
var CloseIcon = Grommet.Icons.Base.Close;
var DocumentUploadIcon = Grommet.Icons.Base.DocumentUpload;
var DocumentDownloadIcon = Grommet.Icons.Base.DocumentDownload;
var GlobeIcon = Grommet.Icons.Base.Globe;
var NextIcon = Grommet.Icons.Base.LinkNext;
var PreviosIcon = Grommet.Icons.Base.LinkPrevious;
var RefreshIcon = Grommet.Icons.Base.Refresh;
var SearchIcon = Grommet.Icons.Base.SearchAdvanced;
var title = "";

// airport object
function Airport(id, idt, name, country, continent, type) {
  this.id = id;
  this.idt = idt;
  this.name = name;
  this.country = country;
  this.continent = continent;
  this.type = type;
}

// list of searchable airports
var airports = [];
airports.push(new Airport(1, "HYDS","Hartsfield Jackson Atlanta International", "United States", "America", "large_airports"));
airports.push(new Airport(2, "SDASD", "Beijing Capital International", "China", "Asia", "large_airports"));
airports.push(new Airport(3, "DSAD","Dubai International", "United Arab Emirates", "Unknown", "closed"));

var selectedAirports = [];
selectedAirports.push(new Airport(1, "HYDS","Hartsfield Jackson Atlanta International", "United States", "America", "large_airports"));
selectedAirports.push(new Airport(2, "SDASD", "Beijing Capital International", "China", "Asia", "large_airports"));
selectedAirports.push(new Airport(3, "DSAD","Dubai International", "United Arab Emirates", "Unknown", "closed"));
/**
 * 
 * Main Class to be Render
 * @class TripCo
 * @extends {React.Component}
 */
class TripCo extends React.Component {
  constructor(props) {
    super(props);

    var loc = window.location, new_uri;
    if (loc.protocol === "https:") {
      new_uri = "wss:";
    } else {
      new_uri = "ws:";
    }
    new_uri += "//" + document.location.host + document.location.pathname;

    this.state = {
      error: null,
      title: null,
      selected_data: [],
      back_data: [],
      front_data: airports,
      word: "hello",
      countries: [],
      types: [],
      continents : [],
      itinerary : [],
      image : null,
      webSocket: new WebSocket(new_uri + "websocket")
    };

    this.initiateWebPage = this.initiateWebPage.bind(this);
    this.updateCountry = this.updateCountry.bind(this);
    this.searchQuery = this.searchQuery.bind(this);
    this.updateFrontData = this.updateFrontData.bind(this);
    this.updateBackData = this.updateBackData.bind(this);
    this.planTrip = this.planTrip.bind(this);
    this.addToSet = this.addToSet.bind(this);
    this.removeFromSet = this.removeFromSet.bind(this);
    this.uploadFile = this.uploadFile.bind(this);
    this.clearAll = this.clearAll.bind(this);
    this.updateSelectedData = this.updateSelectedData.bind(this);
    this.addFrontToSelected = this.addFrontToSelected.bind(this);
    this.setItinerary = this.setItinerary.bind(this);
    this.downloadFile = this.downloadFile.bind(this);
    this.setHint = this.setHint.bind(this);
    this.setImage = this.setImage.bind(this);
  }

  componentDidMount() {
    this.state.webSocket.onopen = e => {this.socketOpen(e);}
    this.state.webSocket.onmessage = e => {this.messageHandler(e);};
    this.state.webSocket.onerror = e => this.setState({ error: "WebSocket error" });
    this.state.webSocket.onclose = e => !e.wasClean && this.setState({ error: `WebSocket error: ${e.code} ${e.reason}` });
  }

  socketOpen(event){
    console.log("[TripCo] Socket Open Successfully");
    var obj = new Object();
    obj.Key = "Init";
    var jsonString = JSON.stringify(obj);
    this.state.webSocket.send(jsonString);
  }

  // Default Function Extends onmessage();
  messageHandler(event) {
    var jsonMessage = JSON.parse(event.data);
    switch (jsonMessage.Key){
      case "Init":
        console.log("[TripCo] Init Reply");
        this.initiateWebPage(jsonMessage);
        break;
      case "Contient":
        this.updateCountry(jsonMessage);
        break;
      case "Country":
        break;
      case "Search":
        console.log("[TripCo] Search Reply");
        this.updateBackData(jsonMessage);
        break;
      case "ReadXML":
        console.log("[TripCo] ReadXML Reply");
        this.updateBackData(jsonMessage);
        this.updateSelectedData(this.state.back_data);
        break;
      case "DownloadXML":
        this.download(jsonMessage);
        break;
      case "PlanTrip":
        this.setItinerary(jsonMessage);
        this.setImage(jsonMessage);
        break;
    }
  }

  setImage(jsonMessage) {
      var path = jsonMessage.Image;
      console.log(path);
      this.setState({ image: <img src={path} width="100%"></img>});
  }

  // Default Function Extends onclose();
  componentWillUnmount() {
    console.log("[TripCo] webSocket Closed");
    this.state.webSocket.close();
  }

  initiateWebPage(jsonMessage){
    console.log("[TripCo] initiateWebPage");
    var mytypes = jsonMessage.Type.split(",");
    var mycontinents = jsonMessage.Continent.split(",");
    var myTypes = [];
    var myContinents = [];
    var i;
    for(i = 0; i < mytypes.length; i++) myTypes.push(mytypes[i]);
    for(i = 0; i < mycontinents.length; i++) myContinents.push(mycontinents[i]);
    this.setState({ types: myTypes.filter((x, i, a) => a.indexOf(x) == i)});
    this.setState({ continents: myContinents.filter((x, i, a) => a.indexOf(x) == i)});
  }

  updateCountry(jsonMessage){
    var mycountries = jsonMessage.Country.split(",");
    var myCountries = [];
    for(var i = 0; i < mycountries.length; i++) myCountries.push(mycountries[i]);
    this.setState({ countries: myCountries.filter((x, i, a) => a.indexOf(x) == i) });
  }

  searchQuery(query){
    var obj = new Object();
    obj.Key = "Search";
    obj.Value = query;
    var jsonString = JSON.stringify(obj);
    console.log("[TripCo] Search Query" + query);
    this.state.webSocket.send(jsonString);
  }

  addFrontToSelected(){
    this.updateSelectedData(this.state.front_data);
    var cities = this.state.front_data.length;
    this.setHint(cities + " Cities Added Successfully!");
  }
  
  setHint(hints){
    const element = (
      <Toast status='ok'>{hints}</Toast>
    );
    ReactDOM.render(
      element,
      document.getElementById('hint')
    );
  }

  updateSelectedData(newData){
    var selectedResults = [...new Set([...newData, ...this.state.selected_data])];
    this.setState({ selected_data: selectedResults});
  }

  updateBackData(jsonMessage){
    var airport_idts = jsonMessage.Identifier.split(",");
    var airport_Names = jsonMessage.Name.split(",");
    var airport_Countries = jsonMessage.Country.split(",");
    var airport_Continents = jsonMessage.Continent.split(",");
    var airport_Types = jsonMessage.Type.split(",");
    var newAirports = [];
    for(var i = 0; i < airport_Names.length; i++){
      newAirports.push(new Airport(i, airport_idts[i], airport_Names[i], airport_Countries[i], airport_Continents[i], airport_Types[i]));
    }
    this.setState({ back_data: newAirports });
    this.setState({ front_data: newAirports });
    this.initiateWebPage(jsonMessage);
    this.updateCountry(jsonMessage);
  }
  
  setItinerary(jsonMessage) {
      console.log(jsonMessage);
      var itin = jsonMessage.Array;
      console.log(itin);
      for (var i = 0; i < itin.length; i++) {
          console.log(itin[i]);
      }
      this.setState({ itinerary: itin });
      const element = (
        <Toast status='ok'>Plan Trip Successfully</Toast>
      );
      ReactDOM.render(
        element,
        document.getElementById('hint2')
      );
  }

  // Check the airport names and countries to see if there are matches
  // Push them to the searchResults
  updateFrontData(query) {
    if (query == "refresh") {
      this.setState({ front_data: this.state.back_data });
    } else {
      var searchResults = this.state.back_data.filter(function(obj) {
        if (
          obj.name.toUpperCase().includes(query.toUpperCase()) ||
          obj.country.toUpperCase().includes(query.toUpperCase()) ||
          obj.continent.toUpperCase().includes(query.toUpperCase()) ||
          obj.type.toUpperCase().includes(query.toUpperCase())
        )
          return obj;
      });
      this.setState({ front_data: searchResults });
    }
    console.log("[App] Table refreshes value " + query);
  }

  clearAll(){
    this.setState({ selected_data: []});
  }

  planTrip(tripTitle){
    console.log("Entered plan trip");
    title = tripTitle;
    console.log(title);

    var obj = new Object();
    obj.Key = "PlanTrip";
    var idts = "";
    for(var i = 0; i < this.state.selected_data.length; i++){
      if(i != 0) idts += ",";
      idts += this.state.selected_data[i].idt;
    }
    obj.Identifier = idts;
    obj.Title = title;
    var jsonString = JSON.stringify(obj);
    console.log(jsonString);
    console.log("[TripCo] Plan Trip" + idts);
    this.state.webSocket.send(jsonString);
  }

  addToSet(query){
    console.log("[TripCo] selected_data query " + query);
    console.log("[TripCo] selected_data query " + this.state.selected_data.length);
    var selectedResults = this.state.back_data.filter(function(obj) {
      if (obj.idt.includes(query)) return obj;});
    selectedResults = [...new Set([...selectedResults ,...this.state.selected_data])];
    this.setState({ selected_data: selectedResults});
  }

  removeFromSet(query){
    var selectedResults = this.state.back_data.filter(function(obj) {
      if (obj.idt.includes(query))return obj;});
    this.setState({ selected_data: this.state.selected_data.filter(function(item) {
        return selectedResults.indexOf(item) === -1;
      })
    });
  }

  uploadFile(inputFile){
    var file = inputFile.files[0];
    var reader = new FileReader();
    var rawData = new ArrayBuffer();            
    var webSocket = this.state.webSocket;
    reader.onload = function(e) {
        rawData = e.target.result;
        var obj = new Object();
        obj.Key = "ReadXML";
        obj.FileName  = file.name;
        obj.Value = rawData;
        console.log("[TripCo] uploadFile " + file.name);
        var jsonString= JSON.stringify(obj);
        webSocket.send(jsonString);
        webSocket.send(rawData);
        console.log("[TripCo] uploadFile Finished");
    };
    reader.readAsArrayBuffer(file);
    inputFile.value = "";
  }
  
  downloadFile() {
    var obj = new Object();
    obj.Key = "DownloadXML";
    obj.Title = title;
    var idts = "";
    for(var i = 0; i < this.state.selected_data.length; i++){
      if(i !== 0) idts += ",";
      idts += this.state.selected_data[i].idt;
    }
    obj.data = idts;
    var jsonString = JSON.stringify(obj);
    console.log("[TripCo] Download XML" + idts);
    this.state.webSocket.send(jsonString); 
  }
  
  download(jsonMessage){
    var blah = window.location.pathname + jsonMessage.Path;
    console.log(blah);
    var link = document.createElement("a");
    link.download = jsonMessage.Path;
    link.href = blah;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }
  render() {
    return (
      <Box id="screen" pad="small">
        <div id="hint"/>
        <div id="hint2"/>
        <Headline align="center" size="medium">TripCo Online</Headline>
        <Tabs>
          <Tab title="Plan">
            <Box id="PlanBox" full="false">
              <App>
                <Box id="Search" margin="small" full="true" pad="small">
                  <MySearch
                    myDataProp={this.state.word}
                    updateStateProp={this.updateFrontData}
                    searchQuery={this.searchQuery}
                  />
                </Box>
                <MySelectionTable
                  data={this.state.front_data}
                  countries={this.state.countries}
                  continents={this.state.continents}
                  types={this.state.types}
                  updateStateProp={this.updateFrontData}
                  removeFromSet={this.removeFromSet}
                  addToSet={this.addToSet}
                  addAll={this.addFrontToSelected}
                />
              </App>
            </Box>
          </Tab>

          <Tab title="Selections">
            <App>
              <MySelectedTable 
                clearAll={this.clearAll}
                uploadFile={this.uploadFile}
                data={this.state.selected_data}
                downloadFile={this.downloadFile}
                planTrip={this.planTrip}/>
            </App>
          </Tab>

          <Tab title="Itinerary">
            <App>
                <MyItineraryTable 
                  data={this.state.itinerary}/>
            </App>
          </Tab>

          <Tab title="TravelMap">
            <App>
                {this.state.image}
            </App>
          </Tab>
        </Tabs>

      </Box>
    );
  }
}

class MyItineraryTable extends React.Component {
    constructor(props) {
        super(props);
    }
    
    render() {
        return (
                <Box>
                <Table>
                <thead>
                <tr>
                <th width="5%">#</th>
                <th width="5%">ID</th>
                <th width="40%">Name</th>
                <th width="40%">Location</th>
                <th width="15%">Coordinates</th>
                <th width="5%">Mileage</th>
                </tr>
                </thead>
                <tbody>
                {this.props.data.map((one, i) => 
                <ItineraryRow key={i} data={one}/>)}
                </tbody>
                </Table>
                </Box>
        )
    }
}
 
class ItineraryRow extends React.Component { 
  constructor(props) {
    super(props);
    var tempLat = parseFloat(this.props.data.latitude).toFixed(2);
    var tempLong = parseFloat(this.props.data.longitude).toFixed(2);
    console.log(typeof tempLat);
    var north;
    var east;
    if (tempLong > 0) {
        east = "E";
    } else {
        east = "W";
    }
    if (tempLat > 0) {
        north = "N";
    } else {
        north = "S";
    }
    var finalCoordinates = tempLat + "° " + north + ", " + tempLong + "° " + east;
    console.log(typeof finalCoordinates);
    this.state = {
        coordinates: finalCoordinates
    };
  }
 
  render() {
    return (
      <TableRow>
        <td>{this.props.data.num}</td>
        <td>{this.props.data.id}</td>
        <td><Anchor href={this.props.data.airportURL}>{this.props.data.name}</Anchor></td>
        <td>{this.props.data.municipality}, <Anchor href={this.props.data.regionURL}>{this.props.data.region}</Anchor>, <Anchor href={this.props.data.countryURL}>{this.props.data.country}</Anchor>, {this.props.data.continent}</td>
        <td>{this.state.coordinates}</td>
        <td>{this.props.data.mileage}</td>
      </TableRow>
    );
  }
} 

/**
 * 
 * 
 * @class MySelectionTable
 * @extends {React.Component}
 * @extends {TripCo.Class}
 */
class MySelectionTable extends React.Component {
  constructor(props) {
    super(props);

    this.handleChange = this.handleChange.bind(this);
    this.refreshItems = this.refreshItems.bind(this);
    this.viewTrip = this.viewTrip.bind(this);
  }

  handleChange(value) {
    console.log("[MySelectionTable] get selected value:" + value);
    this.props.updateStateProp(value);
  }

  refreshItems() {
    this.props.updateStateProp("refresh");
  }

  viewTrip(){
    
  }

  render() {
    return (
      <Box id="TripPreview" align="center" full="true">
      <Box direction="row" justify="center">
        <Button icon={<RefreshIcon />} label='Refresh Table' plain={true} onClick={this.refreshItems}/>
        <Button icon={<AddIcon />} label='Add All' plain={true} onClick={this.props.addAll}/>
        <Button icon={<NextIcon />} label='View My Trip' plain={true} onClick={this.viewTrip}/>
      </Box>
        <Table>
          <thead>
            <tr>
              <th width="10%">id</th>
              <th width="35%">
                Airport
              </th>
              <th width="20%">
                <Menu responsive={true} label="Country" size="small" closeOnClick={false}>
                  {this.props.countries.map((one, i) => (
                    <Myset key={i} data={one} filter={this.handleChange} />
                  ))}
                </Menu></th>
              <th width="20%">
                <Menu responsive={true} label="Continent" size="small" closeOnClick={false}>
                  {this.props.continents.map((one, i) => (
                    <Myset key={i} data={one} filter={this.handleChange} />
                  ))}
                </Menu></th>
              <th width="20%"> <Menu responsive={true} label="Type" size="small" closeOnClick={false}>
                  {this.props.types.map((one, i) => (
                    <Myset key={i} data={one} filter={this.handleChange} />
                  ))}
                </Menu></th>
            </tr>
          </thead>
          <tbody>
            {this.props.data.map((one, i) => 
              <MyTableRow key={i} data={one} 
                addToSet={this.props.addToSet}
                removeFromSet={this.props.removeFromSet}/>)}
          </tbody>
        </Table>
      </Box>
    );
  }
}

/**
 * 
 * 
 * @class Myset
 * @extends {React.Component}
 * @extends {MySelectionTable.Class}
 */
class Myset extends React.Component {
  constructor(props) {
    super(props);
  }

  clicked(item, event) {
    event.preventDefault();
    this.props.filter(item);
  }

  render() {
    return (
      <Button ref="mySet"
        label={this.props.data}
        onClick={this.clicked.bind(this, this.props.data)}
        plain={true}
      />
    );
  }
}

/**
 * 
 * 
 * @class MySelectedTable
 * @extends {React.Component}
 * @extends {TripCo.Class}
 */
class MySelectedTable extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      defaultValue : "Name Trip To Start"
    }
    this.uploadFile = this.uploadFile.bind(this);
    this.addTitle = this.addTitle.bind(this);
    this.ClearPlaceHolder = this.ClearPlaceHolder.bind(this);
    this.SetPlaceHolder = this.SetPlaceHolder.bind(this);
  }

  uploadFile(){
    if(document.getElementById("selectedFile").value === "")
      document.getElementById('selectedFile').click();
    else
      this.props.uploadFile(document.getElementById("selectedFile"));
  }

  addTitle(){ 
    this.props.planTrip(this.refs.titleSet.value);
  }

  ClearPlaceHolder(){
    if(this.refs.titleSet.value == this.state.defaultValue){
      this.refs.titleSet.value = "";
    }
  }

  SetPlaceHolder(){
    if (this.refs.titleSet.value == "") {
      this.refs.titleSet.value = this.state.defaultValue;
    }
  }

  render() {
    return (
      <App>
        <Box id="TripPreview" align="center" full="true" pad="small">
          <Box full='horizontal' colorIndex='light-2' pad='small' justify='center' margin='small'> 
            View Your Trip -- {this.props.data.length} Airports
            <Box full="horizontal" direction="row">
                <Box heading='Input Trip Title' full='horizontal' colorIndex='light-1' margin='small'> 
                     <input onChange={this.checkInput} id="titleSet" ref="titleSet" type="text"
                     defaultValue={this.state.defaultValue} onFocus={this.ClearPlaceHolder} onBlur={this.SetPlaceHolder}/>
                </Box>
               <Button icon={<GlobeIcon />} label="Plan Trip" onClick={this.addTitle} plain={true}/>
            </Box>
          </Box>
          <Box direction="row" justify="center" margin='small'>
            <Button icon={<PreviosIcon />} label="Plan" onClick={this.uploadFile} plain={true}/>
            <input type="file" id="selectedFile" style={{display:'none'}} onChange={this.uploadFile} />
            <Button icon={<DocumentUploadIcon />} label="Upload" onClick={this.uploadFile} plain={true}/>
            <Button icon={<DocumentDownloadIcon />} label="Download" onClick={this.props.downloadFile} plain={true}/>
            <Button icon={<CloseIcon />} label="ClearAll" onClick={this.props.clearAll} plain={true}/>
          </Box>
          <Table >
            <thead>
              <tr>
                <th width="10%"> Identifier</th>
                <th width="35%"> Airport </th>
                <th width="20%"> Country</th>
                <th width="20%"> Continent</th>
                <th width="20%"> Type</th>
              </tr>
            </thead>
            <tbody>
              {this.props.data.map((one, i) => (
                <MySelectedRow key={i} data={one} />
              ))}
            </tbody>
          </Table>
        </Box>
      </App>
    );
  }
}

class MySelectedRow extends React.Component {

  render() {
    return (
      <TableRow>
        <td>{this.props.data.idt}</td>
        <td>{this.props.data.name}</td>
        <td>{this.props.data.country}</td>
        <td>{this.props.data.continent}</td>
        <td>{this.props.data.type}</td>
      </TableRow>
    );
  }
}

// Add an table row for each entry
class MyTableRow extends React.Component {
  
  constructor(props) {
    super(props);
  }

  changed(item, event) {
    console.log("[MyTableRow]" + item);
    if(event.target.checked)
      this.props.addToSet(item);
    else
      this.props.removeFromSet(item);
  }

  render() {
    return (
      <TableRow>
        <td><CheckBox 
          onChange={this.changed.bind(this, this.props.data.idt)}/> 
          {this.props.data.id}</td>
        <td>{this.props.data.name}</td>
        <td>{this.props.data.country}</td>
        <td>{this.props.data.continent}</td>
        <td>{this.props.data.type}</td>
      </TableRow>
    );
  }
}

/**
 * 
 * 
 * @class MySearch
 * @extends {React.Component}
 * @extends {TripCo.Class}
 */
class MySearch extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      defaultValue : "Enter Airport Name or City"
    }
    this.handleSubmit = this.handleSubmit.bind(this);
    this.checkInput = this.checkInput.bind(this);
    this.searchQuery = this.searchQuery.bind(this);
    this.ClearPlaceHolder = this.ClearPlaceHolder.bind(this);
    this.SetPlaceHolder = this.SetPlaceHolder.bind(this);
  }

  // if enter is pressed on the keyboard while typing:
  checkInput(event) {
    this.handleSubmit();
  }

  // when search is pressed or enter key is typed
  handleSubmit() {
    var query = this.refs.searchBox.value;
    console.log("[Main] search box get value : " + query);
    this.props.updateStateProp(query);
  }

  searchQuery(event){
    this.props.searchQuery(this.refs.searchBox.value);
    this.refs.searchBox.value = "";
  }

  ClearPlaceHolder(){
    if(this.refs.searchBox.value == this.state.defaultValue){
      this.refs.searchBox.value = "";
    }
  }

  SetPlaceHolder(){
    if (this.refs.searchBox.value == "") {
      this.refs.searchBox.value = this.state.defaultValue;
    }
  }

  render() {
    return (
      <Box full='horizontal' colorIndex='light-2' pad='small' justify='end'> 
      Trip Planning Make Life Easy
      <Box full='horizontal' direction='row'> 
        <Box full='horizontal' colorIndex='light-1' margin='small'> 
        <input onChange={this.checkInput} onKeyPress={this.checkEnter} onFocus={this.ClearPlaceHolder} onBlur={this.SetPlaceHolder}
          defaultValue={this.state.defaultValue} id="searchBox" ref="searchBox" type="text"/>
        </Box>
        <Button icon={<SearchIcon />} label="Search" onClick={this.searchQuery} plain={true}/>
      </Box>
      </Box>
    );
  }
}

var element = document.getElementById("content");
ReactDOM.render(React.createElement(TripCo), element);