package com.castro.ex4.repository;

import com.castro.ex4.entity.Guestbook;
import com.castro.ex4.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,300).forEach(i->{
            Guestbook guestbook = Guestbook.builder()
                    .title("Title..."+i)
                    .content("Content..."+i)
                    .writer("user"+(i%10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    @Test
    public void updateTest(){
        Optional<Guestbook> result = guestbookRepository.findById(300L);
        if(result.isPresent()){
            Guestbook guestbook = result.get();
            guestbook.changeTitle("Changed Title...");
            guestbook.changeContent("Changed Content...");
            guestbookRepository.save(guestbook);
        }
    }
    //querydsl test
    @Test
    public void testQuery1(){
        Sort sort = Sort.by("gno").descending();
        Pageable pageable = PageRequest.of(0,10,sort);
        String keyword = "1";
        QGuestbook qGuestbook = QGuestbook.guestbook;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression booleanExpression = qGuestbook.title.contains(keyword);
        booleanBuilder.and(booleanExpression);
        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder,pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }
    //querydsl multi search test code
    @Test
    public void testQuery2(){
        Sort sort = Sort.by("gno").descending();
        Pageable pageable = PageRequest.of(0,10,sort);
        String keyword = "1";
        QGuestbook qGuestbook = QGuestbook.guestbook;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression booleanExpression1 = qGuestbook.title.contains(keyword);
        BooleanExpression booleanExpression2 = qGuestbook.content.contains(keyword);
        BooleanExpression booleanExpressionAll = booleanExpression1.or(booleanExpression2);
        booleanBuilder.and(booleanExpressionAll);
        booleanBuilder.and(qGuestbook.gno.gt(0L));
        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder,pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });

    }
}
