package com.example.demo.services;

import com.example.demo.Exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by eduardo.siqueira on 19/02/2018.
 */
public class AbstractService<T> {

    @Autowired
    private JpaRepository<T, Long> repository;

    public T save (T obj) throws CustomException{
        try {
            return repository.save(obj);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException("Erro ao salvar");
        }
    }

    public boolean delete(long id) throws CustomException {
        T obj = repository.getOne(id);
        if(obj != null) {
            try {
                repository.deleteById(id);
                return true;
            }catch (Exception e) {
                throw new CustomException("Erro ao deletar");
            }
        }
        throw new CustomException("Objeto não encontrado");
    }

    public List<T> findAll() {
        return  (List<T>)repository.findAll();
    }

    public T findOne(long id) throws CustomException{
        T obj = repository.getOne(id);
        if(obj==null) {
            throw new CustomException("Item não encontrado");
        }
        return obj;
    }

    public boolean exists(long id) throws CustomException{
        T obj = repository.getOne(id);
        if(obj != null){
            return true;
        }
        return false;
    }
}
