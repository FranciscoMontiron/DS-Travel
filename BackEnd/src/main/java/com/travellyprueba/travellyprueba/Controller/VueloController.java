    
package com.travellyprueba.travellyprueba.Controller;

import com.travellyprueba.travellyprueba.Entity.Vuelo;
import com.travellyprueba.travellyprueba.Repository.VueloRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/vuelos")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class VueloController {
    
    @Autowired VueloRepository vueloRepository;
    
            
    @GetMapping("/listar")
    public ResponseEntity<Collection<Vuelo>> listarVuelos(){
            return new ResponseEntity<>(vueloRepository.findAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vuelo> obtenerVuelo(@PathVariable Integer id){
            Vuelo vuelo = vueloRepository.findById(id).orElseThrow();

            if(vuelo != null) {
                    return new ResponseEntity<>(vueloRepository.findById(id).orElseThrow(),HttpStatus.OK);
            }else {
                    return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity<?> guardarVuelo(@RequestBody Vuelo vuelo){
            return new ResponseEntity<>(vueloRepository.save(vuelo),HttpStatus.CREATED);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVuelo(@PathVariable Integer id){
            vueloRepository.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Vuelo> editarVuelo(@Valid @RequestBody Vuelo vuelo, @PathVariable Integer id){
        Optional<Vuelo> reservaOptional = vueloRepository.findById(id);
        if(!reservaOptional.isPresent()){
                return ResponseEntity.unprocessableEntity().build();
        }
        vuelo.setId(reservaOptional.get().getId());
        vueloRepository.save(vuelo);

        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/traerVuelosFiltrados/{fecha}/{origen}/{destino}")
    public ResponseEntity<List<Vuelo>> getFlights(@PathVariable String fecha,@PathVariable String origen, @PathVariable String destino) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    try {
        Date date = formatter.parse(fecha);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new ResponseEntity<>(vueloRepository.findByOriginAndDate(calendar,origen,destino), HttpStatus.OK);
    } catch (ParseException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    }
    
    
}
