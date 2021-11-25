package company;
import com.mongodb.*;

import java.sql.SQLOutput;
import java.util.Scanner;

/*
* This is the main superuser class which has access to flight modication , extending services, creating new routes
* */
public class superuser {

    public void superuserscreen(){
        Scanner sc = new Scanner((System.in));
        System.out.println("Hello Superuser");
        System.out.println("press 1 for assigning flights");
        System.out.println("press 2 for assigning airport");
        int suchoice = sc.nextInt();
        if(suchoice == 1){
            assignflights();
        }

        if(suchoice == 2){
            assignairports();
        }
    }

    /*
    * This is for creating new flights in the routes
    * private --> only accessible to the superuser
    * */
    private void assignflights(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the following details --");
        System.out.println("Enter the designmated flight number");
        String flighno = sc.next();
        System.out.println("Enter the aircraft type");
        String aircrafttype = sc.next();

        System.out.println("Enter the departure time from the source");
        String departure = sc.next();
        System.out.println("Enter the arrival time in HH:MM");
        String arrival = sc.next();
        System.out.println("Enter the source airport");
        String source = sc.next();
        System.out.println("Enter the destination airport");
        String destination = sc.next();
        System.out.println("Enter the time of travel");
        String timeoftravel = sc.next();
        System.out.println("Enter the total number of seats");
        String totalseats = sc.next();
        System.out.println("Enter the number of business seats");
        String businessseats = sc.next();
        System.out.println("Enter the number of economy seats");
        String economyseats = sc.next();


        // database connections
        try{

            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            DB database = mongoClient.getDB("NordicAirline");
            DBCollection collection = database.getCollection("flightdetails");
            BasicDBObject document = new BasicDBObject();


            document.put("flightno", flighno);
            document.put("aircrafttype",aircrafttype);
            document.put("arrival", arrival);
            document.put("departure",departure);
            document.put("source",source);
            document.put("destination",destination);
            document.put("timeoftravel",timeoftravel);
            document.put("totalseats",totalseats);
            document.put("businessseats",businessseats);
            document.put("economyseats",economyseats);


            collection.insert(document);
            System.out.println("entry noted");
        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("some internal sercer error occured during pushing of flight details");
        }
    }


    private void assignairports(){
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter the name of the Airport");
        String airportname = sc.nextLine();
        System.out.println("Enter the full name of the airport");
        String airportfullname = sc.nextLine();
        System.out.println("Enter the IATA code of the airport");
        String iatacode = sc.next();
        System.out.println("Number of runways");
        String noofrunways = sc.next();
        System.out.println("Number of gates");
        String noofgates = sc.next();


            // database connections
            try{

                MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
                DB database = mongoClient.getDB("NordicAirline");
                DBCollection collection = database.getCollection("airportdetails");
                BasicDBObject document = new BasicDBObject();


                document.put("name", airportname);
                document.put("fullname",airportfullname);
                document.put("IATAcode",iatacode);
                document.put("runways",noofrunways);
                document.put("gates",noofgates);


                collection.insert(document);
                System.out.println("airport entry noted");

        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("some internal error occured while pushing the airport data");
        }
    }
}
