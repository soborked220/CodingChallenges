package todo;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import todo.exception.UserException;
import todo.user.api.UserController;
import todo.user.model.User;


@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private UserController userController;


	public static void main(String[] args){
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner userInput = new Scanner(System.in);
		User user = getUserDetails(userInput);
		while(true){
			runProgram(user, userInput);
		}
	}

	//Like a UI

	private User getUserDetails(Scanner userInput){
		String name = getUserInput("Enter your first name: ", userInput);
		//String email = getUserInput("Enter your email address: ", userInput);
		try{
			return userController.createUser(name);
		}
		catch (UserException e){
			System.out.println("Invalid input(s): " + e.getMessage());
			return getUserDetails(userInput);
		}
	}

	private String getMenuChoice(Scanner userInput){
		System.out.println();
		System.out.println("1. create a todo.task");
		System.out.println("2. complete a todo.task");
		System.out.println("3. see completed tasks");
		System.out.println("4. see incomplete tasks");
		return getUserInput("Pick a menu option: ", userInput);
	}

	private void invalidMenuChoice(){
		System.out.println("invalid menu choice");
		System.out.println("please choose a valid menu choice");
	}

	private String getUserInput(String text, Scanner userInput){
		System.out.println(text);
		return userInput.next( );
	}

	private void runProgram(User user, Scanner userInput) {
		switch (getMenuChoice(userInput)) {
			case "1":
				userController.createTask(user);
				break;
			case "2":
				userController.completeTask(user);
				break;
			case "3":
				userController.getCompletedTasks(user);
				break;
			case "4":
				userController.getIncompleteTasks(user);
				break;
			default: invalidMenuChoice();
		}
	}
}
