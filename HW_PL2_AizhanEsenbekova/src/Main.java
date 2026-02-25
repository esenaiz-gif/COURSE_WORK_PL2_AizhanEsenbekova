class Course {
    private String name;
    private String instructor;
    private int credits;

    public Course(String name, String instructor, int credits) {
        this.name = name;
        this.instructor = instructor;
        this.credits = credits;
    }

    public void getName() {
        System.out.println(name);
    }

    public void displayCourse() {
        System.out.println("Course { name = '" + name +
                "', instructor = '" + instructor +
                "', credits = " + credits + " }");
    }

    public String nameValue() {   // helper for internal use
        return name;
    }
}

class AssignmentTask {
    private String title;
    private Course course;
    private int estimatedHours;
    private int daysUntilDue;
    private boolean completed;

    public AssignmentTask(String title, Course course, int estimatedHours, int daysUntilDue) {
        this.title = title;
        this.course = course;
        this.estimatedHours = estimatedHours;
        this.daysUntilDue = daysUntilDue;
        this.completed = false;
    }

    public void markCompleted() {
        completed = true;
        System.out.println(title + " marked as completed.");
    }

    public void isUrgent() {
        if (daysUntilDue <= 2 && !completed)
            System.out.println("\n" + title + " is VERY URGENT!!!" + "\n");
        else
            System.out.println("\n" + title + " is NOT urgent" + "\n");
    }

    public void displayTask() {
        System.out.println(
                "Title: " + title +
                        "\nCourse: " + course.nameValue() +
                        "\nEstimated Hours: " + estimatedHours +
                        "\nDays left until Deadline: " + daysUntilDue +
                        "\nCompleted: " + completed);
    }

    public int getEstimatedHoursValue() {   // helper for calculations
        return estimatedHours;
    }

    public boolean isCompletedValue() {
        return completed;
    }

    public Course getCourseValue() {
        return course;
    }
}

class StudySession {
    private Course course;
    private int minutes;

    public StudySession(Course course, int minutes) {
        this.course = course;
        this.minutes = minutes;
    }

    public void hours() {
        double h = minutes / 60.0;
        System.out.println("Study hours for " + course.nameValue() + ": " + h + "\n");
    }

    public void displaySession() {
        System.out.println("StudySession: " +
                "\ncourse: " + course.nameValue() +
                "\nminutes: " + minutes + "\n");
    }

    public double hoursValue() {   // helper for totals
        return minutes / 60.0;
    }

    public Course getCourseValue() {
        return course;
    }
}

public class Main {
    public static void main(String[] args) {

        Course oop = new Course("OOP", "Talant Arystanov", 6);
        Course dm = new Course("Discrete Math", "Ahmad Sarosh", 4);

        AssignmentTask t1 = new AssignmentTask("HW: Presentation about classes and methods", oop, 3, 1);
        AssignmentTask t2 = new AssignmentTask("HW: Limits Homework 1", dm, 4, 5);

        StudySession s1 = new StudySession(oop, 90);
        StudySession s2 = new StudySession(dm, 120);

        System.out.println("COURSES:");
        oop.displayCourse();
        dm.displayCourse();

        System.out.println("\nTASKS:");
        t1.displayTask();
        t1.isUrgent();

        t2.displayTask();
        t2.isUrgent();

        System.out.println("\nSTUDY SESSIONS:");
        s1.displaySession();
        s1.hours();

        s2.displaySession();
        s2.hours();

        System.out.println("\nMarking Lab 1 completed...");
        t1.markCompleted();
        t1.displayTask();

        int remaining = 0;
        if (!t1.isCompletedValue()) remaining += t1.getEstimatedHoursValue();
        if (!t2.isCompletedValue()) remaining += t2.getEstimatedHoursValue();

        System.out.println("Remaining estimated hours: " + remaining);
        System.out.println("Remaining estimated hours: " + remaining);
    }
}