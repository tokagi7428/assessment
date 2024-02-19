package com.kbtg.bootcamp.posttest.serviceImpl;

import com.kbtg.bootcamp.posttest.Enum.TransactionType;
import com.kbtg.bootcamp.posttest.dto.ResponseDto;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.model.LotteryModel;
import com.kbtg.bootcamp.posttest.model.UserModel;
import com.kbtg.bootcamp.posttest.model.UserTicketModel;
import com.kbtg.bootcamp.posttest.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.repository.UserRepository;
import com.kbtg.bootcamp.posttest.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.service.UserTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserTicketServiceImpl implements UserTicketService {

    @Autowired
    private UserTicketRepository userTicketRepository;

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseDto getLotteries(String userId) {

        Optional<List<UserTicketModel>> userTicketModelOptional = userTicketRepository.findByUserIdAndStatus(userId,"BUY");
        if(userTicketModelOptional.isPresent()) {
            Set<String> userTicketSet = new HashSet<>();
            for (UserTicketModel ticketModel : userTicketModelOptional.get()) {
                Optional<LotteryModel> lotteryModel = lotteryRepository.findById(ticketModel.getLotteryId());
                lotteryModel.ifPresent(lottery -> userTicketSet.add(lottery.getLotteryNumber()));
            }
            return new ResponseDto(LocalDateTime.now(),
                    HttpStatus.OK.value(),
                    userTicketSet,
                    "get user successfully"
            );
        }
        throw new BadRequestException("ไม่พบผู้ใช้นี้อยู่ในระบบ");
    }

    @Override
    @Transactional
    public ResponseDto sellLottery(Integer id, String userId, Integer ticketId) {
//        System.out.println(id + " : " + userId + " : " + ticketId);
        Optional<UserModel> userModelOptional = userRepository.findById(id);
        Optional<LotteryModel> lotteryModelOptional = lotteryRepository.findById(ticketId);
        if(userModelOptional.isPresent() && lotteryModelOptional.isPresent()){
            LotteryModel lotteryModel = lotteryModelOptional.get();
            // reduce amount of the lottery number
            if(lotteryModel.getAmount() - 1 >= 0){
                lotteryModel.setAmount(lotteryModel.getAmount() - 1);
                lotteryRepository.save(lotteryModel);
            }else{
                throw new BadRequestException("จำนวนล็อตเตอรี่ไม่เพียงพอ");
            }
            UserTicketModel userTicketModel = new UserTicketModel();
            userTicketModel.setLotteryId(lotteryModel.getId());
            userTicketModel.setUserId(userId);
            userTicketModel.setTransactionType("BUY");
            userTicketRepository.save(userTicketModel);

            return new ResponseDto(LocalDateTime.now(),
                    HttpStatus.OK.value(),
                    null,
                    "bought lottery successfully"
            );
        }else{
            throw new BadRequestException("ไม่พบผู้ใช้นี้อยู่ในระบบ หรือ ไม่พบล็อตเตอรี่หมายเลขนี้");
        }

    }

    @Override
    @Transactional
    public ResponseDto soldLottery(String ticketNumber, String userId, Integer ticketId) {
        Optional<UserModel> userModelOptional = userRepository.findByUserId(userId);
        Optional<LotteryModel> lotteryModelOptional = lotteryRepository.findById(ticketId);
        if(userModelOptional.isPresent() && lotteryModelOptional.isPresent()){
            Optional<List<UserTicketModel>> userTicketModelOpt = userTicketRepository.findByLotteryIdAndStatus(ticketId, "BUY");
            List<UserTicketModel> userTicketModelList = userTicketModelOpt.get();

            int priceTicket = 0;
            for(UserTicketModel userTicketModel : userTicketModelList){
                Optional<LotteryModel> lotteryModel =  lotteryRepository.findById(ticketId);
                if(lotteryModel.isPresent() && lotteryModel.get().getLotteryNumber().equals(ticketNumber)) {
                    priceTicket += lotteryModel.get().getPrice();
                    userTicketModel.setTransactionType("SOLD");
                    userTicketRepository.save(userTicketModel);
                }
            }

            Map<String,String> map = new HashMap<String,String>();
            map.put("ticketId", ticketNumber);
            map.put("price", String.valueOf(priceTicket));

            return new ResponseDto(LocalDateTime.now(),
                    HttpStatus.OK.value(),
                    map,
                    "sold lottery successfully"
            );
        }else{
            throw new BadRequestException("ไม่พบผู้ใช้นี้อยู่ในระบบ หรือ ไม่พบล็อตเตอรี่หมายเลขนี้");
        }
    }

}
