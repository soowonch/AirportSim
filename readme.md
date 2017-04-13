# Airport Simulation by our team for CSE6730

### Contributor:
Cong Du <br />
Leon Meng <br />
Soowon Chang <br />
Wuchang Li <br />

###requirement:
####from HW2:
* Airport simulation must be a correct implementation of Assignment #1
     * Fix issues if flagged on previous assignment.
* Expansion of airport simulator or approved changes via proposal
* Must implement items approved in your proposal
* Must run experiments using up to a minimuim of 50 Airports on 1 LP.  No distributed.
* Must submit video presention demonstrating features
   * Live presentations where everyone is waiting for your simulation to complete aren't much fun
* Must submit final report using the previous given template

####from HW1:
Component: IEEE
Weight: 0.12        
Description: Does the report follow the IEEE submission standard?  
                 
Component: graph    
Weight: 0.02
Description: Does the report contain a well-designed graph?

Component: simdesign    
Weight: 0.04
Description: How well does the report explain the simulation design?

Component: results/#plane    
Weight: 0.1
Description: Does the report show the required output statistics for a varying number of planes?

Component: max(analysis/.5*futWork)    
Weight: 0.1
Description: Does the report provide a thoughtful analysis of the results? One can compensate a little bit for a poor/no analysis with a good description of what can be done to improve the simulation in the future.

Component: param_src    
Weight: 0.02
Description: Are there sources listed for the parameters used?

Component: no_compile    
Weight: 0.3
Description: Pity points. No further points awarded for code.

Component: airplane    
Weight: 0.06
Description: Add an airplane class. It should have at minimum properties of speed and
maximum passinger capacity. Feel free to add other properties you feel are
important.

Component: airprt_evnt/evnt    
Weight: 0.06
Description: You need to add an Airplane parameter for the Airport events so you know
which plane the event is related to.

Component: 5_arprt_distances/airpostsim    
Weight: 0.06
Description: Pick 5 airports in the world and add them to the simulator. You'll need the
distance between them to calculate flight time for each flight.

Component: pln departure_init/airport    
Weight: 0.06
Description: For each plane departure, select a remote airport and use some sort of
random distribution to calculate the number of passengers on the flight.

Component: output/airport    
Weight: 0.06
Description: For each airport, keep stats on the number of passengers arriving and
departing. Also sum the total amount circling time for each airport. This is
time where an airplane is ready to land but is waiting for an opening

Component: params    
Weight: 0.06
Description: Adjust the simulator to run for an appropriate period and adjust timings
(timeToLand, timeOnGround etc) accordingly

Component: conflict qs
Weight: 0.24
Description: Develop a queuing system to ensure planes are not arriving and taking off at
the same time on a single runway.
---
We tried to be consistent before (rubric) and after (similar averages: ~80%) the grading. The clear majority of students lost points on the runway conflict component, which is also heavily weighed. *If a major test case failed that allowed a plane to depart and another to land at the same timestep, 0.5 was generally awarded (unless there was no significant attempt to resolve the conflicts, in which case 0 was awarded).*





