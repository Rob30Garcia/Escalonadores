/*
* A classe Task é para as informações das tarefas/processos.
*
* */

public class Task {
    public String name;
    public int joined;
    public int duration;
    public int priority;
    public int typeProcess;
    public double executionTime;
    public double waitingTime;

    //Construtor da classe
    //Converte a tarefa que chega como string para um objeto
    public Task(String task) {
        String[] infos = task.split(" ");
        this.name = infos[0];
        this.joined = Integer.parseInt(infos[1]);
        this.duration = Integer.parseInt(infos[2]);
        this.priority = Integer.parseInt(infos[3]);
        this.typeProcess = Integer.parseInt(infos[4]);
        this.executionTime = 0;
        this.waitingTime = 0;
    }

    //Descrição da classe
    public String getDescription() {
        String description = String.format("%s %d %d %d %d %.2f %.2f",
                this.name, this.joined, this.duration, this.priority, this.typeProcess, this.executionTime, this.waitingTime);

        return description;
    }
}
