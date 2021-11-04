package swjtu.syyymq.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import swjtu.syyymq.dto.RegisterDto;
import swjtu.syyymq.entity.User;

@Mapper
public interface EntityDto {

    EntityDto INSTANCE = Mappers.getMapper( EntityDto.class );

    @Mapping(source = "username",target = "username")
    @Mapping(source = "password",target = "password")
    User registerDtoToUser(RegisterDto dto);
}
