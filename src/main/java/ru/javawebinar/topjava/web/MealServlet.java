package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.api.MealsApi;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealsApiJavaImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Meal> meals = mealsApi.getMeals();
        List<MealTo> mealsTo = MealsUtil.convertMealTo(meals, MealsUtil.CALORIES_PER_DAY);
        req.setAttribute("meals", mealsTo);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    MealsApi mealsApi = new MealsApiJavaImpl();
}
