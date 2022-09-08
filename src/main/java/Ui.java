import java.util.Scanner;

public class Ui {

    private static final Scanner SCANNER = new Scanner(System.in);
    private final TaskList taskList;

    public Ui(TaskList taskList) {
        this.taskList = taskList;
    }

    public void showLine() {
        System.out.println(Message.HORIZONTAL_BORDER);
    }

    public void showFullMessage(String mainMessage) {
        showLine();
        System.out.println(mainMessage);
        showLine();
    }

    public void showWelcome() {
        showFullMessage(Message.WELCOME_MESSAGE);
    }

    public void showBye() {
        showFullMessage(Message.BYE_MESSAGE);
    }

    public String readCommand() {
        return SCANNER.nextLine().strip();
    }

    public void showError(String errorMessage) {
        showFullMessage("☹ OOPS!!! " + errorMessage);
    }

    public void showList() {
        showFullMessage(this.taskList.toString());
    }

    public void showAddition(Task task, int totalTasks) {
        showFullMessage("Got it. I've added this task:\n" + task + "\nNow you have " + totalTasks + " tasks in the list.");
    }

    public void showMarked(Task task) {
        showFullMessage("Nice! I've marked this task as done:\n" + task);
    }

    public void showUnmarked(Task task) {
        showFullMessage("OK, I've marked this task as not done yet:\n" + task);
    }
    
    public void showDeleted(Task task) {
        showFullMessage("Noted. I've removed this task:\n" + task + "\nNow you have " + this.taskList.getCount() + " tasks in the list.");
    }



}
