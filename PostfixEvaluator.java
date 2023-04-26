package unl.cse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class PostfixEvaluator {

	private static final Set<String> OPERATORS = new HashSet<String>(Arrays.asList("+", "-", "*", "/"));
	private final Stack<String> stack;
	
    /**
     * Constructor
     */
    public PostfixEvaluator() {
        this.stack = new Stack<String>();
    }

    private boolean isOperator(String s){
    	return OPERATORS.contains(s);
    }
    
    private String evaluate(String a, String b, String op) {
    	double d1 = Double.parseDouble(a);
    	double d2 = Double.parseDouble(b);
    	double result;
    	if(op.equals("+")) {
    		result = d1 + d2;
    	} else if(op.equals("-")) {
    		result = d1 - d2;
    	} else if(op.equals("*")) {
    		result = d1 * d2;
    	} else if(op.equals("/")) {
    		result = d1 / d2;
    	} else {
    		throw new IllegalStateException("Unrecognized operator: "+op);
    	}
    	return Double.toString(result);
    }

    /**
     * Evaluates the given arithmetic expression in postfix format
     * change this method
     * @param expression
     * @return
     */
    double evaluateExpression(String expression) {

    	String values[] = expression.split("\\s+");
    	for(String v : values) {
    		if(isOperator(v)) {
    			String a = this.stack.pop();
    			String b = this.stack.pop();
    			String result = this.evaluate(b, a, v);
    			this.stack.push(result);
    		} else {
    			this.stack.push(v);
    		}
    	}
    	//At this point, the final result should be on the top of the stack,
    	//we pop it off, parse it and return the result
    	Double d = Double.parseDouble(this.stack.pop());
        return d;
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {

        PostfixEvaluator postfixEvaluator = new PostfixEvaluator();
        System.out.print("Please enter the Arithmatic Expression (Postfix form) to evaluate: ");
        Scanner myScanner = new Scanner(System.in);
        String expression = myScanner.nextLine();
        myScanner.close();
        System.out.println(expression);
        System.out.println("Result: " + postfixEvaluator.evaluateExpression(expression));
    }
}
