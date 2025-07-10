package org.project.speakeval.services.impl;

import org.project.speakeval.domain.About;
import org.project.speakeval.repository.AboutRepository;
import org.project.speakeval.services.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public  class AboutServiceImpl implements AboutService {
    private final AboutRepository aboutRepository;

    @Autowired
    public AboutServiceImpl(AboutRepository aboutRepository) {
        this.aboutRepository = aboutRepository;
    }

    @Override
    public List<About> getAllAbouts() {
        return aboutRepository.findAll();
    }

    @Override
    public About getAboutById(Long id){
        return aboutRepository.findById(id).orElse(null);
    }

    @Override
    public About createAbout(About about){
        return aboutRepository.save(about);
    }

    @Override
    public About updateAbout(Long id,About updatedAbout){
        Optional<About> optionalAbout = aboutRepository.findById(id);
        if(optionalAbout.isPresent()){
            About existingAbout = optionalAbout.get();
            existingAbout.setDescription(updatedAbout.getDescription());
            existingAbout.setPurpose(updatedAbout.getPurpose());
            return aboutRepository.save(existingAbout);
        }
        else
            return null;

    }


}
