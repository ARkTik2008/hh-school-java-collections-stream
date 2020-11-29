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
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    Set<String> result = new HashSet<>();
    Set<List<Integer>> personAreaRelation = new HashSet<>();

    //Создаю список связи personID -- areaId.
    for (Map.Entry<Integer, Set<Integer>> entry : personAreaIds.entrySet()) {
      entry.getValue().forEach(e -> personAreaRelation.add(List.of(entry.getKey(), e)));
    }

    //Пробегаю по списку связей. По каждому personId ищу имя, по areaId - регион
    for (List<Integer> it : personAreaRelation) {
      var personId = it.get(0);
      var areaId = it.get(1);

      String name = persons.stream()
          .filter(p -> p.getId() == personId)
          .map(Person::getFirstName)
          .findFirst().get();
      String region = areas.stream()
          .filter(p -> p.getId() == areaId)
          .map(Area::getName).findFirst()
          .get();

      result.add(name + " - " + region);
    }
    return result;
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
