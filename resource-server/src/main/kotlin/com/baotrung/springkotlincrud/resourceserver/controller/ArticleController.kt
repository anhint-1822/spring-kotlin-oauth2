package com.baotrung.springkotlincrud.resourceserver.controller

import com.baotrung.springkotlincrud.resourceserver.entity.Article
import com.baotrung.springkotlincrud.resourceserver.repository.ArticleRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import java.util.UUID
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ArticleController(private val articleRepository: ArticleRepository) {

    @Transactional(readOnly = true)
    @GetMapping("/articles")
    fun getAllArticle(): List<Article> = articleRepository.findAll()

    @Transactional(readOnly = true)
    @GetMapping("/articles/{id}")
    fun getArticleById(@PathVariable("id") id: String): ResponseEntity<Article> {
        return articleRepository.findById(id).map { article ->
            ResponseEntity.ok(article)
        }.orElse(ResponseEntity.notFound().build())
    }

    @Transactional
    @PostMapping("/articles")
    fun saveArticle(@Valid @RequestBody article: Article): Article {
        if (StringUtils.isEmpty(article.id)) {
            article.id = UUID.randomUUID().toString()
        }
        return articleRepository.save(article)
    }

    @Transactional
    @DeleteMapping("/article/{id}")
    fun deleteArticleById(@PathVariable("id") id: String): ResponseEntity<Void> {
        return articleRepository.findById(id).map { article ->
            articleRepository.delete(article)
            ResponseEntity<Void>(HttpStatus.OK);
        }.orElse(ResponseEntity.notFound().build())
    }
}