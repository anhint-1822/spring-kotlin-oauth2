package com.baotrung.springkotlincrud.repository

import com.baotrung.springkotlincrud.model.Article
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : CrudRepository<Article, Long> {
}