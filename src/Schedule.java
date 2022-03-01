public class Schedule {
    public int timeFinal;
    public String task;

    public Schedule(int time, String task) {
        this.timeFinal = time;
        this.task = task;
    }

    public String getTaskDescription() {
        String description = String.format("%s ", this.task);

        return description;
    }

    public String getDescription() {
        String description = String.format("%d %s\n", this.timeFinal, this.task);

        return description;
    }

}
