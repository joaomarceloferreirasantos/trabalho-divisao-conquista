import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlgoritmosTest {

    public static void main(String[] args) {
        System.out.println("=== A EXECUTAR BATERIA DE TESTES ===");
        
        
        testCasoMistoNormal();
        testVetorTamanhoUm();
        testApenasValoresNegativos();
        testApenasValoresPositivos();
        testEmpatesMesmaSoma();
        
        
        testComparacaoConjuntoPequeno();
        testPontosAlinhadosVerticalmente();
        testPontosCoincidentes();
        
        System.out.println("\n=== TODOS OS TESTES PASSARAM COM SUCESSO! ===");
    }

    

    static void testCasoMistoNormal() {
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        MaxSubarray.Result result = MaxSubarray.findMaxSubarray(arr, 0, arr.length - 1);
        
        assert result.sum == 6 : "Erro: A soma máxima deve ser 6";
        assert result.low == 3 : "Erro: O índice inicial deve ser 3";
        assert result.high == 6 : "Erro: O índice final deve ser 6";
        System.out.println("[PASSOU] testCasoMistoNormal");
    }

    static void testVetorTamanhoUm() {
        int[] arr = {42};
        MaxSubarray.Result result = MaxSubarray.findMaxSubarray(arr, 0, arr.length - 1);
        
        assert result.sum == 42 : "Erro: Soma deve ser 42";
        assert result.low == 0;
        assert result.high == 0;
        System.out.println("[PASSOU] testVetorTamanhoUm");
    }

    static void testApenasValoresNegativos() {
        int[] arr = {-8, -3, -12, -4, -20};
        MaxSubarray.Result result = MaxSubarray.findMaxSubarray(arr, 0, arr.length - 1);
        
        assert result.sum == -3 : "Erro: A soma máxima deve ser -3";
        assert result.low == 1;
        assert result.high == 1;
        System.out.println("[PASSOU] testApenasValoresNegativos");
    }

    static void testApenasValoresPositivos() {
        int[] arr = {3, 5, 2, 9, 1};
        MaxSubarray.Result result = MaxSubarray.findMaxSubarray(arr, 0, arr.length - 1);
        
        assert result.sum == 20 : "Erro: A soma máxima deve ser 20";
        System.out.println("[PASSOU] testApenasValoresPositivos");
    }

    static void testEmpatesMesmaSoma() {
        int[] arr = {5, -10, 5};
        MaxSubarray.Result result = MaxSubarray.findMaxSubarray(arr, 0, arr.length - 1);
        
        assert result.sum == 5 : "Erro: A soma máxima deve ser 5 em caso de empate";
        System.out.println("[PASSOU] testEmpatesMesmaSoma");
    }

    

    static void testComparacaoConjuntoPequeno() {
        List<ClosestPair.Point> pontos = Arrays.asList(
            new ClosestPair.Point(2, 3),
            new ClosestPair.Point(12, 30),
            new ClosestPair.Point(40, 50),
            new ClosestPair.Point(5, 1),
            new ClosestPair.Point(12, 10),
            new ClosestPair.Point(3, 4)
        );

        List<ClosestPair.Point> pontosSortedX = new ArrayList<>(pontos);
        pontosSortedX.sort((p1, p2) -> Double.compare(p1.x, p2.x));
        double distDivConq = ClosestPair.closestUtil(pontosSortedX, 0, pontosSortedX.size() - 1);

        double epsilon = 1e-5;
        assert Math.abs(distDivConq - Math.sqrt(2)) < epsilon : "Erro na distância do conjunto pequeno";
        System.out.println("[PASSOU] testComparacaoConjuntoPequeno");
    }

    static void testPontosAlinhadosVerticalmente() {
        List<ClosestPair.Point> pontos = Arrays.asList(
            new ClosestPair.Point(5, 10),
            new ClosestPair.Point(5, 2),
            new ClosestPair.Point(5, 15),
            new ClosestPair.Point(5, 3)
        );

        List<ClosestPair.Point> pontosSortedX = new ArrayList<>(pontos);
        pontosSortedX.sort((p1, p2) -> Double.compare(p1.x, p2.x));
        double dist = ClosestPair.closestUtil(pontosSortedX, 0, pontosSortedX.size() - 1);

        assert Math.abs(dist - 1.0) < 1e-5 : "Erro na distância de pontos verticais";
        System.out.println("[PASSOU] testPontosAlinhadosVerticalmente");
    }

    static void testPontosCoincidentes() {
        List<ClosestPair.Point> pontos = Arrays.asList(
            new ClosestPair.Point(10, 10),
            new ClosestPair.Point(20, 20),
            new ClosestPair.Point(10, 10),
            new ClosestPair.Point(30, 30)
        );

        List<ClosestPair.Point> pontosSortedX = new ArrayList<>(pontos);
        pontosSortedX.sort((p1, p2) -> Double.compare(p1.x, p2.x));
        double dist = ClosestPair.closestUtil(pontosSortedX, 0, pontosSortedX.size() - 1);

        assert Math.abs(dist - 0.0) < 1e-5 : "Erro na distância de pontos coincidentes";
        System.out.println("[PASSOU] testPontosCoincidentes");
    }
}