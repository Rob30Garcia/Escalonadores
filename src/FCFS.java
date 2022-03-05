
/*
 * A classe FCFS é para execução do escalonador First-Come, First-Served
 *
 * */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FCFS {
    private final ArrayList<Task> tasks = new ArrayList<Task>();

    private ArrayList<Integer> scheduleOfTimeFinal = new ArrayList<Integer>();

    //Construtor da classe
    public FCFS(ArrayList<String> tasks) {
        Iterator<String> iterator = tasks.iterator();
        while(iterator.hasNext()) {
            String task = iterator.next();
            Task TaskConverted = new Task(task);
            this.tasks.add(TaskConverted);
        }

        this.SmallestTimeJoined();
    }

    //Essa função privada é para pegar o menor tempo e amazena na agenda
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

    //Essa função privada é para executar uma tarefa individualmente, nesse escalonador o tempo de execução é retornado.
    //Amazena no scheduleOfTimeFinal o tempo de execução
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

    //Essa função publica é para executar todas as tarefas com a função executeTask, passando por todas as tarefas na entrada.
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

    //Essa função é para pegar o tempo de espera a partir da agenda que armazena o histórico da execução das tarefas
    public double averageWaitingTime() {
        double sum = 0;

        for (int i = 0; i < this.tasks.size(); i++) {
            sum = sum + (this.scheduleOfTimeFinal.get(i) - this.tasks.get(i).joined);
        }

        double average = sum/(double) this.tasks.size();

        return average;
    }

    //Descrição de todas as tarefas
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
