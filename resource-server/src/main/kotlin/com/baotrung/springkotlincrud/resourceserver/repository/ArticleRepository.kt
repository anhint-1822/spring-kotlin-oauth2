package com.baotrung.springkotlincrud.resourceserver.repository

import com.baotrung.springkotlincrud.resourceserver.entity.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, String> {

}