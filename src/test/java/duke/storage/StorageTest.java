package duke.storage;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import duke.exceptions.storage.CreateTasksFileFailedException;

public class StorageTest {
    private static final Path INVALID_FILE_PATH = Paths.get("/ACCESS_DENIED");
    private Storage storage;

    @BeforeEach
    public void initFields() {
        this.storage = new Storage(INVALID_FILE_PATH, "");
    }

    @Test
    public void readTasks_invalidFilePath_loadTasksFailedExceptionThrown() {
        assertThrows(CreateTasksFileFailedException.class, () -> this.storage.loadTasks());
    }
}
