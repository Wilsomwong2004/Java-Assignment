import java.util.Scanner;

public class Lab2Exe3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a 3 digit value (0...1000): ");
        int num = input.nextInt();
        System.out.println("Num Entered:" + num);
        int sum = 0;

        while (num > 0) {
            
            sum += num % 10;
            num /= 10;
        }

        System.out.println("Sum of digits:" + sum);

    }
}