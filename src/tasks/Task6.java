package tasks;

import common.Area;
import common.Person;
import common.Task;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 implements Task {

  private Set<String> getPersonDescriptions(Collection<Person> persons,
      Map<Integer, Set<Integer>> personAreaIds,
      Collection<Area> areas) {

    Set<Map<Integer, Integer>> personAreaRelation = new HashSet<>();
    ArrayList<Integer> personsIds = new ArrayList<>();
    ArrayList<Integer> areaIds = new ArrayList<>();

    //Создаю список связи personID -- areaId.
    for (Map.Entry<Integer, Set<Integer>> entry : personAreaIds.entrySet()) {
      entry.getValue().forEach(e -> personAreaRelation.add(Map.of(entry.getKey(), e)));
    }

    //Пробегаю по списку связей. По каждому personId ищу имя, по areaId - регион
    for (Map<Integer, Integer> it : personAreaRelation) {
      for (Map.Entry<Integer, Integer> entry : it.entrySet()) {
        personsIds.add(entry.getKey());
        areaIds.add(entry.getValue());
      }
    }

    List<String> personsNames = personsIds.stream()
        .map(id -> persons.stream().filter(p -> p.getId().equals(id)).findFirst().get()
            .getFirstName())
        .collect(Collectors.toList());

    List<String> areaNames = areaIds.stream()
        .map(id -> areas.stream().filter(p -> p.getId().equals(id)).findFirst().get().getName())
        .collect(Collectors.toList());

    return Stream.iterate(0, i -> i + 1).limit(personsNames.size())
        .map(i -> String.join(" - ", personsNames.get(i), areaNames.get(i)))
        .collect(Collectors.toSet());
  }

  @Override
  public boolean check() {
    List<Person> persons = List.of(
        new Person(1, "Oleg", Instant.now()),
        new Person(2, "Vasya", Instant.now())
    );
    Map<Integer, Set<Integer>> personAreaIds = Map.of(1, Set.of(1, 2), 2, Set.of(2, 3));
    List<Area> areas = List.of(new Area(1, "Moscow"), new Area(2, "Spb"), new Area(3, "Ivanovo"));
    return getPersonDescriptions(persons, personAreaIds, areas)
        .equals(Set.of("Oleg - Moscow", "Oleg - Spb", "Vasya - Spb", "Vasya - Ivanovo"));
  }
}
