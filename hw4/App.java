package hw4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class App {

    public static void main(String[] args) {
        final String roadFilename = args[0];
        final String imageFilename = args[1];
        final String reportFilename = args[2];
        final int numCellTower = Integer.parseInt(args[3]);
        final double sigma = Double.parseDouble(args[4]);
      

        final boolean isOSM = true;
      
        RoadNetwork roadNetwork;
		try {
			File file = new File(roadFilename);
			roadNetwork = new RoadNetwork(file, isOSM);
			roadNetwork.draw();
	        
	        for(Road road: roadNetwork.roads){
	        	road.setPointAmount();
	        	//System.out.println(road.id + ": " + road.getTotalPointAmount());
	        }
	        
//	        System.out.println(roadNetwork.getPointAmount());
	        
	        Road mostPoints= roadNetwork.hasMostPoints();  //road with the most post points in the road network
//	        System.out.println(mostPoints.getTotalPointAmount());
//	        System.out.println(mostPoints.name);
	        
	        long startTime = System.currentTimeMillis();
	        
//	        int numcellTow = 3;
//	        double sigma = 0.2;
	        
	        ArrayList<CellNetwork> cellNetworkConfigurations =
	                mostPoints.getAllCellConfigurations(numCellTower, sigma);
	        
	        
	        
	        for(CellNetwork networks: cellNetworkConfigurations)
	        	networks.setAmountOfCoveredPoints(mostPoints);
	        
	        
	        
	        
	        CellNetwork bestCellConfiguration = null;
	        
	        int bestScore = 0;
	     
	      
	        for (CellNetwork cn : cellNetworkConfigurations) {
	            // do test on cn to see if cn's score is better than bestScore
	            // if it is update bestCellConfiguration = cn
	        	//System.out.println(cn.towers);
	        	if(cn.pointsCovered() > bestScore){
	        		bestScore = cn.coveredPoints;
	        		bestCellConfiguration = cn;
	 
	        	}
	        		
	        }
	        
	        System.out.println("Unthreaded best configuration: " + bestCellConfiguration);
	       // System.out.println(bestCellConfiguration.pointsCovered());
	        long finishTime = System.currentTimeMillis();
	        System.out.println("Unthreaded performance time: " + (finishTime - startTime) + "ms" + "\n");
	        
	        
	        int bestScorePrime=0;
	        CellNetwork bestConfig= null;
	        
	        long startTwo = System.currentTimeMillis(); 
	        MTRoad.calculateCoverage(cellNetworkConfigurations, bestScorePrime, bestConfig);
	        bestConfig = MTRoad.bestConfiguration;
	        
	        
	        
	        long finishTwo=System.currentTimeMillis();
	        System.out.println("Threaded best configuration: " + bestConfig);
//	        System.out.println("Amount covered: " + bestConfig.pointsCovered());
	        System.out.println("Threaded performance time: " + (finishTwo - startTwo) + "ms");
	        

//	        System.out.println("Best Score: " +  bestScore);
//	        System.out.println("Best CellConfiguration: " +  bestCellConfiguration);
//	        bestCellConfiguration.draw();
	       
	      
			try {
				File file2 = new File(reportFilename);
				Report re = new Report(file2);
				re.write(mostPoints, bestCellConfiguration, startTime, finishTime);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double distance= Double.POSITIVE_INFINITY;
        
//		System.out.println("xmax: " + Boundaries.xmax);
//		System.out.println("xmin: " + Boundaries.xmin);
//		System.out.println("ymax: " + Boundaries.ymax);
//		System.out.println("ymin: " + Boundaries.ymin);
		
		
//
        StdDraw.save(imageFilename);
			
    }
}