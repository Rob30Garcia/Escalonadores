public class Task {
    public String name;
    public int joined;
    public int duration;
    public int priority;
    public int typeProcess;

    public Task(String task) {
        String[] infos = task.split(" ");
        this.name = infos[0];
        this.joined = Integer.parseInt(infos[1]);
        this.duration = Integer.parseInt(infos[2]);
        this.priority = Integer.parseInt(infos[3]);
        this.typeProcess = Integer.parseInt(infos[4]);
    }

    public String getDescription() {
        String description = String.format("%s %d %d %d %d",
                this.name, this.joined, this.duration, this.priority, this.typeProcess);

        return description;
    }
}
