
package srtf;

import java.util.Scanner;


public class Main {
      /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NullPointerException{
        // TODO code application logic here
        SRTF s=new SRTF();Priority p=new Priority();ShortestJobFirst sjf=new ShortestJobFirst();
        
        System.out.println("Enter 1 to run SRTF scheduling");
        System.out.println("Enter 2 to run Priority scheduling");
        System.out.println("Enter 3 to run SJF scheduling");
        System.out.println("Enter another number to Exit");
        Scanner in =new Scanner(System.in);
        int num=in.nextInt();
            switch (num){
                case 1:s.input();s.SRTF();s.display();
                       System.out.println("Average Waiting Time = "+s.getAverageTurnaround());
                       System.out.println("Average Turnaround Time: "+SRTF.getAverageWaitingTime());
                       break;
                case 2: p.input();p.priority();p.display();
                        System.out.println("Average Waiting Time = "+p.getAverageTurnaround());
                        System.out.println("Average Turnaround Time: "+p.getAverageWaitingTime());
                        break;
                case 3: sjf.SJF();
                default: System.out.println("Thanks:)");
                         break;
            }
    }
}
