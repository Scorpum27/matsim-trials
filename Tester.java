package org.matsim.run;

import java.util.Random;

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
import org.matsim.core.scenario.ScenarioUtils;

public class Tester {

/* ---------- Test Current Directory --------------------
public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(System.getProperty("user.dir"));
	}
*/

// ---------- START: configLoader & populationWriter --------------------
	
public static void main(String[] args) {
	String ConfigLocation = System.getProperty("user.dir")+"/scenarios/equil/config.xml" ;
	Config config = ConfigUtils.loadConfig(ConfigLocation);
	Scenario scenario = ScenarioUtils.createScenario(config);
	Network network = scenario.getNetwork();
	Population population = scenario.getPopulation();
	int nNewPeople = 100;
	}

//---------- END: configLoader & populationWriter --------------------

}
