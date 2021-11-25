package company;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
* This is the main flight searching algorithm function
*
*
* Precomputation -->
* first assign all the available airports (by name) a value --> in a for loop
* assigning edges to the nodes whereever flights are available
*
*
* ===> computing --> all possible paths b/w the vertices and selecting the top routes only
*
*
* ===> giving all the choice to the user to select k-stopping flights
*
*
* */

class Graph{
    private int v;
    private ArrayList<Integer>[] adjList;

    ArrayList <ArrayList <Integer> >  allpaths = new ArrayList< ArrayList <Integer> > (5);

    public Graph(int vertices){
        this.v = vertices;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getV() {
        return v;
    }

    @SuppressWarnings("unchecked")
    protected void initadjList(){
        adjList = new ArrayList[v];
        for(int i=0;i<v;i++){
            adjList[i] = new ArrayList<>();
        }
    }

    protected void addEdge(int u,int v){
        adjList[u].add(v);
    }

    protected void printAllPaths(int s, int d)
    {
        // s --> source , d --> destination
        boolean[] isVisited = new boolean[v];
        ArrayList<Integer> pathList = new ArrayList<>();

        // the first member is the source
        pathList.add(s);


        // kind of dfs to trace the path of the pointer moving
        printAllPathsUtil(s, d, isVisited, pathList);
    }

    private void printAllPathsUtil(Integer u, Integer d,
                                   boolean[] isVisited,
                                   ArrayList<Integer> localPathList)
    {

        if (u.equals(d)) {
            System.out.println(localPathList);
            allpaths.add(localPathList);
            System.out.println("the matrix here --> ");
            System.out.println(allpaths);
            // if match found then no need to traverse more till depth
            return;
        }

        // Mark the current node
        isVisited[u] = true;

        // Recur for all the vertices
        // adjacent to current vertex
        for (Integer i : adjList[u]) {
            if (!isVisited[i]) {
                // store current node
                // in path[]
                localPathList.add(i);
                printAllPathsUtil(i, d, isVisited, localPathList);

                // remove current node
                // in path[]
                localPathList.remove(i);
            }
        }

        // Mark the current node
        isVisited[u] = false;
    }


}


public class flightsearchalgorithms extends Graph {


    Map<String ,Integer> airportmap = new HashMap<>();
    Map<Integer,String> airportrevmap = new HashMap<>();

    public flightsearchalgorithms() {
        super(0);
    }

    private void precommpute(){
        try{
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            DB database = mongoClient.getDB("NordicAirline");
            DBCollection collection = database.getCollection("airportdetails");

            BasicDBObject searchairport = new BasicDBObject();
//            searchairport.put({});
            DBCursor cursor = collection.find(searchairport);
            int cnt = 0;
            while(cursor.hasNext()){
                String airportname = cursor.next().get("name").toString();
                airportmap.put(airportname,cnt);
                airportrevmap.put(cnt, airportname);
                cnt++;

            }

            // putting in all the vertices
            setV(cnt);


            // checking the map;
            for (Map.Entry<String, Integer> e : airportmap.entrySet())
                System.out.println(e.getKey() + " "
                        + e.getValue());
////
//            System.out.println(airportmap.get("London"));
//            System.out.println(airportrevmap.get(1));
        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("some internal server error cocured while loading the data!");
        }
    }


    //============================ Main Graph Search Algorithm ===============================
    private void pickallvertices(){

        try{

            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            DB database = mongoClient.getDB("NordicAirline");
            DBCollection collection = database.getCollection("flightdetails");

            BasicDBObject searchflights = new BasicDBObject();
            DBCursor cursor = collection.find(searchflights);

            while(cursor.hasNext()){
                DBObject document = cursor.next();
                String from = document.get("source").toString();
                String to = document.get("destination").toString();
//                System.out.println(from+" --> "+to);

                // note that this is a directed graph
//                System.out.println("the no of elements --> " + v);
                addEdge(airportmap.get(from), airportmap.get(to));

            }




        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("some internal server error occured while fetching the results of the airport");
        }

    }

    public void interactor(String from, String to){
        flightsearchalgorithms fla = new flightsearchalgorithms();
        fla.precommpute();

        fla.initadjList();
        fla.pickallvertices();
//        System.out.println(from+" "+to);
//        System.out.println(fla.airportmap.get(from));
//        System.out.println(fla.airportmap.get(to));
        fla.printAllPaths(fla.airportmap.get(from), fla.airportmap.get(to));


        // printing the arraylist matrix
//        System.out.println(allpaths);

//        for(int i=0;i<allpaths.size();i++){
//            System.out.println(allpaths.get(i));
//        }
    }

    // main function for testing the algorithm
    public static void main(String[] args) {

    }
}
