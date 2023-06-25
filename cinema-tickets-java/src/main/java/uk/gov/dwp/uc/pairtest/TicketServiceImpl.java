package uk.gov.dwp.uc.pairtest;

import java.util.*;

import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import  thirdparty.seatbooking.SeatReservationService;

public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */
    private int ticketCount = 0;
    private int ticketAmountTotal = 0;
    private int noOfSeats = 0;
    private boolean adultFlag = false;
    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        // Checking if the accountId of the user is valid or not
        if(accountId > 0){

            // for loop to iterate through multiple tickets in the function argument
            for(TicketTypeRequest t: ticketTypeRequests){
                System.out.println("TICKET COUNT "+ticketCount);
                //Checking if the no of tickets requested is greater than 20
                if(t.getNoOfTickets() > 20 ){
                    System.out.println("TICKET LIMIT EXCEEDED");
                    throw new InvalidPurchaseException();
                }

                // incrementing the ticket count
                ticketCount = ticketCount + t.getNoOfTickets();
                System.out.println("TICKET COUNT "+ticketCount);
                //Checking if the current no of tickets count is greater than 20
                if(ticketCount > 20){
                    System.out.println("TICKET LIMIT EXCEEDED");
                    throw new InvalidPurchaseException();
                }
                //Checking if the ticket type is ADULT or not
                if(t.getTicketType() == TicketTypeRequest.Type.ADULT ){
                    // Setting the adultflag variable to true if the type is ADULT
                    // This is done to check if there is are adults involved in booking the tickets
                    adultFlag = true;

                    //Calculating the amount for the ADULT ticket price
                    ticketAmountTotal = ticketAmountTotal + (t.getNoOfTickets() * 20);
                }

                //Checking if the ticket type is CHILD or not
                if(t.getTicketType() == TicketTypeRequest.Type.CHILD ){

                    //Calculating the amount for the CHILD ticket price
                    ticketAmountTotal = ticketAmountTotal + (t.getNoOfTickets() * 10);
                }

                ////Checking if the ticket type is NOT INFANT
                if(t.getTicketType() != TicketTypeRequest.Type.INFANT ){
                    //Calculating the number of seats for types CHILD and ADULT
                    noOfSeats = noOfSeats + t.getNoOfTickets();
                }

            }
            System.out.println("The number of seats = "+noOfSeats);
            System.out.println("The price for seats = "+ticketAmountTotal);

            // Checking if there is ADULT included in the tickets
            if(adultFlag){

                // Creating the instance for making payment using TicketPaymentServiceImpl class
                TicketPaymentServiceImpl tp = new TicketPaymentServiceImpl();
                tp.makePayment(accountId,ticketAmountTotal);

                // Creating the instance for reserving seat using SeatReservationService class
                SeatReservationService s = new SeatReservationServiceImpl();
                s.reserveSeat(accountId, noOfSeats);


            }
            else{
                System.out.println("No ADULT tickets found while booking the ticket for CHILD OR INFANT");
                throw new InvalidPurchaseException();
            }

        }
        else{
            throw new InvalidPurchaseException();
        }

    }

}
