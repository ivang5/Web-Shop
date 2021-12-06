package com.ivang.webshop.controller;

import java.net.URI;
import java.util.List;

import com.ivang.webshop.dto.AdminDTO;
import com.ivang.webshop.dto.BuyerDTO;
import com.ivang.webshop.dto.SellerDTO;
import com.ivang.webshop.entity.Admin;
import com.ivang.webshop.entity.Buyer;
import com.ivang.webshop.entity.Seller;
import com.ivang.webshop.service.AdminServiceInterface;
import com.ivang.webshop.service.BuyerServiceInterface;
import com.ivang.webshop.service.SellerServiceInterface;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping(value = "shop/users")
@RequiredArgsConstructor
public class UserController {
    
    private final AdminServiceInterface adminService;
    private final BuyerServiceInterface buyerService;
    private final SellerServiceInterface sellerService;

    @GetMapping(value = "/admin")
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        // return new ResponseEntity<List<AdminDTO>>(adminService.findAll(), HttpStatus.OK);
        return ResponseEntity.ok().body(adminService.findAll());
    }

    @GetMapping(value = "/buyer")
    public ResponseEntity<List<BuyerDTO>> getAllBuyers() {
        // return new ResponseEntity<List<BuyerDTO>>(buyerService.findAll(), HttpStatus.OK);
        return ResponseEntity.ok().body(buyerService.findAll());
    }

    @GetMapping(value = "/seller")
    public ResponseEntity<List<SellerDTO>> getAllSellers() {
        // return new ResponseEntity<List<SellerDTO>>(sellerService.findAll(), HttpStatus.OK);
        return ResponseEntity.ok().body(sellerService.findAll());
    }

    @GetMapping(value = "/admin/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable("id") Long id) {
        Admin admin = adminService.findOne(id);

        if (admin == null) {
            // return new ResponseEntity<AdminDTO>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }

        // return new ResponseEntity<AdminDTO>(new AdminDTO(admin), HttpStatus.OK);
        return ResponseEntity.ok().body(new AdminDTO(admin));
    }

    @GetMapping(value = "/buyer/{id}")
    public ResponseEntity<BuyerDTO> getBuyerById(@PathVariable("id") Long id) {
        Buyer buyer = buyerService.findOne(id);

        if (buyer == null) {
            // return new ResponseEntity<BuyerDTO>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }

        // return new ResponseEntity<BuyerDTO>(new BuyerDTO(buyer), HttpStatus.OK);
        return ResponseEntity.ok().body(new BuyerDTO(buyer));
    }

    @GetMapping(value = "/seller/{id}")
    public ResponseEntity<SellerDTO> getSellerById(@PathVariable("id") Long id) {
        Seller seller = sellerService.findOne(id);

        if (seller == null) {
            // return new ResponseEntity<SellerDTO>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }

        // return new ResponseEntity<SellerDTO>(new SellerDTO(seller), HttpStatus.OK);
        return ResponseEntity.ok().body(new SellerDTO(seller));
    }

    // @PostMapping(value = "/admin")
    // public ResponseEntity<?> createAdmin(@RequestBody AdminDTO admin) {
    //     try {
    //         adminService.save(admin);
    //     } catch (Exception e) {
    //         // return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
    //         return ResponseEntity.badRequest().body("Bad request, something went wrong");
    //     }

    //     // return new ResponseEntity<>(HttpStatus.CREATED);
    //     URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/shop/users/admin").toUriString());
    //     return ResponseEntity.created(uri).build();
    // }

    @PostMapping(value = "/buyer")
    public ResponseEntity<?> createBuyer(@RequestBody BuyerDTO buyer) {
        try {
            buyerService.save(buyer);
        } catch (Exception e) {
            // return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body("Bad request, something went wrong");
        }

        // return new ResponseEntity<>(HttpStatus.CREATED);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/shop/users/buyer").toUriString());
        return ResponseEntity.created(uri).build();
    }

    @PostMapping(value = "/seller")
    public ResponseEntity<?> createSeller(@RequestBody SellerDTO seller) {
        try {
            sellerService.save(seller);
        } catch (Exception e) {
            // return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body("Bad request, something went wrong");
        }

        // return new ResponseEntity<>(HttpStatus.CREATED);
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

        // return new ResponseEntity<>(HttpStatus.OK);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/buyer")
    public ResponseEntity<?> updateBuyer(@RequestBody BuyerDTO buyer) {
        try {
            buyerService.update(buyer);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
        }

        // return new ResponseEntity<>(HttpStatus.OK);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/seller")
    public ResponseEntity<?> updateSeller(@RequestBody SellerDTO seller) {
        try {
            sellerService.update(seller);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
        }

        // return new ResponseEntity<>(HttpStatus.OK);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/admin")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        Admin admin = adminService.findOne(id);

        if (admin != null) {
            adminService.remove(id);
            // return new ResponseEntity<>(HttpStatus.OK);
            return ResponseEntity.ok().build();
        }

        // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/buyer")
    public ResponseEntity<Void> deleteBuyer(@PathVariable Long id) {
        Buyer buyer = buyerService.findOne(id);

        if (buyer != null) {
            buyerService.remove(id);
            // return new ResponseEntity<>(HttpStatus.OK);
            return ResponseEntity.ok().build();
        }

        // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/seller")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        Seller seller = sellerService.findOne(id);

        if (seller != null) {
            sellerService.remove(id);
            // return new ResponseEntity<>(HttpStatus.OK);
            return ResponseEntity.ok().build();
        }

        // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.notFound().build();
    }
}
