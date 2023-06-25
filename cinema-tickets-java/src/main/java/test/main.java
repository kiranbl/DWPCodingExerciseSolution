package main;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
public class c {
    public static void main(String args[]){

        TicketServiceImpl t = new TicketServiceImpl();
        TicketTypeRequest t1 = new TicketTypeRequest(TicketTypeRequest.Type.CHILD,010);
        TicketTypeRequest t2 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT,7);
        TicketTypeRequest t3 = new TicketTypeRequest(TicketTypeRequest.Type.INFANT,3);
        t.purchaseTickets(98765L,t1,t2,t3);
    }
}
