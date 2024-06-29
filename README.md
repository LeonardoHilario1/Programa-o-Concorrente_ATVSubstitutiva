# Programa-o-Concorrente_ATVSubstitutiva

Este projeto visa comparar o desempenho de um algoritmo que coleta e processa dados meteorológicos de várias cidades brasileiras, utilizando diferentes números de threads. O objetivo é entender como o uso de multithreading pode afetar o tempo de execução do algoritmo.

Estrutura do Projeto
O projeto está organizado em três classes principais:

WeatherService
Main
CityData
Classes e Funções

 WeatherService
 
Esta classe é responsável por realizar as requisições à API do OpenWeatherMap e processar os dados recebidos.

API_KEY: Chave da API para acessar os dados meteorológicos.

fazerRequisicao(CityData cidade): Método que faz a requisição HTTP para obter os dados de temperatura de uma cidade específica e retorna um mapa contendo a temperatura mínima, máxima e média.

processarDados(String cidade, List<Double> temperaturas): Método que processa e imprime os dados meteorológicos no console.

 Main
   
Esta classe é responsável por executar o experimento com diferentes números de threads.

main(String[] args): Método principal que define a lista de capitais brasileiras e executa o experimento várias vezes com diferentes configurações de threads.

executarExperimentoVariasVezes(int numThreads, List<CityData> capitais, int repeticoes): Método que executa o experimento várias vezes e calcula o tempo médio de execução.

executarExperimento(int numThreads, List<CityData> capitais): Método que seleciona a versão do algoritmo a ser executada (com ou sem threads).

executarVersaoReferencia(List<CityData> capitais): Método que executa a versão do algoritmo sem threads.

executarVersaoComThreads(int numThreads, List<CityData> capitais): Método que executa a versão do algoritmo utilizando múltiplas threads.

 
 CityData

Esta classe representa os dados de uma cidade, incluindo nome, latitude e longitude.

CityData(String nome, double latitude, double longitude): Construtor que inicializa os dados da cidade.

Como Executar

Obtenha uma chave de API do OpenWeatherMap:

Registre-se em OpenWeatherMap e obtenha uma chave de API.
Substitua a chave de API no código:

No arquivo WeatherService.java, substitua API_KEY pela sua chave de API.
Compile e execute o código:

Compile todas as classes e execute a classe Main.
sh
Copiar código
javac Experimento/*.java
java Experimento.Main

Resultados Esperados

O programa executará o algoritmo com 0, 3, 9 e 27 threads, e imprimirá o tempo médio de execução para cada configuração. Os resultados demonstrarão como o uso de threads pode impactar o desempenho do algoritmo.
