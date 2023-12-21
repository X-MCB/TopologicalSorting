import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    static PrintWriter writer;

    static {
        try {
            writer = new PrintWriter("resultados.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {


      testeTXT("grafo30.txt");

    }


    public static Grafo construirGrafo(int numeroDeVertices, int numArestas) throws IOException {
        double tempoTotal = 0;
        Grafo G = null;
        for(int i = 0; i < 1000; i++) {

            double tempoInicio = System.currentTimeMillis();

            G = new Grafo(numeroDeVertices, numArestas, false);


            double tempoFim = System.currentTimeMillis();

            tempoTotal += tempoFim - tempoInicio;
        }

//        System.out.println("grafo" + numeroDeVertices + ": " + ((tempoFim - tempoInicio)/1000) + "s");
//        Toolkit.getDefaultToolkit().beep();
//        (new Scanner(System.in)).nextLine();

        writer.println("grafo" + numeroDeVertices + ": " + (tempoTotal/1000)/1000 + "s");


        return G;
    }

    public static void testeTXT(String arquivo) throws IOException {
        OrdenacaoTopologica ord = new OrdenacaoTopologica();


        int repeticoes = 1;

        double tempoInicio;
        double tempoFim;
        double tempoTotal = 0;

        for (int i = 0; i < repeticoes;i++) {
            tempoInicio = System.currentTimeMillis();

            ord.realizaLeituraTXT(arquivo);
            System.out.println(ord.executa());

            tempoFim = System.currentTimeMillis();

            tempoTotal += (tempoFim - tempoInicio);

        }

        writer.println(arquivo + ": " + (tempoTotal/1000)/1000 + "s");

  //      System.out.println((tempoTotal/repeticoes)/1000.0);
  //      Toolkit.getDefaultToolkit().beep();
  //      (new Scanner(System.in)).nextLine();

    }

    public static void testeGrafo(Grafo G) throws IOException {

        OrdenacaoTopologica ord = new OrdenacaoTopologica();


        int repeticoes = 10;

        double tempoInicio;
        double tempoFim;
        double tempoTotal = 0;

        for (int i = 0; i < repeticoes;i++) {
             tempoInicio = System.currentTimeMillis();

            ord.realizaLeituraObjeto(G);
            System.out.println(ord.executa());

             tempoFim = System.currentTimeMillis();

             tempoTotal += (tempoFim - tempoInicio);



        }

        writer.println("grafo" + G.getVerticesLength() + ": " + (tempoTotal/repeticoes)/1000.0 + "s");


    //    System.out.println((tempoTotal/repeticoes)/1000.0);
    //    Toolkit.getDefaultToolkit().beep();
    //    (new Scanner(System.in)).nextLine();

    }
}
