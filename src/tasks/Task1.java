package tasks;

import common.Person;
import common.PersonService;
import common.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */

/*
Асимптотика:
метод List.sort использует внутри себя алгоритм Timsort
https://docs.oracle.com/en/java/javase/13/docs/api/java.base/java/util/List.html#sort(java.util.Comparator)
The implementation was adapted from Tim Peters's list sort for Python ( TimSort).
Судя по описанию данного алгоритма (https://en.wikipedia.org/wiki/Timsort), в среднем результат O(n log n)
*/

public class Task1 implements Task {
  // !!! Редактируйте этот метод !!!
  private List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = PersonService.findPersons(personIds);
    List<Person> personsList = new ArrayList<>(persons);
    personsList.sort(Comparator.comparing(v -> personIds.indexOf(v.getId())));

    return personsList;
  }

  @Override
  public boolean check() {
    List<Integer> ids = List.of(1, 2, 3);

    return findOrderedPersons(ids).stream()
        .map(Person::getId)
        .collect(Collectors.toList())
        .equals(ids);
  }
}
