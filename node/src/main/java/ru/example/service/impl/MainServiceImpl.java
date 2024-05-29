package ru.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.example.dao.HistoryDAO;
import ru.example.dao.RawDataDAO;
import ru.example.dao.EpochRepository;
import ru.example.entity.Epoch;
import ru.example.entity.HistoryData;
import ru.example.service.MainService;
import ru.example.service.ProducerService;
import ru.example.entity.RawData;
import ru.example.service.enums.ServiceCommands;

import java.util.*;

@Log4j
@RequiredArgsConstructor
@Service
public class MainServiceImpl implements MainService {

    private final RawDataDAO rawDataDAO;

    private final ProducerService producerService;

    private final HistoryDAO historyDAO;

    private final EpochRepository studentRepository;

    @Transactional
    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);

        var sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        String text = update.getMessage().getText();
        sendMessage.setDisableNotification(false);
        boolean flag = true;

        if(text.equals("/start"))
        {
            /*for (var a : answerRepository.findAll()) {
                    //if (conditionRepository.findById("condition").get()
                      //      .getCondition().equals("")){
                        // photo_ref
                        b.append(a.getPhoto());
                        msg.setDisableNotification(true);
                        msg.setText(b.toString());
                        msg.setChatId(update.getMessage().getChatId());
                        msg.setParseMode(a.getTASK());
                        conditionRepository.deleteById("condition");
                        conditionRepository.save(new Condition("condition", "InProcess"));
                        producerService.producerAnswer(msg);

                        msg = new SendMessage();
                        b = new StringBuilder();

                        String[] cmds = new String[]{a.getFirst_ans(), a.getSecond_ans(), a.getThird_ans()};
                        for (var c : cmds) {
                            b.append(c);
                            b.append('\n');
                        }
                        msg.setText(b.toString());
                        msg.setChatId(update.getMessage().getChatId());
                        msg.setDisableNotification(false);
                        producerService.producerAnswer(msg);
                        //
                    }
                }
            //}
            else {
                //if(conditionRepository.findById("condition").get().getCondition().equals("InProcess")) {
                    //conditionRepository.deleteById("condition");
                    //conditionRepository.save(new Condition("condition", ""));
                    for(var a : answerRepository.findAll())
                    {
                        msg = new SendMessage();
                        b = new StringBuilder();
                        msg.setChatId(update.getMessage().getChatId());
                        msg.setDisableNotification(false);
                        if(a.getCorrect_ans().equals(text))
                        {
                            Answers tmp  = a;
                            answerRepository.deleteById(a.getId());

                            tmp.setAnswer_for_each("Correct\n");
                            answerRepository.save(tmp);
                            Increment increment = incrementRepository.findById("increment").get();
                            incrementRepository.deleteById("increment");
                            incrementRepository.save(new Increment("increment", increment.getInc()+1));

                        }
                        if(incrementRepository.findById("increment").get().getInc() == answerRepository.findAll().size())
                        {
                            for(var c : answerRepository.findAll())
                            {
                                b.append(c.getAnswer_for_each());
                                b.append('\n');
                            }
                        }
                        else {
                            b.append("next");
                        }
                        msg.setText(b.toString());
                        producerService.producerAnswer(msg);
                    }
                //}
                /*else {
                    b.append("Try again");
                    msg.setChatId(update.getMessage().getChatId());
                    msg.setDisableNotification(false);
                    producerService.producerAnswer(msg);
                }*/
            StringBuilder builder = new StringBuilder();
            builder.append("Welcome to historynovelbot , you can choose necessary novel for you after choosing epoch:" + '\n' +
                    "Suggestion variables: " + '\n');
            ServiceCommands[] dataList = ServiceCommands.values();
            for(var a : dataList)
            {
                builder.append(a.name());
                builder.append('\n');
            }
            sendMessage.setText(builder.toString());
        }
        else if( text.equals("/help")) {
            sendMessage.setText("You have commands: /start(for start bot) , /end(exit the bot)\n" +
                    "After this you can choose epoch and current novel");
        }
        else if(text.equals( "/end")) {
            sendMessage.setText("Thanks for using this bot , return as soon as you can");
        }
        else {
            sendMessage.setDisableNotification(true);
            flag = false;
        }
        if(!flag)
        {
            if(historyDAO.existsByEpoch(text))
            {
                StringBuilder builder = new StringBuilder();
                builder.append("List of available events is here:");
                builder.append('\n');
                studentRepository.deleteById("state");
                studentRepository.save(new Epoch("state" , text));
                List<HistoryData> list = historyDAO.findByEpoch(text);
                Set<String> set = new HashSet<>();
                for(var a: list)
                {
                    int size = set.size();
                    set.add(a.getEvent().toLowerCase());
                    if(size != set.size())
                    {
                        builder.append(a.getEvent().toLowerCase());
                        builder.append('\n');
                    }
                }
                sendMessage.setText(builder.toString());
                sendMessage.setDisableNotification(false);
                producerService.producerAnswer(sendMessage);
            }
            else if(historyDAO.existsByEvent(text))
            {
                List<HistoryData> list = historyDAO.findByEvent(text);
                Map<Long, String> map = new TreeMap<>();
                Map<Long, String> text_ = new HashMap<>();
                for(var a : list) {
                    map.put(a.getId(), a.getReference());
                    text_.put(a.getId(), a.getText());
                }
                for(var a : map.keySet())
                {
                    log.debug("DEDUG:"+studentRepository.findById("state").get().getEpoch()+"   "+
                            historyDAO.getById(a).getEpoch());

                    if(studentRepository.findById("state").get().getEpoch()
                            .equals(historyDAO.getById(a).getEpoch())) {
                        sendMessage.setText(map.get(a));
                        sendMessage.setParseMode(text_.get(a));
                        sendMessage.setDisableNotification(true);
                        producerService.producerAnswer(sendMessage);
                    }
                }
            }
            else
            {
                sendMessage.setText("Incorrect command");
                producerService.producerAnswer(sendMessage);
            }
            return;
        }
        producerService.producerAnswer(sendMessage);
    }

    private void saveRawData(Update update) {
        var rawData = RawData.builder()
                .event(update)
                .build();
        rawDataDAO.save(rawData);
    }
}