class Main {
    public static void main(String[] args) {
        for (int row = 0; row < 5; row++) {
            if (row == 0 || row == 4) {
                for (int colomn = 0; colomn < 8; colomn++) {
                    System.out.print("*");
                }
                System.out.println("");
            } else {
                for (int colomn = 0; colomn < 8; colomn++) {
                    if (colomn == 0 || colomn ==7) {
                        System.out.print("*");
                    }
                    else {
                        System.out.print(" ");
                    }
                }
                System.out.println("");
            }
        }

    }
}
