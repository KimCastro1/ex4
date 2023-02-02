package com.castro.ex4.test;

import com.castro.ex4.dto.GuestbookDTO;
import com.castro.ex4.dto.PageRequestDTO;
import com.castro.ex4.dto.PageResultDTO;
import com.castro.ex4.entity.Guestbook;
import com.castro.ex4.service.GuestbookService;
import com.castro.ex4.service.GuestbookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookServiceImpl guestbookService;

    @Test
    public void testRegister(){
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user0")
                .build();
        System.out.println(guestbookService.register(guestbookDTO));
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = guestbookService.getList(pageRequestDTO);

        System.out.println("prev:"+resultDTO.isPrev());
        System.out.println("next:"+resultDTO.isNext());
        System.out.println("total:"+resultDTO.getTotalPage());
        System.out.println("==================================");
        for(GuestbookDTO guestbookDTO:resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }
        System.out.println("==================================");
        resultDTO.getPageList().forEach(i->System.out.println(i));
    }

}
