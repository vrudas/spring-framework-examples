package io.sfe.notesapp.web.note;

import io.sfe.notesapp.domain.note.NoteService;
import io.sfe.notesapp.domain.note.NoteService.SaveNoteCommand;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static java.util.stream.Collectors.toUnmodifiableList;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @RequestMapping(
        path = {"", "/"},
        method = RequestMethod.GET,
        produces = MediaType.TEXT_HTML_VALUE,
        consumes = MediaType.ALL_VALUE
    )
    public String allNotes(Model model) {
        var notes = noteService.findAll()
            .stream()
            .map(note -> NoteDto.of(note.getId(), note.getText()))
            .collect(toUnmodifiableList());

        model.addAttribute("notes", notes);

        return "note/notes";
    }

    @GetMapping("/create-note")
    public String createNotePage() {
        return "note/create-note";
    }

    @PostMapping
    public String saveNote(@RequestParam(name = "text") String text) {
        var saveNoteCommand = SaveNoteCommand.of(text);
        noteService.save(saveNoteCommand);

        return "redirect:/notes";
    }
}
