import java.util.Map;

/**
 * Stores student records keyed by student ID.
 *
 * <p>Phase 1 starter: all directory operations are intentionally unimplemented
 * until the student-authored tests have been observed failing.</p>
 */
public class StudentDirectory {

    /**
     * Immutable student record. The student ID is the key used by the directory.
     */
    public record Student(String id, String name, double grade) {
    }

    /**
     * Adds a student to the directory.
     *
     * @param student the student to add
     * @throws UnsupportedOperationException until the implementation phase
     */
    public void addStudent(Student student) {
        throw new UnsupportedOperationException("TODO: implement addStudent");
    }

    /**
     * Retrieves the student associated with the given ID.
     *
     * @param id the student ID
     * @return the matching student, or {@code null} when no record exists
     * @throws UnsupportedOperationException until the implementation phase
     */
    public Student getStudent(String id) {
        throw new UnsupportedOperationException("TODO: implement getStudent");
    }

    /**
     * Updates an existing student's name and grade.
     *
     * @param id the student ID
     * @param name the replacement name
     * @param grade the replacement grade
     * @throws UnsupportedOperationException until the implementation phase
     */
    public void updateStudent(String id, String name, double grade) {
        throw new UnsupportedOperationException("TODO: implement updateStudent");
    }

    /**
     * Removes the student associated with the given ID.
     *
     * @param id the student ID
     * @return {@code true} when a record was removed, otherwise {@code false}
     * @throws UnsupportedOperationException until the implementation phase
     */
    public boolean removeStudent(String id) {
        throw new UnsupportedOperationException("TODO: implement removeStudent");
    }

    /**
     * Returns an unmodifiable snapshot of the directory contents.
     *
     * @return an unmodifiable copy of the current directory entries
     * @throws UnsupportedOperationException until the implementation phase
     */
    public Map<String, Student> getSnapshot() {
        throw new UnsupportedOperationException("TODO: implement getSnapshot");
    }

    /**
     * Counts each character's occurrences in the supplied text.
     *
     * @param text the text to inspect
     * @return a map from each character to its occurrence count
     * @throws UnsupportedOperationException until the implementation phase
     */
    public static Map<Character, Integer> countCharacterFrequency(String text) {
        throw new UnsupportedOperationException("TODO: implement countCharacterFrequency");
    }
}
