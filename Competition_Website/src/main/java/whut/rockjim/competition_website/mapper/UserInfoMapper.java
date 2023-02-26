package whut.rockjim.competition_website.mapper;

import org.springframework.stereotype.Repository;
import whut.rockjim.competition_website.model.dto.UserInfoDTO;

@Repository
public interface UserInfoMapper {
    UserInfoDTO getUserInfoById(Long id);
}
