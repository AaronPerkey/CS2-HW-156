package unl.cse;

/**
 * Demo driver class for the polymorphism activities
 *
 */
public class CourseDemo {

	public static void main(String args[]) {

		Undergraduate luke = new Undergraduate("1234", "Skywalker", "Luke");
		Undergraduate wedge = new Undergraduate("5678", "Antilles", "Wedge");

		Graduate ben = new Graduate("4411", "Kenobi", "Ben");
		Graduate mace = new Graduate("0021", "Windu", "Mace");

		Droid c3po = new Droid("C3PO", "Protocol");
		Droid r2d2 = new Droid("R2D2", "Astromech");

		
		
		Course c = new Course("CSCE", "156");
		Section<Undergraduate> ugradSection = new Section<Undergraduate>("001");
		ugradSection.enroll(luke);
		ugradSection.enroll(wedge);
		
		Section<Graduate> gradsSection = new Section<Graduate>("002");
		gradsSection.enroll(mace);
		gradsSection.enroll(ben);
		
		Section<Droid> droid = new Section<Droid>("003");
		droid.enroll(r2d2);
		droid.enroll(c3po);

		System.out.println(c);

	}
}
