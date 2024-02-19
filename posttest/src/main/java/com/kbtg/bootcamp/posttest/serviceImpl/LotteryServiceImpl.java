package com.kbtg.bootcamp.posttest.serviceImpl;

import com.kbtg.bootcamp.posttest.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.dto.ResponseDto;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.model.LotteryModel;
import com.kbtg.bootcamp.posttest.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LotteryServiceImpl implements LotteryService {

    @Autowired
    private LotteryRepository lotteryRepository;

    @Override
    @Transactional
    public ResponseDto createLotteries(LotteryDto lotteryDto) {
        System.out.println(lotteryDto.toString());
        Optional<LotteryModel> lotteryModelOpt = lotteryRepository.findByLotteryNumber(lotteryDto.getTicket());
        if(lotteryModelOpt.isPresent()) {
            throw new BadRequestException("หมายเลขนี้มีการใช้งานแล้ว");
        }
        LotteryModel lotteryModel = new LotteryModel();
        lotteryModel.setLotteryNumber(lotteryDto.getTicket());
        lotteryModel.setAmount(lotteryDto.getAmount());
        lotteryModel.setPrice(lotteryDto.getPrice());
        lotteryModel.setStatus("ACTIVE");
        lotteryModel.setActive(true);
        lotteryRepository.save(lotteryModel);
        return new ResponseDto(
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                lotteryModel.getLotteryNumber(),
                "create lottery successfully"
        );
    }

    @Override
    public List<LotteryDto> getAllLotteries() {

        Optional<List<LotteryModel>> listOptional = lotteryRepository.findAllLotteriesNotStatus();

        if(listOptional.isPresent()) {
            List<LotteryDto> list = new ArrayList<LotteryDto>();
            for(LotteryModel model : listOptional.get()) {
                LotteryDto lotteryDto = new LotteryDto();
                lotteryDto.setTicket(model.getLotteryNumber());
                lotteryDto.setAmount(model.getAmount());
                lotteryDto.setPrice(model.getPrice());
                lotteryDto.setId(model.getId());
                lotteryDto.setStatus(model.getStatus());
                lotteryDto.setActive(model.getActive());
                list.add(lotteryDto);
            }
            return list;
        }
        throw new NotFoundException("ไม่มีล็อตเตอรี่ในระบบ กรุณาตรวจสอบข้อมูล");
    }

    @Override
    @Transactional
    public ResponseDto editLottery(LotteryDto lotteryDto, Integer id) {
        Optional<LotteryModel> lotteryModelOpt = lotteryRepository.findByLotteryNumber(lotteryDto.getTicket());
        if(lotteryModelOpt.isPresent()) {
            LotteryModel lotteryModel = lotteryModelOpt.get();
//            lotteryModel.setLotteryNumber(lotteryDto.getTicket());
            lotteryModel.setAmount(lotteryDto.getAmount());
            lotteryModel.setPrice(lotteryDto.getPrice());
            if(lotteryDto.getStatus() != null){
                lotteryModel.setStatus(lotteryDto.getStatus());
            }
            if(lotteryDto.getActive() != null){
                lotteryModel.setActive(lotteryDto.getActive());
            }

            lotteryRepository.save(lotteryModel);
            return new ResponseDto(
                    LocalDateTime.now(),
                    HttpStatus.OK.value(),
                    lotteryModel,
                    "updated lottery successfully"
            );
        }
        throw new BadRequestException("หมายเลขนี้มีการใช้งานแล้ว");
    }

    @Override
    @Transactional
    public ResponseDto deleteLottery(LotteryDto lotteryDto, Integer id) {
        Optional<LotteryModel> lotteryModelOpt = lotteryRepository.findByLotteryNumber(lotteryDto.getTicket());
        if(lotteryModelOpt.isPresent()) {
            LotteryModel lotteryModel = lotteryModelOpt.get();
            lotteryModel.setStatus("DELETE");
            lotteryRepository.save(lotteryModel);
            return new ResponseDto(
                    LocalDateTime.now(),
                    HttpStatus.OK.value(),
                    null,
                    "deleted lottery successfully"
            );
        }
        throw new BadRequestException("หมายเลขนี้มีการใช้งานแล้ว");
    }

}
