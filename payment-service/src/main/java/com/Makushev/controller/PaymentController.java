package com.Makushev.controller;

import com.Makushev.domain.PaymentMethod;
import com.Makushev.dto.BookingDTO;
import com.Makushev.dto.UserDTO;
import com.Makushev.model.PaymentOrder;
import com.Makushev.reponse.PaymentLinkResponse;
import com.Makushev.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @RequestBody BookingDTO booking,
            @RequestParam PaymentMethod paymentMethod) throws StripeException {

        UserDTO user = new UserDTO();
        user.setFullName("Daniel");
        user.setEmail("parodiathe@mail.ru");
        user.setId(1L);

        PaymentLinkResponse res = paymentService.createOrder(user, booking, paymentMethod);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(
            @PathVariable Long paymentOrderId
    ) throws Exception {

        PaymentOrder res = paymentService.getPaymentOrderById(paymentOrderId);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/proceed")
    public ResponseEntity<Boolean> proceedPayment(
            @RequestParam String paymentId,
            @RequestParam String paymentLinkId

    ) throws Exception {

        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);

        Boolean res = paymentService.proceedPayment(paymentOrder,
                paymentId,
                paymentLinkId);

        return ResponseEntity.ok(res);
    }


}
