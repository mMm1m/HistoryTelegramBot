package ru.example.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.example.dao.HistoryDAO;
import ru.example.entity.HistoryData;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.validation.constraints.Min;
import java.util.List;

@Configuration
public class DAOInit {
    @Autowired
    private HistoryDAO historyDAO;

    @PostConstruct
    public void init()
    {
        // it became epoch/event - not cmd
        List<HistoryData> list = List.of( new HistoryData("https://avatars.dzeninfra.ru/get-zen_doc/96780/pub_5cfe2402253cb300aec67ab7_5cfe2572af7e3300afe02727/scale_1200", "Main battles of Rome Empire", "ancient", "The battle of Cape Eknom"),
                new HistoryData("https://avatars.dzeninfra.ru/get-zen_doc/225409/pub_5cfe2402253cb300aec67ab7_5cfe25962a288b00b0767117/scale_1200", "Main battles of Rome Empire", "ancient", "The Battle of Cannes"),
                new HistoryData("https://avatars.dzeninfra.ru/get-zen_doc/50509/pub_5cfe2402253cb300aec67ab7_5cfe25b4388e2100af060220/scale_1200", "Main battles of Rome Empire", "ancient", "Retribution in the case of a Deputy"),
                new HistoryData("https://avatars.dzeninfra.ru/get-zen_doc/29317/pub_5cfe2402253cb300aec67ab7_5cfe2602e24ab100bce1ea96/scale_1200", "Main battles of Rome Empire", "ancient", "The Siege of Alesia"),
                new HistoryData("https://www.google.com/url?sa=i&url=https%3A%2F%2Farzamas.academy%2Fmag%2F823-plague&psig=AOvVaw3qR435l4Q2w6S7jYz_nrgx&ust=1715348358139000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCNDx3N_YgIYDFQAAAAAdAAAAABAE", "Plague", "middleage", "Atmosphere on the streets"),
                new HistoryData("https://avatars.dzeninfra.ru/get-zen_doc/10145076/pub_6509ad236843aa59240dbaf9_6509cb3e52835e19d20d8421/scale_1200", "Plague", "middleage", "Famous guys"),
                new HistoryData("https://warspot-asset.s3.amazonaws.com/articles/pictures/000/093/951/content/11-01_-_oblozhka-_rytsar-tamplier-8f33513dca630f15620428d37df3a89b.jpg", "The Crusade", "middleage", "Famous knight"),
                new HistoryData("https://upload.wikimedia.org/wikipedia/commons/b/b7/Siege_of_Jerusalem_1099_%281%29.jpg", "The Crusade", "middleage", "The siege of Ierusalim"),
                new HistoryData("https://petro-barocco.ru/wp-content/uploads/2012/09/002p77ct-%D0%BF%D1%91%D1%82%D1%80-%D0%B2-%D0%B4%D0%B5%D1%82%D1%81%D1%82%D0%B2%D0%B51.jpg", "History of Russian Empire in 18 century", "newtime", "Peter the Great become a king"),
                new HistoryData("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d8/Vladimir_Putin_31_December_1999-3.jpg/1200px-Vladimir_Putin_31_December_1999-3.jpg", "History of modern Russia Part 1", "moderntime", "Vladimir Putin become a president firstly"),
                new HistoryData("https://cdn.er.ru/media/news/February2024/wsvXkkq2POOY3jOC3Eik.jpg", "History of modern Russia Part 2", "moderntime", "Vladimir Putin become a president in five time")
                );

        for(var data : list) {
            if(historyDAO.existsByEpoch(data.getEpoch()) && historyDAO.existsByEvent(data.getEvent()) && historyDAO.existsByReference(data.getReference())) continue;
            historyDAO.save(data);
        }
    }


}
