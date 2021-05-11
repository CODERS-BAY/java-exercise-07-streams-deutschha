package app;

import games.Game;
import games.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Games {

    private static final Path CSV = Paths.get("Games/games.csv");
    private static final String BUNDESLIGA = "BUNDESLIGA";
    private static final String BAYERN = "FC Bayern Muenchen";

    public static void main(String[] args) throws IOException {

        List<Game> games = null;
        try (Stream<String> lines = Files.lines(CSV)) {
            games = lines.skip(1).map(Game::fromString).collect(toList());
        } catch (Exception e) {
            throw new NoSuchElementException("No CSV file found");
        }

        games.forEach(System.out::println);
        System.out.println();

        // -------------------

        // TODO: Wie viele Spiele sind Bundesliga Spiele? Bundesliga ist ein Enum, kein String. (contain BUNDESLIGA)?
        // (Lösung mit filter)

        long bundesligaGameCount = -1;
        
        long amount = games.stream().filter(b-> b.getInfo().contains(BUNDESLIGA)).count();
        
        bundesligaGameCount = amount;
        
        System.out.println("There were " + bundesligaGameCount + " Bundesliga games");
        System.out.println();

        // -------------------

        // TODO: Welche Spiele sind Auswärts- und welche Heimspiele?
        // (Lösung mit partitionBy)

        Map<Boolean, List<Game>> homeAwayMap = null;
        
        Map<Boolean, List<Game>> homeAwayMap1 = games.stream().collect(Collectors.partitioningBy(p->p.getHome().equals(BAYERN)));

        System.out.println("*** HOME ***");
        homeAwayMap1.get(true).forEach(System.out::println);
        System.out.println("*** AWAY ***");
        homeAwayMap1.get(false).forEach(System.out::println);
        System.out.println();

        // -------------------

        // TODO Gruppiere die Spiele in won, lost und draw (draw = Unentschieden)
        // (Lösung mit groupingBy)

        Map<Result, List<Game>> wonLostDrawMap = null;
        
       // Map<Result, List<Game>> wonLostDrawMap = games.stream().collect(Collectors.groupingBy(game-> {
        //	if(game.getHomeGoeals()>game.getAwayGoals
       // }))
        
      //  System.out.println("*** WON ***");
      //  wonLostDrawMap.get(Result.WON).forEach(System.out::println);
      //  System.out.println("*** DRAW ***");
      //  wonLostDrawMap.get(Result.DRAW).forEach(System.out::println);
      //  System.out.println("*** LOST ***");
      //  wonLostDrawMap.get(Result.LOST).forEach(System.out::println);
      //  System.out.println();
		
        // -------------------

        // TODO Wie viele Tore wurden im Durchschnitt pro Spiel erzielt? mapToInt
        // (Lösung mit mapToInt)
        double avgGoalsPerGame1 = 0.0;
        
        avgGoalsPerGame1 = games.stream().mapToInt(Game::goalCount).average().orElse(0.0);
        
        
        
        System.out.printf("Average goals per game: %.2f\n", avgGoalsPerGame1);

        // TODO Wie viele Tore wurden im Durchschnitt pro Spiel erzielt? averagingDouble
        // (Lösung mit withCollectors.averagingDouble)
        
        double avgGoalsPerGame2 = 0.0;

        avgGoalsPerGame2 = games.stream().collect(Collectors.averagingDouble(Game::goalCount));
        
        System.out.printf("Average goals per game: %.2f\n", avgGoalsPerGame2);
        System.out.println();

        // -------------------

        // TODO Wie viele Spiele hat Bayern München zu Hause gewonnen?
        // (home equals BAYERN)?
        // (Lösung mit double filter und count)
        long wonHomeGamesCount = -1;
        
        wonHomeGamesCount = games.stream().filter(h->h.getHome().equals(BAYERN)).filter(game->game.getHomeGoals()> game.getAwayGoals()).count();

        System.out.println(BAYERN + " won " + wonHomeGamesCount + " games at home");
        System.out.println();

        // -------------------

        // TODO Was war das Spiel mit den wenigsten Toren? sorted findFirst
        // (Lösung mit sorted und findFirst)
        Game leastNumberOfGoalsGame1 = null;
        
        leastNumberOfGoalsGame1 = games.stream().sorted(Comparator.comparing(Game::goalCount)).findFirst().get();

        System.out.println("Game with least number of goals: " + leastNumberOfGoalsGame1);

        // TODO Was war das Spiel mit den wenigsten Toren? min Comparator.comparingInt
        // (Lösung mit min und Comparator.comparingInt)
        Game leastNumberOfGoalsGame2 = null;

        leastNumberOfGoalsGame2 = games.stream().min(Comparator.comparing(Game::goalCount)).get();
        
        System.out.println("Game with least number of goals: " + leastNumberOfGoalsGame2);
        System.out.println();

        // -------------------

        // TODO Welche unterschiedlichen (distinct) Startzeiten gibt es?
        // (Lösung mit einem stream und Collectors.joining)
        String startingTimesString = null;

        //startingTimesString = games.stream().map(Game::getTime).distinct().collect(Collectors.joining("| ");
        
        System.out.println("Distinct starting times: " + startingTimesString);
        System.out.println();

        // -------------------

        // TODO hat Bayern ein Auswärtsspiel mit mindestens 2 Toren Unterschied gewonnen?
        // (home equals BAYERN)?
        // (Lösung mit anyMatch)

        boolean bayernWon = false;
        
        //bayernWon = games.stream()

        System.out.println("Bayern won away game with at least 2 goals difference: " + (bayernWon ? "yes" : "no"));
        System.out.println();

        // -------------------

        // TODO Ein Freund von dir gab dir die Spiele von 2019, die jedoch nach der Heimmannschaft gruppiert wurden. Du möchtest aber alle Spiele als einfache Liste abrufen!
        // (Lösung with flatMap und Collectors.toList)
        Map<String, List<Game>> games2019ByHomeTeam = games.stream()
                .filter(game -> game.getDate().contains("2019"))
                .collect(Collectors.groupingBy(Game::getHome));
        List<Game> flattenedGames = null;

        List<Game> flattenedGames1 = games2019ByHomeTeam
        							.values()
        							.stream()
        							.flatMap(Collection::stream).collect(toList());
        
        flattenedGames1.forEach(System.out::println);
    }

	private static double getAsDouble(OptionalDouble aver) {
		// TODO Auto-generated method stub
		return 0;
	}
}
