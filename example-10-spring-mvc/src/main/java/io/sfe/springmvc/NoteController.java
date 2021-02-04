package io.sfe.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class NoteController {

    @RequestMapping("/notes")
    public ModelAndView allNotes(ModelAndView modelAndView) {
        modelAndView.setViewName("notes");

        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = DateTimeFormatter.ISO_DATE_TIME.format(now);

        modelAndView.addObject("dateTime", formattedDateTime);
        modelAndView.addObject(
            "notes",
            List.of(
                new Note("note1"),
                new Note("note2"),
                new Note("note3")
            )
        );

        return modelAndView;
    }
}
