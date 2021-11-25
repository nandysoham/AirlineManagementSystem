package company;

import com.mongodb.*;

import java.util.Scanner;

public class findingflights {
    /*
    * to find the airport id from the input given by the user!!!
    * */
    private String findaiportdetails(String city){
        String airportid = "";
        try{
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            DB database = mongoClient.getDB("NordicAirline");
            DBCollection collection = database.getCollection("airportdetails");
            BasicDBObject document = new BasicDBObject();

            BasicDBObject searchairport = new BasicDBObject();
            searchairport.put("name",city);
            DBCursor cursor = collection.find(searchairport);

            if(cursor.hasNext()){
                airportid = cursor.one().get("_id").toString();
            }

            return airportid;

        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("some internal server error occured");
            return airportid;
        }


    }

    /*
    * This is for checking whether the cities inputed are there in the map of Nordic Airlines
    * */
    private boolean showsearchparams(String depcity, String arrcity){
        String depid = findaiportdetails(depcity);
        if(depid.equals("")){
            System.out.println("Sorry the departure city doesnot exist on our map");
            System.out.println("You can check the nearby cities for your travel");
            return false;
        }

        String arrid = findaiportdetails((arrcity));
        if(arrid.equals("")){
            System.out.println("Sorry the arrival city doesnot exist on our map");
            System.out.println("You can check the nearby cities for your travel");
            return false;
        }

        System.out.println(depid);
        System.out.println(arrid);
        flightsearchalgorithms fla = new flightsearchalgorithms();
        fla.interactor(depcity, arrcity);

        return true;
    }



    /*
    * This is for selecting the departure and arrival cities
    * JUST FRONT END ONLY
    * */
    public void showdistplay(){
        boolean times = false;
        Scanner sc = new Scanner(System.in);
        while(!times){

            System.out.println("Search for the widest range of flights from NORDIC Airlines");
            System.out.println();
            System.out.println("City you are travelling from -");
            String deptcity = sc.next();
            System.out.println("City you are heading to - ");
            String arrcity =  sc.next();
            times = showsearchparams(deptcity,arrcity);
        }
    }

}
