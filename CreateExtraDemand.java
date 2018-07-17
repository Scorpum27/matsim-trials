package org.matsim.run;

import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Population;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.api.core.v01.population.PopulationWriter;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.gbl.Gbl;
import org.matsim.core.population.routes.NetworkRoute;
import org.matsim.core.population.routes.RouteUtils;
import org.matsim.core.scenario.ScenarioUtils;

public class CreateExtraDemand {

	int newPeople;
	Network network;
	Population population;
	
	public CreateExtraDemand(Network networkInput, Population population, int newPeople) {
		this.network = networkInput; 		// load network: load network to get nodes and links for potential workplaces
		this.newPeople = newPeople;
		this.population = population;
	}
	
	public Link randomLinkSelector(Network network) {
		int nLinks = network.getLinks().size();
		System.out.println("Amount of links: "+nLinks);				
		Random r = new Random();
		int l = r.nextInt(nLinks)+1; // don't have a node 0 and need to access also node 100, not only node 99
		System.out.println("Getting link number: "+l);
		Id<Link> randomLinkID = Id.createLinkId(l);
		System.out.println("Getting link ID: "+Id.createLinkId(l));		
		Link randomLink = network.getLinks().get(randomLinkID);
		System.out.println("Selected random node is: "+ randomLink);
		return randomLink;
	}
	
	public Node randomNodeSelector(Network network) {
		int nNodes = network.getNodes().size();
		System.out.println("Amount of nodes: "+nNodes);				
		Random r = new Random();
		int n = r.nextInt(nNodes)+1; // don't have a node 0 and need to access also node 100, not only node 99
		System.out.println("Getting node number: "+n);
		Id<Node> randomNodeID = Id.createNodeId(n);
		System.out.println("Getting node ID: "+Id.createNodeId(n));		
		Node randomNode = network.getNodes().get(randomNodeID);
		System.out.println("Selected random node is: "+ randomNode);
		return randomNode;
	}
	
	public Link outLinkSelector(Node preNode) {
		int nOutlinks = preNode.getOutLinks().size();
		Set<Id<Link>> outlinkIDs = preNode.getOutLinks().keySet();
		Random r = new Random();
		int nOut = r.nextInt(nOutlinks);
		int n = 0;
		for(Id<Link> outlinkID : outlinkIDs) {
			if (n==nOut) {
				System.out.println("Selected random link ID is: " + outlinkID.toString());
				return preNode.getOutLinks().get(outlinkID);
			}
			n++;
		}
		System.out.println("%%%%%%%%%%%%%%% Returning Null %%%%%%%%%%%%%%%%");
		return null;
	}
	
	public Node outLinkToNode(Link outLink) {
		return outLink.getToNode();
	}
	
	
	public void run() {
		PopulationFactory populationFactory = population.getFactory();
		System.out.println(populationFactory.toString());
		for (int p=1; p<this.newPeople+1; p++) {
			int intPersonID = 100+p;
			System.out.println("PersonID: "+intPersonID);
			Person person = populationFactory.createPerson(Id.createPersonId(intPersonID));
			Plan plan = populationFactory.createPlan();
			
			// make random work and home coordinates
			Link homeLink = randomLinkSelector(network);
			Node homeNode = outLinkToNode(homeLink);
			Coord homeCoord = homeNode.getCoord();
			Link workLink = outLinkSelector(homeNode);
			Node workNode = outLinkToNode(workLink);
			Coord workCoord = workNode.getCoord();
			
			//make home activity with end time and add to plan
			
			Activity home = populationFactory.createActivityFromCoord("h", homeCoord);
			home.setLinkId(homeLink.getId());
			Random r = new Random();
			home.setEndTime(6.0*60*60+r.nextInt(2*60*60));
			plan.addActivity(home);
			
			// make work leg by car and add to plan
			Leg toWorkLeg = populationFactory.createLeg("toWork");
			toWorkLeg.setMode("car");
			toWorkLeg.setTravelTime(5*60 + new Random().nextInt(15*60));
			LinkedList<Id<Link>> routeLinkIds = new LinkedList<Id<Link>>();
			routeLinkIds.add(homeLink.getId());
			routeLinkIds.add(workLink.getId());			
			NetworkRoute networkRoute = RouteUtils.createNetworkRoute(routeLinkIds, network);
			toWorkLeg.setRoute(networkRoute); // TODO
			plan.addLeg(toWorkLeg);
			
			// make work activity and add to plan
			Activity work = populationFactory.createActivityFromCoord("w", workCoord);
			work.setLinkId(workLink.getId());
			work.setEndTime(home.getEndTime() + toWorkLeg.getTravelTime() + 4*60*60);
			plan.addActivity(work);
			
			person.addPlan(plan);
			population.addPerson(person);							// Add persons to existing population
		}
		
		PopulationWriter pw = new PopulationWriter(population);		// Write new population to new file >> change config after that to new network name!
		pw.write("scenarios/equil/plans200.xml");
	}
	
	

	public static void main(String[] args) {
		
		Config config = ConfigUtils.loadConfig( "scenarios/equil/config.xml" ) ;
		Scenario scenario = ScenarioUtils.loadScenario(config);
		Network network = scenario.getNetwork();
		Population population = scenario.getPopulation();
		int nNewPeople = 100;
		System.out.println(population.getPersons().toString());
		System.out.println("initiation Complete %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		CreateExtraDemand createNewPeople = new CreateExtraDemand(network, population, nNewPeople);
		createNewPeople.run();		
	}
}
