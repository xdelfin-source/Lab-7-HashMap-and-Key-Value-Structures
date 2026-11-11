# CpE 211 Lab 7: HashMap and Key–Value Structures — Agent Log

## Phase 1: PLAN

### Planned File Locations

- `lab 7/src/StudentDirectory.java`
- `lab 7/test/LabTestRunner.java`

### Functional Invariants

1. **Student ID duplicate rejection on insertion**
   - A student record with an ID already present in the directory must not be inserted.
   - The existing record must remain unchanged.

2. **Key-based CRUD**
   - Retrieval: looking up a student by ID returns the matching record, or a missing-key result when absent.
   - Update: an existing student record can be modified through its ID.
   - Removal: removing a student by ID deletes the record and reports whether it existed.

3. **Defensive copy isolation for `getSnapshot()`**
   - `getSnapshot()` returns an unmodifiable copy of the directory contents.
   - The internal backing map must not be exposed.
   - Mutating or attempting to mutate the returned snapshot must not affect the directory's internal state.

4. **Character frequency counter**
   - `countCharacterFrequency(String text)` returns `Map<Character, Integer>`.
   - Each character maps to the number of times it occurs in `text`.
   - An empty input produces an empty map.

### Planned Acceptance Test Matrix

| Test Area | Scenario | Expected Result |
|---|---|---|
| Empty Map operations | Retrieve, remove, snapshot, and frequency-count on an empty directory/text | Missing lookup result; removal reports `false`; snapshot is empty and unmodifiable; frequency map is empty |
| Duplicate Key handling | Insert a second record using an existing student ID | Insertion is rejected; original record remains unchanged |
| Missing Key lookup | Retrieve a student ID not in the directory | Missing-key result, without throwing |
| Missing Key removal | Remove a student ID not in the directory | Removal reports `false`; directory remains unchanged |
| Record Update | Update an existing student's information through its ID | Updated record is returned by subsequent lookup |
| Removal | Remove an existing student ID | Removal reports `true`; subsequent lookup reports missing |
| Repeated Characters frequency check | Count characters in text containing repeated characters | Each character maps to its correct occurrence count |

### Predicted Complexity Table

| Operation | Average Time | Worst-Case Time | Space | Notes |
|---|---|---|---|---|
| `put` | `O(1)` | `O(n)` | `O(1)` | Worst case caused by hash-collision degradation |
| `get` | `O(1)` | `O(n)` | `O(1)` | Worst case caused by hash-collision degradation |
| `remove` | `O(1)` | `O(n)` | `O(1)` | Worst case caused by hash-collision degradation |
| `getSnapshot()` | `O(n)` | `O(n)` | `O(n)` | Copies all entries into an unmodifiable snapshot |
| `countCharacterFrequency(String)` | `O(n)` | `O(n)` | `O(n)` | One pass over the input; map stores distinct characters |

### Strict Constraint Note

No source files will be created in `lab 7/src/` or `lab 7/test/` until Phase 2 student assertions are written and observed failing.

## Phase 2: TEST

### Test-first setup

`lab 7/test/LabTestRunner.java` was authored against the starter signatures before any directory operation was implemented. At this point `lab 7/src/StudentDirectory.java` contains only the `Student` record and method stubs that throw `UnsupportedOperationException("TODO: implement ...")`. It has no backing map and no HashMap-based algorithm logic.

The dependency-free runner executes each test method independently, reports `PASS` or `FAIL`, and exits with status `1` when any test fails.

### Test assertions and rationale

1. **`testEmptyMap()`**
   - Assertions: a lookup for a missing ID returns `null`; removal reports `false`; the snapshot is non-null, empty, and rejects mutation with `UnsupportedOperationException`; character frequency counting on `""` returns an empty map.
   - Rationale: establishes clean behavior at the empty boundary and confirms that empty snapshots are unmodifiable.

2. **`testDuplicateKeyRejection()`**
   - Input: add `S001`/Alice, then add another record with ID `S001`.
   - Assertions: the second insertion throws `IllegalArgumentException`; the original Alice record is still returned; the snapshot still has size `1`.
   - Rationale: verifies that a duplicate student ID is rejected without overwriting the existing record.

3. **`testMissingKeyLookupAndRemoval()`**
   - Input: a directory containing one record.
   - Assertions: lookup of `S404` returns `null`; removal of `S404` returns `false`; the directory still has size `1`.
   - Rationale: verifies missing-key operations are clean and do not disturb existing entries.

4. **`testRecordUpdate()`**
   - Action: add `S001`/Alice, then update the record through `updateStudent("S001", "Alice Updated", 1.25)`.
   - Assertions: the ID remains `S001`, the name becomes `Alice Updated`, and the grade becomes `1.25`.
   - Rationale: verifies key-based updates modify the correct record fields.

5. **`testRemoval()`**
   - Action: add one record and remove `S001`.
   - Assertions: the first removal returns `true`; the snapshot size changes from `1` to `0`; lookup returns `null`; a second removal returns `false`.
   - Rationale: verifies removal deletes the entry, decrements the directory size, and is safely repeatable.

6. **`testDefensiveSnapshotIsolation()`**
   - Action: obtain a snapshot containing one record and attempt `snapshot.put("S002", BOB)`.
   - Assertions: the mutation throws `UnsupportedOperationException`; `S001` remains retrievable; `S002` is absent; the directory still has size `1`.
   - Rationale: verifies `getSnapshot()` neither exposes the internal backing map nor permits external mutation.

7. **`testRepeatedCharactersFrequency()`**
   - Input: `countCharacterFrequency("AABCCC")`.
   - Expected: `{A=2, B=1, C=3}`.
   - Rationale: verifies repeated characters are accumulated rather than overwritten.

### Expected red-phase result

Every test should fail at the first unimplemented method it reaches. The test class should compile because all starter signatures are present.

### Observed red-phase result

Compilation command, run from `lab 7/`:

```powershell
javac -d out src/StudentDirectory.java test/LabTestRunner.java
```

Compilation succeeded with no diagnostics.

Execution command:

```powershell
java -ea -cp out LabTestRunner
```

Observed output:

```text
FAIL: testEmptyMap
      java.lang.UnsupportedOperationException: TODO: implement getStudent
FAIL: testDuplicateKeyRejection
      java.lang.UnsupportedOperationException: TODO: implement addStudent
FAIL: testMissingKeyLookupAndRemoval
      java.lang.UnsupportedOperationException: TODO: implement addStudent
FAIL: testRecordUpdate
      java.lang.UnsupportedOperationException: TODO: implement addStudent
FAIL: testRemoval
      java.lang.UnsupportedOperationException: TODO: implement addStudent
FAIL: testDefensiveSnapshotIsolation
      java.lang.UnsupportedOperationException: TODO: implement addStudent
FAIL: testRepeatedCharactersFrequency
      java.lang.UnsupportedOperationException: TODO: implement countCharacterFrequency

SUMMARY: 0 passed, 7 failed
```

Exit code: `1`. This is the expected red phase: all seven tests compile and fail against the intentionally unimplemented starter methods.
