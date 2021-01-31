package io.sfe.springboot.note;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class NoteController {

    @RequestMapping("/notes")
    public String allNotes(Model model) {
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = DateTimeFormatter.ISO_DATE_TIME.format(now);

        model.addAttribute("dateTime", formattedDateTime);
        model.addAttribute(
            "notes",
            List.of(
                new Note("note1"),
                new Note("note2"),
                new Note("note3")
            )
        );

        return "notes";
    }
}
