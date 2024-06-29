package Experimento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        List<CityData> capitais = Arrays.asList(
            new CityData("Aracaju", -10.9167, -37.05),
            new CityData("Belém", -1.4558, -48.5039),
            new CityData("Belo Horizonte", -19.9167, -43.9333),
            new CityData("Boa Vista", 2.8197, -60.6733),
            new CityData("Brasília", -15.7939, -47.8828),
            new CityData("Campo Grande", -20.4428, -54.6464),
            new CityData("Cuiabá", -15.5989, -56.0949),
            new CityData("Curitiba", -25.4297, -49.2711),
            new CityData("Florianópolis", -27.5935, -48.5585),
            new CityData("Fortaleza", -3.7275, -38.5275),
            new CityData("Goiânia", -16.6667, -49.25),
            new CityData("João Pessoa", -7.12, -34.88),
            new CityData("Macapá", 0.033, -51.05),
            new CityData("Maceió", -9.6658, -35.7353),
            new CityData("Manaus", -3.1189, -60.0217),
            new CityData("Natal", -5.7833, -35.2),
            new CityData("Palmas", -10.1674, -48.3277),
            new CityData("Porto Alegre", -30.0331, -51.23),
            new CityData("Porto Velho", -8.7619, -63.9039),
            new CityData("Recife", -8.05, -34.9),
            new CityData("Rio Branco", -9.9747, -67.81),
            new CityData("Rio de Janeiro", -22.9111, -43.2056),
            new CityData("Salvador", -12.9747, -38.4767),
            new CityData("São Luís", -2.5283, -44.3044),
            new CityData("São Paulo", -23.55, -46.6333),
            new CityData("Teresina", -5.0892, -42.8019),
            new CityData("Vitória", -20.2889, -40.3083)
        );

        executarExperimentoVariasVezes(0, capitais, 10);  
        executarExperimentoVariasVezes(3, capitais, 10);  
        executarExperimentoVariasVezes(9, capitais, 10); 
        executarExperimentoVariasVezes(27, capitais, 10); 
    }

    public static void executarExperimentoVariasVezes(int numThreads, List<CityData> capitais, int repeticoes) {
        long somaTempos = 0;
        for (int i = 0; i < repeticoes; i++) {
            long tempoExecucao = executarExperimento(numThreads, capitais);
            somaTempos += tempoExecucao;
        }
        long tempoMedio = somaTempos / repeticoes;
        System.out.println("Tempo médio de execução para " + numThreads + " threads: " + tempoMedio + " ms");
    }

    public static long executarExperimento(int numThreads, List<CityData> capitais) {
        if (numThreads == 0) {
            return executarVersaoReferencia(capitais);
        } else {
            return executarVersaoComThreads(numThreads, capitais);
        }
    }

    public static long executarVersaoReferencia(List<CityData> capitais) {
        long tempoInicio = System.currentTimeMillis();
        for (CityData cidade : capitais) {
            Map<String, List<Double>> dados = WeatherService.fazerRequisicao(cidade);
            dados.forEach((nome, temperaturas) -> WeatherService.processarDados(nome, temperaturas));
        }
        long tempoFim = System.currentTimeMillis();
        long tempoExecucao = tempoFim - tempoInicio;
        System.out.println("Tempo de execução: " + tempoExecucao + " ms");
        return tempoExecucao;
    }

    public static long executarVersaoComThreads(int numThreads, List<CityData> capitais) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        try {
            long tempoInicio = System.currentTimeMillis();
            List<CompletableFuture<Void>> futures = new ArrayList<>();
            for (CityData cidade : capitais) {
                CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> WeatherService.fazerRequisicao(cidade), executor)
                        .thenAccept(dados -> dados.forEach((nome, temperaturas) -> WeatherService.processarDados(nome, temperaturas)));
                futures.add(future);
            }

            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allOf.get();  
            long tempoFim = System.currentTimeMillis();
            long tempoExecucao = tempoFim - tempoInicio;
            System.out.println("Tempo de execução para a rodada: " + tempoExecucao + " ms");
            return tempoExecucao;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1;
        } finally {
            executor.shutdown();
        }
    }
}