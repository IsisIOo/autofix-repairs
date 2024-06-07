package com.example.autofixrepairs.controller;

import com.example.autofixrepairs.entity.Repair;
import com.example.autofixrepairs.service.DetailService;
import com.example.autofixrepairs.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repair")
public class RepairController {
    @Autowired
    RepairService repairService;
    @Autowired
    DetailService detailService;

    //todos los weas
    @GetMapping("/")
    //este obtiene todos los registros existentes
    public ResponseEntity<List<Repair>> getAllRepair() {
        List<Repair> recordHistory = repairService.getRecordRepository();
        return ResponseEntity.ok(recordHistory);
    }

    //uno solo
    @GetMapping("/repair-patent/{patent}")
    //recibe solo un registro
    public ResponseEntity<Repair> getOneRepairByPatent(@PathVariable String patent) {
        Repair recordHistory = repairService.getOneRecordRespository(patent);
        return ResponseEntity.ok(recordHistory);
    }

    @PostMapping("/")
    public ResponseEntity<Repair> saveRecord(@RequestBody Repair recordHistory) {
        Repair recordHistoryNew = repairService.saveRecord(recordHistory);
        return ResponseEntity.ok(recordHistoryNew);
    }

    @DeleteMapping("/repair-id/{id}")
    public ResponseEntity<Boolean> deleteRecordById(@PathVariable Long id) throws Exception {
        var isDeleted = repairService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/newRepair/")
    //este necesita, las fechas los descuentos, las recargas y el iva por separado
    public ResponseEntity<Repair> updateRecord(@RequestBody Repair rec){

        //Creamos una nueva entidad con new
        Repair repairHistory = new Repair();

        //Conseguimos los costos para colocarlo en el auto
        double totalAmount = detailService.getCostbyRepair(rec);

        //IVA
        double iva = detailService.IVASOLO(totalAmount);

        //recargos
        double recargos = detailService.getCostRecharges( totalAmount, rec.getPatent());

        //descuentos
        double descuentos = detailService.DescuentosSegunHora1(rec.getPatent(), totalAmount);

        //Vamos a colocar cada uno de los componentes en el nuevo auto
        repairHistory.setId(rec.getId());

        //FECHAS CLIENTE
        repairHistory.setAdmissionHour(rec.getAdmissionHour());
        repairHistory.setAdmissionDateDayName(rec.getAdmissionDateDayName());
        repairHistory.setAdmissionDateDay(rec.getAdmissionDateDay());
        repairHistory.setAdmissionDateMonth(rec.getAdmissionDateMonth());

        //repairHistory.setRepairType(rec.getRepairType());

        //FECHAS TALLER
        repairHistory.setDepartureDateDay(rec.getDepartureDateDay());
        repairHistory.setDepartureDateMonth(rec.getDepartureDateMonth());
        repairHistory.setDepartureHour(rec.getDepartureHour());

        //FECHAS SALIDA CLIENTE
        repairHistory.setClientDateDay(rec.getClientDateDay());
        repairHistory.setClientDateMonth(rec.getClientDateMonth());
        repairHistory.setClientHour(rec.getClientHour());

        repairHistory.setTotalAmount(totalAmount);

        repairHistory.setTotalDiscounts(descuentos);
        repairHistory.setTotalIva(iva);
        repairHistory.setTotalRecharges(recargos);

        Repair repairHistoryNew = repairService.saveRecord(repairHistory);
        return ResponseEntity.ok(repairHistoryNew);
    }

    @GetMapping("/byCar/{}")
    public ResponseEntity<List<Repair>> getByStudentId(@PathVariable("carId") Long carId) {
        List<Repair> repairs = repairService.byCarId(carId);
        return ResponseEntity.ok(repairs);
    }






}