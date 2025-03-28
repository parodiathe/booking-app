package com.Makushev.service;

import com.Makushev.domain.PaymentMethod;
import com.Makushev.dto.BookingDTO;
import com.Makushev.dto.UserDTO;
import com.Makushev.model.PaymentOrder;
import com.Makushev.reponse.PaymentLinkResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;

public interface PaymentService {

    PaymentLinkResponse createOrder(UserDTO user,
                                    BookingDTO booking,
                                    PaymentMethod paymentMethod) throws StripeException;

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentOrderByPaymentId(String paymentId);

    String createStripePaymentLink(UserDTO user,
                                        Long amount,
                                        Long orderId) throws StripeException;

    Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId);
}


