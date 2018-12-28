import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import refactored.Customer;
import refactored.Movie;
import refactored.Rental;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CustomerTest {

	private static String validName = "Stephen";

	private static String regularMovie = "Regular Movie";
	private static String newReleaseMovie = "New Release Movie";
	private static String childrenMovie = "Children Movie";

	@Test
	public void canGetCustomerName(){
		//Arrange
		Customer customer = new Customer(validName);

		//Act
		String name = customer.getName();

		//Assert
		assertThat(name, is(validName));
	}

	@Test
	public void canAddRental(){
		//Arrange
		Rental rental = mock(Rental.class);
		Customer customer = mockCustomer();

		//Act
		customer.addRental(rental);
		Object rentalResult = customer.getRentals().firstElement();

		//Assert
		assertThat(rentalResult, is((Object) rental));
	}

	/*	Statement Unit tests:	 */

	@Test
	public void regularMovie_WhenUnderTwoDays(){
		//Arrange
		Rental rental = mockRental(mockRegularMovie(),1);
		Customer customer = mockCustomer();
		customer.addRental(rental);

		//Act
		List<String> statement = toList(customer.statement());

		//Assert
		List<String> expectedStatement = toList(createStatement(2, regularMovie));
		assertThat(getTotal(statement), is(getTotal(expectedStatement)));
		assertThat(getMovieDetails(statement), is(getMovieDetails(expectedStatement)));
	}

	@Test
	public void regularMovie_WhenTwoDays(){
		//Arrange
		Rental rental = mockRental(mockRegularMovie(), 2);
		Customer customer = mockCustomer();
		customer.addRental(rental);

		//Act
		List<String> statement = toList(customer.statement());

		//Assert
		List<String> expectedStatement = toList(createStatement(2, regularMovie));
		assertThat(getTotal(statement), is(getTotal(expectedStatement)));
		assertThat(getMovieDetails(statement), is(getMovieDetails(expectedStatement)));
	}

	@Test
	public void regularMovie_WhenOverTwoDays(){
		//Arrange
		Rental rental = mockRental(mockRegularMovie(), 3);
		Customer customer = mockCustomer();
		customer.addRental(rental);

		//Act
		List<String> statement = toList(customer.statement());

		//Assert
		List<String> expectedStatement = toList(createStatement(3.5, regularMovie));
		assertThat(getTotal(statement), is(getTotal(expectedStatement)));
		assertThat(getMovieDetails(statement), is(getMovieDetails(expectedStatement)));
	}

	@Test
	public void newReleaseMovie_WhenTwoDays(){
		//Arrange
		Rental rental = mockRental(mockNewReleaseMovie(), 2);
		Customer customer = mockCustomer();
		customer.addRental(rental);

		//Act
		List<String> statement = toList(customer.statement());

		//Assert
		List<String> expectedStatement = toList(createStatement(6, newReleaseMovie));
		assertThat(getTotal(statement), is(getTotal(expectedStatement)));
		assertThat(getMovieDetails(statement), is(getMovieDetails(expectedStatement)));
	}

	@Test
	public void newReleaseMovie_WhenThreeDays(){
		//Arrange
		Rental rental = mockRental(mockNewReleaseMovie(),3);
		Customer customer = mockCustomer();
		customer.addRental(rental);

		//Act
		List<String> statement = toList(customer.statement());

		//Assert
		List<String> expectedStatement = toList(createStatement(9, newReleaseMovie));
		assertThat(getTotal(statement), is(getTotal(expectedStatement)));
		assertThat(getMovieDetails(statement), is(getMovieDetails(expectedStatement)));
	}

	@Test
	public void childrenMovie_WhenUnderThreeDays(){
		//Arrange
		Rental rental = mockRental(mockChildrenMovie(),2);
		Customer customer = mockCustomer();
		customer.addRental(rental);

		//Act
		List<String> statement = toList(customer.statement());

		//Assert
		List<String> expectedStatement = toList(createStatement(1.5, childrenMovie));
		assertThat(getTotal(statement), is(getTotal(expectedStatement)));
		assertThat(getMovieDetails(statement), is(getMovieDetails(expectedStatement)));
	}

	@Test
	public void childrenMovie_WhenThreeDays(){
		//Arrange
		Rental rental = mockRental(mockChildrenMovie(), 3);
		Customer customer = mockCustomer();
		customer.addRental(rental);

		//Act
		List<String> statement = toList(customer.statement());

		//Assert
		List<String> expectedStatement = toList(createStatement(1.5, childrenMovie));
		assertThat(getTotal(statement), is(getTotal(expectedStatement)));
		assertThat(getMovieDetails(statement), is(getMovieDetails(expectedStatement)));
	}

	@Test
	public void childrenMovie_WhenOverThreeDays(){
		//Arrange
		Rental rental = mockRental(mockChildrenMovie(), 4);
		Customer customer = mockCustomer();
		customer.addRental(rental);

		//Act
		List<String> statement = toList(customer.statement());

		//Assert
		List<String> expectedStatement = toList(createStatement(3, childrenMovie));
		assertThat(getTotal(statement), is(getTotal(expectedStatement)));
		assertThat(getMovieDetails(statement), is(getMovieDetails(expectedStatement)));
	}

	/*	Statement Unit tests: Renter Points	 */

	@Test
	public void regularMovie_HasOneRenterPoint(){
		//Arrange
		Rental rental = mockRental(mockRegularMovie(), 1);
		Customer customer = mockCustomer();
		customer.addRental(rental);

		//Act
		List<String> statement = toList(customer.statement());

		//Assert
		List<String> expectedStatement = toList(createStatement(2, 1, regularMovie));
		assertThat(getTotal(statement), is(getTotal(expectedStatement)));
		assertThat(getMovieDetails(statement), is(getMovieDetails(expectedStatement)));
	}

	@Test
	public void newReleaseMovie_WhenRentedOneDay_HasOneRenterPoint(){
		//Arrange
		Rental rental = mockRental(mockNewReleaseMovie(), 1);
		Customer customer = mockCustomer();
		customer.addRental(rental);

		//Act
		List<String> statement = toList(customer.statement());

		//Assert
		List<String> expectedStatement = toList(createStatement(3, 1, newReleaseMovie));
		assertThat(getTotal(statement), is(getTotal(expectedStatement)));
		assertThat(getMovieDetails(statement), is(getMovieDetails(expectedStatement)));
		assertThat(getPoints(statement), is(getPoints(expectedStatement)));
	}

	@Test
	public void newReleaseMovie_WhenRentedTwoDays_HasBonusRenterPoint(){
		//Arrange
		Rental rental = mockRental(mockNewReleaseMovie(), 2);
		Customer customer = mockCustomer();
		customer.addRental(rental);

		//Act
		List<String> statement = toList(customer.statement());

		//Assert
		List<String> expectedStatement = toList(createStatement( 6, 2, newReleaseMovie));
		assertThat(getTotal(statement), is(getTotal(expectedStatement)));
		assertThat(getMovieDetails(statement), is(getMovieDetails(expectedStatement)));
		assertThat(getPoints(statement), is(getPoints(expectedStatement)));
	}

	@Test
	public void canRentTwoRegularMovies(){
		//Arrange
		Rental rental = mockRental(mockRegularMovie(), 1);
		Customer customer = mockCustomer();
		customer.addRental(rental);
		customer.addRental(rental);

		//Act
		List<String> statement = toList(customer.statement());

		//Assert
		List<String> expectedStatement = toList(createStatement(4, 2, 2, regularMovie, regularMovie));
		assertThat(getTotal(statement), is(getTotal(expectedStatement)));
		assertThat(getMovieDetails(statement), is(getMovieDetails(expectedStatement)));
		assertThat(getPoints(statement), is(getPoints(expectedStatement)));
	}

	private Customer mockCustomer(){
		return new Customer(validName);
	}

	private Rental mockRental(Movie movie, int days){
		return new Rental(movie, days);
	}

	private Movie mockRegularMovie(){
		return new Movie(regularMovie, Movie.REGULAR);
	}

	private Movie mockNewReleaseMovie(){
		return new Movie(newReleaseMovie, Movie.NEW_RELEASE);
	}

	private Movie mockChildrenMovie(){
		return new Movie(childrenMovie, Movie.CHILDREN);
	}

	private String createStatement( double amountDue, String movieTitle){
		return createStatement(amountDue, amountDue, 0, movieTitle);
	}

	private String createStatement( double amountDue, int points, String movieTitle ){
		return createStatement(amountDue, amountDue, points, movieTitle);
	}

	private String createStatement( double amountDue, double movieCost, int points, String... movieTitles ){
		String rentalRecord = "Rental record for " + validName + "\n";
		String movieDetails = "";
		for(String movieTitle : movieTitles)
			movieDetails += statementMovieRow(movieTitle, movieCost);
		String amountOwed = "Amount owed is " + String.valueOf(amountDue) + "\n";
		String renterPoints = "You earned " + points  + " frequent renter points";
		return rentalRecord + movieDetails + amountOwed + renterPoints;
	}

	private String statementMovieRow(String movieTitle, double amountDue){
		return "\t" + movieTitle + "\t" + String.valueOf(amountDue) + "\n";
	}

	private List<String> toList(String string){
		return Arrays.asList(string.split("\n"));
	}

	private String getMovieDetails(List<String> lines){
		return lines.stream()
			.filter(line -> line.contains("\t"))
			.collect(Collectors.joining(","));
	}

	private String getTotal(List<String> lines){
		return lines.stream()
			.filter(line -> line.contains("Amount owed is"))
			.findFirst()
			.orElse(null);
	}

	private String getPoints(List<String> lines){
		return lines.stream()
			.filter(line -> line.contains("frequent renter points"))
			.findFirst()
			.orElse(null);
	}
}
