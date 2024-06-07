package com.example.autofixrepairs.controller;

import com.example.autofixrepairs.entity.Details;
import com.example.autofixrepairs.entity.Repair;
import com.example.autofixrepairs.repository.RepairRepository;
import com.example.autofixrepairs.service.DetailService;
import com.example.autofixrepairs.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detail")
public class DetailController {
    @Autowired
    RepairService repairService;
    @Autowired
    DetailService detailService;
    @Autowired
    RepairRepository repairRepository;

    @PostMapping("/")
    public ResponseEntity<Details> saveRecord(@RequestBody Details detalle) {

        //recupero el repair que tenga la misma patente para poder colocar el tiempo de admision
        Details recordHistoryNew = detailService.saveDetail(detalle);

        return ResponseEntity.ok(recordHistoryNew);
    }

    @PostMapping("/newDetail/")
    public ResponseEntity<Details> updateRecord(@RequestBody Details detail){
        //recuperando el rec de la patente
        Repair rec = repairRepository.findByPatentOne(detail.getPatent());
        //datos que tomo como fijos
        int hora = repairRepository.findByPatentOne(detail.getPatent()).getAdmissionHour();
        int dia = repairRepository.findByPatentOne(detail.getPatent()).getAdmissionDateDay();
        int mes = repairRepository.findByPatentOne(detail.getPatent()).getAdmissionDateMonth();


        //Creamos una nueva entidad con new
        Details newDetail = new Details();

        //Conseguimos los costos para colocarlo en el auto
        double totalAmount = detailService.getCostbyRepair(rec);

        //Vamos a colocar cada uno de los componentes en el nuevo auto
        newDetail.setPatent(detail.getPatent());
        newDetail.setId(detail.getId());
        newDetail.setRepairHour(hora);
        newDetail.setRepairDay(dia);
        newDetail.setRepairMonth(mes);
        newDetail.setRepairType(detail.getRepairType());
        newDetail.setTotalAmount(totalAmount);

        Details newnewdetails = detailService.saveDetail(newDetail);
        return ResponseEntity.ok(newnewdetails);
    }

//obtener solo uno, el que necesito en la pestaña de detalles
    @GetMapping("/patent1/{patent}")
    //recibe solo un registro
    public ResponseEntity<Details> getOneDetailByPatent(@PathVariable String patent) {
        Details detail = detailService.getOneDetail(patent);
        return ResponseEntity.ok(detail);
    }

}
