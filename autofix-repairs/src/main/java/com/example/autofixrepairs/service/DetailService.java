package com.example.autofixrepairs.service;

import com.example.autofixrepairs.entity.Details;
import com.example.autofixrepairs.entity.Repair;
import com.example.autofixrepairs.model.Car;
import com.example.autofixrepairs.model.Descuento;
import com.example.autofixrepairs.model.RepairList;
import com.example.autofixrepairs.repository.DetailsRepository;
import com.example.autofixrepairs.repository.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@Service
public class DetailService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DetailsRepository detailsRepository;

    @Autowired
    RepairRepository repairRepository;

    public Details saveDetail(Details detalle){
        return detailsRepository.save(detalle);
    }

    public Details getOneDetail(String patent){
        return detailsRepository.findByPatentDetails(patent);
    }


    public Car getCar(String patent) {
        Car car = restTemplate.getForObject("http://autofix-car/api/car/carpatent/" + patent, Car.class);
        return car;
    }

    public Descuento saveDescuento(Descuento descuento){
        return restTemplate.postForObject("http://descuento/api/descuento/", descuento, Descuento.class);
    }

    //para obtener los descuentos, la verdad solo será uno pero no tenia como hacerl un get 1
    public List<Descuento> getDescuentoentity() {
        ResponseEntity<List<Descuento>> response = restTemplate.exchange(
                "http://descuento/api/descuento/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Descuento>>() {
                }
        );
        List<Descuento> descuentos = response.getBody();
        return descuentos;
    }

    public RepairList getPriceRepairs(String repair) {
        RepairList price = restTemplate.getForObject("http://autofix-list-repair/api/repair-list/by-repair/" + repair, RepairList.class);
        return price;
    }

    //saca el precio de la reparacion sin recargos ni descuentos
    public double precioSegunReparacionyMotor(Repair rec) {
        double total_price = 0;
        String patente_auto = rec.getPatent(); //obtiene el id de un repair. con el id de repair lo
        // busca en details y desde details se recupera la patente y la demas info del auto

        String motor = getCar(patente_auto).getMotorType();
        System.out.println(motor);

        //obtengo el id de repair, a partir de ese id hago la funcion getdetailsbyid y
        // puedo obtener las reparaciones que se le hicieron al auto
        String repairtype = rec.getRepairType();

        //esto es mas que nada para sacar la cantidad de reparaciones, el tamano del for
        String[] repairArray = repairtype.split(",");
        List<String> repairList = Arrays.asList(repairArray);


        System.out.println(repairtype); //entrega raparacines

        //esto esta debido a que no se puede usar la funcion para buscar la reparacion con el string compelto cuando son mas de una reparacion
        for(int i=0; i<repairList.size(); i++){
            System.out.println(repairList.get(i)); //PARA SABER SI LO TOMA INDIVIDUAL

            if (motor.toLowerCase().equals("gasolina")) {
                if (repairList.get(i).toLowerCase().contains("reparaciones del sistema de frenos")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();
                    System.out.println("el precio de las reparaciones de frenos es:" + getPriceRepairs(repairList.get(i)).getGasolineAmount());
                }
                if (repairList.get(i).toLowerCase().contains("servicio del sistema de refrigeración")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones del motor")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();

                }
                if (repairList.get(i).toLowerCase().contains("reparaciones de la transmisión")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount(); //4
                }
                if (repairList.get(i).toLowerCase().contains("reparacion del sistema electrico")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones del sistema de escape")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparacion de neumaticos y ruedas")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones de la suspension y la direccion")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparacion del sistema de aire acondicionado y calefaccion")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount(); //9
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones del sistema de combustible")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparacion y reemplazo del parabrisas y cristales")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getGasolineAmount();
                }
            }

            if (motor.toLowerCase().equals("diesel")) {
                if (repairList.get(i).toLowerCase().contains("reparaciones del sistema de frenos")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount();
                }
                if (repairList.get(i).toLowerCase().contains("servicio del sistema de refrigeración")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount(); //2
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones del motor")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount(); //3
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones de la transmisión")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount(); //4
                }
                if (repairList.get(i).toLowerCase().contains("reparacion del sistema electrico")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount();//5
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones del sistema de escape")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount(); //6
                }
                if (repairList.get(i).toLowerCase().contains("reparacion de neumaticos y ruedas")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount(); //7
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones de la suspension y la direccion")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount(); //8
                }
                if (repairList.get(i).toLowerCase().contains("reparacion del sistema de aire acondicionado y calefaccion")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount(); //9
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones del sistema de combustible")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount(); //10
                }
                if (repairList.get(i).toLowerCase().contains("reparacion y reemplazo del parabrisas y cristales")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getDieselAmount();
                }
            }

            if (motor.toLowerCase().equals("hibrido")) {
                if (repairList.get(i).toLowerCase().contains("reparaciones del sistema de frenos")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
                if (repairList.get(i).toLowerCase().contains("servicio del sistema de refrigeración")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones del motor")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones de la transmisión")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount(); //4
                }
                if (repairList.get(i).toLowerCase().contains("reparacion del sistema electrico")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones del sistema de escape")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparacion de neumaticos y ruedas")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones de la suspension y la direccion")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparacion del sistema de aire acondicionado y calefaccion")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount(); //9
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones del sistema de combustible")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparacion y reemplazo del parabrisas y cristales")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getHibridAmount();
                }
            }

            if (motor.toLowerCase().equals("electrico")) {
                if (repairList.get(i).toLowerCase().contains("reparaciones del sistema de frenos")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount();
                }
                if (repairList.get(i).toLowerCase().contains("servicio del sistema de refrigeración")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones del motor")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones de la transmisión")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount();//4
                }
                if (repairList.get(i).toLowerCase().contains("reparacion del sistema electrico")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones del sistema de escape")) {
                    total_price = total_price +0;
                }
                if (repairList.get(i).toLowerCase().contains("reparacion de neumaticos y ruedas")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones de la suspension y la direccion")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount();
                }
                if (repairList.get(i).toLowerCase().contains("reparacion del sistema de aire acondicionado y calefaccion")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount(); //9
                }
                if (repairList.get(i).toLowerCase().contains("reparaciones del sistema de combustible")) {
                    total_price = total_price ;
                }
                if (repairList.get(i).toLowerCase().contains("reparacion y reemplazo del parabrisas y cristales")) {
                    total_price = total_price + getPriceRepairs(repairList.get(i)).getElectricAmount();
                }
            }
        }
            System.out.println("el precio total sin nada. " + total_price);
            return total_price;
    }


    //----------------IVA----------------
    //saca solo el iva para hacerle set
    public double IVASOLO(double total_price){
        double iva = total_price * 0.19;
        return iva;
    }


    //costo total

    //funcion donde debe entrar el historial con todo igual, solo cambiando eso
    public double getCostbyRepair(Repair rec) {
        double total_price1 = 0;

        double total_price = precioSegunReparacionyMotor(rec);
        double iva = IVASOLO(total_price); //le saca el iva al costo original
        double descuento1 = DescuentosSegunHora1(rec, total_price);
        double descuento2 = Descuentoporcantidadreparaciones1(rec, total_price);
        //total_price = DescuentoSegunMarca(patent, total_price);
        //comentada el descuento segun marca porque espero usar essa funcion como un boton
        double recargo1 = recargoPorAtraso1(rec, total_price);
        double recargo2 = RecargoPorKilometraje1(rec, total_price);
        double recargo3 = recargoPorAntiguedad1(rec, total_price);
        //el calculo es recargos al precio normal, descuento al precio normal + ivasolo
        total_price1 = total_price + (recargo1 + recargo2 + recargo3 ) + iva - (descuento1+ descuento2);
        System.out.println("total"+total_price1);
        System.out.println("iva solo:" + iva);
        System.out.println("descuento1 segun hora:" + descuento1);
        System.out.println("descuento2 cantidad rep:" + descuento1);
        System.out.println("recargo1 atraso:" + recargo1);
        System.out.println("recargo2 km:" + recargo2);
        System.out.println("recargo3 antiguedad:" + recargo3);
        System.out.println("precio normal:"+ total_price);
        return total_price1;
    }


    //descuento y recargos totales por separado
    public double getCostDiscounts(double totalAmount, Repair rec){
        double discounts = 0;
        double discounts1 = DescuentosSegunHora1(rec, totalAmount);
        System.out.println("descuento por hora de llegada: "+ discounts1);
        double discounts2 = Descuentoporcantidadreparaciones1(rec, totalAmount);
        System.out.println("descuento por cantidad de reparaciones: "+ discounts2);
        discounts = discounts1 + discounts2 ;
        return discounts;
    }

    public double getCostRecharges(double totalAmount, Repair rec){
        //funcion para sacar los recargos por separado, que sera colocado en repair.
        double recharges = 0;
        System.out.println(totalAmount);
        String patent= rec.getPatent();
        double recharges1 = recargoPorAtraso1(rec, totalAmount);
        System.out.println("recargo por atraso  1 es:" + recharges1);
        double recharges2 = RecargoPorKilometraje1(rec, totalAmount)+ recharges;
        System.out.println("recargo por atraso  2 es:" + recharges2);
        double recharges3 = recargoPorAntiguedad1(rec, totalAmount) + recharges;
        System.out.println("recargo por atraso  3 es:" + recharges3);
        recharges = recharges1 + recharges2 + recharges3;
        return recharges;
    }

//---------------COSAS INDIVIDUALES-----------
    //--------------recargos---------------
    public double recargoPorAntiguedad1(Repair rec, double total_price) {
        //recargo por antiguedad
        int year_car = getCar(rec.getPatent()).getProductionYear();
        double total_price_year = 0;
        String type1 = getCar(rec.getPatent()).getType();
        if (type1.toLowerCase().equals("sedan") || type1.toLowerCase().equals("hatchback")) {
            if ((2024 - year_car) <= 5) {
                System.out.println("No se aplicó recargo por antiguedad bajo 5 años");
            }

            if ((2024 - year_car) >= 6 && (2024 - year_car) <= 10) {
                total_price_year = total_price * 0.05;
            }

            if ((2024 - year_car) >= 11 && (2024 - year_car) <= 15) {
                total_price_year = total_price * 0.09;
            }

            if ((2024 - year_car) >= 16) {
                total_price_year = total_price * 0.15;
            }
        }

        if (type1.toLowerCase().equals("suv") || type1.toLowerCase().equals("pickup") || type1.toLowerCase().equals("furgoneta")) {
            if ((2024 - year_car) <= 5) {
                System.out.println("No se aplicó recargo por antiguedad bajo 5 años");
            }
            if ((2024 - year_car) >= 6 && (2024 - year_car) <= 10) {
                total_price_year = total_price * 0.07;
            }
            if ((2024 - year_car) >= 11 && (2024 - year_car) <= 15) {
                total_price_year = total_price * 0.11;
            }
            if ((2024 - year_car) >= 16) {
                total_price_year = total_price * 0.2;
            }
        }
        System.out.println("el recargo por antiguedad es:" + total_price_year);
        return total_price_year;
    }

    public double RecargoPorKilometraje1(Repair rec, double total_price) {
        //recargo por kilometraje
        double total_price_km=0;
        String type1 = getCar(rec.getPatent()).getType();
        int km = getCar(rec.getPatent()).getKilometers();
        if (type1.toLowerCase().equals("sedan") || type1.toLowerCase().equals("hatchback")) {
            if (km <= 5000) {
                System.out.println("No se aplicó recargo por kilometraje bajo 5000");
            }
            if (5001 < km && km <= 12000) {
                total_price_km = total_price * 0.03;
            }
            if (12001 < km && km <= 25000) {
                total_price_km = total_price * 0.07;
            }
            if (25001 < km && km <= 40000) {
                total_price_km = total_price * 0.12;
            }
            if (40000 < km) {
                total_price_km = total_price * 0.2;
            }
        }

        if (type1.toLowerCase().equals("suv") || type1.toLowerCase().equals("pickup") || type1.toLowerCase().equals("furgoneta")) {
            if (km < 5000) {
                System.out.println("No se aplicó recargo por kilometraje bajo 5000 11111");
            }
            if (5001 < km && km < 12000) {
                total_price_km = total_price * 0.05;
            }
            if (12001 < km && km < 25000) {
                total_price_km = total_price * 0.09;
            }
            if (25001 < km && km < 40000) {
                total_price_km = total_price * 0.12;
            }
            if (40000 < km) {
                total_price_km = total_price * 0.2;
            }
        }

        System.out.println("recargo km eeeesss :" + total_price_km);
        return total_price_km;
    }

    public double recargoPorAtraso1(Repair rec, double total_price) {
        double recargo_total =0;

        //fechas de retiro indicadas por el taller
        int dia_retiro_taller = rec.getDepartureDateDay();
        int mes_retiro_taller = rec.getDepartureDateMonth();

        //fecha retirada por el cliente
        int dia_retiro_cliente = rec.getClientDateDay();
        int mes_retiro_cliente = rec.getClientDateMonth();

        //si el retiro del cliente es mayor al retiro del taller es pq está atrasado por dias
        if ((dia_retiro_cliente - dia_retiro_taller) > 0 && mes_retiro_cliente == mes_retiro_taller) {
            int retraso = dia_retiro_cliente - dia_retiro_taller;

            //corresponde a la cantidad de dias de retraso por 5% de cada dia que se demoro
            recargo_total = retraso * 0.05 * total_price;
            System.out.println("el cliente esta retrasado por " + retraso + "DIAS y su recargo es:" + recargo_total );
            return recargo_total;

        }
        //atrasado por meses y dias
        if ((dia_retiro_cliente - dia_retiro_taller) > 0 && (mes_retiro_cliente - mes_retiro_taller) > 0) {
            int retraso = dia_retiro_cliente - dia_retiro_taller;
            int retraso_meses = mes_retiro_cliente - mes_retiro_taller;
            System.out.println("el cliente esta retrasado por " + retraso + "dias y " + retraso_meses + "meses");
            //voy a considerar que los meses solo tienen 30 dias

            //retraso de dias simples
            double recargo_dias = retraso * 0.05 * total_price;
            //por ejemplo, si son 3 meses de diferencia, para sacar los dias seria 3 *30 =90 dias y cada dia tiene 0.05 de recargo
            double recargo_meses = retraso_meses * 30 * 0.05 * total_price;

            recargo_total = recargo_dias + recargo_meses;
            return recargo_total;
        }
        return recargo_total;
    }

    //descuentos
    public double DescuentosSegunHora1(Repair rec, double total_price) {
        // ahora veo si aplica el descuento segun la hora de ingreso
        //agregar dia
        double total_price_hour = 0;
        String patent = rec.getPatent();
        int hour = rec.getAdmissionHour();//hora para determinar si se le aplica descuento por hora de llegada
        String day = rec.getAdmissionDateDayName().toLowerCase();//dia para determinar si se le aplica descuento por dia de llegada
        if (9 < hour && hour < 12 ) {//agregar que se entre lunes y jueves
            if(day.equals("jueves")  ||  day.equals("lunes")) {
                total_price_hour = total_price * 0.1;
                System.out.println("El descuento aplicado por la hora: " + total_price_hour);
                return total_price_hour;
            }
        }
        return total_price_hour;
    }

    public double Descuentoporcantidadreparaciones1(Repair rec, double total_price) {
        //recargo por numero de reparaciones
        double total_price_km=0;
        String type1 = getCar(rec.getPatent()).getMotorType();
        String patent = rec.getPatent();
        int cantidadReparaciones = repairRepository.findByPatentRepairs(rec.getPatent()).size();
        System.out.println("cantidad de reparaciones de " + patent +" es "+cantidadReparaciones);

        if (type1.toLowerCase().equals("gasolina")) {
            if (cantidadReparaciones == 1 ||cantidadReparaciones == 2) {
                total_price_km = total_price * 0.05;
                System.out.println("se aplico descuento");
            }
            if (3 <= cantidadReparaciones && cantidadReparaciones <= 5) {
                total_price_km = total_price * 0.1;
            }
            if (6 <= cantidadReparaciones && cantidadReparaciones <= 9) {
                total_price_km = total_price * 0.15;
            }
            if (10 <= cantidadReparaciones) {
                total_price_km = total_price * 0.2;
            }
        }

        if (type1.toLowerCase().equals("diesel")) {
            if (cantidadReparaciones == 1 ||cantidadReparaciones == 2) {
                total_price_km = total_price * 0.07;
            }
            if (3 <= cantidadReparaciones && cantidadReparaciones <= 5) {
                total_price_km = total_price * 0.12;
            }
            if (6 <= cantidadReparaciones && cantidadReparaciones <= 9) {
                total_price_km = total_price * 0.17;
            }
            if (10 <= cantidadReparaciones) {
                total_price_km = total_price * 0.22;
            }
        }

        if (type1.toLowerCase().equals("hibrido")) {
            if (cantidadReparaciones == 1 ||cantidadReparaciones == 2) {
                total_price_km = total_price*0.1;
            }
            if (3 <= cantidadReparaciones && cantidadReparaciones <= 5) {
                total_price_km = total_price * 0.15;
            }
            if (6 <= cantidadReparaciones && cantidadReparaciones <= 9) {
                total_price_km = total_price * 0.2;
            }
            if (10 <= cantidadReparaciones) {
                total_price_km = total_price * 0.25;
            }
        }

        if (type1.toLowerCase().equals("electrico")) {
            if (cantidadReparaciones == 1 ||cantidadReparaciones == 2) {
                total_price_km = total_price*0.08;
            }
            if (3 <= cantidadReparaciones && cantidadReparaciones <= 5) {
                total_price_km = total_price * 0.13;
            }
            if (6 <= cantidadReparaciones && cantidadReparaciones <= 9) {
                total_price_km = total_price * 0.18;
            }
            if (10 <= cantidadReparaciones) {
                total_price_km = total_price * 0.23;
            }
        }
        System.out.println("el monto que ingreso a la funcion de descuento por reparaciones: " + total_price);
        System.out.println("el descuento por reparaciones es:" + total_price_km);
        return total_price_km;
    }

    public double DescuentoSegunMarca1(String patent, double total_price) {

        List <Descuento> descuento = getDescuentoentity();
        Descuento descuento1 = descuento.get(0);

        //descuento segun marca
        double total_price_brand = 0;
        String brand = getCar(patent).getBrand();
        if (brand.toLowerCase().equals("toyota") && descuento1.getToyotanumber()>=1) {
            total_price_brand = descuento1.getToyotaAmount();
            descuento1.setToyotanumber(descuento1.getToyotanumber() - 1);
            System.out.println("El descuento aplicado por la marca Toyota: " + total_price_brand + "intentos restantes toyota : "+ descuento1.getToyotanumber());
        }
        if (brand.toLowerCase().equals("ford") && descuento1.getFordnumber()>=1) {
            total_price_brand = descuento1.getFordAmount();
            descuento1.setFordnumber(descuento1.getFordnumber() - 1);
            System.out.println("El descuento aplicado por la marca Ford: " + total_price_brand + "intentos restantes ford : "+ descuento1.getFordnumber());
        }
        if (brand.toLowerCase().equals("hyundai") && descuento1.getHyundainumber()>=1) {
            total_price_brand = descuento1.getHyundaiAmount();
            descuento1.setHyundainumber(descuento1.getHyundainumber() - 1);
            System.out.println("El descuento aplicado por la marca Hyundai: " + total_price_brand + "intentos restantes hyundai : "+ descuento1.getHyundainumber());
        }
        if (brand.toLowerCase().equals("honda") && descuento1.getHondanumber()>=1) {
            total_price_brand = descuento1.getHondaAmount();
            descuento1.setHondanumber(descuento1.getHondanumber() - 1);
            System.out.println("El descuento aplicado por la marca Honda: " + total_price_brand + "intentos restantes honda : "+ descuento1.getHondanumber());
        }

        saveDescuento(descuento1);
        System.out.println("Precio total de descuento por marca: " + total_price_brand );
        return total_price_brand;
    }

}
