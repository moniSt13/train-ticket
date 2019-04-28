package order.controller;

import order.entity.*;
import order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/orderservice")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ Order Service ] !";
    }

    /***************************For Normal Use***************************/

    @PostMapping(value = "/order/tickets")
    public HttpEntity getTicketListByDateAndTripId(@RequestBody Seat seatRequest, @RequestHeader HttpHeaders headers) {
        System.out.println("[Order Service][Get Sold Ticket] Date:" + seatRequest.getTravelDate().toString());
        return ok(orderService.getSoldTickets(seatRequest, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/order")
    public HttpEntity createNewOrder(@RequestBody Order createOrder, @RequestHeader HttpHeaders headers) {
        System.out.println("[Order Service][Create Order] Create Order form " + createOrder.getFrom() + " --->"
                + createOrder.getTo() + " at " + createOrder.getTravelDate());
        System.out.println("[Order Service][Verify Login] Success");
        return ok(orderService.create(createOrder, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/order/admin")
    public HttpEntity addcreateNewOrder(@RequestBody Order order, @RequestHeader HttpHeaders headers) {
        return ok(orderService.addNewOrder(order, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/order/query")
    public HttpEntity queryOrders(@RequestBody OrderInfo qi,
                                  @RequestHeader HttpHeaders headers) {
        System.out.println("[Order Other Service][Query Orders] Query Orders for " + qi.getLoginId());
        System.out.println("[Order Other Service][Verify Login] Success");
        return ok(orderService.queryOrders(qi, qi.getLoginId(), headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/order/refresh")
    public HttpEntity queryOrdersForRefresh(@RequestBody OrderInfo qi,
                                            @RequestHeader HttpHeaders headers) {
        System.out.println("[Order Other Service][Query Orders] Query Orders for " + qi.getLoginId());
        // ArrayList<Order>
        return ok(orderService.queryOrdersForRefresh(qi, qi.getLoginId(), headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/order/{travelDate}/{trainNumber}")
    public HttpEntity calculateSoldTicket(@PathVariable Date travelDate, @PathVariable String trainNumber,
                                          @RequestHeader HttpHeaders headers) {
        System.out.println("[Order Other Service][Calculate Sold Tickets] Date:" + travelDate + " TrainNumber:"
                + trainNumber);
        return ok(orderService.queryAlreadySoldOrders(travelDate, trainNumber, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/order/price/{orderId}")
    public HttpEntity getOrderPrice(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Order Other Service][Get Order Price] Order Id:" + orderId);
        // String
        return ok(orderService.getOrderPrice(orderId, headers));
    }


    @CrossOrigin(origins = "*")
    @GetMapping(path = "/order/orderPay/{orderId}")
    public HttpEntity payOrder(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Order Other Service][Pay Order] Order Id:" + orderId);
        // Order
        return ok(orderService.payOrder(orderId, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/order/{orderId}")
    public HttpEntity getOrderById(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Order Other Service][Get Order By Id] Order Id:" + orderId);
        // Order
        return ok(orderService.getOrderById(orderId, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/order/status/{orderId}/{status}")
    public HttpEntity modifyOrder(@PathVariable String orderId, @PathVariable int status, @RequestHeader HttpHeaders headers) {
        System.out.println("[Order Other Service][Modify Order Status] Order Id:" + orderId);
        // Order
        return ok(orderService.modifyOrder(orderId, status, headers));
    }


    @CrossOrigin(origins = "*")
    @GetMapping(path = "/order/security/{checkDate}/{accountId}")
    public HttpEntity securityInfoCheck(@PathVariable Date checkDate, @PathVariable String accountId,
                                        @RequestHeader HttpHeaders headers) {
        System.out.println("[Order Other Service][Security Info Get]" + accountId);
        return ok(orderService.checkSecurityAboutOrder(checkDate, accountId, headers));
    }


    @CrossOrigin(origins = "*")
    @PutMapping(path = "/order")
    public HttpEntity saveOrderInfo(@RequestBody Order orderInfo,
                                    @RequestHeader HttpHeaders headers) {

        System.out.println("[Order Other Service][Verify Login] Success");
        return ok(orderService.saveChanges(orderInfo, headers));
    }

    @CrossOrigin(origins = "*")
    @PutMapping(path = "/order/admin")
    public HttpEntity updateOrder(@RequestBody Order order, @RequestHeader HttpHeaders headers) {
        // Order
        return ok(orderService.updateOrder(order, headers));
    }


    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/order/{orderId}")
    public HttpEntity deleteOrder(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Order Other Service][Delete Order] Order Id:" + orderId);
        // Order
        return ok(orderService.deleteOrder(orderId, headers));
    }

    /***************For super admin(Single Service Test*******************/

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/order")
    public HttpEntity findAllOrder(@RequestHeader HttpHeaders headers) {
        System.out.println("[Order Other Service][Find All Order]");
        // ArrayList<Order>
        return ok(orderService.getAllOrders(headers));
    }

//    private VerifyResult verifySsoLogin(String loginToken, @RequestHeader HttpHeaders headers) {
//        System.out.println("[Order Service][Verify Login] Verifying....");
//
//        HttpEntity requestTokenResult = new HttpEntity(null, headers);
//        ResponseEntity<VerifyResult> reTokenResult = restTemplate.exchange(
//                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
//                HttpMethod.GET,
//                requestTokenResult,
//                VerifyResult.class);
//        VerifyResult tokenResult = reTokenResult.getBody();
////        VerifyResult tokenResult = restTemplate.getForObject(
////                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
////                VerifyResult.class);
//
//
//        return tokenResult;
//    }

}
