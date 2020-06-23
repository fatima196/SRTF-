
package srtf;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import jdk.nashorn.internal.codegen.CompilerConstants;


public class SRTF {
    static ArrayList <Process> processesList=new ArrayList();
  
    static ProcessInCPU cpu=new ProcessInCPU();

    private static int NumOfProcess;
    private static float AverageTurnaround;
    private static float AverageWaitingTime;

    static Process[] Processes;
    static Queue<Process> ReadyQueue = new LinkedList<Process>();
    static Queue<Process> completedQueue = new LinkedList<Process>();
    static int ContextSwitching;
    
    public static void setNProcesses(int n){
        NumOfProcess=n;
    }
    
       static void Initialize()
    {
        Processes = new Process[NumOfProcess];
        for (int i = 0; i < NumOfProcess ; i++) {
            Processes[i]=new Process();
        }
    }
    
    public static void setContextSwitching(int c){
        ContextSwitching=c;
    }
    
    public static float getAverageTurnaround(){
        return AverageTurnaround;
    }
    
    public static float getAverageWaitingTime(){
        return AverageWaitingTime;
    }
    
    private static void addToC_Q(Process p,int T)
    {
        completedQueue.offer(p);
        p.completionTime=T;
        p.turnaroundTime= p.completionTime-p.arrivalTime;
    }


    private static void receiveProcess(int processArrivalTime)
    {
        for (int i = 0; i <NumOfProcess ; i++) {
            if(Processes[i].arrivalTime==processArrivalTime)
            {
                InsertInRQ(Processes[i]);
            }
        }
    }

    private static void InsertInRQ(Process p)
    {
        if(ReadyQueue.size()==0){ReadyQueue.offer(p); return;
        }

        Process temp;
        boolean inserted=false;
        int n=ReadyQueue.size();

        for (int i=0;i<n;i++) {
            temp=ReadyQueue.poll();
            if(p.burstTime<temp.burstTime&&!inserted)
            {
                inserted=true;
                ReadyQueue.offer(p);
            }
            ReadyQueue.offer(temp);
        }
        if(!inserted) {
            ReadyQueue.offer(p);
        }

    }

    private static void sort() {

        for (int i = 0; i <NumOfProcess-1 ; i++) {
            for (int j = 0; j <NumOfProcess-1 ; j++) {

                if(Processes[j].arrivalTime>Processes[j+1].arrivalTime)
                {
                    Process temp=Processes[j];
                    Processes[j]=Processes[j+1];
                    Processes[j+1]=temp;
                }
            }
        }
    }
    
    public static void SRTF (){
        
        sort();
        
        System.out.println("Order in which processes gets executed "); 
        for (int  i = 0 ; i <Processes.length; i++) {
            System.out.print(Processes[i].processName+ "   ") ;
            processesList.add(Processes[i]);
        }
            System.out.println();
        
        
        int Time=0;
        int contextSwiching=0;
        while (completedQueue.size()!=NumOfProcess)
        {
            receiveProcess(Time);
            
            if(cpu.CurrentProcess.burstTime<0)
            {
                if(ReadyQueue.size()!=0)
                {
                    cpu.CurrentProcess=ReadyQueue.poll();
                }
            }
            
            if(ReadyQueue.size()!=0){
            if(ReadyQueue.peek().burstTime<cpu.CurrentProcess.burstTime && cpu.CurrentProcess.burstTime>0 )
            {
                cpu.CurrentProcess.endTime.add(Time);
                contextSwiching=ContextSwitching;
                while (contextSwiching>0)
                {
                    contextSwiching--;
                    cpu.CurrentProcess.waittingTime++;
                    
                    for (Process p:ReadyQueue) {
                        p.waittingTime++;
                    }
                    Time++;
                    receiveProcess(Time);
                }
                
                InsertInRQ(cpu.CurrentProcess);
                for (Process l:ReadyQueue){
                    System.out.println("--process name--"+"  "+l.processName);
                }
                cpu.CurrentProcess=ReadyQueue.poll();
            }}

            
           
            
            
            if(cpu.CurrentProcess.burstTime==0)
            {   
                 contextSwiching=ContextSwitching;
                while (contextSwiching>0)
                {
                    contextSwiching--;
                    cpu.CurrentProcess.waittingTime++;
                    
                    for (Process p:ReadyQueue) {
                        p.waittingTime++;
                    }
                    Time++;
                    receiveProcess(Time);
                }

                cpu.CurrentProcess.endTime.add(Time);
                addToC_Q(cpu.CurrentProcess,Time);
                if(ReadyQueue.size()==0)cpu.CurrentProcess=new Process();
                else cpu.CurrentProcess=ReadyQueue.poll();
            }

            
            if(cpu.CurrentProcess.burstTime>0)
            {
                cpu.CurrentProcess.startTime.add(Time);
                cpu.CurrentProcess.burstTime--;
                for (Process p:ReadyQueue) {
                    p.waittingTime++;
                }
            }

            Time++;
        }
        
       
        
        
        
        float sum1=0,sum2=0;
        
        for (Process p:completedQueue) {
            sum1+=p.turnaroundTime;
            sum2+=p.waittingTime;
        }
        
        AverageTurnaround=sum1/NumOfProcess;
        AverageWaitingTime=sum2/NumOfProcess;
        
       
 
    }
    
    static void input()
    {
        Scanner scan=new Scanner(System.in);
        System.out.println("Enter number of Processes");
        int n = scan.nextInt();
        setNProcesses(n);
        
        Initialize();

        System.out.println("Context Switching");
        ContextSwitching=scan.nextInt();


        scan.nextLine();
        System.out.println("Enter for each processes names , Arrival times , Burst times : ");
        for (int i = 0; i < n; i++) {
            Processes[i].processName = scan.next();
            Processes[i].arrivalTime = scan.nextInt();
            Processes[i].burstTime = scan.nextInt();
        }



    }
    
    public static  void display(){
        for (Process p:completedQueue) {
            System.out.println(p.processName+" Watting Time = "+p.waittingTime); 
        }
        for (Process p:completedQueue) {
            System.out.println(p.processName+" turnaround Time = "+p.turnaroundTime);  
        }
        
         for (Process u :processesList) {
            for (int ti:u.startTime)
                System.out.println(u.processName+" "+"startTime = "+ti);
            for (int tim:u.endTime)
                System.out.println(u.processName+" "+"endTime = "+tim);
        }
        
        for (Process t:processesList)System.out.println("ssssssss"+processesList.size()); 
        
    }


   

    
}
