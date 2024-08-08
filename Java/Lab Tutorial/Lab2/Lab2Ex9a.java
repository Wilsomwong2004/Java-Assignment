public class Lab2Ex9a {
    public static void main(String[] args) {
        
        
        double currentFee = 10000;
        double futureFee = 0;
        
        
        for (int i = 1; i <= 10; i++){
            futureFee = currentFee + (currentFee * 0.05);
            System.out.println("Fee after " + i + " years: " + futureFee);
            currentFee = futureFee;
        }
        //"%.2f \n" stands for 2 decimal points (NEED TO USE "printf", NOT "println")//
        System.out.printf("Fee after 10 Years: %.2f \n" , futureFee);
    }
}