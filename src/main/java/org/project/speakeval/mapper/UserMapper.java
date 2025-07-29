package org.project.speakeval.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.project.speakeval.domain.User;
import org.project.speakeval.dto.client.response.auth.RegisterResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    RegisterResponse toRegisterResponse(User user);

}
