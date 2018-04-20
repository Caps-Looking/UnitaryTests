package com.example.demo.repositorys;

import com.example.demo.entitys.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public List<Usuario> findUsuarioByCnpjOrAndCpf(String cpf, String cnpj);
}
