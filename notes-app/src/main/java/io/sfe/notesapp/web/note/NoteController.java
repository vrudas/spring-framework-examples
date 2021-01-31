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
                Note.of(1, "note1"),
                Note.of(2, "note2"),
                Note.of(3, "note3")
            )
        );

        return "notes";
    }
}
