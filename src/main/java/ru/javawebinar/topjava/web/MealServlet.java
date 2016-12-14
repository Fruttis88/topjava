package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealCRUDDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final String LIST_MEAL = "/meals.jsp";
    private static final String CREATE_OR_EDIT = "/editMeal.jsp";
    private static final Logger LOG = getLogger(UserServlet.class);
    private MealDao dao = new MealCRUDDaoImpl();


    private List<MealWithExceed> getListWithExceed() {
        return MealsUtil.getFilteredWithExceeded(dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String forward = "";
        String action = request.getParameter("action");


        if (action != null) {
            switch (action.toLowerCase()) {
                case "create":
                    LOG.debug("Creating new meal");
                    forward = CREATE_OR_EDIT;
                    break;
                case "edit":
                    try {
                        forward = CREATE_OR_EDIT;
                        Meal meal = dao.getById(Integer.parseInt(request.getParameter("mealId")));
                        LOG.debug("Edit meal " + meal);
                        request.setAttribute("meal", meal);
                    } catch (Exception e) {
                        LOG.error("Error when editing meal " + e.toString());
                        response.sendRedirect("meals");
                        return;
                    }
                    break;
                case "delete":
                    LOG.debug("Deleating meal");
                    try {
                        int id = Integer.valueOf(request.getParameter("mealId"));
                        dao.delete(id);
                        LOG.debug("Remove meal with id = " + id);
                    } catch (Exception e) {
                        LOG.error("Error deleating meal " + e.toString());
                    }
                    response.sendRedirect("meals");
                    return;
            }
        } else {
            request.setAttribute("meals", getListWithExceed());
            request.getRequestDispatcher(LIST_MEAL).forward(request, response);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("time"));
            String description = request.getParameter("description");
            int calories = Integer.valueOf(request.getParameter("calories"));

            Meal meal = new Meal(localDateTime, description, calories);

            Integer id = Integer.parseInt(request.getParameter("id") == null || request.getParameter("id").isEmpty() ? "-1" : request.getParameter("id"));

            if (id == -1) {
                dao.createOrEdit(meal);
            } else {
                meal.setId(Integer.valueOf(id));
                dao.createOrEdit(meal);
            }
        } catch (Exception e) {
            LOG.error(e.toString());
        }

        response.sendRedirect("meals");
    }
}
