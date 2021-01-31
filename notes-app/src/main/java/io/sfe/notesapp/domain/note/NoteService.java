package io.sfe.notesapp.domain.note;

import java.util.Objects;

public interface NoteService {

    Note save(SaveNoteCommand saveNoteCommand);

    class SaveNoteCommand {
        private final String text;

        public static SaveNoteCommand of(String text) {
            return new SaveNoteCommand(text);
        }

        private SaveNoteCommand(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SaveNoteCommand that = (SaveNoteCommand) o;
            return Objects.equals(text, that.text);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text);
        }

        @Override
        public String toString() {
            return "SaveNoteCommand{" +
                "text='" + text + '\'' +
                '}';
        }
    }
}
