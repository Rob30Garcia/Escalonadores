import java.util.ArrayList;
import java.util.Iterator;

public class FCFS {
    private final ArrayList<Task> tasks = new ArrayList<Task>();

    private ArrayList<Integer> scheduleOfTimeFinal = new ArrayList<Integer>();

    public FCFS(ArrayList<String> tasks) {
        Iterator<String> iterator = tasks.iterator();
        while(iterator.hasNext()) {
            String task = iterator.next();
            Task TaskConverted = new Task(task);
            this.tasks.add(TaskConverted);
        }

        this.SmallestTimeJoined();
    }

    private void SmallestTimeJoined() {
        int time = Integer.MAX_VALUE;

        Iterator<Task> iterator = this.tasks.iterator();
        while(iterator.hasNext()) {
            Task task = iterator.next();
            if(task.joined < time) {
                time = task.joined;
            }
        }

        this.scheduleOfTimeFinal.add(time);
    }

    private int executeTask(Task task){
        int executionTime = 0;

        int timeStart = this.scheduleOfTimeFinal.get(this.scheduleOfTimeFinal.size() - 1);

        if(timeStart < task.joined) {
            timeStart = task.joined;
        }

        executionTime = timeStart + task.duration;

        this.scheduleOfTimeFinal.add(executionTime);

        return executionTime;
    }

    public double averageExecutionTime() {
        double sum = 0;

        Iterator<Task> iterator = this.tasks.iterator();
        while(iterator.hasNext()) {
            Task task = iterator.next();
            sum = sum + (this.executeTask(task) - task.joined);
        }

        double average = sum/(double) this.tasks.size();

        return average;
    }

    public double averageWaitingTime() {
        double sum = 0;

        for (int i = 0; i < this.tasks.size(); i++) {
            sum = sum + (this.scheduleOfTimeFinal.get(i) - this.tasks.get(i).joined);
        }

        double average = sum/(double) this.tasks.size();

        return average;
    }

    public String getDescription() {
        String tasks = "";

        Iterator<Task> iterator = this.tasks.iterator();
        while(iterator.hasNext()) {
            Task task = iterator.next();
            tasks = tasks + String.format("%s %d %d %d %d\n",
                    task.name, task.joined, task.duration, task.priority, task.typeProcess);
        }

        return tasks;
    }

}
