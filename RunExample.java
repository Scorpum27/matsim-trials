package org.matsim.run;

/*
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkFactory;

import java.util.HashMap;
import java.util.Map;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.population.PopulationUtils;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup.ActivityParams;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;
// import org.matsim.core.network.MatsimNetworkReader;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.pt.transitSchedule.api.TransitScheduleFactory;
// import own.EventHandlerExamples.MyEventHandler_1;
// import org.matsim.tutorial.class2017.basicprogramming.Rectangle;
import org.matsim.core.population.routes.RouteUtils;
*/


/*
//%%%%%%%%%% Run Main %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
// Config >> Scenario >> Controler
public class RunExample {
	public static void main( String[] args ) {
		Config config = ConfigUtils.loadConfig( args[ 0 ] );			// Load Config File through GUI: Maven dependencies>Run as Java>GUI>Choose config file 
																		// you could modify some config settings here
		// Config config = ConfigUtils.createConfig();					// alternative
		
		Scenario scenario = ScenarioUtils.createScenario( config );		// Load scenario (=population + network) referred to in config file
																		// you could modify some scenario elements here
		Controler controler = new Controler( scenario );    			// you could configure your scenario here
		controler.run();
		}
}
//%%%%%%%%%% Run Main End %%%%%%%%%%
*/
	


/*//%%%%%%%%%% Create Objects & Instances %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	Config: 							Config config = ConfigUtils.loadConfig( args[ 0 ] );
	Scenario:							Scenario scenario = ScenarioUtils.createScenario( config );		
		Controler:						Controler controler = new Controler( scenario );
		Network:						Network network = scenario.getNetwork();
			NetworkFactory:				NetworkFactory networkFactory = network.getFactory();							// add links/nodes: see below
				Node: 					Node node = networkFactory.createNode(Id.createNodeId(0), new Coord(-10, 10));  // add node: network.addNode(node);
				Link: 					Link link = networkFactory.createLink(Id.createLinkId("0_1"), node0, node1); 	// add link: network.addLink(link);
		Population:						Population population = scenario.getNetwork();									// add person: population.addPerson(person);
			PopulationFactory:			PopulationFactory populationFactory = population.getFactory();					
				Person:					Person person = populationFactory.createPerson(Id.create("1", Person.class));   // add plan: person.addPlan(plan);
					Id<Person>:			Id<Person> personID = Id.create("personName", Person.class);					// alternative: createPersonId(String str) 
				Plan:					Plan plan = populationFactory.createPlan();										// add activity/leg;
					Leg:				Leg leg = populationFactory.createLeg("mode");									// add leg: plan.addLeg(leg);
					Activity:			Activity activity = populationFactory.createActivityFromCoord("home", coord);	// add activity: plan.addActivity(activity);
/*
Config config = ConfigUtils.loadConfig("fileInput");
Scenario scenario = ScenarioUtils.createScenario( config );		
Controler controler = new Controler( scenario );
Network network = scenario.getNetwork();
NetworkFactory networkFactory = network.getFactory();							// add links/nodes: see below
Node node = networkFactory.createNode(Id.createNodeId(0), new Coord(-10, 10));  // add node: network.addNode(node);
Link link = networkFactory.createLink(Id.createLinkId("0_1"), node0, node1); 	// add link: network.addLink(link);
Population population = scenario.getNetwork();									// add person: population.addPerson(person);
PopulationFactory populationFactory = population.getFactory();					
Person person = populationFactory.createPerson(Id.create("1", Person.class));   // add plan: person.addPlan(plan);
Id<Person> personID = Id.create("personName", Person.class);					// alternative: createPersonId(String str) 
Plan plan = populationFactory.createPlan();										// add activity/leg;
Leg leg = populationFactory.createLeg("mode");									// add leg: plan.addLeg(leg);
Activity activity = populationFactory.createActivityFromCoord("home", coord);	// add activity: plan.addActivity(activity);
*/
//%%%%%%%%%% Create Objects & Instances %%%%%%%%%%
	

/*//%%%%%%%%%% Public Transport %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	Config: 								Config config = ConfigUtils.loadConfig( args[ 0 ] );
	Scenario:								Scenario scenario = ScenarioUtils.createScenario( config );
		TransitSchedule						// !! TransitSchedule tSchedule = scneario.getTransitSchedule(); (get Factory or add TransitLines)
			TransitScheduleFactory			TransitScheduleFactory transitScheduleFactory = scenario.getTransitSchedule().getFactory();
											createTransitLine; createTransitSchedule; createTransitRoute; createTransitRouteStop; createTransitStopFacility
		TransitLine (Line name)				TransitLine tLine = transitScheduleFactory.createTransitLine(lineId)
											Line.addRoute(TransitRoute);
											TransitLine tLine = scneario.getTransitSchedule().getTransitLines().get(lineID);
			TransitRoute (actual object)	TransitRoute tRoute = transitScheduleFactory.createTransitRoute(routeId, route, stops, mode); (TransitRoute tRoute = tLine.getRoutes().get(routeID);)
				RouteProfile (stops)		make a list of these [TransitStopFacility stop] >> add to TransitRoute during creation as input argument 
				Route (links)				Make a NetworkRoute >> setLinkIds in NetworkRoute >> TransitRoute.setRoute(route)
				Departures (indiv.. vehic.)	addDeparture(Departure departure)


	// Starter
		Config config = ConfigUtils.loadConfig( args[ 0 ] );
		Scenario scenario = ScenarioUtils.createScenario( config );
	// Create TransitSchedule placeholder and a factory
		TransitSchedule tSchedule = scneario.getTransitSchedule();
		TransitScheduleFactory transitScheduleFactory = tSchedule.getFactory();
	// Create link list >> make NetworkRoute from link list
		Network network = scenario.getNetwork();
		NetworkFactory networkFactory = network.getFactory();
		Node node = networkFactory.createNode(Id.createNodeId(0), new Coord(-10, 10));
		Link link = networkFactory.createLink(Id.createLinkId("0_1"), node0, node1);
		RouteFactories = PopulationFactory.getRouteFactories();
		NetworkRoute networkRoute = RouteUtils.createNetworkRoute(List<Id<Link>> routeLinkIds, Network network); // alternatively maybe: Route route = RouteFactories.createRoute(Class<R> routeClass, Id<Link> startLinkId, Id<Link> endLinkId);
	// Create stop list (TransitStopFacilities)
		TransitStopFacility tStopFacility = transitScheduleFactory.createTransitStopFacility(Id<TransitStopFacility> facilityId, Coord coordinate, boolean blocksLane)
		TransitRouteStop tRouteStop = transitScheduleFactory.createTransitRouteStop(TransitStopFacility stop, double arrivalDelay, double departureDelay)
	// Build TransitRoute from stops and NetworkRoute
		TransitRoute tRoute = transitScheduleFactory.createTransitRoute(routeId, networkRoute, stops, mode);
	// Add departures to TransitRoute
		departure = transitScheduleFactory.createDeparture(Id<Departure> departureId, double time);
		tRoute.addDeparture(departure)
	// Make TransitLine and add TransitRoute to line
		transitLine = transitScheduleFactory.createTransitLine(Id<TransitLine> lineId)
	// Make TransitScheduleWriter
		new TransitScheduleWriter(tSchedule).writeFile("input/hapt/newschedule.xml");
*///%%%%%%%%%% End Public Transport %%%%%%%%%%





/*//%%%%%%%%%% Event Handling %%%%%%%%%%%%%%%%%%%%%%%%%%
	EventManager >> EventHandler >> Apply to EventReader >> Read >> Get result bins stored during run in Handlers
	EventsManager eventManager = EventsUtils.createEventsManager();			// event manager
	MyEventHandler_1 handler1 = new MyEventHandler_1();						// event handler objects
	events.addHandler(handler1);											// attach handler to reader
	MatsimEventsReader reader = new MatsimEventsReader(events);				// create reader
	reader.readFile(inputFile);												// read file
		String inputFile = "output/example5/ITERS/it.10/10.events.xml.gz";	// path to events file
	System.out.println("av.T-time: " + handler2.getTotalTravelTime());		// handlers can keep variables
	
	public class MyEventHandler_1 implements LinkEnterEventHandler,
	LinkLeaveEventHandler, PersonArrivalEventHandler,
	PersonDepartureEventHandler{
	@Override
	public void reset(int iteration) {
		System.out.println("reset...");}
	@Override
	public void handleEvent(LinkEnterEvent event) {
		System.out.println("LinkEnterEvent");
		System.out.println("Time: " + event.getTime());
		System.out.println("LinkId: " + event.getLinkId());}}
/* // %%%%%%%%%% Event Handling End %%%%%%%%%%%%%%%%%%%%%%%%%%




/* //%%%%%%%%%% Load Network & Population %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	Network network = scenario.getNetwork();						// controler.run is enough to run simulation. This is for working with network/population directly
		// new MatsimNetworkReader(scenario.getNetwork()).readFile("input/network.xml"); // alternative: read network manually (not from config file)
		Population population = scenario.getPopulation();
*/ //%%%%%%%%%% Load Network & Population %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	
	

/* //%%%%%%%%%% PopulationCreator/Writer (see above how to create plans, activties and assign to people) %%%%%%%%%%%%%%%%
	MatsimWriter popWriter = new org.matsim.api.core.v01.population.PopulationWriter(population, network);
	popWriter.write("./input/population.xml");
/* //%%%%%%%%%% PopulationCreator/Writer %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%




// %%%%%%%%%% Create activity and its properties and add to configuration file for scoring %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	ActivityParams home = new ActivityParams("home"); // 
	home.setTypicalDuration(6.0*60*60);
	config.planCalcScore().addActivityParams(home);
// %%%%%%%%%% Create Activity Properties End %%%%%%%%%%





//%%%%%%%%%% Activity %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	Leg leg1 = PopulationUtils.createLeg("walk"); // Create a leg >> is yet to be added to person's plan!

	Coord coordAct1 = new Coord(1.0, 2.0);
	Activity act1 = PopulationUtils.createActivityFromCoord("tennis", coordAct1); // Create an activity >> is yet to be added to person's plan!
//%%%%%%%%%% Activity End %%%%%%%%%%





/* // %%%%%%%%%% Create Network %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	Network network = scenario.getNetwork();
	NetworkFactory networkFactory = network.getFactory();
	Node node = networkFactory.createNode(Id.createNodeId(0), new Coord(-10, 10)); // add node: network.addNode(node);
	Link link = networkFactory.createLink(Id.createLinkId("0_1"), node0, node1); // add link: network.addLink(link);
*/ // %%%%%%%%%% Create Network End %%%%%%%%%%

	
	
	

/* // %%%%%%%%%% Make Charts %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
public class SaveChart {
	XYLineChart chart = new XYLineChart("Traffic link 6", "hour", "departures");
	chart.addSeries("times", hours, this.volumeLink6);
	chart.saveAsPng(filename, 800, 600);
}*/ //%%%%%%%%%% Make Charts End %%%%%%%%%%

	
	
	

/* // %%%%%%%%%% Create Maps %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	Rectangle rectangle1= new Rectangle(a,b,color1);
	Rectangle rectangle2=new Rectangle (c,d,color2);
	Map<Integer,Rectangle> geometryStuff = new HashMap<>();
	geometryStuff.put(1, rectangle1);
	geometryStuff.put(2, rectangle2);
*/ //%%%%%%%%%% End Create Maps %%%%%%%%%%

	
	
	

/*// %%%%%%%%%% GENERAL & RUN %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

//Current Directory: System.out.println(System.getProperty("user.dir"));

*****************
PROJECT-FOLDER
> Classes: src/main/java/package/ClassName.java
> Input/Scenarios:
		- input/config.xml
		- input/network.xml
		- input/plans.xml
> pom.xml // configures MATSim and version
*****************
RUN-FILE:
> Running this file will ask for Config file in order to start MATSim
> Alternatively MATSim can be started by Maven Dependencies>matsim-0.9.0>Run as Java application> GUI > Choose config file
> set package as in your file structure
> Import the required core classes for scenarios, utils and controlers
> Highest level: RunMATSim with main method
	- "main" method calls "run"
	- "run"
		- requires specific config data file which is given by "ConfigUtils.loadConfig(args[0])"
		- loads scenario=[plans+network]=[scenario takes config input, which points to plans+network]
		- loads controller
*****************
package org.matsim.run;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.gbl.Gbl;
import org.matsim.core.scenario.ScenarioUtils;

public class RunMatsim {

	public static void main(String[] args) {
		Gbl.assertIf(args.length >=1 && args[0]!="" );
		run(ConfigUtils.loadConfig(args[0]));
		// makes some sense to not modify the config here but in the run method to help  with regression testing.}
	
	static void run(Config config) {	
		// possibly modify config here
		
		Scenario scenario = ScenarioUtils.loadScenario(config) ;
		// possibly modify scenario here
		
		Controler controler = new Controler( scenario ) ;
		// possibly modify controler here		
		controler.run();
}}

*****************
CONFIG-FILE:
> Note path to network file
> Note path to plans file
> Change controller and other parameters and options
	- Scoring policies
	- Replanning strategies
	- Output folders
*****************

<module name= " network ">
<param name= " inputNetworkFile " value= "<path -to - network -file >" />
</ module >
<module name= " plans ">
<param name= " inputPlansFile " value= "<path -to -plans - file " />
</ module >
<module name= " controler ">
<param name= " firstIteration " value= "0" />
<param name= " lastIteration " value= "0" />
</ module >
<module name= " planCalcScore " >
< parameterset type= " activityParams " >
12 The Multi-Agent Transport Simulation MATSim
<param name= " activityType " value= "h" />
<param name= " typicalDuration " value= " 12:00:00 " />
</ parameterset >
< parameterset type= " activityParams " >
<param name= " activityType " value= "w" />
<param name= " typicalDuration " value= " 08:00:00 " />
</ parameterset >
</ module >

******************
NETWORK-FILE:
> Contains and configures the nodes and links
******************

<network name= " example network ">
<nodes >
<node id= "1" x=" 0.0" y=" 0.0"/>
<node id= "2" x=" 1000.0 " y="0.0"/>
<node id= "3" x=" 1000.0 " y=" 1000.0 "/>
</ nodes >
<links >
<link id= "1" from= "1" to= "2" length= " 3000.00 " capacity= " 3600 "
freespeed= " 27.78 " permlanes= "2" modes= "car" />
<link id= "2" from= "2" to= "3" length= " 4000.00 " capacity= " 1800 "
freespeed= " 27.78 " permlanes= "1" modes= "car" />
<link id= "3" from= "3" to= "2" length= " 4000.00 " capacity= " 1800 "
freespeed= " 27.78 " permlanes= "1" modes= "car" />
<link id= "4" from= "3" to= "1" length= " 6000.00 " capacity= " 3600 "
freespeed= " 27.78 " permlanes= "2" modes= "car" />
</ links >
</ network >

******************
POPULATION-FILE:
> Contains all agents and their corresponding plans
> Plans minimally include desired activity and location (coordinates)
*****************

<population >
<person id= "1">
<plan selected= "yes" score= " 93.2987721 ">
<act type= " home " link= "1" end_time= " 07:16:23 " />
<leg mode= "car ">
<route type= " links ">1 2 3</ route >
</ leg >
<act type= " work " link= "3" end_time= " 17:38:34 " />
<leg mode= "car ">
<route type= " links ">3 1</ route >
</ leg >
<act type= " home " link= "1" />
</ plan >
</ person >
<person id= "2">
<plan selected= "yes" score= " 144.39002 ">
...
</ plan >
</ person >
</ population >

*/

	
	