package io.sfe.notesapp.web.note;

import io.sfe.notesapp.domain.note.Note;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @RequestMapping(
        path = {"", "/"},
        method = RequestMethod.GET,
        produces = MediaType.TEXT_HTML_VALUE,
        consumes = MediaType.ALL_VALUE
    )
    public String allNotes(Model model) {
        model.addAttribute(
            "notes",
            List.of(
                Note.of(1, "note1"),
                Note.of(2, "note2"),
                Note.of(3, "note3")
            )
        );

        return "note/notes";
    }
}
