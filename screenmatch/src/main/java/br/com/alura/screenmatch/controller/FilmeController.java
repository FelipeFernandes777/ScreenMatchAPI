package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.domain.filme.DadosAlteracaoFilme;
import br.com.alura.screenmatch.domain.filme.DadosCadastroFilmes;
import br.com.alura.screenmatch.domain.filme.Filme;
import br.com.alura.screenmatch.domain.filme.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired // Instancia a minha conexão com o banco de dados
    private FilmeRepository repository;

    @GetMapping("/formulario")
    public String carregaPagindaDeFormulario(Long id, Model model) {

        if (id != null) {
            var filme = repository.getReferenceById(id);
            //Encaminha um atributo como o nome filme, seguindo os paramentros que é a minha variavel filme
           model.addAttribute("filme", filme);
        }

        return "filmes/formulario";
    }

    @GetMapping
    public String carregaPaginaDeListagem(Model model) {

        //Busca todos os atributos do meu banco de dados e devolve o valor como um atributo de lista
        model.addAttribute("lista", repository.findAll());
        return "filmes/listagem";
    }

    @PostMapping("/formulario")
    @Transactional
    public String cadastraFilmes(DadosCadastroFilmes dados) {

        var filme = new Filme(dados);
        repository.save(filme);

        return "redirect:/filmes";
    }

    @PutMapping
    @Transactional
    public String alteradFilmes(DadosAlteracaoFilme dados){

        var filme = repository.getReferenceById(dados.id());
        filme.atualizaDados(dados);
        
        return "redirect:/filmes";
    }

    @DeleteMapping
    @Transactional
    public String removeFilme(Long id) {
        repository.deleteById(id);

        return "redirect:/filmes";
    }

}