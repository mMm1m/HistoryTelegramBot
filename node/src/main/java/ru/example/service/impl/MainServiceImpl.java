package ru.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.example.NodeApplication;
import ru.example.StartApplication;
import ru.example.dao.HistoryDAO;
import ru.example.dao.RawDataDAO;
import ru.example.entity.HistoryData;
import ru.example.service.MainService;
import ru.example.service.ProducerService;
import ru.example.entity.RawData;
import ru.example.service.enums.Epochs;

import java.util.*;

@Log4j
@RequiredArgsConstructor
@Service
public class MainServiceImpl implements MainService {

    private final RawDataDAO rawDataDAO;

    private final ProducerService producerService;

    private final HistoryDAO historyDAO;

    List<String> list = List.of("ancient", "middleage");

    //private static String state = "";


    // private final FileService fileService;

    @Transactional
    @Override
     public void processTextMessage(Update update) {
        saveRawData(update);

        var sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        String text = update.getMessage().getText();
        //System.out.println(text);
        boolean flag = true;

            if(text.equals("/start"))
            {
                StringBuilder builder = new StringBuilder();
                builder.append("Welcome to historynovelbot , you can choose necessary novel for you:" + '\n' +
                        "Suggestion variables: " + '\n');
                List<HistoryData> dataList = historyDAO.findAll();
                Set<String> happens = new HashSet<>();
                for(var a : dataList)
                {
                    //log.debug(a.getEvent());
                    if(!happens.contains(a.getEvent())) happens.add(a.getEvent());
                    else continue;
                    builder.append(a.getEvent());
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
            else flag = false;
        if(!flag)
        {
            if(historyDAO.existsByEvent(text))
            {
                List<HistoryData> list = historyDAO.findByEvent(text);
                Map<Long, String> map = new TreeMap<>();
                for(var a : list) map.put(a.getId(), a.getReference());
                for(var a : map.keySet())
                {
                        log.debug("Equal state: " + map.get(a));
                        sendMessage.setText(map.get(a));
                        sendMessage.setDisableNotification(true);
                        producerService.producerAnswer(sendMessage);
                }
            }
            else
            {
                sendMessage.setText("Incorrect command");
                sendMessage.setDisableNotification(false);
                producerService.producerAnswer(sendMessage);
            }
            return;
        }
        log.debug(sendMessage.getText());
        sendMessage.setDisableNotification(false);
        producerService.producerAnswer(sendMessage);
    }


    private void saveRawData(Update update) {
        var rawData = RawData.builder()
                .event(update)
                .build();
        rawDataDAO.save(rawData);
    }
}
