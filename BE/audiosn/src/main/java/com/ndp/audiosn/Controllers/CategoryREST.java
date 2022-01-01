package com.ndp.audiosn.Controllers;

import java.util.List;

import com.ndp.audiosn.Entities.Category;
import com.ndp.audiosn.Services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/categories")
public class CategoryREST {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> retrieveAll() {
        ResponseEntity<Object> entity;

        List<Category> categories = categoryService.retrieveAll();

        entity = new ResponseEntity<>(categories, HttpStatus.OK);

        return entity;
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> createNewCategory(@RequestBody Category category) {
        ResponseEntity<Object> entity;

        if(category.getName() == null) {
            entity = new ResponseEntity<>("{ \"Notice\": \"Not allow empty\" }", HttpStatus.BAD_REQUEST);
        } else {
            Category tmpSaved = categoryService.createOne(category);

            if(tmpSaved == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Failed!\" }", HttpStatus.BAD_REQUEST);
            } else {
                entity = new ResponseEntity<>(tmpSaved, HttpStatus.CREATED);
            }
        }

        return entity;
    }
}
