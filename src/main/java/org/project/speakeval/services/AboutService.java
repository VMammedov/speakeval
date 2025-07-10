package org.project.speakeval.services;

import org.project.speakeval.domain.About;
import java.util.List;

public interface AboutService {
    List<About> getAllAbouts();
    About getAboutById(Long id);
    About createAbout(About about);
    About updateAbout(Long id,About about);
}
