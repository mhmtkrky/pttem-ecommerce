package com.pttem.ecommerce.purchasingmanagement.controller;

import com.pttem.ecommerce.purchasingmanagement.entity.PurchasingEntity;
import com.pttem.ecommerce.purchasingmanagement.model.AddProductToPurchasingModel;
import com.pttem.ecommerce.purchasingmanagement.service.PurchasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest/purchasing")
public class PurchasingController {

    private final PurchasingService service;

    @Autowired
    public PurchasingController(PurchasingService service) {
        this.service = service;
    }


    @GetMapping("")
    private void getList(){
        service.getList();
    }

    @GetMapping("/{uuid}")
    private List<PurchasingEntity> getUserUUID(@PathVariable Long uuid){
        return service.getUserUUID(uuid);
    }

    @PostMapping("/add")
    private PurchasingEntity add(@Valid @RequestBody AddProductToPurchasingModel model){
        return service.addProduct(model);
    }
    @PostMapping("/remove")
    private PurchasingEntity remove(@Valid @RequestBody AddProductToPurchasingModel model){
        return service.removeProduct(model);
    }
    @GetMapping("/complete/{id}")
    private PurchasingEntity complete(@PathVariable Long id){
        return service.complete(id);
    }
}
