package com.ivang.webshop.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivang.webshop.dto.AdminDTO;
import com.ivang.webshop.dto.BuyerDTO;
import com.ivang.webshop.dto.SellerDTO;
import com.ivang.webshop.entity.Admin;
import com.ivang.webshop.entity.Buyer;
import com.ivang.webshop.entity.Seller;
import com.ivang.webshop.service.AdminServiceInterface;
import com.ivang.webshop.service.BuyerServiceInterface;
import com.ivang.webshop.service.SellerServiceInterface;
import com.ivang.webshop.utility.SecurityHelper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@CrossOrigin
@RestController
@RequestMapping(value = "shop/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {
    
    private final AdminServiceInterface adminService;
    private final BuyerServiceInterface buyerService;
    private final SellerServiceInterface sellerService;

    @GetMapping(value = "/admin")
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        return ResponseEntity.ok().body(adminService.findAll());
    }

    @GetMapping(value = "/buyer")
    public ResponseEntity<List<BuyerDTO>> getAllBuyers() {
        return ResponseEntity.ok().body(buyerService.findAll());
    }

    @GetMapping(value = "/seller")
    public ResponseEntity<List<SellerDTO>> getAllSellers() {
        return ResponseEntity.ok().body(sellerService.findAll());
    }

    @GetMapping(value = "/admin/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable("id") Long id) {
        Admin admin = adminService.findOne(id);

        if (admin == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(new AdminDTO(admin));
    }

    @GetMapping(value = "/buyer/{id}")
    public ResponseEntity<BuyerDTO> getBuyerById(@PathVariable("id") Long id) {
        Buyer buyer = buyerService.findOne(id);

        if (buyer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(new BuyerDTO(buyer));
    }

    @GetMapping(value = "/seller/{id}")
    public ResponseEntity<SellerDTO> getSellerById(@PathVariable("id") Long id) {
        Seller seller = sellerService.findOne(id);

        if (seller == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(new SellerDTO(seller));
    }

    @PostMapping(value = "/admin")
    public ResponseEntity<?> createAdmin(@RequestBody AdminDTO admin) {
        try {
            adminService.save(admin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request, something went wrong");
        }

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/shop/users/admin").toUriString());
        return ResponseEntity.created(uri).build();
    }

    @PostMapping(value = "/buyer")
    public ResponseEntity<?> createBuyer(@RequestBody BuyerDTO buyer) {
        try {
            buyerService.save(buyer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request, something went wrong");
        }

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/shop/users/buyer").toUriString());
        return ResponseEntity.created(uri).build();
    }

    @PostMapping(value = "/seller")
    public ResponseEntity<?> createSeller(@RequestBody SellerDTO seller) {
        try {
            sellerService.save(seller);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request, something went wrong");
        }

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/shop/users/seller").toUriString());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/admin")
    public ResponseEntity<?> updateAdmin(@RequestBody AdminDTO admin) {
        try {
            adminService.update(admin);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/buyer")
    public ResponseEntity<?> updateBuyer(@RequestBody BuyerDTO buyer) {
        try {
            buyerService.update(buyer);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/seller")
    public ResponseEntity<?> updateSeller(@RequestBody SellerDTO seller) {
        try {
            sellerService.update(seller);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/admin")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        Admin admin = adminService.findOne(id);

        if (admin != null) {
            adminService.remove(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/buyer")
    public ResponseEntity<Void> deleteBuyer(@PathVariable Long id) {
        Buyer buyer = buyerService.findOne(id);

        if (buyer != null) {
            buyerService.remove(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/seller")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        Seller seller = sellerService.findOne(id);

        if (seller != null) {
            sellerService.remove(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token_old = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                String username = SecurityHelper.getUsernameFromJWT(refresh_token_old, algorithm);
                List<String> authorities = getAuthorities(username);
                Map<String, String> tokens = SecurityHelper.getTokens(request, username, authorities, algorithm);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                log.error("Error validating refresh token: {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    public List<String> getAuthorities(String username) {
        Admin admin = adminService.findByUsername(username);
        Buyer buyer = buyerService.findByUsername(username);
        String role;

        if (admin != null) {
            role = "admin";
        } else if (buyer != null) {
            role = "buyer";
        } else {
            role = "seller";
        }

        List<String> authorities = Arrays.asList(role);

        return authorities;
    }
}
