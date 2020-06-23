
package srtf;

import java.util.Scanner;

public class ShortestJobFirst {
      public static void SJF() {
        
	Scanner s = new Scanner(System.in);
		System.out.println ("Enter number of process:-");
		int n = s.nextInt();
		int processNum[] = new int[n+1];
		 
		int burstTime[] = new int[n+1]; 
		
		int turnTime[] = new int[n+1]; 
		int watingTime[] = new int[n+1]; 
                int tmp;        
		
		double SumWating=0, SumTurn=0;
 
		for(int i=0;i<n;i++)
		{
			
			System.out.println ("process " + (i+1) + " brust time=");
			burstTime[i] = s.nextInt();
			processNum[i] = i+1;
			
		}
                for(int i=0;i<n;i++){
                    watingTime[i]=0;
                    turnTime[i]=0;
                }
		for(int i=0;i<n-1;i++){
                    for(int j=0;j<n-1;j++){
                        if(burstTime[j]>burstTime[j+1]){
                            tmp=burstTime[j];
                            burstTime[j]=burstTime[j+1];
                            burstTime[j+1]=tmp;
                            
                            tmp=processNum[j];
                            processNum[j]=processNum[j+1];
                            processNum[j+1]=tmp;
                        }
                    }
                }
                for(int i=0;i<n;i++){
                    turnTime[i]=burstTime[i]+watingTime[i];
                    watingTime[i+1]=turnTime[i];
                }
                 turnTime[n]=burstTime[n]+watingTime[n];
                 for(int j=0;j<n;j++){
                     turnTime[j]=burstTime[j]+watingTime[j];
                      SumWating+=watingTime[j];
                 }
                 
		
	
		System.out.println("\n process  brust  turn  waiting");
		for(int i=0;i<n;i++)
		{
			SumWating+= watingTime[i];
			SumTurn+= turnTime[i];
			System.out.println("p"+processNum[i]+"\t"+burstTime[i]+"\t"+turnTime[i]+"\t"+watingTime[i]);
		}
		System.out.println ("\nAvg watingTime is "+ (double)(SumWating/n));
                System.out.println ( "Avg turnTime is "+ (double)(SumTurn/n));
		s.close();
	}
}
