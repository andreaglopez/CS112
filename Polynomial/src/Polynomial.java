package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {

	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage
	 * format of the polynomial is:
	 * 
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * 
	 * with the guarantee that degrees will be in descending order. For example:
	 * 
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * 
	 * which represents the polynomial:
	 * 
	 * <pre>
	 * 4 * x ^ 5 - 2 * x ^ 3 + 2 * x + 3
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients
	 *         and degrees read from scanner
	 */
	public static Node read(Scanner sc) throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}

	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input
	 * polynomials. The returned polynomial MUST have all new nodes. In other words,
	 * none of the nodes of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the
	 *         returned node is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		float coeff = 0;
		int degree = 0;

		Node pointer1 = poly1;
		Node pointer2 = poly2;

		Node polyAdd = null;
		Node temp = null;
		Node front = null;
		if (pointer1 == null) {
			front = pointer2;
		}
		if (pointer2 == null) {
			front = pointer1;
		}

		while (pointer1 != null && pointer2 != null) {
			// polyAdd = new Node(coeff, degree ,null);
			if (pointer1.term.degree == pointer2.term.degree) {
				if (pointer1.term.coeff + pointer2.term.coeff != 0) {
					degree = pointer1.term.degree;
					coeff = (pointer1.term.coeff + pointer2.term.coeff);
					polyAdd = new Node(coeff, degree, null);
				}
				pointer1 = pointer1.next;
				pointer2 = pointer2.next;

			} else if (pointer1.term.degree < pointer2.term.degree) {
				degree = pointer1.term.degree;
				coeff = pointer1.term.coeff;
				polyAdd = new Node(coeff, degree, null);
				pointer1 = pointer1.next;

			} else {
				degree = pointer2.term.degree;
				coeff = pointer2.term.coeff;
				polyAdd = new Node(coeff, degree, null);
				pointer2 = pointer2.next;
			}

			if (temp != null) {
				temp.next = polyAdd;
			} else {
				front = polyAdd;
			}
			temp = polyAdd;
			// polyAdd.next = new Node(coeff, degree, null);
			// polyAdd = polyAdd.next;
			// what i was doing was replacing the node each time
			// need a pointer for polyAdd then that pointer stays there
		}

		while (pointer1 != null) {

			polyAdd = new Node(pointer1.term.coeff, pointer1.term.degree, null);
			if (temp == null) {
				front = polyAdd;
			} else {
				temp.next = polyAdd;
			}
			temp = polyAdd;
			pointer1 = pointer1.next;
		}
		while (pointer2 != null) {
			polyAdd = new Node(pointer2.term.coeff, pointer2.term.degree, null);
			if (temp == null) {
				front = polyAdd;
			} else {
				temp.next = polyAdd;
			}
			temp = polyAdd;
			pointer2 = pointer2.next;
		}

		return front;
	}

	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input
	 * polynomials. The returned polynomial MUST have all new nodes. In other words,
	 * none of the nodes of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the
	 *         returned node is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		// add is messing with this method as it is deleting existing nodes and
		// replacing it this nothing is done
		Node pointer1 = poly1;
		Node pointer2 = poly2;

		// Node polyMulti = null;
		/*
		 * int max = 0;
		 */
		Node temp = null; // putting all the terms in
		/*
		 * Node front = null; // this has temp added and simplify
		 */
		if (poly1 == null || poly2 == null) {
			return null;
		}

		while (pointer1 != null) {

			Node polyMulti = null;
			pointer2 = poly2;

			while (pointer2 != null) {
				polyMulti = new Node(pointer1.term.coeff * pointer2.term.coeff,
						pointer1.term.degree + pointer2.term.degree, polyMulti); // pointing it backwards this is why
					// need a helper method to 'reverse' the actions on line 179
				// temp.next = temp; // prints backwards
				pointer2 = pointer2.next;
				/*
				 * if ((pointer1.term.degree + pointer2.term.degree) > max) { max =
				 * pointer1.term.degree + pointer2.term.degree;
				 */
			}
			pointer1 = pointer1.next;
			polyMulti = reverse(polyMulti);
			temp = add(polyMulti, temp);

		}
		// System.out.println(polyMulti.term.coeff); - making sure it is printing the correct coefficient
		// System.out.println(polyMulti.term.degree); - making sure it is printing the correct degree
		//reverse(temp); - making sure it is printing out the correct nodes
		return temp;
	}

	//helper method
	private static Node reverse(Node product) {
		Node prev = null;
		Node current = product;
		Node next = null;
		while (current != null) {
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
		product = prev;
		return product;
	}

//	}
// * not working with test cases - have to use add *

//	float sumTotal = 0;
//	Node front = null;
//	Node temp = polyMulti; // pointer to the unsimplify verison
//
//	while(max>=0)
//	{
//		while (temp != null) {
//			if (temp.term.degree == max)
//				sumTotal += temp.term.coeff;
//
//			temp = temp.next;
//		}
//
//		if (sumTotal != 0) {
//			front = new Node(sumTotal, max, front); // is possible to do this
//		}
//		temp = polyMulti;
//		sumTotal = 0;
//
//		max--;
//	}
//
//	printing(front);
//		return front;
//	}

	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x    Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		Node point = poly;
		float answer = 0;

		while (point != null) {
			answer += (point.term.coeff * (float) Math.pow(x, point.term.degree));
			point = point.next;
		}
		return answer;
	}

//	private static Node printing(Node p) {
//		while (p != null) {
//			System.out.print("(" + p.term.coeff + "," + p.term.degree + ") --->");
//			p = p.next;
//		}
//		System.out.println();
//		return p;
//	}

	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		}

		String retval = poly.term.toString();
		for (Node current = poly.next; current != null; current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}
}
