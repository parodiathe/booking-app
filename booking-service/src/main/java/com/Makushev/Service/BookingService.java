package com.Makushev.Service;

import com.Makushev.domain.BookingStatus;
import com.Makushev.model.Booking;
import com.Makushev.model.SalonReport;
import com.Makushev.payload.BookingRequest;
import com.Makushev.payload.SalonDTO;
import com.Makushev.payload.ServiceDTO;
import com.Makushev.payload.UserDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingRequest booking,
                          UserDTO user,
                          SalonDTO salon,
                          Set<ServiceDTO> serviceDTOSet) throws Exception;

    List<Booking> getBookingsByCustomer(Long customerId);
    List<Booking> getBookingBySalon(Long salonId);
    Booking getBookingById(Long id) throws Exception;
    Booking updateBooking(Long bookingId, BookingStatus status) throws Exception;
    List<Booking> getBookingsByDate(LocalDate date, Long salonId);
    SalonReport getSalonReport(Long salonId);

}
