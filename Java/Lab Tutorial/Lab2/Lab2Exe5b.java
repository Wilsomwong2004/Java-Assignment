import java.util.Scanner;

public class Lab2Exe5b {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Please Enter a code (0-128) : ");
        int code = input.nextInt();
        char ch = (char)code;
        System.out.println(code + " code =" + ch + " Character");
        
    }
}
