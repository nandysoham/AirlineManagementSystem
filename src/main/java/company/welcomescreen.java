package company;

import java.util.Scanner;
/*
* This is the main Welcome screen which the user will first interact with
* */
public class welcomescreen {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("------------NORDIC AIRLINES ------------");
            System.out.println();
            System.out.println();
            System.out.println("Welcome to Nordic Airlines !!!");
            System.out.println("press -1 for admin panel");
            System.out.println("press 1 for flight bookings");
            System.out.println("press 2 for logging in to your account");
            System.out.println("pring 100 for breaking the loop");

            int webchoice = sc.nextInt();
            if(webchoice == 1){
                findingflights ff = new findingflights();
                ff.showdistplay();

            }
            else if(webchoice == 2){

            }

            if(webchoice == -1){
                System.out.println("Welcome admin");
                System.out.println("Please enter your username");
                String adminusername = sc.next();
                System.out.println("please enter your password");
                String adminpassword = sc.next();
                System.out.println(adminusername + " "+ adminpassword);
                if(adminusername.equals("nordic") && adminpassword.equals("abcd123")){
                    System.out.println("Welcome to the superuser dashboard");
                    superuser su = new superuser();
                    su.superuserscreen();
                }
                else{
                    System.out.println("Access Denied!!!");
                }

            }

            if(webchoice == 100){
                break;
            }
        }

    }
}
