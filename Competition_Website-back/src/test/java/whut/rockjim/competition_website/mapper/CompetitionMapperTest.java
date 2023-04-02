package whut.rockjim.competition_website.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import whut.rockjim.competition_website.service.CompetitionService;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CompetitionMapperTest {
        @Autowired
    private CompetitionService competitionService;


        @Test
    public void test(){
            Long l=1523825285877768193L;
            competitionService.addRegistrationNumber(l);
        }
}