package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.model.LotteryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LotteryRepository extends JpaRepository<LotteryModel,Integer> {
    Optional<LotteryModel> findById(Integer id);

    Optional<LotteryModel> findByLotteryNumber(String ticket);

    @Query(nativeQuery = true, value = "SELECT * FROM lottery WHERE status <> 'DELETE'")
    Optional<List<LotteryModel>> findAllLotteriesNotStatus();
}
