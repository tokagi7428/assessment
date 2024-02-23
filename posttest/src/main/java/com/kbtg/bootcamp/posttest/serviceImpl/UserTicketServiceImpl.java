package com.kbtg.bootcamp.posttest.serviceImpl;

import com.kbtg.bootcamp.posttest.dto.TicketResponse;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.model.TicketModel;
import com.kbtg.bootcamp.posttest.model.UserModel;
import com.kbtg.bootcamp.posttest.model.UserTicketModel;
import com.kbtg.bootcamp.posttest.repository.TicketRepository;
import com.kbtg.bootcamp.posttest.repository.UserRepository;
import com.kbtg.bootcamp.posttest.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.service.UserTicketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserTicketServiceImpl implements UserTicketService {

    private final UserTicketRepository userTicketRepository;

    private final TicketRepository ticketRepository;

    private final UserRepository userRepository;

    public UserTicketServiceImpl(UserTicketRepository userTicketRepository, TicketRepository ticketRepository, UserRepository userRepository) {
        this.userTicketRepository = userTicketRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Map<String,List<String>> getLotteries(String userId) {

        Optional<List<UserTicketModel>> userTicketModelOptional = userTicketRepository.findByUserIdAndTransactionalType(userId,"BUY");

        if(userTicketModelOptional.isPresent()) {
//            Set<String> userTicketSet = new HashSet<>();
            List<String> userTicketList = new ArrayList<>();
            for (UserTicketModel userTicketModel : userTicketModelOptional.get()) {
                Optional<TicketModel> ticketModel = ticketRepository.findById(userTicketModel.getTicketId());
                userTicketList.add(ticketModel.get().getTicket());
            }
            Map<String,List<String>> userTicketMap = new LinkedHashMap<>();
            userTicketMap.put("tickets", userTicketList);

            return userTicketMap;
        }
        throw new NotFoundException("Ticket not found");
    }

    @Override
    @Transactional
    public Map<String,String> buyLottery(String userId, Integer ticketId) {
        Optional<UserModel> userModelOptional = userRepository.findByUserId(userId);
        Optional<TicketModel> lotteryModelOptional = ticketRepository.findById(ticketId);
        if(userModelOptional.isPresent() && lotteryModelOptional.isPresent()){
            TicketModel ticketModel = lotteryModelOptional.get();
            // reduce amount of the lottery number
            if(ticketModel.getAmount() - 1 >= 0){
                ticketModel.setAmount(ticketModel.getAmount() - 1);
                ticketRepository.save(ticketModel);
            }else{
                throw new BadRequestException("The ticket is not enough");
            }
            UserTicketModel userTicketModel = new UserTicketModel();
            userTicketModel.setTicketId(ticketModel.getId());
            userTicketModel.setUserId(userModelOptional.get().getUserId());
            userTicketModel.setTransactionType("BUY");
            userTicketRepository.save(userTicketModel);
            Map<String,String> mapData = new HashMap<String,String>();
            mapData.put("id", String.valueOf(ticketModel.getId()));
            return mapData;
        }else{
            throw new NotFoundException("Not found user or ticket");
        }
    }

    @Override
    @Transactional
    public Map<String,String> sellLottery(String userId, Integer ticketId) {
        Optional<UserModel> userModelOptional = userRepository.findByUserId(userId);
        Optional<TicketModel> lotteryModelOptional = ticketRepository.findById(ticketId);
        if(userModelOptional.isPresent() && lotteryModelOptional.isPresent()){
            Optional<List<UserTicketModel>> userTicketModelOpt = userTicketRepository.findByTicketIdAndTransactionalTypeAndUserId(ticketId, "BUY",userId);

            if(userTicketModelOpt.isPresent()){
                List<UserTicketModel> userTicketModelList = userTicketModelOpt.get();
                for(UserTicketModel userTicketModel : userTicketModelList){
                    userTicketModel.setTransactionType("SELL");
                    userTicketModel.setTransactionSellDate(LocalDateTime.now());
                    userTicketRepository.save(userTicketModel);
                }
                Map<String,String> map = new HashMap<String,String>();
                map.put("ticket", lotteryModelOptional.get().getTicket());
                return map;
            }else{
                throw new BadRequestException("Not found this ticket for buy");
            }
        }else{
            throw new NotFoundException("Not found user or ticket");
        }
    }

    @Override
    public TicketResponse getMyLotteries(String userId) {
        Optional<UserModel> userModelOptional = userRepository.findByUserId(userId);
        if(userModelOptional.isPresent()){
            Optional<List<UserTicketModel>> userTicketModelOpt = userTicketRepository.findByUserIdAndTransactionalType(userId, "BUY");
            if(userTicketModelOpt.isPresent()){
                List<String> tickets = new ArrayList<String>();
                int count = 0;
                int cost = 0;
                List<UserTicketModel> userTicketModelList = userTicketModelOpt.get();
                for(UserTicketModel userTicketModel : userTicketModelList){
                    Optional<TicketModel> lotteryModelOpt = ticketRepository.findById(userTicketModel.getTicketId());
                    tickets.add(lotteryModelOpt.get().getTicket());
                    cost += lotteryModelOpt.get().getPrice();
                    count++;
                }
                return new TicketResponse(tickets,count,cost);
            }else{
                throw new BadRequestException("Not found this ticket");
            }
        }else{
            throw new NotFoundException("Not found user");
        }
    }

}
