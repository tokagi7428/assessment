package com.kbtg.bootcamp.posttest.serviceImpl;

import com.kbtg.bootcamp.posttest.dto.ResponseDto;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.model.TicketModel;
import com.kbtg.bootcamp.posttest.model.UserModel;
import com.kbtg.bootcamp.posttest.model.UserTicketModel;
import com.kbtg.bootcamp.posttest.repository.TicketRepository;
import com.kbtg.bootcamp.posttest.repository.UserRepository;
import com.kbtg.bootcamp.posttest.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.service.UserTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class UserTicketServiceImpl implements UserTicketService {

    @Autowired
    private UserTicketRepository userTicketRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseDto getLotteries(String userId) {

        Optional<List<UserTicketModel>> userTicketModelOptional = userTicketRepository.findByUserIdAndTransactionalType(userId,"BUY");
        System.out.println(userTicketModelOptional.isPresent());
        if(userTicketModelOptional.isPresent()) {
            Set<String> userTicketSet = new HashSet<>();
            for (UserTicketModel ticketModel : userTicketModelOptional.get()) {
                userTicketSet.add(ticketModel.getTicketId());
            }
            Map<String,Set<String>> userTicketMap = new LinkedHashMap<>();
            userTicketMap.put("tickets", userTicketSet);
            return new ResponseDto(LocalDateTime.now(),
                    HttpStatus.OK.value(),
                    userTicketMap,
                    "get user_ticket successfully"
            );
        }
        throw new BadRequestException("ไม่พบผู้ใช้นี้อยู่ในระบบ");
    }

    @Override
    @Transactional
    public ResponseDto buyLottery(Integer id, String userId, String ticketId) {
//        System.out.println(id + " : " + userId + " : " + ticketId);
        Optional<UserModel> userModelOptional = userRepository.findById(id);
        Optional<TicketModel> lotteryModelOptional = ticketRepository.findByTicketId(ticketId);
        if(userModelOptional.isPresent() && lotteryModelOptional.isPresent() && userModelOptional.get().getUserId().equals(userId)){
            TicketModel ticketModel = lotteryModelOptional.get();
            // reduce amount of the lottery number
            if(ticketModel.getAmount() - 1 >= 0){
                ticketModel.setAmount(ticketModel.getAmount() - 1);
                ticketRepository.save(ticketModel);
            }else{
                throw new BadRequestException("จำนวนล็อตเตอรี่ไม่เพียงพอ");
            }
            UserTicketModel userTicketModel = new UserTicketModel();
            userTicketModel.setTicketId(ticketModel.getTicketId());
            userTicketModel.setUserId(userModelOptional.get().getUserId());
            userTicketModel.setTransactionType("BUY");
            userTicketRepository.save(userTicketModel);

            return new ResponseDto(LocalDateTime.now(),
                    HttpStatus.OK.value(),
                    null,
                    "buy lottery successfully"
            );
        }else{
            throw new BadRequestException("ไม่พบผู้ใช้นี้อยู่ในระบบ หรือ ไม่พบล็อตเตอรี่หมายเลขนี้");
        }

    }

    @Override
    @Transactional
    public ResponseDto sellLottery(Integer id, String userId, String ticketId) {
        Optional<UserModel> userModelOptional = userRepository.findByUserId(userId);
        Optional<TicketModel> lotteryModelOptional = ticketRepository.findById(id);
        System.out.println(userModelOptional.isPresent());
        System.out.println(lotteryModelOptional.isPresent());
        if(userModelOptional.isPresent() && lotteryModelOptional.isPresent() && lotteryModelOptional.get().getTicketId().equals(ticketId)){
            Optional<List<UserTicketModel>> userTicketModelOpt = userTicketRepository.findByTicketIdAndStatus(ticketId, "BUY");
            List<UserTicketModel> userTicketModelList = userTicketModelOpt.get();

            int priceTicket = 0;
            for(UserTicketModel userTicketModel : userTicketModelList){
                priceTicket += lotteryModelOptional.get().getPrice();
                userTicketModel.setTransactionType("SELL");
                userTicketModel.setTransactionSellDate(LocalDateTime.now());
                userTicketRepository.save(userTicketModel);
            }

            Map<String,String> map = new HashMap<String,String>();
            map.put("ticketId", ticketId);
            map.put("price", String.valueOf(priceTicket));

            return new ResponseDto(LocalDateTime.now(),
                    HttpStatus.OK.value(),
                    map,
                    "sell lottery successfully"
            );
        }else{
            throw new BadRequestException("ไม่พบผู้ใช้นี้อยู่ในระบบ หรือ ไม่พบล็อตเตอรี่หมายเลขนี้");
        }
    }

}
