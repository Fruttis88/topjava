package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * gkislin
 * 02.10.2016
 */
@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Override
    @Transactional
    Meal save (Meal meal);

    @Modifying
    @Transactional
    @Query(name = Meal.DELETE)
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT m FROM Meal m WHERE m.id = :id AND m.user.id = :userId")
    Meal findOne(@Param("id") int id, @Param("userId") int userId);

    @Query(name = Meal.ALL_SORTED)
    List<Meal> findAll(@Param("userId") int userId);

    @Query(name = Meal.GET_BETWEEN)
    List<Meal> findAllBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);

    @Query("SELECT m FROM Meal m LEFT JOIN FETCH m.user WHERE m.id = :id AND m.user.id = :userId")
    Meal getWithUser(@Param("id")Integer id, @Param("userId")Integer userId);

}
