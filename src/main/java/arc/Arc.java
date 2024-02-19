package arc;

import java.nio.file.Path;
import java.nio.file.Paths;

import arc.commands.Command;
import arc.exceptions.ArcException;
import arc.parser.Parser;
import arc.storage.Storage;
import arc.tasks.TaskList;
import arc.ui.gui.Gui;
import javafx.application.Platform;

/**
 * Arc is the main class for the Arc chat-bot that manages tasks for users.
 * It integrates components for UI, storage, and command execution.
 */
public class Arc {
    /**
     * The default file path used for storing tasks data.
     */
    public static final Path TASKS_FILE_PATH = Paths.get(".", "data", "arc.tasks");

    /**
     * The delimiter used for separating command arguments in the storage file.
     */
    public static final String ARG_DELIMITER = "\u241f";
    /**
     * The storage field used to load and save tasks data.
     */
    private final Storage storage;
    /**
     * The tasks field used to store tasks.
     */
    private TaskList tasks;
    /**
     * The ui field used to interact visually with the user.
     */
    private final Gui gui;

    /**
     * Constructs a new Arc object.
     * Initializes the user interface, storage, and task list components.
     * Attempts to load existing tasks from the storage; if unsuccessful, starts with an empty task list.
     *
     * @param filePath The path to the file where tasks data is loaded from and saved to.
     * @param argDelimiter The delimiter used in the tasks data file for parsing command arguments.
     */
    public Arc(Path filePath, String argDelimiter) {
        this.gui = new Gui();
        this.storage = new Storage(filePath, argDelimiter);

        try {
            this.tasks = new TaskList(this.storage.loadTasks());
        } catch (ArcException arcException) {
            this.tasks = new TaskList();
        }
    }

    /**
     * Retrieves the response for a given user command.
     * Parses the command, executes it, and returns the response message.
     * If the command is an exit command, it terminates the application.
     *
     * @param fullCommand The full command string entered by the user.
     * @return The response message generated by executing the command.
     * @throws ArcException If an error occurs during command execution.
     */
    public String getResponse(String fullCommand) throws ArcException {
        Command command = Parser.parse(fullCommand);

        if (command.isExit()) {
            Platform.exit();
        }

        return command.execute(this.tasks, this.storage);
    }

    /**
     * Executes the main application loop.
     * Displays a welcome message, then continuously reads and processes user commands
     * until an exit command is received.
     */
    private void run() {
        gui.run(this);
    }

    /**
     * Initializes the application and starts the interaction loop.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Arc arc = new Arc(TASKS_FILE_PATH, ARG_DELIMITER);
        arc.run();
    }
}