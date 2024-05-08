package ru.example.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "history_table")
@AllArgsConstructor
@RequiredArgsConstructor
public class HistoryData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "reference")
    private String reference;
    @Column(name = "event")
    private String event;
    @Column(name = "epoch")
    private String epoch;

    public HistoryData(String ref, String event, String epoch)
    {
        this.reference = ref;
        this.event = event;
        this.epoch = epoch;
    }
}
