package com.daniel.todosample.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daniel.todosample.models.User;
import com.daniel.todosample.repositories.UserRepository;
import com.daniel.todosample.services.exceptions.DataBindingViolationException;
import com.daniel.todosample.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado. Id: " + id + ", Tipo: " + User.class.getName())
        );
    }

    @Transactional 
    public User create(User obj) {

        obj.setId(null);
        obj = this.userRepository.save(obj);

        return obj;
    }

    @Transactional
    public User update(User obj) {
         
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    public void delete(Long id) {

        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir, pois há entidades relacionadas.");
        }

    }

}
