package ru.example.entity;

import lombok.*;
import org.glassfish.jersey.message.internal.MsgTraceEvent;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "answers")
public class Answers{
    public Answers(String photo, String ref, String first, String second, String third, String answer_for_each, String ans){
        this.photo = photo;
        this.TASK = ref;
        this.first_ans = first;
        this.second_ans = second;
        this.third_ans = third;
        this.answer_for_each = answer_for_each;
        this.correct_ans = ans;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answers_id")
    private long id;
    private String photo;
    private String TASK;
    private String first_ans;
    private String second_ans;
    private String third_ans;
    private String answer_for_each;
    private String correct_ans;

    //@OneToMany(mappedBy = "Answers")
    //private List<Events> list_of_events = new ArrayList<>();
}
