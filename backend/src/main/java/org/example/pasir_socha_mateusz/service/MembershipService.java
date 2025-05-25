package org.example.pasir_socha_mateusz.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.pasir_socha_mateusz.dto.MembershipDTO;
import org.example.pasir_socha_mateusz.model.Group;
import org.example.pasir_socha_mateusz.model.Membership;
import org.example.pasir_socha_mateusz.model.User;
import org.example.pasir_socha_mateusz.repository.GroupRepository;
import org.example.pasir_socha_mateusz.repository.MembershipRepository;
import org.example.pasir_socha_mateusz.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public MembershipService(MembershipRepository membershipRepository, GroupRepository groupRepository, UserRepository userRepository) {
        this.membershipRepository = membershipRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public List<Membership> getGroupMembers(Long groupId) {
        return membershipRepository.findByGroupId(groupId);
    }

    public Membership addMember(MembershipDTO membershipDTO) {
        User user = userRepository.findByEmail(membershipDTO.getUserEmail())
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono użytkownika o emailu: " + membershipDTO.getUserEmail()));

        Group group = groupRepository.findById(membershipDTO.getGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono grupy o ID: " + membershipDTO.getGroupId()));


        boolean alreadyMember = membershipRepository.findByGroupId(group.getId()).stream()
                .anyMatch(membership -> membership.getUser().getId().equals(user.getId()));

        if (alreadyMember) {
            throw new IllegalStateException("Użytkownik jest już członkiem tej grupy.");
        }

        Membership membership = new Membership();
        membership.setGroup(group);
        membership.setUser(user);
        return membershipRepository.save(membership);
    }

    public void removeMember(Long membershipId) {
        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new EntityNotFoundException("Członkostwo nie istnieje"));

        User currentUser = getCurrentUser();
        User groupOwner = membership.getGroup().getOwner();

        if (!currentUser.getId().equals(groupOwner.getId())) {
            throw new SecurityException("Tylko właściciel grupy może usuwać członków.");
        }

        membershipRepository.deleteById(membershipId);
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono użytkownika: " + email));
    }
}

