public class Lab2Exe8 {
    public static void main(String[] args) {
        double [] myArray = new double[100];
        for(int i = 0; i < 100; i++){
            myArray[i] = Math.random();
            System.out.println("myArray[" + i + "] = " + myArray[i]);
        }
        double mySum=0;
        for(double x: myArray){
            mySum = mySum + x;
            System.out.println("Sum of ALl the values: " + Math.round(mySum));
        }
    }
}
