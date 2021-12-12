package com.ivang.webshop.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import com.ivang.webshop.dto.BuyerDTO;
import com.ivang.webshop.entity.Buyer;
import com.ivang.webshop.repository.BuyerRepository;

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
public class BuyerService implements BuyerServiceInterface, UserDetailsService {
    
    private final BuyerRepository buyerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Buyer buyer = buyerRepository.findByUsername(username);

        if (buyer == null) {
            log.info("Buyer {} not found in the database", username);
            throw new UsernameNotFoundException("Buyer not found in the database");
        } else {
            log.info("Buyer {} found in the database", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("buyer"));

        return new User(buyer.getUsername(), buyer.getPassword(), authorities);
    }

    @Override
    public Buyer findOne(Long id) {
        log.info("Fetching buyer {}", id);
        return buyerRepository.getById(id);
    }

    @Override
    public Buyer findByUsername(String username) {
        log.info("Fetching buyer {}", username);
        return buyerRepository.findByUsername(username);
    }

    @Override
    public List<BuyerDTO> findAll() {
        log.info("Fetching all buyers");
        List<BuyerDTO> buyersDTO = new ArrayList<BuyerDTO>();

		for(Buyer b : buyerRepository.findAll()) {
			buyersDTO.add(new BuyerDTO(b));
		}
        
		return buyersDTO;
    }

    @Override
    public void save(BuyerDTO buyerDTO) {
        log.info("Saving new buyer {} to the database", buyerDTO.getId());
        Buyer buyer = new Buyer();
        populateBuyer(buyer, buyerDTO);
    }

    @Override
    public void update(BuyerDTO buyerDTO) {
        log.info("Updating buyer {}", buyerDTO.getId());
        Buyer buyer = buyerRepository.getById(buyerDTO.getId());

        if (buyer != null) {
            populateBuyer(buyer, buyerDTO);
        }
    }

    @Override
    public void remove(Long id) {
        log.info("Removing buyer {}", id);
        buyerRepository.deleteById(id);
    }

    private void populateBuyer(Buyer buyer, BuyerDTO buyerDTO) {
        buyer.setFirstName(buyerDTO.getFirstName());
        buyer.setLastName(buyerDTO.getLastName());
        buyer.setUsername(buyerDTO.getUsername());
        buyer.setPassword(passwordEncoder.encode(buyerDTO.getPassword()));
        buyer.setBlocked(buyerDTO.isBlocked());
        buyer.setAddress(buyerDTO.getAddress());
        
        buyerRepository.save(buyer);
    }
}
