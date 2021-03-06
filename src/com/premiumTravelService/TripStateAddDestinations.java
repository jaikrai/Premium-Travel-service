package com.premiumTravelService;

import java.util.Scanner;

///     Concrete TripState class to add destinations.
///     Technically, validation and input should be
///     performed in other classes to keep responsibilities
///     single, but this will demonstrate the basic idea.
public class TripStateAddDestinations extends TripState{

    public TripStateAddDestinations(TripContext context){
        super(context, Status.AddDestinations);
    }

    private boolean isDestinationListValid(){
        if(!getTripContext().getTrip().getDestinations().isEmpty())
            return true;

        System.out.println("ERROR: At least ONE destination is required.");
        return false;
    }

    private boolean isDestinationValid(String newDestination){
        if(newDestination.isEmpty()){
            System.out.println("ERROR: Blank destinations are prohibited!");
            return false;
        }

        boolean isDuplicate = getTripContext().getTrip().getDestinations().contains(newDestination);
        if(isDuplicate)
            System.out.println("ERROR: Unique destinations only!");

        return !isDuplicate;
    }

    private boolean continueEnteringDestinations(String newDestination){
        boolean done = newDestination.toLowerCase().equals("done");

        if(done && !getTripContext().getTrip().getDestinations().isEmpty()){
            System.out.println();
            System.out.println("*** DESTINATIONS FINISHED: " + getTripContext().getTrip().getDestinations().size() + " entered ***");
        }

        return !done;
    }

    private void showCurrentDestinations(){
        if(!getTripContext().getTrip().getDestinations().isEmpty()){

            System.out.println("- Currently " + getTripContext().getTrip().getDestinations().size() + " in trip");

            for(int dest = 0; dest < getTripContext().getTrip().getDestinations().size(); dest++)
                System.out.println((dest + 1) + ". " + getTripContext().getTrip().getDestinations().get(dest));

            System.out.println();
        }
    }

    @Override
    public TripStateLoop.Status execute(){
        Scanner scanner = new Scanner(System.in);

        System.out.println();
        System.out.println("*** ADD DESTINATIONS ***");
        System.out.println();
        showCurrentDestinations();
        System.out.println(
                "- COMMAND: [later] to return later, [done] to finish destinations, or enter destination");

        Boolean getDestinations = true;
        while (getDestinations)
        {
            String newDestination = scanner.nextLine().trim();

            //come back later?
            if (returnLater(newDestination)) return TripStateLoop.Status.Stop;

            //check unique and continue entering
            if (continueEnteringDestinations(newDestination))
            {
                if (isDestinationValid(newDestination))
                {
                    getTripContext().getTrip().getDestinations().add(newDestination);
                    System.out.println("- Added destination [" + newDestination + "] add traveller or [done] to move next step");
                }
            }
            else
            {
                //stop if we can change state
                getDestinations = !isDestinationListValid();
            }
        }
        getTripContext().changeState(new TripStateAddPackages(getTripContext()));
        return TripStateLoop.Status.Continue;
    }
}
