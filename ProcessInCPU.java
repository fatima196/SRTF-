
package srtf;


public class ProcessInCPU {
    public Process CurrentProcess;
    public  int Timer;

    public ProcessInCPU() {
        CurrentProcess=new Process();
        Timer=0;
    }
    
    

    public void setCurrentProcess(Process currentProcess) {
        this.CurrentProcess = currentProcess;
    }
}
