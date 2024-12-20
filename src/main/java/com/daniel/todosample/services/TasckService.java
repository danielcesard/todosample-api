package com.daniel.todosample.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daniel.todosample.models.Task;
import com.daniel.todosample.models.User;
import com.daniel.todosample.repositories.TaskRepository;
import com.daniel.todosample.services.exceptions.DataBindingViolationException;
import com.daniel.todosample.services.exceptions.ObjectNotFoundException;

@Service
public class TasckService {
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {
        
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(
            () -> new ObjectNotFoundException("Tarefa não encontrada. Id: " + id + ", Tipo: " + Task.class.getName())
        );
    }

    public List<Task> findAllByUserId(Long userId) {
        List<Task> tasks = this.taskRepository.findByUser_Id(userId);
        return tasks;        
    }

    @Transactional
    public Task create(Task obj) {

        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj) {

        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    @Transactional
    public void delete(Long id) {

        findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir, pois há entidades relacionadas.");
        }

    }

}
