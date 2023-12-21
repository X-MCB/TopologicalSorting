import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class OrdenacaoTopologica {
    private class Elo {
        /* Identifica��o do elemento. */
        public int chave;

        /* N�mero de predecessores. */
        public int contador;

        /* Aponta para o pr�ximo elo da lista. */
        public Elo prox;

        /* Aponta para o primeiro elemento da lista de sucessores. */
        public EloSuc listaSuc;

        public Elo(int chave) {
            this.chave = chave;
            prox = null;
            contador = 0;
            listaSuc = null;
        }

    }

    private class EloSuc {
        /* Aponta para o elo que � sucessor. */
        public Elo id;

        /* Aponta para o pr�ximo elemento. */
        public EloSuc prox;

        public EloSuc(Elo id, EloSuc prox) {
            this.id = id;
            this.prox = prox;
        }
    }

    /* Ponteiro (refer�ncia) para primeiro elemento da lista. */
    private Elo prim;

    /* N�mero de elementos na lista. */
    private int n;

    public OrdenacaoTopologica() {
        prim = null;
        n = 0;
    }

    /* Metodo responsavel pela leitura do arquivo de entrada. */
    public void realizaLeituraTXT(String nomeEntrada) throws IOException {
        Path path = Paths.get(nomeEntrada);
        Scanner scan = new Scanner(path);

        while(scan.hasNext()){

            Elo eloX;
            Elo eloY;

            //recuperar x e y de cada linha do txt
            String xPrecedesY = scan.nextLine().replace(" ", "");

            int x =  Integer.parseInt(xPrecedesY.substring(0, xPrecedesY.indexOf("<")));
            int y =  Integer.parseInt(xPrecedesY.substring(xPrecedesY.indexOf("<") + 1));

            eloX = incluirOuRetornarElo(x);
            eloY = incluirOuRetornarElo(y);

            //Atualizar a lista de sucessores de x
            eloX.listaSuc = new EloSuc(eloY, eloX.listaSuc);

            //Atualizar os dados do elemento y
            eloY.contador++;
        }
    }

    public void realizaLeituraObjeto(Grafo G){

        Elo eloX;
        Elo eloY;

        for(int i = 0; i < G.arestas.length; i++){

            int x = G.arestas[i].origem.getChave();
            int y = G.arestas[i].fim.getChave();

            eloX = incluirOuRetornarElo(x);
            eloY = incluirOuRetornarElo(y);

            //Atualizar a lista de sucessores de x
            eloX.listaSuc = new EloSuc(eloY, eloX.listaSuc);

            //Atualizar os dados do elemento y
            eloY.contador++;
        }
    }

    private Elo incluirOuRetornarElo(int a){
        Elo p = buscar(a);

        if(p == null) return incluirNoFinal(a);

        return p;
    }

    private Elo incluirNoFinal(int a){
        Elo p, q;
        q = new Elo(a);

        if(prim != null) {
            for (p = prim; p.prox != null; p = p.prox) ;

            p.prox = q;
            q.prox = null;
        }

        else{
            prim = q;
            prim.prox = null;
        }
        n++;

        return q;
    }

    private void incluirNoFinal(Elo novo) {//inclui elo da lista de sucessores
        if (prim != null) {
            Elo p = prim;
            while (p.prox != null) {
                p = p.prox;
            }
            p.prox = novo;
            novo.prox = null;
        } else {
            prim = novo;
            prim.prox = null;
        }
    }


    //retorna o Elo que contem 'chave a' ou retorna null caso nao encontre
    private Elo buscar(int a) {
        Elo p;
        for (p = prim; p != null; p = p.prox)
            if (p.chave == a) return p;

        return null;
    }

    private void substituicaoDeCadeia(){
        //busca elementos sem predecessores
        Elo p = prim;
        prim = null;

        while(p != null){
            Elo q = p;
            p = q.prox;
            if(q.contador == 0){ //adiciona no inicio
                q.prox = prim;
                prim = q;
            }
        }
    }


    /* Método para debug. */
    private void debug()
    {
        System.out.println("Debug");
        Elo p;
        for(p = prim; p != null; p = p.prox) {
            System.out.print(p.chave + " predecessores: " + p.contador + ", ");
            System.out.print("sucessores: ");
            for(EloSuc s = p.listaSuc; s != null; s = s.prox)
                System.out.print(s.id.chave + " -> ");
            System.out.println("NULL");
        }
    }

    /* Método para executar o algoritmo completo. */
    public boolean executa() {
        debug();
        substituicaoDeCadeia();
        System.out.println("\nOrdenacao topologica");
        gerarSaida();

        return n == 0;
    }

    private void gerarSaida(){
        Elo q;

        for(q = prim; q != null; q = q.prox){
            System.out.print(q.chave + " ");
            n--;

            for(EloSuc t = q.listaSuc; t != null; t = t.prox){
                t.id.contador--;
                if(t.id.contador == 0)
                    incluirNoFinal(t.id);
                q.listaSuc = q.listaSuc.prox; //remove da lista de sucessores
            }

            prim = prim.prox;
        }

        System.out.println();
    }
}