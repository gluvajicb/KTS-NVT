package tim20.KTS_NVT.controller;


import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tim20.KTS_NVT.dto.UserDTO;
import tim20.KTS_NVT.exceptions.*;
import tim20.KTS_NVT.model.Error;
import tim20.KTS_NVT.repository.UserRoleRepository;
import tim20.KTS_NVT.security.PayPalClient;
import tim20.KTS_NVT.security.PayPalOrder;
import tim20.KTS_NVT.security.UserTokenState;
import tim20.KTS_NVT.service.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/security")
public class SecurityController {

    @Autowired
    private UserService userService;

    @Autowired
    private PayPalClient payPalClient;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserTokenState> login(@Valid @RequestBody UserDTO user) {
        UserTokenState tokenState = userService.loginUser(user);
        return new ResponseEntity<>(tokenState, HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Boolean> register(@Valid @RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public ResponseEntity<Boolean> verifyAccount(@RequestParam String email, @RequestParam String token) {
        return new ResponseEntity<Boolean>(userService.verifyAccount(email, token), HttpStatus.OK);
    }

    @RequestMapping(value = "/authorize-paypal-transaction", method = RequestMethod.POST)
    public HttpResponse<Order> authorizePaypalTransaction(@RequestBody PayPalOrder order) {
        OrdersAuthorizeRequest request = new OrdersAuthorizeRequest(order.getOrderID());
        request.requestBody(buildRequestBodyForAuthorization());
        // 3. Call PayPal to authorization an order
        HttpResponse<Order> response = null;
        try {
            response = payPalClient.client().execute(request);
            response.headers().header("Location", "http://localhost:8080/security/authorize-paypal-transaction");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 4. Save the authorization ID to your database. Implement logic to save the authorization to your database for future reference.
//        if (debug) {
//            System.out.println("Authorization Ids:");
//            response.result().purchaseUnits()
//                    .forEach(purchaseUnit -> purchaseUnit.payments()
//                            .authorizations().stream()
//                            .map(authorization -> authorization.id())
//                            .forEach(System.out::println));
//            System.out.println("Link Descriptions: ");
//            for (LinkDescription link : response.result().links()) {
//                System.out.println("\t" + link.rel() + ": " + link.href());
//            }
//            System.out.println("Full response body:");
//            System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
//        }
        return response;
    }

    @RequestMapping(value = "/create-paypal-transaction", method = RequestMethod.POST)
    public HttpResponse<Order> createPayPalOrder() {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.prefer("return=representation");
        request.requestBody(buildRequestBodyForCreating());
        //3. Call PayPal to set up a transaction
        HttpResponse<Order> response = null;
        try {
            response = payPalClient.client().execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        if (debug) {
//            if (response.statusCode() == 201) {
//                System.out.println("Status Code: " + response.statusCode());
//                System.out.println("Status: " + response.result().status());
//                System.out.println("Order ID: " + response.result().id());
//                System.out.println("Intent: " + response.result().checkoutPaymentIntent();
//                System.out.println("Links: ");
//                for (LinkDescription link : response.result().links()) {
//                    System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
//                }
//                System.out.println("Total Amount: " + response.result().purchaseUnits().get(0).amountWithBreakdown().currencyCode()
//                        + " " + response.result().purchaseUnits().get(0).amountWithBreakdown().value());
//            }
//        }
        return response;
    }

    private OrderActionRequest buildRequestBodyForAuthorization() {
        return new OrderActionRequest();
    }

    private OrderRequest buildRequestBodyForCreating() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext().brandName("EXAMPLE INC").landingPage("BILLING")
                .shippingPreference("SET_PROVIDED_ADDRESS");
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<PurchaseUnitRequest>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().referenceId("PUHF")
                .description("Sporting Goods").customId("CUST-HighFashions").softDescriptor("HighFashions")
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value("230.00")
                        .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("USD").value("180.00"))
                                .shipping(new Money().currencyCode("USD").value("30.00"))
                                .handling(new Money().currencyCode("USD").value("10.00"))
                                .taxTotal(new Money().currencyCode("USD").value("20.00"))
                                .shippingDiscount(new Money().currencyCode("USD").value("10.00"))))
                .items(new ArrayList<Item>() {
                    {
                        add(new Item().name("T-shirt").description("Green XL").sku("sku01")
                                .unitAmount(new Money().currencyCode("USD").value("90.00"))
                                .tax(new Money().currencyCode("USD").value("10.00")).quantity("1")
                                .category("PHYSICAL_GOODS"));
                        add(new Item().name("Shoes").description("Running, Size 10.5").sku("sku02")
                                .unitAmount(new Money().currencyCode("USD").value("45.00"))
                                .tax(new Money().currencyCode("USD").value("5.00")).quantity("2")
                                .category("PHYSICAL_GOODS"));
                    }
                })
                .shippingDetail(new ShippingDetail().name(new Name().fullName("John Doe"))
                        .addressPortable(new AddressPortable().addressLine1("123 Townsend St").addressLine2("Floor 6")
                                .adminArea2("San Francisco").adminArea1("CA").postalCode("94107").countryCode("US")));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }

    @ExceptionHandler(FieldsRequiredException.class)
    public ResponseEntity<Error> fieldsRequired()
    {
        Error error = new Error(1,"All fields must be provided.");
        return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<Error> wrongCredentials()
    {
        Error error = new Error(1,"Provided credentials don't match with any account.");
        return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<Error> usernameTaken()
    {
        Error error = new Error(1,"Username is taken, please choose another one.");
        return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailInUseException.class)
    public ResponseEntity<Error> emailInUse()
    {
        Error error = new Error(1,"Email is already in use, try logging in.");
        return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordsNotMatchingException.class)
    public ResponseEntity<Error> passwordsDontMatch()
    {
        Error error = new Error(1,"Password confirmation is incorrect.");
        return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WrongVerificationTokenAndEmail.class)
    public ResponseEntity<Error> wrongVerificationData()
    {
        Error error = new Error(1,"Provided verification data is not valid.");
        return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(FieldLengthException.class)
    public ResponseEntity<Error> eventNotFound(FieldLengthException e) {
        String message = String.format("Field %s must be between %d and %d characters long.", e.getFieldName(), e.getMinSize(), e.getMaxSize());
        Error error = new Error(1, message);
        return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }
}
