/*
 * A classe Schedule é para as informações de uma execução realizada, literalmente anotando o que foi realizado.
 *
 * */
public class Schedule {
    public int timeFinal;
    public String task;

    //Construtor da classe
    public Schedule(int time, String task) {
        this.timeFinal = time;
        this.task = task;
    }

    //Descrião com o nome da tarefa que foi realizada
    public String getTaskDescription() {
        String description = String.format("%s ", this.task);

        return description;
    }

    //Descrião com o nome da tarefa que foi realizada e do tempo.
    public String getDescription() {
        String description = String.format("%d %s\n", this.timeFinal, this.task);

        return description;
    }

}
