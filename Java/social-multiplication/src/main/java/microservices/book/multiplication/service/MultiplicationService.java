package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;


public interface MultiplicationService {

	/**
	 * Creates a random {@link Multiplication} object with two randomly-generated factors
	 * between 11 and 99.
	 *
	 * @return a multiplication of randomly generated numbers
	 */
	Multiplication createRandomMultiplication();
	
	/**
	 * @return true if the attempt matches the result of the 
	 * multiplication, false otherwise.
	 */
	boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);
	
}
