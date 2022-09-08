package duke.parser;

import duke.DukeException;
import duke.Message;
import duke.command.*;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

import java.time.LocalDate;

public class Parser {

    public static Command parse(String fullCommand) throws DukeException {
        String[] commandList = fullCommand.strip().split(" ");
        String command = commandList[0].toLowerCase();
        if (command.equals("bye") && commandList.length == 1) {
            return new ByeCommand();
        } else if (command.equals("list") && commandList.length == 1) {
            return new ListCommand();
        } else if (command.equals("mark")) {
            return markTask(fullCommand);
        } else if (command.equals("unmark")) {
            return unmarkTask(fullCommand);
        } else if (command.equals("deadline")) {
            return addDeadline(fullCommand);
        } else if (command.equals("event")) {
            return addEvent(fullCommand);
        } else if (command.equals("todo")) {
            return addToDo(fullCommand);
        } else if (command.equals("delete")){
            return deleteTask(fullCommand);
        }
        throw new DukeException(Message.INVALID_USER_INPUT);
    }


    private static AddCommand addToDo(String input) throws DukeException {
        String description = input.substring("todo".length()).strip();
        if (!description.equals("")) {
            ToDo newToDo = new ToDo(description);
            return new AddCommand(newToDo);
        } else {
            throw new DukeException(Message.INVALID_TODO_INPUT);
        }
    }

    private static AddCommand addDeadline(String input) throws DukeException {
        String[] stringArray = input.substring("deadline".length()).strip().split("/by");
        try {
            LocalDate deadlineDate = LocalDate.parse(stringArray[1].strip());
            if (deadlineDate.isBefore(LocalDate.now())){
                throw new DukeException(Message.INVALID_DATE_INPUT);
            }
            Deadline newDeadline = new Deadline(stringArray[0].strip(), deadlineDate);
            return new AddCommand(newDeadline);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(Message.INVALID_DEADLINE_INPUT);
        } catch (java.time.format.DateTimeParseException e) {
            throw new DukeException(Message.INVALID_DATE_FORMAT);
        }
    }

    private static AddCommand addEvent(String input) throws DukeException {
        String[] stringArray = input.substring("event".length()).strip().split("/at");
        if (stringArray.length > 1) {
            Event newEvent = new Event(stringArray[0].strip(), stringArray[1].strip());
            return new AddCommand(newEvent);
        } else {
            throw new DukeException(Message.INVALID_EVENT_INPUT);
        }
    }

    private static MarkCommand markTask(String input) throws DukeException{
        String[] commandList = input.strip().split(" ");
        try {
            int taskIndexNum = Integer.parseInt(commandList[1]);
            return new MarkCommand(taskIndexNum);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new DukeException(Message.INVALID_MARK_TASK_FORMAT);
        }
    }

    private static UnmarkCommand unmarkTask(String input) throws DukeException {
        String[] commandList = input.strip().split(" ");
        try {
            int taskIndexNum = Integer.parseInt(commandList[1]);
            return new UnmarkCommand(taskIndexNum);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new DukeException(Message.INVALID_UNMARK_TASK_FORMAT);
        }
    }

    private static DeleteCommand deleteTask(String command) throws DukeException {
        String[] commandList = command.strip().split(" ");
        try {
            int taskIndexNum = Integer.parseInt(commandList[1]);
            return new DeleteCommand(taskIndexNum);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new DukeException(Message.INVALID_DELETE_TASK_FORMAT);
        }
    }

}
