import java.util.ArrayList;
import java.util.Iterator;

public class RR {
    private int quantum;
    private final ArrayList<Task> tasks = new ArrayList<Task>();
    private final ArrayList<Task> tasksCopy = new ArrayList<Task>();
    private ArrayList<Schedule> scheduleOfTimeFinal = new ArrayList<Schedule>();

    public RR(ArrayList<String> tasks, int quantum) {
        Iterator<String> iterator = tasks.iterator();
        while(iterator.hasNext()) {
            String task = iterator.next();
            Task TaskConverted = new Task(task);
            this.tasks.add(TaskConverted);
            this.tasksCopy.add(TaskConverted);
        }

        this.quantum = quantum;

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

    private Task executionTask(Task task) {
        int timeStart = this.scheduleOfTimeFinal.get(this.scheduleOfTimeFinal.size() - 1).timeFinal;

        int timeFinal = 0;

        if(timeStart < task.joined) {
            timeStart = task.joined;
        }

        if(task.duration <= this.quantum) {
            timeFinal = timeStart + task.duration;

            Schedule newSchedule = new Schedule(timeFinal, task.name);

            this.scheduleOfTimeFinal.add(newSchedule);

            return null;
        } else {
            task.duration = task.duration - this.quantum;
            timeFinal = timeStart + quantum;

            Schedule newSchedule = new Schedule(timeFinal, task.name);

            this.scheduleOfTimeFinal.add(newSchedule);

            return task;
        }
    }

    public void executionAll() {
       ArrayList<Task> tasks = this.tasks;

       int i = 0;
       while(i < tasks.size()) {
           Task taskInExecution = tasks.get(i);

           Task newTask = this.executionTask(taskInExecution);

           if(newTask != null) {
               int j = 0;
               while(j < tasks.size()) {
                   if(tasks.get(j) != null) {
                       if (this.scheduleOfTimeFinal.get(this.scheduleOfTimeFinal.size() - 1).timeFinal < tasks.get(j).joined) {
                           break;
                       }
                   }
                   j++;
               }

               int k = j;
               while(k < tasks.size()) {
                   Task oldTask = tasks.get(k);
                   tasks.set(k, newTask);
                   newTask = oldTask;
                   k++;
               }

                tasks.add(newTask);

           } else {
               tasks.set(i, null);
           }

           i++;
       }
    }

    private void executionTimeOfTask() {
        Iterator<Task> iterator = this.tasksCopy.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            Iterator<Schedule> iteratorSchedule = this.scheduleOfTimeFinal.iterator();
            while(iteratorSchedule.hasNext()) {
                Schedule scheduleOfTask = iteratorSchedule.next();
                if(task.name == scheduleOfTask.task) {
                    task.executionTime = scheduleOfTask.timeFinal;
                }
            }
        }
    }

    public double averageExecutionTime() {
        this.executionTimeOfTask();

        double sum = 0;
        double average = 0;

        Iterator<Task> iterator = this.tasksCopy.iterator();
        while(iterator.hasNext()) {
            Task task = iterator.next();
            sum = sum + (task.executionTime - task.joined);
        }

        average = sum/(double) this.tasksCopy.size();

        return average;
    }

    public String getScheduleDescription() {
        String schedule = "";

        Iterator<Schedule> iterator = this.scheduleOfTimeFinal.iterator();
        while(iterator.hasNext()) {
            Schedule noteInSchedule = iterator.next();
            schedule = schedule + noteInSchedule.getDescription();
        }

        return schedule;
    }

    public String getScheduleTasksDescription() {
        String schedule = "";

        Iterator<Schedule> iterator = this.scheduleOfTimeFinal.iterator();
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
            tasks = tasks + String.format("%s %d %d %d %d\n",
                    task.name, task.joined, task.duration, task.priority, task.typeProcess);
        }

        return tasks;
    }


}
