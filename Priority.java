
package srtf;

import com.sun.org.apache.bcel.internal.generic.SWAP;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import static srtf.SRTF.Processes;
import static srtf.SRTF.ReadyQueue;
import static srtf.SRTF.completedQueue;

public class Priority {
    
     static ProcessInCPU cpu=new ProcessInCPU();

    private static int NumOfProcess;
    
    private static float AverageTurnaround;
    private static float AverageWaitingTime;

    static Process[] Processes;
    static Queue<Process> ReadyQueue = new LinkedList<Process>();
    static Queue<Process> completedQueue = new LinkedList<Process>();
    
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
            if(Processes[i]!=null&&Processes[i].arrivalTime<=processArrivalTime)
            {
                InsertInRQ(Processes[i],ReadyQueue);
                Processes[i]=null;
            }
        }
    }
    
    static void InsertInRQ(Process p,Queue<Process> RQ)
    {
        RQ.offer(p); return; 
    }
    
    static void reinsertInRQ(){
       // Process temp;
        Process[]pro=new Process[ReadyQueue.size()];
        for (int y=0;y<ReadyQueue.size();y++)pro[y] = new Process();
        //int n=ReadyQueue.size();
        //for (Process b:ReadyQueue)System.out.println("cryyyy"+b.processName);
        for (int t=0;t<=ReadyQueue.size();t++){
            pro[t]=ReadyQueue.poll();
            //System.out.println("pro-->"+pro[t].processName);
        }
       // System.out.println("length="+pro.length);
        for (int i = 0; i <pro.length-1 ; i++) {
            for (int j = 0; j <pro.length-1 ; j++) {
                if(pro[j].arrivalTime>pro[j+1].arrivalTime)
                {
                    Process temp=pro[j];
                    pro[j]=pro[j+1];
                    pro[j+1]=temp;
                }
                else if (pro[j].arrivalTime==pro[j+1].arrivalTime){
                    if (pro[j].priority>pro[j+1].priority){
                        Process temp=pro[j];
                        pro[j]=pro[j+1];
                        pro[j+1]=temp;
                        }
                } 
            }
        }
        ReadyQueue.clear();
        for (int k=0;k<pro.length;k++){
            ReadyQueue.offer(pro[k]);
        }
        //for(Process m:ReadyQueue)System.out.println("<<<>>>>"+m.processName);
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
                else if (Processes[j].arrivalTime==Processes[j+1].arrivalTime){
                    if (Processes[j].priority>Processes[j+1].priority){
                        Process temp=Processes[j];
                        Processes[j]=Processes[j+1];
                        Processes[j+1]=temp;
                        }
                } 
            }
        }
    }
     
   /* static void reSort()
    {
        Process t;
        Queue<Process> temp = new LinkedList<Process>();
        int n=ReadyQueue.size();
        for (int i = 0; i <n ; i++) {
            t=ReadyQueue.poll();
            InsertInRQ(t,temp);
        }
        ReadyQueue=temp;
        
         for(Process p:ReadyQueue){
             System.out.println(p.priority);
             reinsertInRQ(p);
         }
        
    }*/
    
    static void priority(){
         sort();
         
        System.out.println("Order in which processes gets executed "); 
        for (int  i = 0 ; i <Processes.length; i++) 
            System.out.print(Processes[i].processName+ "   ") ; 
            System.out.println();
            

        int Time=0; int v=0;  int limit=100; boolean flag=false;
        
       
        //low number represent high priority
        while (completedQueue.size()!=NumOfProcess)
        {
            receiveProcess(Time);
            
            

            if(ReadyQueue.size()!=0)
            if(cpu.CurrentProcess.burstTime<0)
            {
                if(ReadyQueue.size()!=0)
                {
                    cpu.CurrentProcess=ReadyQueue.poll();
                }
            }

           
           
                
          // for (Process p:ReadyQueue)System.out.println("in RQ"+p.processName);
            if(cpu.CurrentProcess.burstTime>0){
                System.out.println("current process"+cpu.CurrentProcess.processName);
                v+=cpu.CurrentProcess.burstTime;
               // System.out.println("---> v= "+v);
                cpu.CurrentProcess.burstTime=0;
               // System.out.println("lablablaa");
                //for (Process p:ReadyQueue)System.out.println("in RQ"+p.processName);
                if (ReadyQueue.size()!=0){
                for (Process p:ReadyQueue) {
                     //System.out.println("in RQ"+p.processName);
                     p.waittingTime=v;
                     System.out.println(p.processName+" waitting time"+p.waittingTime);
                }
                
                for (Process p:ReadyQueue) {
                    if(p.waittingTime>limit){System.out.println("Starvation"); p.starving++; }
                }
                
                for (Process p:ReadyQueue) {
                    //System.out.println("process in ready queue-->"+p.processName);
                   if(p.starving>=1)
                   {
                      // System.out.println("YES");
                      // System.out.println(p.processName+"  old  "+p.priority);
                       p.priority--;
                      // System.out.println(p.processName+"  new  "+p.priority);
                       flag=true;
                   }
                }
                if (flag==true){
               // for (Process p:ReadyQueue)System.out.println("b-----"+p.processName+p.priority);
                //System.out.println(p.priority);
                for (Process p:ReadyQueue)System.out.println("name= "+p.processName+"  priority= "+p.priority);
                reinsertInRQ();
                //for (Process p:ReadyQueue)System.out.println("name= "+p.processName+"priority= "+p.priority);
               // System.out.println("----------------");
                 }}
                
                if(cpu.CurrentProcess.burstTime==0)
                {

                addToC_Q(cpu.CurrentProcess,v);//for (Process o:completedQueue){System.out.print("in completion-->"+o.processName);}System.out.println();
                if(ReadyQueue.size()==0)
                    cpu.CurrentProcess=new Process(); 
                    
                cpu.CurrentProcess=ReadyQueue.poll(); 
                }
            }
            

            Time+=v;
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
        System.out.println("Enter number of Processes ");
        int n = scan.nextInt();
        setNProcesses(n);
        
        Initialize();

        scan.nextLine();
        System.out.println("Enter for each processes names , Arrival times , Burst times , priority : ");
        for (int i = 0; i < n; i++) {
            Processes[i].processName = scan.next();
            Processes[i].arrivalTime = scan.nextInt();
            Processes[i].burstTime = scan.nextInt();
            Processes[i].priority=scan.nextInt();
        }
    }
    
    public static  void display(){
        for (Process p:completedQueue) {
            System.out.println(p.processName+" Watting Time = "+p.waittingTime); 
        }
        for (Process p:completedQueue) {
            System.out.println(p.processName+" turnaround Time = "+p.turnaroundTime);  
        }
    }
    
    
    
}
