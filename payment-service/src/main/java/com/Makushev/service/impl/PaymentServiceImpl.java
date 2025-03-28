package com.Makushev.service.impl;

import com.Makushev.domain.PaymentMethod;
import com.Makushev.domain.PaymentOrderStatus;
import com.Makushev.dto.BookingDTO;
import com.Makushev.dto.UserDTO;
import com.Makushev.model.PaymentOrder;
import com.Makushev.reponse.PaymentLinkResponse;
import com.Makushev.repository.PaymentOrderRepository;
import com.Makushev.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentOrderRepository paymentOrderRepository;

    @Value("${stripe.api.publishableKey}")
    private String stripePublishedKey;

    @Value("${stripe.api.secretKey}")
    private String stripeSecretKey;

    private String extractPaymentLinkId(String paymentUrl) {
        String[] parts = paymentUrl.split("/pay/");
        if (parts.length > 1) {
            return parts[1].split("#")[0];
        }
        return null;
    }

    @Override
    public PaymentLinkResponse createOrder(UserDTO user,
                                           BookingDTO booking,
                                           PaymentMethod paymentMethod) throws StripeException {
        Long amount = (long) booking.getTotalPrice();

        PaymentOrder order = new PaymentOrder();
        order.setAmount(amount);
        order.setPaymentMethod(paymentMethod);
        order.setBookingId(booking.getId());
        order.setSalonId(booking.getSalonId());
        order.setUserId(user.getId());

        PaymentOrder savedOrder = paymentOrderRepository.save(order);

        String paymentUrl = createStripePaymentLink(user,
                savedOrder.getAmount(),
                savedOrder.getId());

        String paymentLinkId = extractPaymentLinkId(paymentUrl);

        savedOrder.setPaymentLinkId(paymentLinkId);
        paymentOrderRepository.save(savedOrder);

        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
        paymentLinkResponse.setGetPayment_link_url(paymentUrl);

        return paymentLinkResponse;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        PaymentOrder paymentOrder = paymentOrderRepository.findById(id).orElse(null);
        if (paymentOrder == null) {
            throw new Exception("payment order not found");
        }
        return paymentOrder;
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {
        return paymentOrderRepository.findByPaymentLinkId(paymentId);
    }

    @Override
    public String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success/" + orderId)
                .setCancelUrl("http://localhost:3000/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("rub")
                                .setUnitAmount(amount * 100) // до копеек
                                .setProductData(SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData
                                        .builder().setName("Make an appointment").build()
                                ).build()
                        ).build()
                ).build();

        Session session = Session.create(params);

        return session.getUrl();
    }

    @Override
    public Boolean proceedPayment(PaymentOrder paymentOrder,
                                  String paymentId,
                                  String paymentLinkId) {

        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderRepository.save(paymentOrder);
            return true;
        }

        return false;
    }
}

