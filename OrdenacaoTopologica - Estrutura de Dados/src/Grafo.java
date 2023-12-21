import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Grafo {

    public class Vertice{
        private int chave;

        private boolean visitado;

        private ListaDeVertices listaDeVerticesSucessores;


        private int numA; // numero de arestas que participa

        private Vertice(int chave){
            this.chave = chave;
            listaDeVerticesSucessores = null;
            numA = 0;
        }

        @Override
        public String toString() {
            return Integer.toString(this.chave);
        }

        public int getChave() { return chave; }
    }

    public class Aresta{
        public Vertice origem;
        public Vertice fim;

        private Aresta(Vertice origem, Vertice fim){
            this.origem = origem;
            this.fim = fim;
        }

        @Override
        public String toString() {
            return origem + " < " + fim;
        }
    }

    private class ListaDeVertices{
        Vertice vertice;
        ListaDeVertices prox;
        private ListaDeVertices(Vertice vertice, ListaDeVertices prox){
            this.vertice = vertice;
            this.prox = prox;
        }
    }

    private Vertice[] vertices;
    public Aresta[] arestas;
    private int contadorArestas;

    public int getVerticesLength() {
        return vertices.length;
    }

    // Ao chamar o construtor, seus vertices serão criados e colocados num vetor de vertices
    // Após isso será chamada a funcão gerarGrafoAleatorio() que criará arestas com estes
    // vertices. Para finalizar essa função pode ou não criar um txt do grafo gerado
    public Grafo(int numVertices, int numArestas, boolean criarTXT) throws IOException {
        vertices = new Vertice[numVertices];
        arestas = new Aresta[numArestas];
        contadorArestas = 0;

        for(int i = 0; i < numVertices; i++)
            vertices[i] = new Vertice(i+1);
            // cria vertices inicialmente isolados, nao formam arestas

        gerarGrafoAleatorio();


        if(criarTXT)
            criarTXT();
    }

    // cria uma aresta e a coloca no vetor de arestas se possivel
    // caso os vertices recebidos sejam iguais: nao forma aresta
    // caso haja aresta igual ou paralela no vetor de arestas: nao forma aresta
    // caso ocorra um ciclo ao formar essa aresta: nao forma aresta
    // se nenhuma dessas situacoes ocorrer: uma nova aresta é construida e inserida
    // no vetor de arestas, os contadores numA de cada vertice serão incrementados
    // e a lista de sucessores de v1 receberá v2
    private void gerarAresta(Vertice v1, Vertice v2){
        if(v1.chave == v2.chave) return;

        boolean jaExisteArestaIgualOuParalela = false;
        for(int i = 0; i < arestas.length; i++)
            if(arestas[i] != null && ((arestas[i].origem == v1 && arestas[i].fim == v2)
                || (arestas[i].origem == v2 && arestas[i].fim == v1)))
                jaExisteArestaIgualOuParalela = true;

        if(!vaiFormarCiclo(v1, v2) && !jaExisteArestaIgualOuParalela){
            arestas[contadorArestas++] = new Aresta(v1, v2);
            v1.numA++;
            v2.numA++;
            colocarNaListaDeSucessores(v1, v2);
            return;
        }
        else return;
    }

    // realiza uma busca a partir do vertice v2 ate encontrar ou nao o vertice v1
    // para isso, visita o vertice v2 e seus sucessores e realiza uma chamada recursiva
    // para cada vertice da lista de sucessores de v2.
    private boolean vaiFormarCiclo(Vertice v1, Vertice v2) {
        if (v1.chave == v2.chave) return true;
        if (v2.listaDeVerticesSucessores == null) return false;

        for (ListaDeVertices w = v2.listaDeVerticesSucessores; w != null; w = w.prox) {
            if (!w.vertice.visitado) {
                w.vertice.visitado = true;
                if (w.vertice.chave == v1.chave) {
                    // Reinicia a marcação de visitados antes de retornar
                    reiniciarMarcacaoDeVisitados();
                    return true;
                }
                if (vaiFormarCiclo(v1, w.vertice)) {
                    // Reinicia a marcação de visitados antes de retornar
                    reiniciarMarcacaoDeVisitados();
                    return true;
                }
            }
        }

        // Reinicia a marcação de visitados antes de retornar
        reiniciarMarcacaoDeVisitados();
        return false;
    }

    private void reiniciarMarcacaoDeVisitados() {
        for (Vertice vertice : vertices) {
            if (vertice != null) {
                vertice.visitado = false;
            }
        }
    }

    // primeiro cria todas as arestas que cabem no vetor de arestas, isso eh feito conectando
    // vertices aleatorios, mas ao realizar isso pode ocorrer a presenca de vertices isolados,
    // ou seja, que não formam aresta com ninguem; então, na segunda etapa, os vertices isolados
    // sao localizados por seu numA igual a zero e colocados numa lista e, em seguida, substitui
    // as arestas ja formadas por arestas novas com vertices isolados.
    public void gerarGrafoAleatorio(){
        Random random = new Random();

        //primeira etapa
        for(; contadorArestas != arestas.length;)
            gerarAresta(vertices[random.nextInt(vertices.length)], vertices[random.nextInt(vertices.length)]);


        //segunda etapa
        ListaDeVertices verticesIsolados = null;
        for(int i = 0; i < vertices.length; i++){
            if(vertices[i].numA == 0)
                verticesIsolados = new ListaDeVertices(vertices[i], verticesIsolados);
        }
        if(verticesIsolados != null) {
            for(int i = 0; verticesIsolados != null; i++){
                if(i == vertices.length) {
                    i = 0;
                }

                if(arestas[i].fim.numA > 1 && arestas[i].origem.numA > 1) {

                    arestas[i].origem.numA--;
                    arestas[i].fim.numA--;

                    arestas[i] = new Aresta(verticesIsolados.vertice, arestas[i].fim);
                    verticesIsolados.vertice.numA++;
                    arestas[i].fim.numA++;

                    verticesIsolados = verticesIsolados.prox;


                }
            }
        }
    }

    public void criarTXT() throws IOException {
        PrintWriter writer = new PrintWriter("grafo" + vertices.length + ".txt", StandardCharsets.UTF_8);

        for(int i = 0; i < arestas.length; i++)
            writer.println(arestas[i]);

        writer.close();
    }

    @Override
    public String toString() {
        String str = "";
        for(int i = 0; i < arestas.length; i++){
            str += arestas[i] + "\n";
        }
        return str;
    }

    private void colocarNaListaDeSucessores(Vertice v1, Vertice v2){
        v1.listaDeVerticesSucessores = new ListaDeVertices(v2, v1.listaDeVerticesSucessores);
    }

}
