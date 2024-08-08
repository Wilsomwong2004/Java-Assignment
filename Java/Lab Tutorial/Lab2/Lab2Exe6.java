
import java.util.Scanner;


public class Lab2Exe6 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the month :");
        int month = input.nextInt();
        System.out.println("Enter the year :");
        int year = input.nextInt();
        int days = 0;
        switch (month) {
            case 2 -> {
                if (year % 4 == 0 && (year % 100!= 0 || year % 400 == 0)) {
                    days = 29;
                    if (month == 4 || month == 6 || month == 9 || month == 11){
                        days = 30;
                    }
                    else{
                        days = 31;
                    }
                }
            }
            case 4, 6, 9, 11 -> days = 30;
            case 1,3,5,7,8,10,12 -> days = 31;
            default -> System.out.println("Invalid month");
        }
        if(days != 0){
            System.out.println("The number of days in " + month + "/" + year + " is " + days);
        }
    }
}
