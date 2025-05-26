package org.example.pasir_socha_mateusz.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.pasir_socha_mateusz.dto.GroupDTO;
import org.example.pasir_socha_mateusz.model.Group;
import org.example.pasir_socha_mateusz.model.Membership;
import org.example.pasir_socha_mateusz.model.User;
import org.example.pasir_socha_mateusz.repository.DebtRepository;
import org.example.pasir_socha_mateusz.repository.GroupRepository;
import org.example.pasir_socha_mateusz.repository.MembershipRepository;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final MembershipRepository membershipRepository;
    private final MembershipService membershipService;
    private final DebtRepository debtRepository;

    public GroupService(GroupRepository groupRepository, MembershipRepository membershipRepository, MembershipService membershipService, DebtRepository debtRepository) {
        this.groupRepository = groupRepository;
        this.membershipRepository = membershipRepository;
        this.membershipService = membershipService;
        this.debtRepository = debtRepository;
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @MutationMapping
    public Group createGroup(GroupDTO groupDTO) {
        User owner = membershipService.getCurrentUser();
        Group group = new Group();
        group.setOwner(owner);
        group.setName(groupDTO.getName());
        Group savedGroup = groupRepository.save(group);
        Membership membership = new Membership();
        membership.setGroup(savedGroup);
        membership.setUser(owner);
        membershipRepository.save(membership);
        return savedGroup;
    }

    public void deleteGroup(Long id) {

        Group group=groupRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Grupa o ID " + id + " nie istnieje."));
        debtRepository.deleteAll(debtRepository.findByGroupId(id));
        membershipRepository.deleteAll(membershipRepository.findByGroupId(id));
        groupRepository.delete(group);
    }
}

