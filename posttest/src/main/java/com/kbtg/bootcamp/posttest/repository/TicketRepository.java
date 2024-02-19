package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.model.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<TicketModel,Integer> {
    Optional<TicketModel> findById(Integer id);

    Optional<TicketModel> findByTicketId(String ticket);

    @Query(nativeQuery = true, value = "SELECT * FROM tickets WHERE status <> ?1")
    Optional<List<TicketModel>> findAllTicketsNotStatus(String status);

}
