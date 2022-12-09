package com.binar.tix.repository;

import com.binar.tix.entities.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrdersRepository extends JpaRepository<Orders, String> {

    @Query(nativeQuery = true, value = "SELECT count(*) as status" +
            " FROM public.orders a inner join  orders_details b on a.invoice_no = b.invoice_no where a.schedule_id = :scheduleId and b.seats_id = :seatsId")
    StatusSeats checkSeats(@Param("scheduleId") int scheduleId, @Param("seatsId") int seatsId);

    Page<Orders> findByUserId(int userId, Pageable paging);
}
