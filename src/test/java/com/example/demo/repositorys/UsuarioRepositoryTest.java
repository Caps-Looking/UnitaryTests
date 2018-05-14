package com.example.demo.repositorys;

import com.example.demo.entitys.Usuario;
import com.example.demo.enums.TipoDePessoaEnum;
import com.example.demo.enums.TipoDeUsuarioEnum;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    public UsuarioRepository usuarioRepository;

    @Test
    public void criarDevePersistirDados() {
        Usuario usuario = Usuario.builder()
                .tipoDeUsuarioEnum(TipoDeUsuarioEnum.COMUM)
                .cpf("12345678910")
                .email("email@email.com")
                .senha("123456789")
                .nomeCompleto("Teste de Integração")
                .tipodePessoaEnum(TipoDePessoaEnum.FISICA)
                .build();
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
        assertThat(usuarioSalvo.getCpf()).isEqualTo("12345678910");
        assertThat(usuarioSalvo.getSenha()).isEqualTo("123456789");
    }

    @Test
    public void deletarDeveRemoverDados() {
        Usuario usuario = Usuario.builder()
                .tipoDeUsuarioEnum(TipoDeUsuarioEnum.COMUM)
                .cpf("12345678910")
                .email("email@email.com")
                .senha("123456789")
                .nomeCompleto("Teste de Integração")
                .tipodePessoaEnum(TipoDePessoaEnum.FISICA)
                .build();
        usuarioRepository.save(usuario);
        usuarioRepository.deleteById(usuarioRepository.findByNomeCompleto("Teste de Integração").get(0).getId());
        assertTrue(usuarioRepository.findByNomeCompleto("Teste de Integração").isEmpty());
    }

}