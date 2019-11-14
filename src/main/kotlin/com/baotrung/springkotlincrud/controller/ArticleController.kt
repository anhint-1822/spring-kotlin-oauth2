package com.baotrung.springkotlincrud.controller

import com.baotrung.springkotlincrud.model.Article
import com.baotrung.springkotlincrud.repository.ArticleRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ArticleController(private val articleRepository: ArticleRepository) {

    @GetMapping("/articles")
    fun getAllArticle(): List<Article> = articleRepository.findAll() as List<Article>;

    @PostMapping("/articles")
    fun saveArticle(@Valid @RequestBody article: Article): Article = articleRepository.save(article);

    @GetMapping("/articles/{article}")
    fun getArticleById(@PathVariable(value = "article") id: Long): ResponseEntity<Article> {
        return articleRepository.findById(id).map { article ->
            ResponseEntity.ok(article)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/articles/{id}")
    fun updateArticleById(@PathVariable(value = "id") id: Long, @Valid @RequestBody newArticle: Article): ResponseEntity<Article> {
        return articleRepository.findById(id).map { article ->
            val updatedArticle = article.copy(title = newArticle.title, content = newArticle.content)
            ResponseEntity.ok().body(articleRepository.save(updatedArticle))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/article/{article}")
    fun deleteArticleById(@PathVariable(value = "article") id: Long): ResponseEntity<Void> {
        return articleRepository.findById(id).map { article ->
            articleRepository.delete(article)
            ResponseEntity<Void>(HttpStatus.OK);
        }.orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    fun showMessage() : String {
        return "This is api test";
    }
}