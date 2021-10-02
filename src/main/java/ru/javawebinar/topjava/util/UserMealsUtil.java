package ru.javawebinar.topjava.util;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles

        // группируем элементы по датам
        Map<LocalDate, List<UserMeal>> map = new LinkedHashMap<>();
        for (UserMeal userMeal : meals) {

            LocalDate key = userMeal.getDateTime().toLocalDate();
            List<UserMeal> value = map.getOrDefault(key, new ArrayList<>());
            value.add(userMeal);
            map.putIfAbsent(key, value);
        }


        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        // подсчет превышения в рамках одной даты
        for (LocalDate localDate : map.keySet()) {
            int sum = 0;

            for (UserMeal userMeal : map.get(localDate)) {
                sum += userMeal.getCalories();
            }
            boolean excess = sum > caloriesPerDay;

            // фильтруем и содаем финальную сущность с признаком превышения
            for (UserMeal userMeal : map.get(localDate)) {
                if (! TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) continue;
                userMealWithExcessList.add(new UserMealWithExcess(userMeal, excess));
            }
        }

        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams

        // группировка элементов по дате
        Map<LocalDate, List<UserMeal>> map = meals.stream().collect(Collectors.groupingBy(a -> a.getDateTime().toLocalDate()));

        // сумма значений в рамках одной даты
        Map<LocalDate, Integer> mapCalories = map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, v -> (Integer) v.getValue().stream().mapToInt(UserMeal::getCalories).sum()));

        // превышеение для каждой даты
        Map<LocalDate, Boolean> mapExcess = mapCalories.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue() > caloriesPerDay));

        List<UserMealWithExcess> result = meals.stream()
                .filter(v -> TimeUtil.isBetweenHalfOpen(v.getDateTime().toLocalTime(), startTime, endTime))
                .map(v -> new UserMealWithExcess(v, mapExcess.get(v.getDateTime().toLocalDate())))
                .collect(Collectors.toList());

        return result;
    }
}
