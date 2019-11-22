package com.baotrung.resourceserver.controller

import com.baotrung.resourceserver.entity.Article
import com.baotrung.resourceserver.repository.ArticleRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
