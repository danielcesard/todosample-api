package com.daniel.todosample.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniel.todosample.models.Task;
import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    List<Task> findByUser_Id(Long id);

    // @Query(value = "SELECT t FROM Task WHERE t.user.id = :id")
    // List<Task> findByUser_Id(@Param("id") Long id);

    //@Query(value = "SELECT * FROM task t WHERE t.user_id = :id", nativeQuery = true)
    //List<Task> findByUser_Id(@Param("id") Long id);
}
