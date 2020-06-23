
package srtf;

import java.util.ArrayList;


public class Process {
    public  String processName;
    public  int arrivalTime;
    public  int burstTime;
    public  int completionTime;
    public  int turnaroundTime;
    public  int waittingTime;
    //for priorty scadualing 
    public  int priority;
    public  int starving;
    public ArrayList<Integer> startTime;
    public ArrayList<Integer> endTime;
    
     Process()
    {
        processName="";
        waittingTime=0;
        turnaroundTime=0;
        arrivalTime=-1;
        burstTime=-1;
        completionTime=-1;
        starving=0;
        startTime=new ArrayList();
        endTime=new ArrayList();
    }
    
}
