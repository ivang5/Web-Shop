package com.ivang.webshop.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import com.ivang.webshop.dto.SellerDTO;
import com.ivang.webshop.entity.Seller;
import com.ivang.webshop.repository.SellerRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class SellerService implements SellerServiceInterface, UserDetailsService {
    
    private final SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Seller seller = sellerRepository.findByUsername(username);

        if (seller == null) {
            log.info("Seller {} not found in the database", username);
            throw new UsernameNotFoundException("Seller not found in the database");
        } else {
            log.info("Seller {} found in the database", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("seller"));

        return new User(seller.getUsername(), seller.getPassword(), authorities);
    }

    @Override
    public Seller findOne(Long id) {
        log.info("Fetching seller {}", id);
        return sellerRepository.getById(id);
    }

    @Override
    public Seller findByUsername(String username) {
        log.info("Fetching seller {}", username);
        return sellerRepository.findByUsername(username);
    }

    @Override
    public List<SellerDTO> findAll() {
        log.info("Fetching all sellers");
        List<SellerDTO> sellersDTO = new ArrayList<SellerDTO>();

        for (Seller s : sellerRepository.findAll()) {
            sellersDTO.add(new SellerDTO(s));
        }

        return sellersDTO;
    }

    @Override
    public void save(SellerDTO sellerDTO) {
        log.info("Saving new seller {} to the database", sellerDTO.getId());
        Seller seller = new Seller();
        populateSeller(seller, sellerDTO);
    }

    @Override
    public void update(SellerDTO sellerDTO) {
        log.info("Updating seller {}", sellerDTO.getId());
        Seller seller = sellerRepository.getById(sellerDTO.getId());

        if (seller != null) {
            populateSeller(seller, sellerDTO);
        }
    }

    @Override
    public void remove(Long id) {
        log.info("Removing seller {}", id);
        sellerRepository.deleteById(id);
    }

    private void populateSeller(Seller seller, SellerDTO sellerDTO) {
        seller.setFirstName(sellerDTO.getFirstName());
        seller.setLastName(sellerDTO.getLastName());
        seller.setUsername(sellerDTO.getUsername());
        seller.setPassword(sellerDTO.getPassword());
        seller.setBlocked(sellerDTO.isBlocked());
        seller.setOperatesFrom(sellerDTO.getOperatesFrom());
        seller.setEmail(sellerDTO.getEmail());
        seller.setAddress(sellerDTO.getAddress());
        seller.setName(sellerDTO.getName());
        
        sellerRepository.save(seller);
    }
}
