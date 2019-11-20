package com.baotrung.resourceserver.repository

import com.baotrung.resourceserver.entity.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, String> {
}
