package hw4;

import java.util.ArrayList;

interface CONSTANTS {
	public static final int THREAD_COUNT = 4;
}

class Task implements Runnable, CONSTANTS{
	private int id;
	private ArrayList<CellNetwork> cellNetworks;
	
	private int score;
	private CellNetwork bestConfig = null;
	
	public Task() {
		System.out.println("default constructor not allowed");
		System.exit(-1);
	}

	public Task(int id, ArrayList<CellNetwork> cellNetworks) {
		this.id = id;
		this.cellNetworks = cellNetworks;
	}

	public void run() {
		// figure out start, end index of cellNetworks based on id
		// cellNetworks.get(idx)
		int start = id * cellNetworks.size()/ THREAD_COUNT;
		int end = (id + 1)* cellNetworks.size()/ THREAD_COUNT;
		bestConfig= MTRoad.getBestConfiguration(cellNetworks, start, end);
		
		System.out.println("task id: " + id + "\n" + bestConfig);
	}
	
	public int getId(){
		return id;
	}
	
	public ArrayList<CellNetwork> getNetworks(){
		return cellNetworks;
	}
	
	public double getScore(){
		return score;
	}
	
	public CellNetwork getBestConfig(){
		return bestConfig;
	}
	
	public void setBestConfig(CellNetwork config){
		bestConfig = config;
	}
}

public class MTRoad extends Road implements CONSTANTS {
	
	static CellNetwork bestConfiguration= null;
	
	public static void calculateCoverage(ArrayList<CellNetwork> cellNetworks, 
				Integer score, CellNetwork bestConfig) {
		Task[] tasks = new Task[THREAD_COUNT];
		Thread[] threads = new Thread[THREAD_COUNT];
		
		for (int i = 0; i < THREAD_COUNT; i++) {
			tasks[i] = new Task(i, cellNetworks);
			threads[i] = new Thread(tasks[i]);
		}
		
		// run the threads
		for (int i = 0; i < THREAD_COUNT; i++) {
			threads[i].start();
		}
		
		// wait for threads to finish
		for (int i = 0; i < THREAD_COUNT; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// of the THREAD_COUNT threads pick the best one
		ArrayList<CellNetwork> bestConfigurations = new ArrayList<CellNetwork>();
		for (int i = 0; i < THREAD_COUNT; i++) {
			// obtain results from tasks[i].someMethod() blah
			tasks[i].setBestConfig(MTRoad.getBestConfiguration(tasks[i].getNetworks(), 0, tasks[i].getNetworks().size()));
			bestConfigurations.add(tasks[i].getBestConfig());
		}
		
		for(CellNetwork cn : bestConfigurations){
			if(cn.pointsCovered() > score){
				score = cn.pointsCovered();
				bestConfiguration = cn;
			}
		}
		
		
		
		// set score and set bestConfig
		
	
	}
	
	public static CellNetwork getBestConfiguration(ArrayList<CellNetwork> networks, int start, int end){
		int score = 0;
		CellNetwork bestConfig = null;
		
		for(int i = start; start < end ; start++){
			CellNetwork cn = networks.get(i);
			if(cn.pointsCovered() > score){
				score = cn.pointsCovered();
				bestConfig = cn;
			}
		}
		
		return bestConfig;
				
		
	}
	
}

