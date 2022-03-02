public class Task {
    public String name;
    public int joined;
    public int duration;
    public int priority;
    public int typeProcess;
    public double executionTime;

    public Task(String task) {
        String[] infos = task.split(" ");
        this.name = infos[0];
        this.joined = Integer.parseInt(infos[1]);
        this.duration = Integer.parseInt(infos[2]);
        this.priority = Integer.parseInt(infos[3]);
        this.typeProcess = Integer.parseInt(infos[4]);
        this.executionTime = 0;
    }

    public String getDescription() {
        String description = String.format("%s %d %d %d %d %.2f",
                this.name, this.joined, this.duration, this.priority, this.typeProcess, this.executionTime);

        return description;
    }
}
