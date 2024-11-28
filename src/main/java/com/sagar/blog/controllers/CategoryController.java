package com.sagar.blog.controllers;

import com.sagar.blog.models.Blog;
import com.sagar.blog.models.Category;
import com.sagar.blog.repositories.BlogRepository;
import com.sagar.blog.repositories.CategoryRepository;
import com.sagar.blog.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogService blogService;

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "category/list";
    }

    @GetMapping("/all")
    public String getAllCategories(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "categories/categories";
    }

    @GetMapping("/new")
    public String newCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/form";
    }

    @PostMapping
    public String saveCategory(@Valid @ModelAttribute Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "category/form";
        }
        categoryRepository.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/{categoryId}/blogs")
    public String getBlogsByCategory(@PathVariable Long categoryId, Model model) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        List<Blog> blogs = blogService.getAllBlogs();
        List<Blog> filteredBlogs = new ArrayList<>();

        for(Blog blog: blogs){
            if(blog.getCategory() != null && blog.getCategory().getId().equals(categoryId)){
                filteredBlogs.add(blog);
            }
        }


        model.addAttribute("category", category);
        model.addAttribute("blogs", filteredBlogs);
        return "blogs/blogs-by-category";
    }

    // Edit and delete methods here
}
