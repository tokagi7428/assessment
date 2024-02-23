package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.model.UserTicketModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicketModel, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM user_ticket")
    Optional<List<UserTicketModel>> findByUserId(String userId);

    @Query(nativeQuery = true, value = "SELECT * FROM user_ticket where ticket = ?1 and transaction_type = ?2 and user_id = ?3")
    Optional<List<UserTicketModel>> findByTicketAndStatusAndUserId(String ticketId, String transactionType, String userId);

    @Query(nativeQuery = true, value = "SELECT * FROM user_ticket where user_id = ?1 and transaction_type = ?2")
    Optional<List<UserTicketModel>> findByUserIdAndTransactionalType(String userId, String transactionType);

    @Query(nativeQuery = true, value = "SELECT * FROM user_ticket where id = ?1 and transaction_type = ?2 and user_id = ?3")
    Optional<UserTicketModel> findByIdAndTransactionalTypeAndUserId(Integer id, String transactionType, String userId);

    @Query(nativeQuery = true, value = "SELECT * FROM user_ticket where ticket_id = ?1 and transaction_type = ?2 and user_id = ?3")
    Optional<List<UserTicketModel>> findByTicketIdAndTransactionalTypeAndUserId(Integer ticketId, String buy, String userId);
}
