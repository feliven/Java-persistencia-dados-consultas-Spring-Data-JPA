package br.com.alura.screenmatch.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Ator;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.AtorRepository;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=4b35c2a4";

    private SerieRepository serieRepository;
    private AtorRepository atorRepository;
    private List<Serie> series;

    public Principal(SerieRepository repository, AtorRepository atorRepository) {
        this.serieRepository = repository;
        this.atorRepository = atorRepository;
    }

    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 0) {

            var menu = """
                    1 - Série - baixar dados da API
                    2 - Episódios - baixar dados da API
                    3 - Listar séries salvas
                    4 - Buscar série por título
                    5 - Buscar série por ator

                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void listarSeriesBuscadas() {

        // List<Serie> series = new ArrayList<>();
        // series = dadosSeries.stream().map(d -> new
        // Serie(d)).collect(Collectors.toList());

        series = serieRepository.findAll();

        series.forEach(s -> System.out.println(s.getTitulo()));
    }

    private void buscarSerieWeb() {
        DadosSerie dadosSerie = getDadosSerie();

        List<Ator> atores = List.of(dadosSerie.atores().split(", "))
                .stream()
                .map(nome -> atorRepository.findByNomeIgnoreCase(nome)
                        .orElseGet(() -> atorRepository.save(new Ator(nome))))
                .toList();

        Serie serie = new Serie(dadosSerie, atores);

        // dadosSeries.add(dadosSerie);

        try {
            serieRepository.save(serie);
            System.out.println(dadosSerie);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Já existe uma série com esse nome.");
            return;
        }
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = scanner.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
        listarSeriesBuscadas();

        System.out.println("Escolha a série pelo nome:");
        var nomeSerie = scanner.nextLine();
        var series = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if (series.size() > 0) {

            var serieEncontrada = series.getFirst();

            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo
                        .obterDados(
                                ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                            .map(e -> new Episodio(t.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);

            try {
                serieRepository.save(serieEncontrada);
            } catch (DataIntegrityViolationException e) {
                System.out.println("Já existe um episódio com esse título.");
                return;
            }

        } else {
            System.out.println("Série não foi encontrada");
        }

    }

    private void buscarSeriePorTitulo() {

        System.out.println("Escolha a série pelo nome:");
        var nomeSerie = scanner.nextLine();

        var seriesBuscadas = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if (seriesBuscadas.size() > 0) {
            System.out.println("Dados da(s) série(s): " + System.lineSeparator());
            seriesBuscadas.forEach(s -> System.out
                    .println(s.getTitulo() + ", avaliação=" + s.getAvaliacao() + ", sinopse=" + s.getSinopse()));
        } else {
            System.out.println("Série não foi encontrada");
        }
    }

    private void buscarSeriePorAtor() {

        System.out.println("Digite o nome do ator:");
        var nomeAtor = scanner.nextLine();

        var seriesEncontradas = serieRepository.findByAtoresNomeContainingIgnoreCase(nomeAtor);

        if (seriesEncontradas.size() > 0) {
            System.out.println("Dados da(s) série(s): " + System.lineSeparator());
            seriesEncontradas.forEach(s -> System.out
                    .println(s.getTitulo() + ", atores=" + s.getAtores() + ", sinopse=" + s.getSinopse()));
        } else {
            System.out.println("Nenhuma série foi encontrada com esse ator");
        }
    };
}