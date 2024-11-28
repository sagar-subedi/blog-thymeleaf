package com.sagar.blog.controllers;

import com.sagar.blog.DTOs.BlogDTO;
import com.sagar.blog.models.Blog;
import com.sagar.blog.models.Category;
import com.sagar.blog.models.Tag;
import com.sagar.blog.repositories.CategoryRepository;
import com.sagar.blog.repositories.TagRepository;
import com.sagar.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public String listBlogs(Model model) {
        model.addAttribute("blogs", blogService.getAllBlogs());
        return "blogs/list";
    }

    @GetMapping("/new")
    public String createBlogForm(Model model) {
        model.addAttribute("blog", new BlogDTO());;
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("availableTags", tagRepository.findAll());
        return "blogs/form";
    }

    @PostMapping
    public String saveBlog(@ModelAttribute BlogDTO blogDto) {

        Blog blog = new Blog();
        Category category = categoryRepository.findById(blogDto.getCategoryId()).orElseThrow();
        List<Tag> tags = tagRepository.findByIdIn(blogDto.getTagIds());
        blog.setCategory(category);
        blog.setTags(tags);
        blog.setContent(blogDto.getContent());
        blog.setTitle(blogDto.getTitle());
        blogService.saveBlog(blog);
        return "redirect:/blogs";
    }

    @GetMapping("/edit/{id}")
    public String editBlogForm(@PathVariable Long id, Model model) {
        Blog blog = blogService.getBlogById(id);

        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setTitle(blog.getTitle());
        blogDTO.setContent(blog.getContent());
        blogDTO.setCategoryId(blog.getCategory().getId());

        List<Long> tagIds =  blog.getTags().stream()
                .map(Tag::getId) // Map each entity to its ID
                .collect(Collectors.toList());

        blogDTO.setTagIds(tagIds);

        model.addAttribute("blog", blogDTO);
        model.addAttribute("availableCategories", categoryRepository.findAll());
        model.addAttribute("availableTags", tagRepository.findAll());
        return "blogs/edit";
    }

    @GetMapping("/view/{id}")
    public String getBlog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getBlogById(id));
        return "blogs/blogpage";
    }

    @PostMapping("/delete/{id}")
    public String deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return "redirect:/blogs";
    }
}
