package tasks;

import common.Person;
import common.Task;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

  private long count;

  //Добавил skip(1) в стрим + убрал лишние проверки - если список на входе пуст, то и так вернется пустота
  public List<String> getNames(List<Person> persons) {
    return persons.stream().skip(1).map(Person::getFirstName).collect(Collectors.toList());
  }

  //меняем на инициализацию через конструктор хэшсета
  //Изменил название метода - getDifferentNames не очень говорящее.
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  //Упростил через String join + поправил багулину с отчеством - возвращалась фамилия
  public String convertPersonToString(Person person) {
    return Stream.of(person.getSecondName(), person.getFirstName(), person.getMiddleName())
        .collect(Collectors.joining(" "));
  }

  //упростил через стримы
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {

    Map<Integer, String> map = new HashMap<>();
    persons.forEach(person -> map.putIfAbsent(person.getId(), convertPersonToString(person)));
    return map;
    //return persons.stream().collect(Collectors.toMap(Person::getId, this::convertPersonToString));
  }

  //изменил итерацию по элементам на более наглядную
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    return persons2.stream().anyMatch(persons1::contains);
  }

  //без лишних перемнных и инкрементов возвращается сразу же результат
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = true;
    boolean reviewerDrunk = false;
    return codeSmellsGood || reviewerDrunk;
  }
}
