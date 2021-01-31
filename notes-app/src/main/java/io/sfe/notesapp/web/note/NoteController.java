package io.sfe.notesapp.web.note;

import io.sfe.notesapp.domain.note.Note;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class NoteController {

    @RequestMapping("/notes")
    public String allNotes(Model model) {
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
