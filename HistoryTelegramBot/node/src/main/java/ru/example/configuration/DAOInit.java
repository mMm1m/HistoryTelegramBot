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
        List<HistoryData> list = List.of( new HistoryData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_jgho-PgO5rtMOErauKhhRLlb8vsl5cof3RBLf1o1jw&s", "battleBetweenCatAndPerson", "middleage"),
                new HistoryData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpLq_rd6fX7q-69J63c-76mUSP4X1O4mklN8P9nn5gtA&s", "battle", "ancient"),
                new HistoryData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpLq_rd6fX7q-69J63c-76mUSP4X1O4mklN8P9nn5gtA&s", "cubes", "middleage"),
                new HistoryData("https://m.media-amazon.com/images/M/MV5BOTA3NmU1NDMtYzcxMC00ZjI5LTllZWItYWI3MmZkNTE1ZTg0XkEyXkFqcGdeQW1hcmNtYW5u._V1_QL75_UX500_CR0,0,500,281_.jpg", "battleBetweenCatAndPerson", "middleage"));
        for(var data : list) {
            if(historyDAO.existsByEpoch(data.getEpoch()) && historyDAO.existsByEvent(data.getEvent()) && historyDAO.existsByReference(data.getReference())) continue;
            historyDAO.save(data);
        }
    }
}
