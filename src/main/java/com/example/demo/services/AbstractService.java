package com.example.demo.services;

import com.example.demo.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public class AbstractService<T> {

    @Autowired
    private JpaRepository<T, Long> repository;

    public T save(T obj) throws DuplicatedException {
        try {
            return repository.save(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    public boolean delete(long id) throws GenericException {
        T obj = repository.getOne(id);
        if (obj != null) {
            try {
                repository.deleteById(id);
                return true;
            } catch (Exception e) {
                throw new DependencyException(e.getMessage());
            }
        }

        throw new NotFoundException(ExceptionMessage.MENSAGEM_NOT_FOUND);
    }

    public List<T> findAll() {
        return (List<T>) repository.findAll();
    }

    public T findOne(long id) throws GenericException {
        T obj = repository.getOne(id);
        if (obj == null) {
            throw new NotFoundException(ExceptionMessage.MENSAGEM_NOT_FOUND);
        }
        return obj;
    }

}
