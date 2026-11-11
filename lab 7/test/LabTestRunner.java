import java.util.Map;
import java.util.Objects;

/**
 * Dependency-free test runner for the student directory acceptance tests.
 */
public class LabTestRunner {

    private static final StudentDirectory.Student ALICE =
            new StudentDirectory.Student("S001", "Alice", 1.50);
    private static final StudentDirectory.Student BOB =
            new StudentDirectory.Student("S002", "Bob", 1.75);

    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        if (run("testEmptyMap", LabTestRunner::testEmptyMap)) {
            passed++;
        } else {
            failed++;
        }
        if (run("testDuplicateKeyRejection", LabTestRunner::testDuplicateKeyRejection)) {
            passed++;
        } else {
            failed++;
        }
        if (run("testMissingKeyLookupAndRemoval", LabTestRunner::testMissingKeyLookupAndRemoval)) {
            passed++;
        } else {
            failed++;
        }
        if (run("testRecordUpdate", LabTestRunner::testRecordUpdate)) {
            passed++;
        } else {
            failed++;
        }
        if (run("testRemoval", LabTestRunner::testRemoval)) {
            passed++;
        } else {
            failed++;
        }
        if (run("testDefensiveSnapshotIsolation", LabTestRunner::testDefensiveSnapshotIsolation)) {
            passed++;
        } else {
            failed++;
        }
        if (run("testRepeatedCharactersFrequency", LabTestRunner::testRepeatedCharactersFrequency)) {
            passed++;
        } else {
            failed++;
        }

        System.out.printf("%nSUMMARY: %d passed, %d failed%n", passed, failed);
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static boolean run(String name, TestCase test) {
        try {
            test.run();
            System.out.println("PASS: " + name);
            return true;
        } catch (Throwable failure) {
            System.out.println("FAIL: " + name);
            System.out.println("      " + failure);
            return false;
        }
    }

    private static void testEmptyMap() {
        StudentDirectory directory = new StudentDirectory();

        assertNull(directory.getStudent("missing"));
        assertFalse(directory.removeStudent("missing"));

        Map<String, StudentDirectory.Student> snapshot = directory.getSnapshot();
        assertNotNull(snapshot);
        assertTrue(snapshot.isEmpty());
        assertThrows(UnsupportedOperationException.class,
                () -> snapshot.put("S999", new StudentDirectory.Student("S999", "Nobody", 1.00)));

        assertTrue(StudentDirectory.countCharacterFrequency("").isEmpty());
    }

    private static void testDuplicateKeyRejection() {
        StudentDirectory directory = new StudentDirectory();
        directory.addStudent(ALICE);

        assertThrows(IllegalArgumentException.class, () -> directory.addStudent(
                new StudentDirectory.Student("S001", "Duplicate Alice", 1.25)));

        assertEquals(ALICE, directory.getStudent("S001"));
        assertEquals(1, directory.getSnapshot().size());
    }

    private static void testMissingKeyLookupAndRemoval() {
        StudentDirectory directory = new StudentDirectory();
        directory.addStudent(ALICE);

        assertNull(directory.getStudent("S404"));
        assertFalse(directory.removeStudent("S404"));
        assertEquals(1, directory.getSnapshot().size());
    }

    private static void testRecordUpdate() {
        StudentDirectory directory = new StudentDirectory();
        directory.addStudent(ALICE);

        directory.updateStudent("S001", "Alice Updated", 1.25);

        StudentDirectory.Student updated = directory.getStudent("S001");
        assertNotNull(updated);
        assertEquals("S001", updated.id());
        assertEquals("Alice Updated", updated.name());
        assertEquals(1.25, updated.grade());
    }

    private static void testRemoval() {
        StudentDirectory directory = new StudentDirectory();
        directory.addStudent(ALICE);
        assertEquals(1, directory.getSnapshot().size());

        assertTrue(directory.removeStudent("S001"));
        assertEquals(0, directory.getSnapshot().size());
        assertNull(directory.getStudent("S001"));
        assertFalse(directory.removeStudent("S001"));
    }

    private static void testDefensiveSnapshotIsolation() {
        StudentDirectory directory = new StudentDirectory();
        directory.addStudent(ALICE);

        Map<String, StudentDirectory.Student> snapshot = directory.getSnapshot();
        assertEquals(1, snapshot.size());
        assertThrows(UnsupportedOperationException.class, () -> snapshot.put("S002", BOB));

        assertEquals(ALICE, directory.getStudent("S001"));
        assertNull(directory.getStudent("S002"));
        assertEquals(1, directory.getSnapshot().size());
    }

    private static void testRepeatedCharactersFrequency() {
        Map<Character, Integer> expected = Map.of('A', 2, 'B', 1, 'C', 3);
        assertEquals(expected, StudentDirectory.countCharacterFrequency("AABCCC"));
    }

    private static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("expected condition to be true");
        }
    }

    private static void assertFalse(boolean condition) {
        if (condition) {
            throw new AssertionError("expected condition to be false");
        }
    }

    private static void assertNull(Object actual) {
        if (actual != null) {
            throw new AssertionError("expected null but was <" + actual + ">");
        }
    }

    private static void assertNotNull(Object actual) {
        if (actual == null) {
            throw new AssertionError("expected a non-null value");
        }
    }

    private static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("expected <" + expected + "> but was <" + actual + ">");
        }
    }

    private static void assertEquals(double expected, double actual) {
        if (Double.compare(expected, actual) != 0) {
            throw new AssertionError("expected <" + expected + "> but was <" + actual + ">");
        }
    }

    private static void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("expected <" + expected + "> but was <" + actual + ">");
        }
    }

    private static <T extends Throwable> T assertThrows(Class<T> expectedType, TestCase action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (expectedType.isInstance(thrown)) {
                return expectedType.cast(thrown);
            }
            throw new AssertionError(
                    "expected " + expectedType.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + expectedType.getSimpleName() + " to be thrown");
    }

    @FunctionalInterface
    private interface TestCase {
        void run() throws Exception;
    }
}
