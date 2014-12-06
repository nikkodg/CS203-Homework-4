package hw4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Report {

	//Print most points
	//Print best cell config 
	//Prefromance Time
	File file = new File("report.txt"); 
	
	
    public Report(File file) throws IOException {
        // TODO Auto-generated constructor stub
    	if(!file.exists()){
			file.createNewFile();
		}
    }

    public void add(String message) {
        // TODO Auto-generated method stub

    }

    public void write(Road mostpoints, CellNetwork bestCellconfig, long startT, long endT) throws IOException {
 
    	long totalT = endT - startT;
    	FileWriter fw = new FileWriter(file);
    	BufferedWriter bw = new BufferedWriter(fw);
		bw.write("Group: Andrew and Nikko" + "\n");
		bw.newLine();
		bw.write(mostpoints.toString());
		bw.newLine();
		bw.write("Best Cell Configuration: " + "\n" + bestCellconfig + "\n");
		bw.newLine();
		bw.write("Performance time: " + totalT + "ms");
		bw.newLine();
		bw.flush();
		bw.close();
		}
    }
