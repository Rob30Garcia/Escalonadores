import java.util.*;
import java.io.*;

public class EscalonadoresMain {
    public static void main(String args[]) throws IOException {

        Scanner scan = new Scanner(System.in);

        System.out.print("Informe o nome do arquivo: ");
        String input = scan.nextLine();

        FileInputStream stream = new FileInputStream(input);
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(reader);
        String lineOfInput = br.readLine();

        ArrayList<String> tasks = new ArrayList<String>();

        while(lineOfInput != null) {
            tasks.add(lineOfInput);
            lineOfInput = br.readLine();
        }

        System.out.println("Escolha um dos escalonadores: 1 - FCFS | 2 - RR | 3 - SJF");
        int escolha = scan.nextInt();
        if(escolha == 1) {
            FCFS SchedulerOne = new FCFS(tasks);

            System.out.println(SchedulerOne.averageExecutionTime());
            System.out.println(SchedulerOne.averageWaitingTime());
        } else if (escolha == 2) {
            System.out.print("Digite o quantum: ");
            int quantum = scan.nextInt();

            RR SchedulerTwo = new RR(tasks, quantum);

            SchedulerTwo.executionAll();

            System.out.println(SchedulerTwo.getScheduleTasksDescription());

            System.out.println(SchedulerTwo.averageExecutionTime());

            System.out.println(SchedulerTwo.averageWaitingTime());

        } else if(escolha == 3) {
            SJF sfc = new SJF(tasks);

            System.out.println(sfc.getScheduleTasksDescription());
            System.out.println(sfc.averageExecutionTime());
            System.out.println(sfc.averageWaitingTime());

        }

        scan.close();
    }
}
