import java.util.*;
import java.io.*;

public class EscalonadoresMain {
    public static void main(String args[]) throws IOException {

        Scanner scan = new Scanner(System.in);

        String entrada = scan.nextLine();

        FileInputStream stream = new FileInputStream(entrada);
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(reader);
        String linha = br.readLine();
        while(linha != null) {
            System.out.println(linha);
            linha = br.readLine();
        }

        scan.close();
    }
}
