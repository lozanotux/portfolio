package microservices.book.multiplication.concepts;

import org.springframework.boot.test.json.JacksonTester;

import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.multiplication.domain.Multiplication;

public class JacksonTesterTest {
	
	private JacksonTester<Multiplication> json;
	
	public static void main(String[] args) throws Exception {
		
		// Instantiate the object
		JacksonTesterTest jtt = new JacksonTesterTest();
		ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(jtt, objectMapper);
        
        // The output is the JSON as String:
        /*	"{
         *  	"factorA":70,
         *		"factorB":20
         *	}"
         */
		System.out.println(jtt.json.write(new Multiplication(70, 20)).getJson());
	}

}
