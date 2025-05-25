package org.example.pasir_socha_mateusz.controller;

import jakarta.validation.Valid;
import org.example.pasir_socha_mateusz.dto.GroupDTO;
import org.example.pasir_socha_mateusz.model.Group;
import org.example.pasir_socha_mateusz.service.GroupService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GroupGraphQLController {
    private final GroupService groupService;

    public GroupGraphQLController(GroupService groupService){
        this.groupService=groupService;
    }

    @QueryMapping
    public List<Group> groups(){
        return groupService.getAllGroups();
    }

    @MutationMapping
    public Group createGroup(@Valid @Argument GroupDTO groupDTO){
        return groupService.createGroup(groupDTO);
    }
    @MutationMapping
    public Boolean deleteGroup(@Argument Long id){
        groupService.deleteGroup(id);
        return true;
    }
}
