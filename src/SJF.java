import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SJF {
    private final ArrayList<Task> tasks = new ArrayList<Task>();
    private final ArrayList<Task> tasksCopy = new ArrayList<Task>();
    private ArrayList<Schedule> scheduleOfTimeFinal = new ArrayList<Schedule>();

    public SJF(ArrayList<String> tasks) {
        Iterator<String> iterator = tasks.iterator();
        while(iterator.hasNext()) {
            String task = iterator.next();
            Task TaskConverted = new Task(task);
            this.tasks.add(TaskConverted);
            this.tasksCopy.add(TaskConverted);
        }

        this.SmallestTimeJoined();
    }

    private void SmallestTimeJoined() {
        int time = Integer.MAX_VALUE;
        String taskName = "";

        Iterator<Task> iterator = this.tasks.iterator();
        while(iterator.hasNext()) {
            Task task = iterator.next();
            if(task.joined < time) {
                time = task.joined;
                taskName = task.name;
            }
        }

        Schedule first = new Schedule(time, taskName);

        this.scheduleOfTimeFinal.add(first);
    }

    private Task SmallestTask(int time) {
        Task smallestTask = new Task("P0 0 0 0 0 0");

        int smallestDuration = Integer.MAX_VALUE;

        Iterator<Task> iterator = this.tasks.iterator();
        while(iterator.hasNext()) {
            Task verifyTask = iterator.next();
            if(verifyTask.duration < smallestDuration && verifyTask.joined <= time) {
                smallestDuration = verifyTask.duration;
                smallestTask = verifyTask;
            }
        }

        this.tasks.remove(smallestTask);

        return smallestTask;
    }

    private int executeTask(Task task){
        int executionTime = 0;

        int timeStart = this.scheduleOfTimeFinal.get(this.scheduleOfTimeFinal.size() - 1).timeFinal;

        if(timeStart < task.joined) {
            timeStart = task.joined;
        }

        executionTime = timeStart + task.duration;

        Schedule schedule = new Schedule(executionTime, task.name);

        this.scheduleOfTimeFinal.add(schedule);

        return executionTime;
    }

    public double averageExecutionTime() {
        double sum = 0;
        double average = 0;

        int time = 0;
        int i = 0;
        while(i < this.tasksCopy.size()) {
            time = this.scheduleOfTimeFinal.get(this.scheduleOfTimeFinal.size() - 1).timeFinal;
            Task task = SmallestTask(time);

            sum = sum + (executeTask(task) - task.joined);
            i++;
        }

        average = sum/(double) this.tasksCopy.size();

        return average;
    }


    public double averageWaitingTime() {
        double sum = 0;

        int flag = 0;
        Schedule schedule;
        int i = 1;
        while (i < this.scheduleOfTimeFinal.size()) {
            Iterator<Task> iterator = this.tasksCopy.iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                if(task.name == this.scheduleOfTimeFinal.get(i).task) {
                    sum = sum + (this.scheduleOfTimeFinal.get(i - 1).timeFinal - task.joined);
                }
            }
            i++;
        }

        double average = sum/(double) this.tasksCopy.size();

        return average;
    }


    public String getScheduleDescription() {
        String schedule = "";

        List<Schedule> scheduleToDescription = this.scheduleOfTimeFinal.subList(1, this.scheduleOfTimeFinal.size());

        Iterator<Schedule> iterator = scheduleToDescription.iterator();
        while(iterator.hasNext()) {
            Schedule noteInSchedule = iterator.next();
            schedule = schedule + noteInSchedule.getDescription();
        }

        return schedule;
    }

    public String getScheduleTasksDescription() {
        String schedule = "";

        List<Schedule> scheduleToDescription = this.scheduleOfTimeFinal.subList(1, this.scheduleOfTimeFinal.size());

        Iterator<Schedule> iterator = scheduleToDescription.iterator();
        while(iterator.hasNext()) {
            Schedule noteInSchedule = iterator.next();
            schedule = schedule + noteInSchedule.getTaskDescription();
        }

        return schedule;
    }

    public String getDescription() {
        String tasks = "";

        Iterator<Task> iterator = this.tasksCopy.iterator();
        while(iterator.hasNext()) {
            Task task = iterator.next();
            tasks = tasks + task.getDescription() + "\n";
        }

        return tasks;
    }

}
