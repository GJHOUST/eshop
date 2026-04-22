package org.example.eshop.mapper;

import org.example.eshop.dto.RegisterUserDto;
import org.example.eshop.dto.UpdateUserDto;
import org.example.eshop.dto.ViewUserDto;
import org.example.eshop.model.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    ViewUserDto toViewUserDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "password", ignore = true)
    User toUser(RegisterUserDto registerUserDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    @Mapping(target = "cart", ignore = true)
    void updateUser(UpdateUserDto dto, @MappingTarget User user);
}
