package com.ivang.webshop.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import com.ivang.webshop.dto.AdminDTO;
import com.ivang.webshop.entity.Admin;
import com.ivang.webshop.repository.AdminRepository;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
@Primary
public class AdminService implements AdminServiceInterface, UserDetailsService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);

        if (admin == null) {
            log.info("Admin {} not found in the database", username);
            throw new UsernameNotFoundException("Admin not found in the database");
        } else {
            log.info("Admin {} found in the database", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("admin"));

        return new User(admin.getUsername(), admin.getPassword(), authorities);
    }

    @Override
    public Admin findOne(Long id) {
        log.info("Fetching admin {}", id);
        return adminRepository.getById(id);
    }

    @Override
    public Admin findByUsername(String username) {
        log.info("Fetching admin {}", username);
        return adminRepository.findByUsername(username);
    }

    @Override
    public List<AdminDTO> findAll() {
        log.info("Fetching all admins");
        List<AdminDTO> adminsDTO = new ArrayList<AdminDTO>();

        for (Admin a : adminRepository.findAll()) {
            adminsDTO.add(new AdminDTO(a));
        }
        
        return adminsDTO;
    }

    @Override
    public void save(AdminDTO adminDTO) {
        log.info("Saving new admin {} to the database", adminDTO.getId());
        Admin admin = new Admin();
        populateAdmin(admin, adminDTO);
    }

    @Override
    public void update(AdminDTO adminDTO) {
        log.info("Updating admin {}", adminDTO.getId());
        Admin admin = adminRepository.getById(adminDTO.getId());

        if (admin != null) {
            populateAdmin(admin, adminDTO);
        }
    }

    @Override
    public void remove(Long id) {
        log.info("Removing admin {}", id);
        adminRepository.deleteById(id);
    }

    private void populateAdmin(Admin admin, AdminDTO adminDTO) {
        admin.setFirstName(adminDTO.getFirstName());
        admin.setLastName(adminDTO.getLastName());
        admin.setUsername(adminDTO.getUsername());
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        admin.setBlocked(adminDTO.isBlocked());
        
        adminRepository.save(admin);
    }
}
