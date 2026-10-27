import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlgoritmosTest {

   
    @Nested
    @DisplayName("Testes da Etapa 1 - Subarranjo de Soma Máxima")
    class SubarranjoSomaMaximaTest {

        @Test
        @DisplayName("Caso Normal: Vetor com mistura de positivos e negativos")
        void testCasoMistoNormal() {
            int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
           
            MaxSubarray.Result result = MaxSubarray.findMaxSubarray(arr, 0, arr.length - 1);

            assertEquals(6, result.sum, "A soma máxima deve ser 6");
            assertEquals(3, result.low, "O índice inicial deve ser 3");
            assertEquals(6, result.high, "O índice final deve ser 6");
        }

        @Test
        @DisplayName("Caso de Borda: Vetor de tamanho 1")
        void testVetorTamanhoUm() {
            int[] arr = {42};
            MaxSubarray.Result result = MaxSubarray.findMaxSubarray(arr, 0, arr.length - 1);

            assertEquals(42, result.sum, "A soma máxima num vetor de tamanho 1 é o próprio elemento");
            assertEquals(0, result.low, "O índice inicial deve ser 0");
            assertEquals(0, result.high, "O índice final deve ser 0");
        }

        @Test
        @DisplayName("Caso de Borda: Vetor contendo apenas valores negativos")
        void testApenasValoresNegativos() {
            int[] arr = {-8, -3, -12, -4, -20};
            MaxSubarray.Result result = MaxSubarray.findMaxSubarray(arr, 0, arr.length - 1);

            assertEquals(-3, result.sum, "Num vetor apenas com negativos, a soma máxima é o elemento menos negativo (-3)");
            assertEquals(1, result.low, "O índice inicial deve corresponder ao elemento -3");
            assertEquals(1, result.high, "O índice final deve corresponder ao elemento -3");
        }

        @Test
        @DisplayName("Caso de Borda: Vetor composto apenas por valores positivos")
        void testApenasValoresPositivos() {
            int[] arr = {3, 5, 2, 9, 1};
            MaxSubarray.Result result = MaxSubarray.findMaxSubarray(arr, 0, arr.length - 1);

            assertEquals(20, result.sum, "Num vetor só de positivos, a soma máxima é a soma do vetor inteiro");
            assertEquals(0, result.low);
            assertEquals(4, result.high);
        }

        @Test
        @DisplayName("Caso de Borda: Empates entre intervalos com a mesma soma")
        void testEmpatesMesmaSoma() {
            int[] arr = {5, -10, 5};
            MaxSubarray.Result result = MaxSubarray.findMaxSubarray(arr, 0, arr.length - 1);

            // Tanto o subarranjo [0..0] (valor 5) quanto o [2..2] (valor 5) dão soma 5
            assertEquals(5, result.sum, "O algoritmo deve identificar corretamente a soma máxima de 5 em caso de empate");
            assertTrue(
                (result.low == 0 && result.high == 0) || (result.low == 2 && result.high == 2) || (result.low == 0 && result.high == 2),
                "O intervalo retornado deve corresponder a um dos subarranjos válidos com soma máxima 5"
            );
        }
    }

    
    @Nested
    @DisplayName("Testes da Etapa 2 - Par de Pontos Mais Próximos")
    class ParPontosMaisProximosTest {

        private static final double EPSILON = 1e-5;

        @Test
        @DisplayName("Caso Base: Força Bruta vs Divisão e Conquista num conjunto pequeno")
        void testComparacaoConjuntoPequeno() {
            List<ClosestPair.Point> pontos = Arrays.asList(
                new ClosestPair.Point(2, 3),
                new ClosestPair.Point(12, 30),
                new ClosestPair.Point(40, 50),
                new ClosestPair.Point(5, 1),
                new ClosestPair.Point(12, 10),
                new ClosestPair.Point(3, 4)
            );

          
            double distForcaBruta = ClosestPair.bruteForce(pontos, 0, pontos.size() - 1);

            List<ClosestPair.Point> pontosSortedX = new ArrayList<>(pontos);
            pontosSortedX.sort((p1, p2) -> Double.compare(p1.x, p2.x));
            double distDivConq = ClosestPair.closestUtil(pontosSortedX, 0, pontosSortedX.size() - 1);

            assertEquals(distForcaBruta, distDivConq, EPSILON, "Força Bruta e Divisão e Conquista devem dar o mesmo resultado");
            assertEquals(Math.sqrt(2), distDivConq, EPSILON);
        }

        @Test
        @DisplayName("Caso de Borda: Pontos alinhados verticalmente")
        void testPontosAlinhadosVerticalmente() {
            List<ClosestPair.Point> pontos = Arrays.asList(
                new ClosestPair.Point(5, 10),
                new ClosestPair.Point(5, 2),
                new ClosestPair.Point(5, 15),
                new ClosestPair.Point(5, 3)
            );

            
            List<ClosestPair.Point> pontosSortedX = new ArrayList<>(pontos);
            pontosSortedX.sort((p1, p2) -> Double.compare(p1.x, p2.x));
            double dist = ClosestPair.closestUtil(pontosSortedX, 0, pontosSortedX.size() - 1);

            assertEquals(1.0, dist, EPSILON, "A menor distância vertical deve ser 1.0");
        }

        @Test
        @DisplayName("Caso de Borda: Pontos coincidentes (distância zero)")
        void testPontosCoincidentes() {
            List<ClosestPair.Point> pontos = Arrays.asList(
                new ClosestPair.Point(10, 10),
                new ClosestPair.Point(20, 20),
                new ClosestPair.Point(10, 10), // Ponto duplicado
                new ClosestPair.Point(30, 30)
            );

            List<ClosestPair.Point> pontosSortedX = new ArrayList<>(pontos);
            pontosSortedX.sort((p1, p2) -> Double.compare(p1.x, p2.x));
            double dist = ClosestPair.closestUtil(pontosSortedX, 0, pontosSortedX.size() - 1);

            assertEquals(0.0, dist, EPSILON, "A distância entre pontos coincidentes deve ser 0.0");
        }
    }
}