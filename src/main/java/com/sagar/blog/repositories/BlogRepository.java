package com.sagar.blog.repositories;

import com.sagar.blog.models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByIdIn(List<Long> ids);
    List<Blog> findByCategoryId(Long categoryId);
}

