/* *
 * A Classe para o Escalonador Round Robin
 * */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RR {
    private int quantum;
    private final ArrayList<Task> tasks = new ArrayList<Task>();
    private final ArrayList<Task> tasksCopy = new ArrayList<Task>();
    private ArrayList<Schedule> scheduleOfTimeFinal = new ArrayList<Schedule>();
    private ArrayList<Schedule> scheduleCopy = new ArrayList<Schedule>();

    //construtor da classe, onde é armazenado o quantum
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

    //Função que pega o menor tempo e armazenda na agenda como inicio. Porém no Round Robin tem o nome da tarefa armazenada
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

    //Função que executa uma task individualmente
    private Task executionTask(Task task) {
        int timeStart = this.scheduleOfTimeFinal.get(this.scheduleOfTimeFinal.size() - 1).timeFinal;

        int timeFinal = 0;

        if(timeStart < task.joined) {
            timeStart = task.joined;
        }

        //compara a duração com o quantum, caso seja menor que o quantum ele armazenada na agenda e retorna null
        //Isso para não ser reexecutada
        //caso não seja menor a duração que o quantum, retorna a task com o tempo de duração menor
        if(task.duration <= this.quantum) {
            timeFinal = timeStart + task.duration;

            Schedule newSchedule = new Schedule(timeFinal, task.name);

            this.scheduleOfTimeFinal.add(newSchedule);
            this.scheduleCopy.add(newSchedule);

            return null;
        } else {
            task.duration = task.duration - this.quantum;
            timeFinal = timeStart + quantum;

            Schedule newSchedule = new Schedule(timeFinal, task.name);

            this.scheduleOfTimeFinal.add(newSchedule);
            this.scheduleCopy.add(newSchedule);

            return task;
        }
    }

    //Executa todas as tarefas
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

    //Coloca os tempos finais nas tarefas para serem armazenados
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

    //Depois de todas as tarefas executadas é pego a média do tempo de execução, sendo a execução menos a entrada
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

    //Depois de executado precisamos pegar os tempos de espera, nesse caso precisou de uma flag para pega o primeiro valor da agenda
    //Isso preciso para não ter uma duplicação das tarefas quando procurasse na agenda.
    public double averageWaitingTime() {
        ArrayList<Schedule> schedule = this.scheduleCopy;

        double average = 0;
        double sum = 0;
        int flag = 0;
        Iterator<Task> iterator = this.tasksCopy.iterator();
        while(iterator.hasNext()) {
            Task task = iterator.next();

            //para haver troca de valores
            int firstTime = task.joined;
            int secondTime = 0;

            int i = 1;
            while(i < schedule.size()){
                if(task.name == schedule.get(i).task && firstTime < schedule.get(i).timeFinal) {
                    secondTime = schedule.get(i - 1).timeFinal;
                    task.waitingTime = task.waitingTime + secondTime - firstTime;
                    firstTime = schedule.get(i).timeFinal;
                }
                i++;
            }

            if(flag == 0) {
                task.waitingTime = task.waitingTime - this.quantum;
                flag = 1;
            }

            sum = sum + task.waitingTime;
        }

        average = sum / (double) this.tasksCopy.size();
        return average;
    }

    //Descrição da agenda
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

    //Descrição da agenda por tarefa
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

    //Descrição das tarefas
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
