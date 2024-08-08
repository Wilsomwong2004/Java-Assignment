
import java.util.Scanner;

public class Lab2Exe7 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Please Enter your Grade: ");
        int marks = input.nextInt();
        String grade=" ",disc=" ";
        if(marks <= 40){
            grade = "F";
            disc = "Fail";
        }
        else if(marks >= 41 && marks <= 49){
            grade = "F+";
            disc = "Fail";
        }
        else if(marks >= 50 && marks <= 54){
            grade = "D";
            disc = "Pass";
        }
        else if(marks >= 55 && marks <= 64){
            grade = "C";
            disc = "Pass";
        }
        else if(marks >= 65 && marks <= 74){
            grade = "B";
            disc = "Pass";
        }
        else if(marks >= 75 && marks <= 84){
            grade = "A";
            disc = "Pass";
        }
        else if(marks >= 85 && marks <= 100){
            grade = "A+";
            disc = "Pass";
        }
        else{
            grade = "Invalid Marks";
            disc = "Invalid Marks";
        }
        System.out.println("Your Grade is: " + grade);
        System.out.println("Your Disc is: " + disc);
        System.out.println("Do you want to continue? (y/n)");

        String choice = input.next();
        if(choice.equalsIgnoreCase("y")){
            main(args);
        }
    }
}
