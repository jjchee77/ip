package duke.commands;

import duke.exceptions.DukeException;
import duke.storage.Storage;
import duke.tasks.Task;
import duke.tasks.TaskList;
import duke.ui.Ui;

public class CommandMark extends Command {
    private Integer taskIndex;

    public CommandMark(Integer taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        Task task = tasks.get(this.taskIndex);
        task.mark();

        storage.saveTasks(tasks);

        ui.showMessage(String.format("Nice! I've marked this task as done:\n  %s", task));
    }
}