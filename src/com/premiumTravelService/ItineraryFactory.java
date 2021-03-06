package com.premiumTravelService;


///     Static factory to generate itinerary output
///     Will not generate if trip is not in Complete state
public class ItineraryFactory {

    public static String get(Trip trip) throws Exception{
        //throw exception if trip not in valid state
        ValidateTripCanProduceItinerary(trip);

        //decorate the itinerary with various sections and return output
        IItineraryComponent itinerary = new Itinerary(trip);
        itinerary = new ItineraryAppendSeparator(itinerary);
        itinerary = new ItineraryAppendBookingDetails(itinerary);
        itinerary = new ItineraryAppendSeparator(itinerary);
        itinerary = new ItineraryAppendAddDestinations(itinerary);
        itinerary = new ItineraryAppendSeparator(itinerary);
        itinerary = new ItineraryAppendAddPackages(itinerary);
        itinerary = new ItineraryAppendSeparator(itinerary);
        itinerary = new ItineraryAppendAddContactPerson(itinerary);
        itinerary = new ItineraryAppendSeparator(itinerary);
        itinerary = new ItineraryAppendAddTravellers(itinerary);
        itinerary = new ItineraryAppendSeparator(itinerary);
        itinerary = new ItineraryAppendThankYou(itinerary);
        itinerary = new ItineraryAppendSeparator(itinerary);

        return itinerary.output();
    }

    //Used to verify the factory will produce an itinerary.
    //If not checked prior to calling factory, exception can occur.
    public static boolean TripCanProduceItinerary(Trip trip){
        //!!!!THIS IS NOT EXACTLY THE SAME AS C#!!!!!!!!
        // there is no 'name of' operator in Java. if there is time
        // I might search for 3rd party library
        assert trip != null : "trip != null";

        return trip.getTripStateStatus() == TripState.Status.Complete;
    }

    public static void ValidateTripCanProduceItinerary(Trip trip) throws Exception{
        //!!!!THIS IS NOT EXACTLY THE SAME AS C#!!!!!!!!
        // there is no 'name of' operator in Java. if there is time
        // I might search for 3rd party library
        assert trip != null : "trip != null";

        if(!TripCanProduceItinerary(trip))
            throw new Exception("trip must be in complete state to generate itinerary. currently in " + trip.getTripStateStatus());
    }
}
