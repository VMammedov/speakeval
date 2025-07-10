package org.project.speakeval.controller;

import org.project.speakeval.services.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.project.speakeval.domain.About;

import java.util.List;

@RestController
@RequestMapping("/api/about")
public class AboutController {
    private final AboutService aboutService;

    @Autowired
    public AboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }

    @GetMapping
    public ResponseEntity<List<About>> getAllAbouts() {
        return ResponseEntity.ok(aboutService.getAllAbouts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<About> getAboutById(@PathVariable Long id) {
        About about = aboutService.getAboutById(id);
        if (about != null) {
            return ResponseEntity.ok(about);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<About> createAbout(@RequestBody About about) {
        return ResponseEntity.ok(aboutService.createAbout(about));
    }

    @PutMapping("/{id}")
    public ResponseEntity<About> updateAbout(@PathVariable Long id, @RequestBody About about) {
        About updatedAbout = aboutService.updateAbout(id, about);
        if(updatedAbout != null) {
            return ResponseEntity.ok(updatedAbout);
        }
        else
            return ResponseEntity.notFound().build();
        }
}
