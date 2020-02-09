package tim20.KTS_NVT.controller;

import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tim20.KTS_NVT.model.Ticket;
import tim20.KTS_NVT.model.User;
import tim20.KTS_NVT.security.PayPalClient;
import tim20.KTS_NVT.security.PayPalOrder;
import tim20.KTS_NVT.service.EmailService;
import tim20.KTS_NVT.service.TicketService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private PayPalClient payPalClient;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/authorize-paypal-transaction", method = RequestMethod.POST)
    public ResponseEntity<PayPalOrder> authorizePaypalTransaction(@RequestBody PayPalOrder order) {
//        OrdersAuthorizeRequest request = new OrdersAuthorizeRequest(order.getOrderID());
//        request.requestBody(buildRequestBodyForAuthorization());
//        HttpResponse<Order> response = null;
//        try {
//            response = payPalClient.client().execute(request);
//            response.headers().header("Location", "http://localhost:8080/payment/authorize-paypal-transaction");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        OrdersCaptureRequest request = new OrdersCaptureRequest(order.getOrderID());
        request.requestBody(new OrderRequest());
        HttpResponse<Order> response = null;
        try {
            response = payPalClient.client().execute(request);
            response.headers().header("Location", "http://localhost:8080/payment/authorize-paypal-transaction");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Order authorizedOrder;
        PayPalOrder myOrder = new PayPalOrder();
        OrdersGetRequest request2 = new OrdersGetRequest(response.result().id());
        try {
            HttpResponse<Order> response2 = payPalClient.client().execute(request2);
            authorizedOrder = response2.result();
            myOrder.setOrderID(authorizedOrder.id());
            List<Ticket> tickets = new ArrayList<>();
            for (PurchaseUnit pu: authorizedOrder.purchaseUnits()) {
                Ticket ticket = ticketService.findOne(Long.valueOf(pu.customId()));
                ticket.setPaid(true);
                ticketService.saveTicket(ticket);
                tickets.add(ticket);
            }

            User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            emailService.sendOrderPaidEmail(tickets, u.getEmail());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(myOrder, HttpStatus.OK);
    }

    @RequestMapping(value = "/create-paypal-transaction", method = RequestMethod.POST)
    public ResponseEntity<PayPalOrder> createPayPalOrder(@RequestBody PayPalOrder order) {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.prefer("return=representation");
        request.requestBody(buildRequestBodyForCreating(new ArrayList<>(Arrays.asList(order.getTicketIds()))));
        HttpResponse<Order> response = null;
        try {
            response = payPalClient.client().execute(request);
            response.headers().header("Location", "http://localhost:8080/payment/create-paypal-transaction");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Order o = response.result();
        PayPalOrder myOrder = new PayPalOrder();
        myOrder.setOrderID(o.id());
        return new ResponseEntity<>(myOrder, HttpStatus.OK);
    }

    private OrderActionRequest buildRequestBodyForAuthorization() {
        return new OrderActionRequest();
    }

    private OrderRequest buildRequestBodyForCreating(List<Long> ticketIds) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext().brandName("KTS/NVT").landingPage("BILLING").shippingPreference("NO_SHIPPING");
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<PurchaseUnitRequest>();

        for (long id: ticketIds) {
            Ticket ticket = ticketService.findOne(id);
            PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().referenceId(ticket.getId().toString())
                    .description(ticket.getEvent().getDescription())
                    .customId(ticket.getId().toString())
                    .softDescriptor("Ticket #" + ticket.getId())
                    .amountWithBreakdown(new AmountWithBreakdown()
                            .currencyCode("EUR")
                            .value(ticket.getPrice().toString())
                            .amountBreakdown(new AmountBreakdown()
                                    .itemTotal(new Money().currencyCode("EUR").value(ticket.getPrice().toString()))
                                    .shipping(new Money().currencyCode("EUR").value("0.00"))
                                    .handling(new Money().currencyCode("EUR").value("0.00"))
                                    .taxTotal(new Money().currencyCode("EUR").value("0.00"))
                                    .shippingDiscount(new Money().currencyCode("EUR").value("0.00"))))
                    .items(new ArrayList<Item>() {
                        {
                            add(new Item().name("Ticket #" + ticket.getId()).description(ticket.getEvent().getDescription())
                                    .unitAmount(new Money().currencyCode("EUR").value(ticket.getPrice().toString()))
                                    .tax(new Money().currencyCode("EUR").value("0.00"))
                                    .quantity("1")
                                    .category("DIGITAL_GOODS"));
                        }
                    });
            purchaseUnitRequests.add(purchaseUnitRequest);
        }

        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }

}
