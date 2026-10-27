import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Benchmark {

    private static final int REPETICOES = 5;
    private static final Random random = new Random();

    public static void main(String[] args) {
        String csvFileName = "resultados_benchmark.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFileName))) {
            
            writer.println("Problema,Tamanho_N,Execucao,Algoritmo,Tempo_ms");

            System.out.println("=== Início do Benchmark ===");

            
            System.out.println("\n[Etapa 1] Testando Subarranjo de Soma Máxima...");
            int[] tamanhosEtapa1 = {100, 200, 400, 800, 1600, 3200, 6400, 12800};

            for (int n : tamanhosEtapa1) {
                System.out.printf("Testando N = %d...\n", n);
                for (int exec = 1; exec <= REPETICOES; exec++) {
                    int[] arr = gerarVetorAleatorio(n);

                    
                    long inicioFB = System.nanoTime();
                    maxSubarrayBruteForce(arr);
                    long fimFB = System.nanoTime();
                    double tempoFB = (fimFB - inicioFB) / 1e6; // Milissegundos
                    writer.printf("SomaMaxima,%d,%d,ForcaBruta,%.4f\n", n, exec, tempoFB);

                    
                    long inicioDC = System.nanoTime();
                    MaxSubarray.findMaxSubarray(arr, 0, arr.length - 1);
                    long fimDC = System.nanoTime();
                    double tempoDC = (fimDC - inicioDC) / 1e6;
                    writer.printf("SomaMaxima,%d,%d,DivisaoConquista,%.4f\n", n, exec, tempoDC);

                    // 3. O(n) Kadane (Questão Desafio)[cite: 3]
                    long inicioKadane = System.nanoTime();
                    maxSubarrayKadane(arr);
                    long fimKadane = System.nanoTime();
                    double tempoKadane = (fimKadane - inicioKadane) / 1e6;
                    writer.printf("SomaMaxima,%d,%d,Kadane_On,%.4f\n", n, exec, tempoKadane);
                }
            }

            
            System.out.println("\n[Etapa 2] Testando Par de Pontos Mais Próximos...");
            
            int[] tamanhosEtapa2 = {100, 200, 400, 800, 1600, 3200, 6400};

            for (int n : tamanhosEtapa2) {
                System.out.printf("Testando N = %d...\n", n);
                for (int exec = 1; exec <= REPETICOES; exec++) {
                    List<ClosestPair.Point> pontos = gerarPontosAleatorios(n);

                    
                    long inicioFB = System.nanoTime();
                    ClosestPair.bruteForce(pontos, 0, pontos.size() - 1);
                    long fimFB = System.nanoTime();
                    double tempoFB = (fimFB - inicioFB) / 1e6;
                    writer.printf("PontosProximos,%d,%d,ForcaBruta,%.4f\n", n, exec, tempoFB);

                    
                    long inicioDC = System.nanoTime();
                    
                    List<ClosestPair.Point> pontosSortedX = new ArrayList<>(pontos);
                    pontosSortedX.sort(Comparator.comparingDouble(p -> p.x));
                    ClosestPair.closestUtil(pontosSortedX, 0, pontosSortedX.size() - 1);
                    long fimDC = System.nanoTime();
                    double tempoDC = (fimDC - inicioDC) / 1e6;
                    writer.printf("PontosProximos,%d,%d,DivisaoConquista,%.4f\n", n, exec, tempoDC);
                }
            }

            System.out.println("\n=== Benchmark Concluído com Sucesso! ===");
            System.out.println("Resultados guardados no ficheiro: " + csvFileName);

        } catch (IOException e) {
            System.err.println("Erro ao salvar ficheiro CSV: " + e.getMessage());
        }
    }

   

    private static int[] gerarVetorAleatorio(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(2001) - 1000; 
        }
        return arr;
    }

    private static List<ClosestPair.Point> gerarPontosAleatorios(int n) {
        List<ClosestPair.Point> pontos = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            double x = random.nextDouble() * 10000.0;
            double y = random.nextDouble() * 10000.0;
            pontos.add(new ClosestPair.Point(x, y));
        }
        return pontos;
    }

    
    private static void maxSubarrayBruteForce(int[] arr) {
        long maxSum = Long.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            long currentSum = 0;
            for (int j = i; j < arr.length; j++) {
                currentSum += arr[j];
                if (currentSum > maxSum) {
                    maxSum = currentSum;
                }
            }
        }
    }

   
    private static void maxSubarrayKadane(int[] arr) {
        long maxSoFar = arr[0];
        long currentMax = arr[0];
        for (int i = 1; i < arr.length; i++) {
            currentMax = Math.max((long) arr[i], currentMax + arr[i]);
            maxSoFar = Math.max(maxSoFar, currentMax);
        }
    }
}