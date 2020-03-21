import java.util.Scanner;

public class three {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print(" input : ");
        int input = in.nextInt();

        for (int i=1; i<(input*2); i += 2)
        {
            for (int k=0; k < (4 - i / 2); k++)
            {
                System.out.print(" ");
            }
            for (int j=0; j<i; j++)
            {
                System.out.print("*");
            }
            System.out.println("");
        }

    }
}
