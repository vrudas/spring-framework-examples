package io.sfe.notesapp.web.note;

import io.sfe.notesapp.domain.note.Note;
import io.sfe.notesapp.domain.note.NoteService;
import org.springframework.http.HttpStatus;
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
            .map(note -> NoteDto.of(note.id(), note.text()))
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
        noteService.save(text);

        return "redirect:/notes";
    }

    @GetMapping("/{noteId}")
    public String findNoteById(
        @PathVariable("noteId") int noteId,
        Model model
    ) {
        Note noteById = noteService.findById(noteId);
        NoteDto noteDto = NoteDto.of(noteById.id(), noteById.text());

        model.addAttribute("note", noteDto);

        return "note/note";
    }

    @DeleteMapping("/{noteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteNoteById(@PathVariable("noteId") int noteId) {
        noteService.delete(noteId);
        return "redirect:/notes";
    }

    @GetMapping("{noteId}/update-note")
    public String updateNotePage(
        @PathVariable int noteId,
        Model model
    ) {
        Note note = noteService.findById(noteId);
        NoteDto noteDto = NoteDto.of(note.id(), note.text());

        model.addAttribute("note", noteDto);

        return "note/update-note";
    }

    @PutMapping("/{noteId}")
    @ResponseStatus(HttpStatus.OK)
    public String updateNoteById(
        @PathVariable("noteId") int noteId,
        @RequestParam("text") String text
    ) {
        noteService.updateNote(noteId, text);
        return "redirect:/notes/" + noteId;
    }
}
